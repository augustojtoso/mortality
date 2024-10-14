package com.humanit.recruiting.mortality.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.humanit.recruiting.mortality.application.MortalityRateDto;
import com.humanit.recruiting.mortality.application.YearlyMortalityRateDto;
import com.humanit.recruiting.mortality.infra.MortalityRate;
import com.humanit.recruiting.mortality.infra.MortalityRateRepository;
import com.humanit.recruiting.mortality.infra.YearCountryId;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MortalitySteps {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private Holder holder;
    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MortalityRateRepository mortalityRateRepository;

    final String BASE_URL = "/MortalityRates/years/";
    final String COUNTRIES = "/countries";


    @Before
    public void cleanDatabase() {
        mortalityRateRepository.deleteAll();
    }

    @DataTableType
    public YearlyMortalityRateDto yearlyMortalityRateDtoEntry(Map<String, String> mapEntry) {
        return new YearlyMortalityRateDto(
                Integer.valueOf(mapEntry.get("year")),
                Map.of(mapEntry.get("country"), new MortalityRateDto(
                        new BigDecimal(mapEntry.get("rateMasc")),
                        new BigDecimal(mapEntry.get("rateFem")))
                )
        );
    }

    @DataTableType
    public MortalityRate mortalityRateEntry(Map<String, String> entry) {
        MortalityRate mortalityRate = new MortalityRate();
        mortalityRate.id = yearCountryId(entry);
        mortalityRate.maleRate = new BigDecimal(entry.get("rateMasc"));
        mortalityRate.femaleRate = new BigDecimal(entry.get("rateFem"));
        mortalityRate.malePopulation = BigInteger.TEN;
        mortalityRate.femalePopulation = BigInteger.TEN;
        return mortalityRate;
    }

    @DataTableType
    public MortalityRateDto mortalityRateDto(Map<String, String> entry) {
        return new MortalityRateDto(new BigDecimal(entry.get("rateMasc")), new BigDecimal(entry.get("rateFem")));
    }

    @DataTableType
    private YearCountryId yearCountryId(Map<String, String> entry) {
        var id = new YearCountryId();
        id.yearOfRate = Integer.valueOf(entry.get("year"));
        id.countryCode = entry.get("country");
        return id;
    }

    @When("I request the mortality rates for the year {int}")
    public void iRequestTheMortalityRatesForTheYear(int year) {
        callApi(countriesFromYearsUrl(year), HttpMethod.GET, null);

    }

    private String countriesFromYearsUrl(int year) {
        return withYear(year) + COUNTRIES;
    }

    private String withYear(int year) {
        return BASE_URL + year;
    }

    protected <T> void callApi(String url, HttpMethod method, T body) {
        final HttpEntity<T> requestEntity = new HttpEntity<>(body);
        callApiWithRequestEntity(url, method, requestEntity);
    }

    private <T> void callApiWithRequestEntity(String url, HttpMethod method, HttpEntity<T> requestEntity) {
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + url, method, requestEntity, String.class);
        holder.setStatusCode(response.getStatusCode());
        holder.setResponse(response.getBody());
    }

    @Then("I get a {string} status response")
    public void iGetAOKStatusResponse(String status) {
        Assertions.assertThat(holder.getStatusCode())
                .withFailMessage("Expected status %s but got %s and message: %s", status, holder.getStatusCode(), holder.getResponse())
                .isEqualTo(HttpStatus.valueOf(status));

    }

    @And("the response is:")
    public void theResponseIs(List<YearlyMortalityRateDto> expected) throws JsonProcessingException {
        var actual = objectMapper.readValue(holder.getResponse(), YearlyMortalityRateDto.class);
        var singleExpected = expected.stream().collect(Collectors.toMap(YearlyMortalityRateDto::yearOfRate, Function.identity(), (a, b) -> {
            Map<String, MortalityRateDto> concatMortalityRate = new HashMap<>(a.mortalityRates());
            concatMortalityRate.putAll(b.mortalityRates());
            return new YearlyMortalityRateDto(a.yearOfRate(), concatMortalityRate);
        })).entrySet().stream().findFirst().get().getValue();
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .isEqualTo(singleExpected);
    }

    @Given("I have the registered mortality rates:")
    public void iHaveTheRegisteredMortalityRatesForTheYear(List<MortalityRate> dataset) {
        mortalityRateRepository.saveAll(dataset);
    }

    @When("I send new mortality rate for the year {int} country {string}")
    public void iSendNewMortalityRateForTheYearCountry(int year, String countrycode, MortalityRateDto mortalityRateDto) {
        callApi(countriesFromYearsUrl(year) + "/" + countrycode, HttpMethod.POST, mortalityRateDto);
    }

    @When("I send empty file for the year {int}")
    public void iSendEmptyFileForTheYear(int year) {
        callApiWithRequestEntity(countriesFromYearsUrl(year), HttpMethod.POST, null);

    }

    @When("I send {string} for the year {int}")
    public void iSendForTheYear(String fileName, int year) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        final var file = new ClassPathResource("files/" + fileName);
        body.add("csv", file);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + countriesFromYearsUrl(year), requestEntity, String.class);
        holder.setStatusCode(response.getStatusCode());
        holder.setResponse(response.getBody());
    }

    @And("error message is {string}")
    public void errorMessageIs(String errorMessage) {
        Assertions.assertThat(holder.getResponse()).contains(errorMessage);
    }
}


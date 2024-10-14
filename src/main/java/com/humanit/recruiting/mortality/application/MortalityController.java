package com.humanit.recruiting.mortality.application;

import com.humanit.recruiting.mortality.domain.MortalityRateService;
import com.humanit.recruiting.mortality.infra.MortalityRate;
import com.humanit.recruiting.mortality.infra.MortalityRateRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

@RestController
@Tag(name = "Mortality", description = "Operations related to mortality rates")
@RequestMapping(value = "MortalityRates/years/{yearOfRate}/countries")
@Validated
public class MortalityController {
    private final MortalityRateMapper mortalityMapper;
    private final MortalityRateRepository mortalityRepository;
    private final MortalityRateService mortalityRateService;

    @Autowired
    public MortalityController(MortalityRateMapper mortalityMapper, MortalityRateRepository mortalityRepository, MortalityRateService mortalityRateService) {
        this.mortalityMapper = mortalityMapper;
        this.mortalityRepository = mortalityRepository;
        this.mortalityRateService = mortalityRateService;
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid year format"),
            @ApiResponse(responseCode = "404", description = "Yearly mortality rate not found")
    })
    public YearlyMortalityRateDto getByYear(
            @PathVariable
            @Valid @Digits(integer = 4, fraction = 0, message = "Year must be exactly 4 digits long")
            Integer yearOfRate
    ) {
        final List<MortalityRate> byIdYearOfRate = mortalityRepository.findById_YearOfRate(yearOfRate);
        return mortalityMapper.toDto(byIdYearOfRate)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Yearly mortality rate not found"));
    }

    @PostMapping( consumes = MULTIPART_FORM_DATA)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid year format"),
            @ApiResponse(responseCode = "404", description = "Yearly mortality rate not found"),
            @ApiResponse(responseCode = "400", description = "Invalid country code")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public YearlyMortalityRateDto updateYear(
            @PathVariable @Valid @Digits(integer = 4, fraction = 0, message = "Year must be exactly 4 digits long")
            Integer yearOfRate,
            @RequestParam("csv") @NotNull final MultipartFile csv
    ) {
        mortalityRateService.update(yearOfRate, csv);
        return getByYear(yearOfRate);
    }

    @PostMapping(value = "/{countryCode}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid year format"),
            @ApiResponse(responseCode = "400", description = "Invalid country code")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public YearlyMortalityRateDto updateCountry(
            @PathVariable @Valid @Digits(integer = 4, fraction = 0, message = "Year must be exactly 4 digits long")
            Integer yearOfRate,
            @PathVariable @Valid @Size(min = 2, max = 2, message = "Country Code must be exactly 2 characters long")
            String countryCode,
            @RequestBody @Valid MortalityRateDto countryMortalityRate
    ) {
        mortalityRateService.update(yearOfRate, countryCode, countryMortalityRate);
        return getByYear(yearOfRate);
    }
}


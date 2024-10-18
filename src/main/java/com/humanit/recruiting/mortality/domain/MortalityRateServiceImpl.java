package com.humanit.recruiting.mortality.domain;

import com.humanit.recruiting.mortality.infra.MortalityRate;
import com.humanit.recruiting.mortality.infra.MortalityRateRepository;
import com.humanit.recruiting.mortality.application.MortalityRateDto;
import com.humanit.recruiting.mortality.application.MortalityRateMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

@Service
public class MortalityRateServiceImpl implements MortalityRateService {
    private final MortalityRateRepository mortalityRateRepository;
    private final TotalPopulationService totalPopulationService;
    private final MortalityRateMapper mortalityRateMapper;
    private final Validator validator;

    public MortalityRateServiceImpl(
            MortalityRateRepository mortalityRateRepository,
            TotalPopulationService totalPopulationService, MortalityRateMapper mortalityRateMapper, Validator validator) {
        this.mortalityRateRepository = mortalityRateRepository;
        this.totalPopulationService = totalPopulationService;
        this.mortalityRateMapper = mortalityRateMapper;
        this.validator = validator;
    }

    @Override
    public void update(Integer yearOfRate, String countryCode, MortalityRateDto countryMortalityRate) {
        MortalityRate entity = getEntity(yearOfRate, countryCode, countryMortalityRate);
        mortalityRateRepository.save(entity);
    }

    private MortalityRate getEntity(Integer yearOfRate, String countryCode, MortalityRateDto countryMortalityRate) {
        validCountryCode(countryCode);
        var population = totalPopulationService.getPopulation(yearOfRate, countryCode);
        return mortalityRateMapper.toEntity(yearOfRate, countryCode, countryMortalityRate, population);
    }

    private static void validCountryCode(String countryCode) {
        if (Arrays.stream(Locale.getISOCountries()).noneMatch(it -> it.equals(countryCode.toUpperCase()))) {
            throw new IllegalArgumentException(countryCode + " is an invalid country code for ISO 3166");
        }
    }

    @Override
    public void update(Integer yearOfRate, MultipartFile file)  {
        List<MortalityRate> mortalityRateList = fileToMortalityRates(yearOfRate, file);
        mortalityRateRepository.deleteById_YearOfRate(yearOfRate);
        mortalityRateRepository.saveAll(mortalityRateList);
    }

    private List<MortalityRate> fileToMortalityRates(Integer yearOfRate, MultipartFile file) {
        List<MortalityRate> mortalityRateList = new ArrayList<>(Collections.emptyList());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 3) {
                    String countryCode = columns[0].trim();
                    MortalityRateDto mortalityRateDto = new MortalityRateDto(
                            new BigDecimal(columns[1].trim()),
                            new BigDecimal(columns[2].trim()));
                    Set<ConstraintViolation<MortalityRateDto>> violations = validator.validate(mortalityRateDto);
                    if (!violations.isEmpty()) {
                        throw new ConstraintViolationException(violations);
                    }
                    mortalityRateList.add(getEntity(yearOfRate, countryCode, mortalityRateDto));
                } else {
                    throw new IllegalArgumentException("Invalid file format");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
        if (mortalityRateList.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }
        return mortalityRateList;
    }
}

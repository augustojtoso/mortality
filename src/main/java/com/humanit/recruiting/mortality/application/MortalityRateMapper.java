package com.humanit.recruiting.mortality.application;

import com.humanit.recruiting.mortality.domain.CountryPopulation;
import com.humanit.recruiting.mortality.infra.MortalityRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper
public abstract class MortalityRateMapper {
    public Optional<YearlyMortalityRateDto> toDto(List<MortalityRate> mortalityRateList) {
        return mortalityRateList
                .stream().findFirst()
                .map(first -> new YearlyMortalityRateDto(first.id.yearOfRate, toMortalityRateByCountry(mortalityRateList)));
    }

    private Map<String, MortalityRateDto> toMortalityRateByCountry(List<MortalityRate> mortalityRateList) {
        return mortalityRateList.stream().collect(Collectors.toMap(
                it -> it.id.countryCode,
                this::toCountryMortalityRateDto));
    }

    public abstract MortalityRateDto toCountryMortalityRateDto(MortalityRate mortalityRateList);

    @Mapping(target = "id.yearOfRate", source = "yearOfRate")
    @Mapping(target = "id.countryCode", source = "countryCode")
    public abstract MortalityRate toEntity(Integer yearOfRate, String countryCode, MortalityRateDto countryMortalityRate, CountryPopulation population);
}


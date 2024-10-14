package com.humanit.recruiting.mortality.application;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(name = "YearlyMortalityRate")
public record YearlyMortalityRateDto (
        @Schema(example = "2020")
        Integer yearOfRate,
        Map<String, MortalityRateDto> mortalityRates
) {}
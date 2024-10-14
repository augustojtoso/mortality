package com.humanit.recruiting.mortality.application;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;

import java.math.BigDecimal;

@Schema(name = "MortalityRate")
public record MortalityRateDto(
        @Schema(example = "4.50")
        @Digits(integer = 4, fraction = 2, message = "Rate must have 2 decimal places")
        @Max(value = 1000, message = "Rate must be less than 1000")
        BigDecimal maleRate,
        @Schema(example = "3.21")
        @Digits(integer = 4, fraction = 2, message = "Rate must have 2 decimal places")
        @Max(value = 1000, message = "Rate must be less than 1000")
        BigDecimal femaleRate) {
}

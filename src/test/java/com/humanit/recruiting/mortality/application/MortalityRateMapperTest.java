package com.humanit.recruiting.mortality.application;

import com.humanit.recruiting.mortality.domain.CountryPopulation;
import com.humanit.recruiting.mortality.infra.MortalityRate;
import com.humanit.recruiting.mortality.infra.YearCountryId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;


class MortalityRateMapperTest {
    MortalityRateMapper mapper = Mappers.getMapper(MortalityRateMapper.class);

    @Test
    void toDto() {
        var mortalityRate1 = new MortalityRate();
        var id1 = new YearCountryId();
        id1.countryCode = "PT";
        id1.yearOfRate = 2020;
        mortalityRate1.id = id1;
        mortalityRate1.femaleRate = BigDecimal.valueOf(2.44);
        mortalityRate1.femalePopulation = BigInteger.valueOf(1000);

        mortalityRate1.maleRate = BigDecimal.valueOf(3);
        mortalityRate1.malePopulation = BigInteger.valueOf(1000);

        var mortalityRate2 = new MortalityRate();
        var id2 = new YearCountryId();
        id2.countryCode = "SP";
        id2.yearOfRate = 2020;
        mortalityRate2.id = id2;
        mortalityRate2.femaleRate = BigDecimal.valueOf(1);
        mortalityRate2.femalePopulation = BigInteger.valueOf(1000);
        mortalityRate2.maleRate = BigDecimal.valueOf(1.5);
        mortalityRate2.malePopulation = BigInteger.valueOf(1000);


        Assertions.assertThat(mapper.toDto(listOf(mortalityRate1, mortalityRate2)).get())
                .usingRecursiveComparison()
                .isEqualTo(
                        new YearlyMortalityRateDto(2020,
                                Map.of(
                                        "PT", new MortalityRateDto(BigDecimal.valueOf(3), BigDecimal.valueOf(2.44)),
                                        "SP", new MortalityRateDto(BigDecimal.valueOf(1.5), BigDecimal.valueOf(1))
                                )
                        )
                );

    }

    @Test
    void toEntity() {
        var mortalityRateDto = new MortalityRateDto(BigDecimal.valueOf(3), BigDecimal.valueOf(2.44));
        var yearOfRate = 2020;
        var pt = "PT";
        var mortalityRate = mapper.toEntity(yearOfRate, pt, mortalityRateDto,
                new CountryPopulation(yearOfRate, pt, BigInteger.valueOf(1000), BigInteger.valueOf(1000)));
        Assertions.assertThat(mortalityRate.id.yearOfRate).isEqualTo(2020);
        Assertions.assertThat(mortalityRate.id.countryCode).isEqualTo("PT");
    }
}
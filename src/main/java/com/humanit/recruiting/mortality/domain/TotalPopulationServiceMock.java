package com.humanit.recruiting.mortality.domain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@Order(100)
public class TotalPopulationServiceMock implements TotalPopulationService {
    @Override
    public CountryPopulation getPopulation(Integer yearOfRate, String countryCode) {
        return new CountryPopulation(yearOfRate, countryCode, new BigInteger("1500"), new BigInteger("1000"));
    }
}

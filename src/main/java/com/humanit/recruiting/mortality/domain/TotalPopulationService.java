package com.humanit.recruiting.mortality.domain;

public interface TotalPopulationService {
    CountryPopulation getPopulation(Integer yearOfRate, String countryCode);
}
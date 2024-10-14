package com.humanit.recruiting.mortality.domain;

import java.math.BigInteger;

public record CountryPopulation(Integer year, String countryCode, BigInteger malePopulation, BigInteger femalePopulation) {}
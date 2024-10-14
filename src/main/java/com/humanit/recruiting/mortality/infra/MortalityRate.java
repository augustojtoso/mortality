package com.humanit.recruiting.mortality.infra;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "mortality_rate")
public class MortalityRate {
    @EmbeddedId
    public YearCountryId id;
    @Column(nullable = false)
    public BigDecimal maleRate;

    @Column(nullable = false)
    public BigDecimal femaleRate;

    @Column(nullable = false)
    public BigInteger malePopulation;

    @Column(nullable = false)
    public BigInteger femalePopulation;
}

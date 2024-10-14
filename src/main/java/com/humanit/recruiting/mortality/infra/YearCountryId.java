package com.humanit.recruiting.mortality.infra;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Embeddable
public class YearCountryId implements Serializable {
    public Integer yearOfRate;
    @Size(min = 2, max = 2, message = "Country code must be exactly 2 characters long")
    public String countryCode;

}

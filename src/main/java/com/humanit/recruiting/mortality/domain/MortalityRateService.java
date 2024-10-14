package com.humanit.recruiting.mortality.domain;

import com.humanit.recruiting.mortality.application.MortalityRateDto;
import org.springframework.web.multipart.MultipartFile;

public interface MortalityRateService {
    void update(Integer yearOfRate, String countryCode, MortalityRateDto countryMortalityRate);
    void update(Integer yearOfRate, MultipartFile file) ;
}


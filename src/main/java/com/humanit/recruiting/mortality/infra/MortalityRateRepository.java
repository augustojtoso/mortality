package com.humanit.recruiting.mortality.infra;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MortalityRateRepository extends CrudRepository<MortalityRate, YearCountryId> {
    List<MortalityRate> findById_YearOfRate(Integer yearOfRate);
    @Transactional
    void deleteById_YearOfRate(Integer yearOfRate);
}
package com.mci.br.nossavida.repository;

import com.mci.br.nossavida.model.Period;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface PeriodRepository extends CrudRepository<Period, Long> {
    Iterable<Period> findAllByOrderByEndDateDesc();
}

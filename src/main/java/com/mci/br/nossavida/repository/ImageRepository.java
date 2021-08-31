package com.mci.br.nossavida.repository;

import com.mci.br.nossavida.model.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Long> {
    List<Image> findAllByPeriodId(Long id);
    Optional<Image> findFirstByPeriodId(Long id);
    void deleteByPeriodId(Long id);
}

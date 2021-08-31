package com.mci.br.nossavida.service;

import com.mci.br.nossavida.model.Image;
import com.mci.br.nossavida.model.Period;
import com.mci.br.nossavida.model.dto.PeriodDTO;
import com.mci.br.nossavida.model.exception.PeriodNotFoundException;
import com.mci.br.nossavida.repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PeriodService {

    private final PeriodRepository periodRepository;
    private final ImageService imageService;

    @Autowired
    public PeriodService(PeriodRepository periodRepository, ImageService imageService) {
        this.periodRepository = periodRepository;
        this.imageService = imageService;
    }

    public Period addPeriod(Period period) {
        return periodRepository.save(period);
    }

    public List<Period> getPeriods(){
        return StreamSupport
                .stream(periodRepository.findAllByOrderByEndDateDesc().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Period getPeriod(Long id) {
        Period period = periodRepository.findById(id).orElseThrow(() -> new PeriodNotFoundException(id));
        return period;
    }

    @Transactional
    public Period editPeriod(Long id, Period period) {
        Period periodToEdit = getPeriod(id);
        periodToEdit.setTitle(period.getTitle());
        periodToEdit.setDescription(period.getDescription());
        periodToEdit.setStartDate(period.getStartDate());
        periodToEdit.setEndDate(period.getEndDate());
        return periodToEdit;
    }

    public Period deletePeriod(Long id) {
        Period periodToDelete = getPeriod(id);
        imageService.deleteImageByPeriod(periodToDelete);
        periodRepository.delete(periodToDelete);

        return periodToDelete;
    }

    public List<PeriodDTO> getPeriodsWithImage() {
        List<Period> periods = getPeriods();
        List<PeriodDTO> periodsDTO = new ArrayList<>();
        for(Period period : periods) {
            byte[] image = imageService.getFirstImageByPeriod(period);
            periodsDTO.add(convertToDTO(period, image));
        }
        return periodsDTO;
    }

    private PeriodDTO convertToDTO(Period period, byte[] image) {
        PeriodDTO periodDTO = new PeriodDTO();

        periodDTO.setId(period.getId());
        periodDTO.setTitle(period.getTitle());
        periodDTO.setDescription(period.getDescription());
        periodDTO.setStartDate(period.getStartDate());
        periodDTO.setEndDate(period.getEndDate());
        periodDTO.setImage(image);

        return periodDTO;
    }
}

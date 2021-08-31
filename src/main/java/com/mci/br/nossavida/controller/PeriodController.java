package com.mci.br.nossavida.controller;

import com.mci.br.nossavida.model.Image;
import com.mci.br.nossavida.model.Period;
import com.mci.br.nossavida.model.dto.PeriodDTO;
import com.mci.br.nossavida.service.ImageService;
import com.mci.br.nossavida.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/periods")
public class PeriodController {

    private final PeriodService periodService;

    private final ImageService imageService;

    @Autowired
    public PeriodController(PeriodService periodService, ImageService imageService) {
        this.periodService = periodService;
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<PeriodDTO>> getPeriods() {
        return new ResponseEntity<>(periodService.getPeriodsWithImage(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Period> getPeriod(@PathVariable final Long id) {
        System.out.println(id);
        Period period = periodService.getPeriod(id);
        return new ResponseEntity<>(period, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Period> addPeriod(@RequestBody final Period period) {
        Period periodToAdd = periodService.addPeriod(period);
        return new ResponseEntity<>(periodToAdd, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Period> editPeriod(@PathVariable final Long id, @RequestBody final Period period) {
        Period periodToEdit = periodService.editPeriod(id, period);
        return new ResponseEntity<>(periodToEdit, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Period> deletePeriod(@PathVariable final Long id) {
        Period periodToDelete = periodService.deletePeriod(id);
        return new ResponseEntity<>(periodToDelete, HttpStatus.OK);
    }

    @GetMapping(path = "/images/{id}")
    public ResponseEntity<List<byte[]>> getImagesFromPeriod(@PathVariable final Long id) {
        Period period = periodService.getPeriod(id);
        List<byte[]> images = imageService.getImagesByPeriod(period);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

}

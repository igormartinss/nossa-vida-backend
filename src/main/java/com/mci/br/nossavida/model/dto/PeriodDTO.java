package com.mci.br.nossavida.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PeriodDTO {

    private Long id;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private byte[] image;
}

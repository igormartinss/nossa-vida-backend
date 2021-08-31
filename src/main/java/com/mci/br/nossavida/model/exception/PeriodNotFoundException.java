package com.mci.br.nossavida.model.exception;

import java.text.MessageFormat;

public class PeriodNotFoundException extends RuntimeException{
    public PeriodNotFoundException(Long id) {
        super(MessageFormat.format("Periodo não encontrado para o id {0}", id));
    }
}

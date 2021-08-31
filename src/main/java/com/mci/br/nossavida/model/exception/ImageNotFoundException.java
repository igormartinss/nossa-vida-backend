package com.mci.br.nossavida.model.exception;

import java.text.MessageFormat;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(Long id) {
        super(MessageFormat.format("Imagem não encontrada para o id {0}", id));
    }
}

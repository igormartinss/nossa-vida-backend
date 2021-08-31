package com.mci.br.nossavida.model.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(final Long id){
        super(MessageFormat.format("Usuário não encontrado para o id {0}", id));
    }

    public UserNotFoundException(String username){
        super(MessageFormat.format("Usuário não encontrado {0} ", username));
    }
}

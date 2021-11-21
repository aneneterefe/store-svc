package com.store.api.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GeneralException extends RuntimeException{

    private Exception exception;
    private String errorMessage;
    public GeneralException(final Exception exception, final String errorMessage){
        super(exception);
        this.errorMessage = errorMessage;
    }
}

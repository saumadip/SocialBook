package com.hsbc.sb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ApplicationRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -4747723004070456029L;

    public ApplicationRuntimeException(String s) {
        super(s);
    }
}

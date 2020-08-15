package com.sdqube.hamroaudit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 8:50 PM
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
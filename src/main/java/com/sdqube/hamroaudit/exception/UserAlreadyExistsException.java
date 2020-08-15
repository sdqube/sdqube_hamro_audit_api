package com.sdqube.hamroaudit.exception;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 5:06 PM
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String userExistsLog) {
        super(userExistsLog);

    }
}

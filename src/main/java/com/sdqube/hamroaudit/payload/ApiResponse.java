package com.sdqube.hamroaudit.payload;


/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 7:29 PM
 */
public class ApiResponse {
    Boolean success;
    String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

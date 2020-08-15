package com.sdqube.hamroaudit.payload;


import javax.validation.constraints.NotBlank;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 7:28 PM
 */
public class LoginRequest {
    @NotBlank
    String username;
    @NotBlank
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

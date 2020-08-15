package com.sdqube.hamroaudit.payload;


import com.sdqube.hamroaudit.model.User;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 8:26 PM
 */
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User userInfo;

    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String accessToken, User userInfo) {
        if (accessToken == null)
            throw new NullPointerException("Accesstoken cannot be null");
        this.accessToken = accessToken;

        if (userInfo == null)
            throw new NullPointerException("userInfo cannot be null");
        this.userInfo = userInfo;
    }

    public JwtAuthenticationResponse(String accessToken, String tokenType, User userInfo) {
        if (accessToken == null)
            throw new NullPointerException("Accesstoken cannot be null");
        this.accessToken = accessToken;
        this.tokenType = tokenType;

        if (userInfo == null)
            throw new NullPointerException("userInfo cannot be null");
        this.userInfo = userInfo;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        if (accessToken == null) {
            throw new NullPointerException("Accesstoken cannot be null");
        }
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        if (userInfo == null) {
            throw new NullPointerException("userInfo cannot be null");
        }

        this.userInfo = userInfo;
    }
}

package com.sdqube.hamroaudit.payload;

import com.sdqube.hamroaudit.messaging.UserEventType;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 4:40 PM
 */
public class UserEventPayload {
    String id;
    String username;
    String email;
    String displayName;
    String profilePictureUrl;
    String oldProfilePicUrl;
    UserEventType eventType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getOldProfilePicUrl() {
        return oldProfilePicUrl;
    }

    public void setOldProfilePicUrl(String oldProfilePicUrl) {
        this.oldProfilePicUrl = oldProfilePicUrl;
    }

    public UserEventType getEventType() {
        return eventType;
    }

    public void setEventType(UserEventType eventType) {
        this.eventType = eventType;
    }
}

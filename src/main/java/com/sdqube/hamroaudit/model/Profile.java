package com.sdqube.hamroaudit.model;

import java.util.Date;
import java.util.Set;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/3/20 1:34 AM
 */
public class Profile {

    private String displayName;
    private String profilePictureUrl;
    private Date birthday;
    private Set<Address> addresses;

    public Profile() {
    }

    public Profile(String displayName, String profilePictureUrl, Date birthday, Set<Address> addresses) {
        this.displayName = displayName;
        this.profilePictureUrl = profilePictureUrl;
        this.birthday = birthday;
        this.addresses = addresses;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "displayName='" + displayName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", birthday=" + birthday +
                ", addresses=" + addresses +
                '}';
    }
}
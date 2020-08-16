package com.sdqube.hamroaudit.model;


/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/3/20 1:31 AM
 */
public class Address {
    private String id;
    private String country;
    private String city;
    private String zipCode;
    private String streetName;
    private int buildingNumber;

    public Address() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", streetName='" + streetName + '\'' +
                ", buildingNumber=" + buildingNumber +
                '}';
    }
}

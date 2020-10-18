package com.example.toiletfinder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Toilets {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lat")
    @Expose
    private Float lat;
    @SerializedName("lng")
    @Expose
    private Float lng;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("place_type")
    @Expose
    private String placeType;
    @SerializedName("disabled_access")
    @Expose
    private String disabledAccess;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getDisabledAccess() {
        return disabledAccess;
    }

    public void setDisabledAccess(String disabledAccess) {
        this.disabledAccess = disabledAccess;
    }
}

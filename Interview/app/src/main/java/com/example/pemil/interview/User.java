package com.example.pemil.interview;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by pemil on 09.03.2018.
 */

public class User implements Serializable {
    private String userName;
    private String urlPhoto;
    private HashMap<String, Integer> badges;
    private String location;

    User(String name, String url, HashMap<String,
            Integer> badges, String location) {
        this.userName = name;
        this.urlPhoto = url;
        this.badges = badges;
        this.location = location;
    }

    String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    String getUrlPhoto() {
        return urlPhoto;
    }

    void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public HashMap<String, Integer> getBadges() {
        return badges;
    }

    void setBadges(HashMap<String, Integer> badges) {
        this.badges = badges;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "User{" +
                ", userName='" + userName + '\'' +
                ", urlPhoto='" + urlPhoto + '\'' +
                ", badges=" + badges +
                '}';
    }
}

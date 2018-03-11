package com.example.pemil.interview;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by pemil on 09.03.2018.
 */

public class User implements Serializable {
    private int id;
    private String userName;
    private String urlPhoto;
    private HashMap<String, Integer> badges;
//    private Bitmap bmpImage;

    User(String name) {
        this.userName = name;

    }

    String getUserName() {
        return userName;
    }

    public void setUserName(int id, String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", urlPhoto='" + urlPhoto + '\'' +
                ", badges=" + badges +
                '}';
    }
}

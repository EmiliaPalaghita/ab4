package com.example.pemil.interview;

import java.util.HashMap;

/**
 * Created by pemil on 09.03.2018.
 */

public class User {
    private String userName;
    private int photoId;
    private String urlPhoto;
    private HashMap<String, Integer> badges;

    User(String name, int id) {
        this.userName = name;
        this.photoId = id;
    }

    String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getUrlPhoto() {
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
}

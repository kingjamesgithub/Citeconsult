package com.example.citeconsult;

public class Instructors {
    String key, name, photo, email;

    public Instructors() {

    }

    public Instructors(String key, String name, String photo, String email) {
        this.key = key;
        this.name = name;
        this.photo = photo;
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

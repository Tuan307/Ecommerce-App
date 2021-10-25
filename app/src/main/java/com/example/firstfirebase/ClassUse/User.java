package com.example.firstfirebase.ClassUse;

public class User {
    private String name;
    private String pass;
    private String phone;
    private String image;
    private String address;
    public User() {
    }

    public User(String name, String pass, String phone, String image, String address) {
        this.name = name;
        this.pass = pass;
        this.phone = phone;
        this.image = image;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

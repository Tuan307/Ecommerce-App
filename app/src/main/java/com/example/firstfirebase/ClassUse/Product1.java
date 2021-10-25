package com.example.firstfirebase.ClassUse;

public class Product1 {
    private String name,description,price,image,category,pid,data,time,productstate;

    public Product1() {
    }

    public Product1(String name, String description, String price, String image, String category, String pid, String data, String time, String productstate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.data = data;
        this.time = time;
        this.productstate = productstate;
    }


    public String getProductstate() {
        return productstate;
    }

    public void setProductstate(String productstate) {
        this.productstate = productstate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

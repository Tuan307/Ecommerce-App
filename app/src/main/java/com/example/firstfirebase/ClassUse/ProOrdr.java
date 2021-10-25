package com.example.firstfirebase.ClassUse;

public class ProOrdr {
    private String name,total,phone;

    public ProOrdr() {
    }

    public ProOrdr(String name, String total, String phone) {
        this.name = name;
        this.total = total;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

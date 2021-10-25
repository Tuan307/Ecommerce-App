package com.example.firstfirebase.ClassUse;

public class Order {
    private String oname,ophone,oaddress,ocity,state,odate,otime,ototal;

    public Order() {
    }

    public Order(String oname, String ophone, String oaddress, String ocity, String state, String odate, String otime, String ototal) {
        this.oname = oname;
        this.ophone = ophone;
        this.oaddress = oaddress;
        this.ocity = ocity;
        this.state = state;
        this.odate = odate;
        this.otime = otime;
        this.ototal = ototal;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getOphone() {
        return ophone;
    }

    public void setOphone(String ophone) {
        this.ophone = ophone;
    }

    public String getOaddress() {
        return oaddress;
    }

    public void setOaddress(String oaddress) {
        this.oaddress = oaddress;
    }

    public String getOcity() {
        return ocity;
    }

    public void setOcity(String ocity) {
        this.ocity = ocity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOdate() {
        return odate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }

    public String getOtime() {
        return otime;
    }

    public void setOtime(String otime) {
        this.otime = otime;
    }

    public String getOtotal() {
        return ototal;
    }

    public void setOtotal(String ototal) {
        this.ototal = ototal;
    }
}

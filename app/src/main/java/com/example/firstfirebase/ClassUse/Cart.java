package com.example.firstfirebase.ClassUse;

public class Cart {
    private String pid,pname,pprice ,pquan,pdiscount,productstate,sellerid;

    public Cart() {
    }

    public Cart(String pid, String pname, String pprice, String pquan, String pdiscount) {
        this.pid = pid;
        this.pname = pname;
        this.pprice = pprice;
        this.pquan = pquan;
        this.pdiscount = pdiscount;
    }

    public Cart(String pid, String pname, String pprice, String pquan, String pdiscount, String productstate) {
        this.pid = pid;
        this.pname = pname;
        this.pprice = pprice;
        this.pquan = pquan;
        this.pdiscount = pdiscount;
        this.productstate = productstate;
    }

    public Cart(String pid, String pname, String pprice, String pquan, String pdiscount, String productstate, String sellerid) {
        this.pid = pid;
        this.pname = pname;
        this.pprice = pprice;
        this.pquan = pquan;
        this.pdiscount = pdiscount;
        this.productstate = productstate;
        this.sellerid = sellerid;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getProductstate() {
        return productstate;
    }

    public void setProductstate(String productstate) {
        this.productstate = productstate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPquan() {
        return pquan;
    }

    public void setPquan(String pquan) {
        this.pquan = pquan;
    }

    public String getPdiscount() {
        return pdiscount;
    }

    public void setPdiscount(String pdiscount) {
        this.pdiscount = pdiscount;
    }
}

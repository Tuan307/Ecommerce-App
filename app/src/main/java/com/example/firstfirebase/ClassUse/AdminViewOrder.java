package com.example.firstfirebase.ClassUse;

public class AdminViewOrder {
    private String pdate,pdiscount,pid,pname,pprice,pquan,ptime;

    public AdminViewOrder(String pdate, String pdiscount, String pid, String pname, String pprice, String pquan, String ptime) {
        this.pdate = pdate;
        this.pdiscount = pdiscount;
        this.pid = pid;
        this.pname = pname;
        this.pprice = pprice;
        this.pquan = pquan;
        this.ptime = ptime;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getPdiscount() {
        return pdiscount;
    }

    public void setPdiscount(String pdiscount) {
        this.pdiscount = pdiscount;
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

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public AdminViewOrder() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}

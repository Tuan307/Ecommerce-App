package com.example.firstfirebase.ClassUse;

public class LoaiMenu{
    private String tenmenu;
    private int hinhanhmenu;

    public LoaiMenu(String tenmenu, int hinhanhmenu) {
        this.tenmenu = tenmenu;
        this.hinhanhmenu = hinhanhmenu;
    }

    public LoaiMenu() {
    }

    public String getTenmenu() {
        return tenmenu;
    }

    public void setTenmenu(String tenmenu) {
        this.tenmenu = tenmenu;
    }

    public int getHinhanhmenu() {
        return hinhanhmenu;
    }

    public void setHinhanhmenu(int hinhanhmenu) {
        this.hinhanhmenu = hinhanhmenu;
    }
}

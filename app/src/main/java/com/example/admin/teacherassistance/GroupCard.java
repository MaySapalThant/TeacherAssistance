package com.example.admin.teacherassistance;


public class GroupCard {
    private String name;
    private String rollnum;
    private int gpnum;

    public GroupCard() {
    }

    public GroupCard(String name,String rollnum, int gpnum) {
        this.name = name;
        this.gpnum = gpnum;
        this.rollnum=rollnum;
        //this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGpnum() {
        return gpnum;
    }

    public void setGpnum(int gpnum) {
        this.gpnum = gpnum;
    }

    public String getRollnum() {
        return rollnum;
    }

    public void setRollnum(String rollnum) {
        this.rollnum = rollnum;
    }

}

package com.example.admin.teacherassistance;

public class Calbum {
    private String countryname;
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private String language;

    public Calbum() {
    }

    public Calbum(String countryname, String language,int flag) {
        this.countryname = countryname;
        this.language = language;
        this.flag  = flag;

    }
}

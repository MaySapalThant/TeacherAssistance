package com.example.admin.teacherassistance;

import java.io.Serializable;
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean isClicked;
    private String rollNumber;
    private String name;
    private String emailId;
    private  String elearningid;
    private String elearningemail;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    private String phonenumber;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getElearningid() {
        return elearningid;
    }

    public void setElearningid(String elearningid) {
        this.elearningid = elearningid;
    }

    public String getElearningemail() {
        return elearningemail;
    }

    public void setElearningemail(String elearningemail) {
        this.elearningemail = elearningemail;
    }

    public Student(String roll, String name, String emailId, String elearningid, String elearningemail,String phonenumber) {
        this.name = name;
        this.emailId = emailId;
        this.rollNumber = roll;
        this.elearningid = elearningid;
        this.elearningemail = elearningemail;
        this.phonenumber=phonenumber;
    }

    public Student(String rollNumber, String name, boolean isSelected) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.isSelected = isSelected;
    }
    public boolean isGmailclick() {
        return gmailclick;
    }

    public void setGmailclick(boolean gmailclick) {
        this.gmailclick = gmailclick;
    }

    private boolean gmailclick;

    private boolean isSelected;

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Student(String rollNumber, String name) {
        this.rollNumber = rollNumber;
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }





}
package com.dofl.moneygo.model;

import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private String email;
    private String password;
    private String role;
    private String displayName;
    private String presentMoneyPackage;
    private String previousMoneyPackage;

    public Account() {
    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Account(int id, String email, String password, String role, String displayName,
                   String presentMoneyPackage, String previousMoneyPackage) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.displayName = displayName;
        this.presentMoneyPackage = presentMoneyPackage;
        this.previousMoneyPackage = previousMoneyPackage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPresentMoneyPackage() {
        return presentMoneyPackage;
    }

    public void setPresentMoneyPackage(String presentMoneyPackage) {
        this.presentMoneyPackage = presentMoneyPackage;
    }

    public String getPreviousMoneyPackage() {
        return previousMoneyPackage;
    }

    public void setPreviousMoneyPackage(String previousMoneyPackage) {
        this.previousMoneyPackage = previousMoneyPackage;
    }
}

package com.dofl.qlct.model;

public class Account {
    private int id;
    private String username;
    private String password;
    private String role;
    private String displayName;
    private int packageNumber;
    private String startDate;
    private int previousMoney;
    private int moneyPaid;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(int id, String username, String role, String displayName, int packageNumber,
                   String startDate, int previousMoney, int moneyPaid) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.packageNumber = packageNumber;
        this.displayName = displayName;
        this.startDate = startDate;
        this.previousMoney = previousMoney;
        this.moneyPaid = moneyPaid;
    }

    public Account(int id, String role, String displayName, int packageNumber, String startDate,
                   int previousMoney, int moneyPaid) {
        this.id = id;
        this.role = role;
        this.displayName = displayName;
        this.packageNumber = packageNumber;
        this.startDate = startDate;
        this.previousMoney = previousMoney;
        this.moneyPaid = moneyPaid;
    }

    public int getMoneyPaid() {
        return moneyPaid;
    }

    public void setMoneyPaid(int moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    public int getPreviousMoney() {
        return previousMoney;
    }

    public void setPreviousMoney(int previousMoney) {
        this.previousMoney = previousMoney;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(int packageNumber) {
        this.packageNumber = packageNumber;
    }
}

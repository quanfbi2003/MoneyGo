package com.dofl.moneygo.model;

import java.io.Serializable;

public class MoneyPackage implements Serializable {
    private String recordPackage; //yyyy/MM/dd
    private String summaryPackage;
    private int previousMoney;
    private int totalMoney;
    private int presentMoney;
    private int numberOfRecord;
    private int moneySpent;
    private int moneyPaid;
    private int moneySent;

    public MoneyPackage() {
    }

    public MoneyPackage(String recordPackage, String summaryPackage, int previousMoney,
                        int totalMoney, int presentMoney, int numberOfRecord, int moneySpent,
                        int moneyPaid, int moneySent) {
        this.recordPackage = recordPackage;
        this.summaryPackage = summaryPackage;
        this.previousMoney = previousMoney;
        this.totalMoney = totalMoney;
        this.presentMoney = presentMoney;
        this.numberOfRecord = numberOfRecord;
        this.moneySpent = moneySpent;
        this.moneyPaid = moneyPaid;
        this.moneySent = moneySent;
    }

    public String getRecordPackage() {
        return recordPackage;
    }

    public void setRecordPackage(String recordPackage) {
        this.recordPackage = recordPackage;
    }

    public String getSummaryPackage() {
        return summaryPackage;
    }

    public void setSummaryPackage(String summaryPackage) {
        this.summaryPackage = summaryPackage;
    }

    public int getPreviousMoney() {
        return previousMoney;
    }

    public void setPreviousMoney(int previousMoney) {
        this.previousMoney = previousMoney;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getPresentMoney() {
        return presentMoney;
    }

    public void setPresentMoney(int presentMoney) {
        this.presentMoney = presentMoney;
    }

    public int getNumberOfRecord() {
        return numberOfRecord;
    }

    public void setNumberOfRecord(int numberOfRecord) {
        this.numberOfRecord = numberOfRecord;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(int moneySpent) {
        this.moneySpent = moneySpent;
    }

    public int getMoneyPaid() {
        return moneyPaid;
    }

    public void setMoneyPaid(int moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    public int getMoneySent() {
        return moneySent;
    }

    public void setMoneySent(int moneySent) {
        this.moneySent = moneySent;
    }
}

package com.dofl.moneygo.model;

import java.io.Serializable;

public class Record implements Serializable {
    private String description;
    private String timeCreate; //HH:mm:ss
    private String dateCreate; //yyyy/MM/dd
    private int total;
    private int buyer;
    private int n1Qty;
    private int n2Qty;
    private int n3Qty;
    private int n4Qty;
    private int qty;
    private int n1Total;
    private int n2Total;
    private int n3Total;
    private int n4Total;
    private String moneyPackage;
    private String summaryPackage;

    public Record() {
    }

    public Record(String description, String timeCreate, String dateCreate, int total, int buyer, int n1Qty, int n2Qty, int n3Qty, int n4Qty) {
        this.description = description;
        this.timeCreate = timeCreate;
        this.dateCreate = dateCreate;
        this.total = total;
        this.buyer = buyer;
        this.n1Qty = n1Qty;
        this.n2Qty = n2Qty;
        this.n3Qty = n3Qty;
        this.n4Qty = n4Qty;
    }

    public Record(String description, String timeCreate, String dateCreate, int total, int buyer,
                  int n1Qty, int n2Qty, int n3Qty, int n4Qty, int qty, int n1Total, int n2Total,
                  int n3Total, int n4Total) {
        this.total = total;
        this.description = description;
        this.timeCreate = timeCreate;
        this.dateCreate = dateCreate;
        this.buyer = buyer;
        this.n1Qty = n1Qty;
        this.n2Qty = n2Qty;
        this.n3Qty = n3Qty;
        this.n4Qty = n4Qty;
        this.qty = qty;
        this.n1Total = n1Total;
        this.n2Total = n2Total;
        this.n3Total = n3Total;
        this.n4Total = n4Total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getBuyer() {
        return buyer;
    }

    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }

    public int getN1Qty() {
        return n1Qty;
    }

    public void setN1Qty(int n1Qty) {
        this.n1Qty = n1Qty;
    }

    public int getN2Qty() {
        return n2Qty;
    }

    public void setN2Qty(int n2Qty) {
        this.n2Qty = n2Qty;
    }

    public int getN3Qty() {
        return n3Qty;
    }

    public void setN3Qty(int n3Qty) {
        this.n3Qty = n3Qty;
    }

    public int getN4Qty() {
        return n4Qty;
    }

    public void setN4Qty(int n4Qty) {
        this.n4Qty = n4Qty;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getN1Total() {
        return n1Total;
    }

    public void setN1Total(int n1Total) {
        this.n1Total = n1Total;
    }

    public int getN2Total() {
        return n2Total;
    }

    public void setN2Total(int n2Total) {
        this.n2Total = n2Total;
    }

    public int getN3Total() {
        return n3Total;
    }

    public void setN3Total(int n3Total) {
        this.n3Total = n3Total;
    }

    public int getN4Total() {
        return n4Total;
    }

    public void setN4Total(int n4Total) {
        this.n4Total = n4Total;
    }

    public String getMoneyPackage() {
        return moneyPackage;
    }

    public void setMoneyPackage(String moneyPackage) {
        this.moneyPackage = moneyPackage;
    }

    public String getSummaryPackage() {
        return summaryPackage;
    }

    public void setSummaryPackage(String summaryPackage) {
        this.summaryPackage = summaryPackage;
    }
}

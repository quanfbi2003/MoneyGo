package com.dofl.qlct.model;

public class Record {
    private String id;
    private int total;
    private String description;
    private String timeCreate;
    private String dateCreate;
    private int buyer;
    private int n1Qty;
    private int n2Qty;
    private int n3Qty;
    private int n4Qty;
    private int packageNumber;
    private int qty;
    private int n1Total;
    private int n2Total;
    private int n3Total;
    private int n4Total;
    private int icon;

    public Record() {
    }

    public Record(String id, int total, String description, String timeCreate, String dateCreate,
                  int buyer, int n1Qty, int n2Qty, int n3Qty, int n4Qty, int packageNumber) {
        this.id = id;
        this.total = total;
        this.description = description;
        this.timeCreate = timeCreate;
        this.dateCreate = dateCreate;
        this.buyer = buyer;
        this.n1Qty = n1Qty;
        this.n2Qty = n2Qty;
        this.n3Qty = n3Qty;
        this.n4Qty = n4Qty;
        this.packageNumber = packageNumber;
    }

    public Record(String id, int total, String description, String timeCreate, String dateCreate,
                  int buyer, int n1Qty, int n2Qty, int n3Qty, int n4Qty, int packageNumber,
                  int icon) {
        this.id = id;
        this.total = total;
        this.description = description;
        this.timeCreate = timeCreate;
        this.dateCreate = dateCreate;
        this.buyer = buyer;
        this.n1Qty = n1Qty;
        this.n2Qty = n2Qty;
        this.n3Qty = n3Qty;
        this.n4Qty = n4Qty;
        this.packageNumber = packageNumber;
        this.qty = qty;
        this.icon = icon;
    }

    public Record(int total, String description, String timeCreate, String dateCreate, int buyer,
                  int n1Qty, int n2Qty, int n3Qty, int n4Qty, int packageNumber) {
        this.total = total;
        this.description = description;
        this.timeCreate = timeCreate;
        this.dateCreate = dateCreate;
        this.buyer = buyer;
        this.n1Qty = n1Qty;
        this.n2Qty = n2Qty;
        this.n3Qty = n3Qty;
        this.n4Qty = n4Qty;
        this.packageNumber = packageNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(int packageNumber) {
        this.packageNumber = packageNumber;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}



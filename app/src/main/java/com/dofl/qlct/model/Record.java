package com.dofl.qlct.model;

public class Record {
    private int id;
    private int total;
    private String description;
    private String time_create;
    private String date_create;
    private int buyer;
    private int n1_qty;
    private int n2_qty;
    private int n3_qty;
    private int n4_qty;
    private int package_number;
    private int qty;
    private int n1_total;
    private int n2_total;
    private int n3_total;
    private int n4_total;
    private int icon;

    public Record() {
    }

    public Record(int id, int total, String description, String time_create, String date_create, int buyer, int n1_qty, int n2_qty, int n3_qty, int n4_qty, int package_number) {
        this.id = id;
        this.total = total;
        this.description = description;
        this.time_create = time_create;
        this.date_create = date_create;
        this.buyer = buyer;
        this.n1_qty = n1_qty;
        this.n2_qty = n2_qty;
        this.n3_qty = n3_qty;
        this.n4_qty = n4_qty;
        this.package_number = package_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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

    public int getN1_qty() {
        return n1_qty;
    }

    public void setN1_qty(int n1_qty) {
        this.n1_qty = n1_qty;
    }

    public int getN2_qty() {
        return n2_qty;
    }

    public void setN2_qty(int n2_qty) {
        this.n2_qty = n2_qty;
    }

    public int getN3_qty() {
        return n3_qty;
    }

    public void setN3_qty(int n3_qty) {
        this.n3_qty = n3_qty;
    }

    public int getN4_qty() {
        return n4_qty;
    }

    public void setN4_qty(int n4_qty) {
        this.n4_qty = n4_qty;
    }

    public int getBuyer() {
        return buyer;
    }

    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }

    public String getTime_create() {
        return time_create;
    }

    public void setTime_create(String time_create) {
        this.time_create = time_create;
    }

    public String getDate_create() {
        return date_create;
    }

    public void setDate_create(String date_create) {
        this.date_create = date_create;
    }

    public int getPackage_number() {
        return package_number;
    }

    public void setPackage_number(int package_number) {
        this.package_number = package_number;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getN1_total() {
        return n1_total;
    }

    public void setN1_total(int n1_total) {
        this.n1_total = n1_total;
    }

    public int getN2_total() {
        return n2_total;
    }

    public void setN2_total(int n2_total) {
        this.n2_total = n2_total;
    }

    public int getN3_total() {
        return n3_total;
    }

    public void setN3_total(int n3_total) {
        this.n3_total = n3_total;
    }

    public int getN4_total() {
        return n4_total;
    }

    public void setN4_total(int n4_total) {
        this.n4_total = n4_total;
    }
}


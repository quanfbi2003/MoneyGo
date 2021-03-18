package com.dofl.qlct.model;

public class ListViewHistoryElement {
    private String description;
    private String dateTime;
    private int money;
    private int icon;

    public ListViewHistoryElement() {
    }

    public ListViewHistoryElement(String description, String dateTime, int money, int icon) {
        this.description = description;
        this.dateTime = dateTime;
        this.money = money;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

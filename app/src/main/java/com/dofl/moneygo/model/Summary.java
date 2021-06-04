package com.dofl.moneygo.model;

import java.io.Serializable;

public class Summary implements Serializable {
    private String startDate;
    private String endDate;
    private String monthOfYear;
    private int month;
    private int totalMoneySpent;
    private int numberOfElectricity;
    private int totalOfElectricity;
    private int numberOfWater;
    private int totalOfWater;
    private int airConditional;
    private int totalMoneyPaid;

    public Summary() {
    }

    public Summary(String startDate, String endDate, String monthOfYear, int month,
                   int totalMoneySpent, int numberOfElectricity, int totalOfElectricity,
                   int numberOfWater, int totalOfWater, int airConditional, int totalMoneyPaid) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthOfYear = monthOfYear;
        this.month = month;
        this.totalMoneySpent = totalMoneySpent;
        this.numberOfElectricity = numberOfElectricity;
        this.totalOfElectricity = totalOfElectricity;
        this.numberOfWater = numberOfWater;
        this.totalOfWater = totalOfWater;
        this.airConditional = airConditional;
        this.totalMoneyPaid = totalMoneyPaid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(String monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public void setTotalMoneySpent(int totalMoneySpent) {
        this.totalMoneySpent = totalMoneySpent;
    }

    public int getNumberOfElectricity() {
        return numberOfElectricity;
    }

    public void setNumberOfElectricity(int numberOfElectricity) {
        this.numberOfElectricity = numberOfElectricity;
    }

    public int getTotalOfElectricity() {
        return totalOfElectricity;
    }

    public void setTotalOfElectricity(int totalOfElectricity) {
        this.totalOfElectricity = totalOfElectricity;
    }

    public int getNumberOfWater() {
        return numberOfWater;
    }

    public void setNumberOfWater(int numberOfWater) {
        this.numberOfWater = numberOfWater;
    }

    public int getTotalOfWater() {
        return totalOfWater;
    }

    public void setTotalOfWater(int totalOfWater) {
        this.totalOfWater = totalOfWater;
    }

    public int getAirConditional() {
        return airConditional;
    }

    public void setAirConditional(int airConditional) {
        this.airConditional = airConditional;
    }

    public int getTotalMoneyPaid() {
        return totalMoneyPaid;
    }

    public void setTotalMoneyPaid(int totalMoneyPaid) {
        this.totalMoneyPaid = totalMoneyPaid;
    }
}

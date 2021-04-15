package com.dofl.qlct.model;

public class MPM {
    private String monthOfYear;
    private int month;
    private int numberOfElectricity;
    private int numberOfWater;
    private int airConditional;
    private int packageNumber;
    private String startDate;
    private String endDate;

    public MPM() {
    }

    public MPM(String monthOfYear, int month, int numberOfElectricity, int numberOfWater, int airConditional,
               int packageNumber, String startDate, String endDate) {
        this.monthOfYear = monthOfYear;
        this.month = month;
        this.numberOfElectricity = numberOfElectricity;
        this.numberOfWater = numberOfWater;
        this.airConditional = airConditional;
        this.packageNumber = packageNumber;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(String monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public int getNumberOfElectricity() {
        return numberOfElectricity;
    }

    public void setNumberOfElectricity(int numberOfElectricity) {
        this.numberOfElectricity = numberOfElectricity;
    }

    public int getNumberOfWater() {
        return numberOfWater;
    }

    public void setNumberOfWater(int numberOfWater) {
        this.numberOfWater = numberOfWater;
    }

    public int getAirConditional() {
        return airConditional;
    }

    public void setAirConditional(int airConditional) {
        this.airConditional = airConditional;
    }

    public int getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(int packageNumber) {
        this.packageNumber = packageNumber;
    }
}

package com.dofl.qlct.model;

public class Maintenance {
    private boolean maintenance;
    private int nNetMoney;

    public Maintenance() {
    }

    public Maintenance(boolean maintenance, int nNetMoney) {
        this.maintenance = maintenance;
        this.nNetMoney = nNetMoney;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public int getnNetMoney() {
        return nNetMoney;
    }

    public void setnNetMoney(int nNetMoney) {
        this.nNetMoney = nNetMoney;
    }
}

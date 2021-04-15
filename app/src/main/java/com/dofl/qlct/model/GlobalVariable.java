package com.dofl.qlct.model;

import android.app.Application;

import java.util.List;

public class GlobalVariable extends Application {
    private MPM presentMPM, previousMPM, prePreviousMPM;
    private Account account;
    private List<Record> listRecord, listPreviousRecord;
    private List<Account> listAccount;
    private Maintenance maintenance;

    public Maintenance getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
    }

    public List<Account> getListAccount() {
        return listAccount;
    }

    public void setListAccount(List<Account> listAccount) {
        this.listAccount = listAccount;
    }


    public MPM getPresentMPM() {
        return presentMPM;
    }

    public void setPresentMPM(MPM presentMPM) {
        this.presentMPM = presentMPM;
    }

    public MPM getPrePreviousMPM() {
        return prePreviousMPM;
    }

    public void setPrePreviousMPM(MPM prePreviousMPM) {
        this.prePreviousMPM = prePreviousMPM;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public MPM getPreviousMPM() {
        return previousMPM;
    }

    public void setPreviousMPM(MPM previousMPM) {
        this.previousMPM = previousMPM;
    }

    public List<Record> getListRecord() {
        return listRecord;
    }

    public void setListRecord(List<Record> listRecord) {
        this.listRecord = listRecord;
    }

    public List<Record> getListPreviousRecord() {
        return listPreviousRecord;
    }

    public void setListPreviousRecord(List<Record> listPreviousRecord) {
        this.listPreviousRecord = listPreviousRecord;
    }

    public void addRecord(Record record) {
        this.listRecord.add(record);
    }

    public void removeRecord(Record record) {
        for (int i = 0; i < listRecord.size(); i++) {
            if (listRecord.get(i).getId().equalsIgnoreCase(record.getId())) {
                listRecord.remove(i);
            }
        }
    }

    public void editRecord(Record record) {
        for (int i = 0; i < listRecord.size(); i++) {
            if (listRecord.get(i).getId().equalsIgnoreCase(record.getId())) {
                listRecord.get(i).setTotal(record.getTotal());
                listRecord.get(i).setDescription(record.getDescription());
                listRecord.get(i).setN1Qty(record.getN1Qty());
                listRecord.get(i).setN2Qty(record.getN2Qty());
                listRecord.get(i).setN3Qty(record.getN3Qty());
                listRecord.get(i).setN4Qty(record.getN4Qty());
            }
        }
    }
}

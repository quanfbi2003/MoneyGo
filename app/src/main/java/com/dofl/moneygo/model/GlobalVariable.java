package com.dofl.moneygo.model;

import android.app.Application;

import java.util.Map;

public class GlobalVariable extends Application {
    private RegisteredAccount registeredAccount;
    private Account account;
    private MoneyPackage presentMoneyPackage;
    private MoneyPackage previousMoneyPackage;
    private Map<String, Record> presentRecordPackage;
    private Map<String, Record> previousRecordPackage;
    private Summary presentSummaryPackage;
    private Summary previousSummaryPackage;

    public RegisteredAccount getRegisteredAccount() {
        return registeredAccount;
    }

    public void setRegisteredAccount(RegisteredAccount registeredAccount) {
        this.registeredAccount = registeredAccount;
    }

    public void initGlobalVariable() {
        this.registeredAccount = null;
        this.account = null;
        this.presentMoneyPackage = null;
        this.previousMoneyPackage = null;
        this.presentRecordPackage = null;
        this.previousRecordPackage = null;
        this.presentSummaryPackage = null;
        this.previousSummaryPackage = null;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public MoneyPackage getPresentMoneyPackage() {
        return presentMoneyPackage;
    }

    public void setPresentMoneyPackage(MoneyPackage presentMoneyPackage) {
        this.presentMoneyPackage = presentMoneyPackage;
    }

    public MoneyPackage getPreviousMoneyPackage() {
        return previousMoneyPackage;
    }

    public void setPreviousMoneyPackage(MoneyPackage previousMoneyPackage) {
        this.previousMoneyPackage = previousMoneyPackage;
    }

    public Map<String, Record> getPresentRecordPackage() {
        return presentRecordPackage;
    }

    public void setPresentRecordPackage(Map<String, Record> presentRecordPackage) {
        this.presentRecordPackage = presentRecordPackage;
    }

    public Map<String, Record> getPreviousRecordPackage() {
        return previousRecordPackage;
    }

    public void setPreviousRecordPackage(Map<String, Record> previousRecordPackage) {
        this.previousRecordPackage = previousRecordPackage;
    }

    public Summary getPresentSummaryPackage() {
        return presentSummaryPackage;
    }

    public void setPresentSummaryPackage(Summary presentSummaryPackage) {
        this.presentSummaryPackage = presentSummaryPackage;
    }

    public Summary getPreviousSummaryPackage() {
        return previousSummaryPackage;
    }

    public void setPreviousSummaryPackage(Summary previousSummaryPackage) {
        this.previousSummaryPackage = previousSummaryPackage;
    }
}

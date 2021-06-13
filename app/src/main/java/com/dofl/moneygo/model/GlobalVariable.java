package com.dofl.moneygo.model;

import android.app.Application;

public class GlobalVariable extends Application {
    private RegisteredAccount registeredAccount;
    private Account account;
    private MoneyPackage presentMoneyPackage;
    private MoneyPackage previousMoneyPackage;
    private Summary presentSummaryPackage; //Bo di
    private Summary previousSummaryPackage; //Bo di
    private MoneyPackage n1MoneyPackage;
    private MoneyPackage n2MoneyPackage;
    private MoneyPackage n3MoneyPackage;
    private MoneyPackage n4MoneyPackage;
    private int neighborNetwork;

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
        this.presentSummaryPackage = null;
        this.previousSummaryPackage = null;
        this.n1MoneyPackage = null;
        this.n2MoneyPackage = null;
        this.n3MoneyPackage = null;
        this.n4MoneyPackage = null;
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

    public MoneyPackage getN1MoneyPackage() {
        return n1MoneyPackage;
    }

    public void setN1MoneyPackage(MoneyPackage n1MoneyPackage) {
        this.n1MoneyPackage = n1MoneyPackage;
    }

    public MoneyPackage getN2MoneyPackage() {
        return n2MoneyPackage;
    }

    public void setN2MoneyPackage(MoneyPackage n2MoneyPackage) {
        this.n2MoneyPackage = n2MoneyPackage;
    }

    public MoneyPackage getN3MoneyPackage() {
        return n3MoneyPackage;
    }

    public void setN3MoneyPackage(MoneyPackage n3MoneyPackage) {
        this.n3MoneyPackage = n3MoneyPackage;
    }

    public MoneyPackage getN4MoneyPackage() {
        return n4MoneyPackage;
    }

    public void setN4MoneyPackage(MoneyPackage n4MoneyPackage) {
        this.n4MoneyPackage = n4MoneyPackage;
    }

    public int getNeighborNetwork() {
        return neighborNetwork;
    }

    public void setNeighborNetwork(int neighborNetwork) {
        this.neighborNetwork = neighborNetwork;
    }
}

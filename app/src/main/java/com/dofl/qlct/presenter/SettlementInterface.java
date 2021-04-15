package com.dofl.qlct.presenter;

public interface SettlementInterface {
    void addSuccess();

    void addError(String msg);

    void connectFailed();
}

package com.dofl.moneygo.presenter;

public interface EditHistoryInterface {
    void editSuccess();

    void editError(String msg);

    void removeError(String msg);
}

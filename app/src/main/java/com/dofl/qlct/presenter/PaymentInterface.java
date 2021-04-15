package com.dofl.qlct.presenter;

public interface PaymentInterface {
    void addSuccess();

    void addError(String msg);

    void connectFailed();
}

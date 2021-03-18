package com.dofl.qlct.presenter;

import com.dofl.qlct.model.Account;

public interface LoginInterface {
    void loginSuccess(Account account);

    void loginError();

    void connectFailed();
}

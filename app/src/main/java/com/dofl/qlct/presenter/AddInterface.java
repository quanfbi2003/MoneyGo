package com.dofl.qlct.presenter;

import com.dofl.qlct.model.Record;

public interface AddInterface {
    void addSuccess(Record record);

    void addError(String msg);

    void connectFailed();
}

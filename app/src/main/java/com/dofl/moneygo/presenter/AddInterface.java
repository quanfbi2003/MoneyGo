package com.dofl.moneygo.presenter;

import com.dofl.moneygo.model.Record;

public interface AddInterface {
    void addSuccess(Record record);

    void addError(String msg);
}

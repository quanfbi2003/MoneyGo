package com.dofl.qlct.presenter;

import com.dofl.qlct.model.Record;

public interface EditHistoryInterface {
    void editSuccess(Record record);

    void editError(String msg);

    void connectFailed();
}

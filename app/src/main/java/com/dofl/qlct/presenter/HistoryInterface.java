package com.dofl.qlct.presenter;

import com.dofl.qlct.model.Record;

import java.util.ArrayList;

public interface HistoryInterface {
    ArrayList<Record> getRecordHistory();

    void connectFailed();
}

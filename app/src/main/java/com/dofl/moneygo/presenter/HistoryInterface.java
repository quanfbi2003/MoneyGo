package com.dofl.moneygo.presenter;

import com.dofl.moneygo.model.Record;

import java.util.Map;

public interface HistoryInterface {
    void updatePresentRecordPackage(Map<String, Record> presentRecordPackage);

    void updatePreviousRecordPackage(Map<String, Record> previousRecordPackage);

    void updateError(String msg);
}

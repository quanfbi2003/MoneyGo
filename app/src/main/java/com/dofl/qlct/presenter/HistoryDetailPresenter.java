package com.dofl.qlct.presenter;

import android.util.Log;

import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.utils.JDBC;

import java.sql.Connection;
import java.sql.Statement;

public class HistoryDetailPresenter {
    private HistoryDetailInterface historyDetailInterface;

    public HistoryDetailPresenter(HistoryDetailInterface historyDetailInterface) {
        this.historyDetailInterface = historyDetailInterface;
    }

    public void deleteRecord(Record record) {
        try {
            Connection connection = JDBC.getConnection();
            if (connection != null) {
                String query = "DELETE FROM record WHERE id = \'" + record.getId() + "\'";
                Log.e("Log", query);
                Statement stmt = connection.createStatement();
                int result = stmt.executeUpdate(query);//thực thi lệnh, trả về số dòng thực hiện dc

                if (result == 1) {
                    historyDetailInterface.deleteSuccess();
                    Log.e("Log", "Delete successfully!!!");
                    connection.close();
                } else {
                    historyDetailInterface.deleteError();
                    Log.e("Log", "Delete unsuccessfully!!!");
                }
            } else {
                historyDetailInterface.connectFailed();
                Log.e("Log", "Connection failed!!!");
            }
        } catch (Exception ex) {
            Log.e("ERUs", ex.getMessage());
        }
    }
}

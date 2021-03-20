package com.dofl.qlct.presenter;

import android.util.Log;

import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.utils.JDBC;

import java.sql.Connection;
import java.sql.Statement;

public class EditHistoryPresenter {
    private final EditHistoryInterface editHistoryInterface;

    public EditHistoryPresenter(EditHistoryInterface editHistoryInterface) {
        this.editHistoryInterface = editHistoryInterface;
    }

    public void editRecord(Record record) {
        record.setQty(record.getN1_qty() + record.getN2_qty() + record.getN3_qty() + record.getN4_qty());
        if (record.getQty() != 0 && !record.getDescription().trim().isEmpty() && record.getTotal() != 0) {
            try {
                Connection connection = JDBC.getConnection();
                if (connection != null) {
                    String query = "UPDATE record SET total = \'" +
                            record.getTotal() + "\', description = N\'" +
                            record.getDescription() + "\', n1_qty = \'" +
                            record.getN1_qty() + "\', n2_qty = \'" +
                            record.getN2_qty() + "\', n3_qty = \'" +
                            record.getN3_qty() + "\', n4_qty = \'" +
                            record.getN4_qty() + "\' WHERE id = \'" + record.getId() + "\'";
                    Log.e("Log", query);
                    Statement stmt = connection.createStatement();
                    int result = stmt.executeUpdate(query);//thực thi lệnh, trả về số dòng thực hiện dc

                    if (result == 1) {
                        editHistoryInterface.editSuccess();
                        Log.e("Log", "Edit successfully!!!");
                        connection.close();
                    } else {
                        editHistoryInterface.editError("Edit unsuccessfully!!!");
                        Log.e("Log", "Edit unsuccessfully!!!");
                    }
                } else {
                    editHistoryInterface.connectFailed();
                    Log.e("Log", "Connection failed!!!");
                }
            } catch (Exception ex) {
                Log.e("ERUs", ex.getMessage());
            }
        } else if (record.getQty() == 0) {
            editHistoryInterface.editError("Choose participants!!!");
            Log.e("Log", "Edit unsuccessfully!!!");
        } else if (record.getDescription().trim().isEmpty()) {
            editHistoryInterface.editError("Description cannot be blank!!!");
            Log.e("Log", "Edit unsuccessfully!!!");
        } else if (record.getTotal() == 0) {
            editHistoryInterface.editError("Total cannot be 0!!!");
            Log.e("Log", "Edit unsuccessfully!!!");
        } else {
            editHistoryInterface.editError("Edit unsuccessfully!!!");
            Log.e("Log", "Edit unsuccessfully!!!");
        }
    }
}

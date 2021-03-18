package com.dofl.qlct.presenter;

import android.util.Log;

import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.utils.DataProcessing;
import com.dofl.qlct.presenter.utils.JDBC;

import java.sql.Connection;
import java.sql.Statement;

public class AddPresenter {
    private final AddInterface addInterface;

    public AddPresenter(AddInterface addInterface) {
        this.addInterface = addInterface;
    }

    public void addRecord(Record record) {
        if (record.getQty() != 0) {
            record = DataProcessing.processing(record);
            try {
                Connection connection = JDBC.getConnection();
                if (connection != null) {
                    String query = "INSERT INTO record (total, qty, buyer, n1_qty, n1_total, n2_qty, n2_total, n3_qty, n3_total, n4_qty, n4_total, time_create, date_create, package_number, description) VALUES (\'" + record.getTotal() + "\', \'" + record.getQty() + "\', \'" + record.getBuyer() + "\', \'" + record.getN1_qty() + "\', \'" + record.getN1_total() + "\', \'" + record.getN2_qty() + "\', \'" + record.getN2_total() + "\', \'" + record.getN3_qty() + "\', \'" + record.getN3_total() + "\', \'" + record.getN4_qty() + "\', \'" + record.getN4_total() + "\', \'" + record.getTime_create() + "\', N\'" + record.getDate_create() + "\', \'" + record.getPackage_number() + "\', N\'" + record.getDescription() + "\')";

                    Log.e("Log", query);
                    Statement stmt = connection.createStatement();
                    int result = stmt.executeUpdate(query);//thực thi lệnh, trả về số dòng thực hiện dc

                    if (result == 1) {
                        addInterface.addSuccess();
                        Log.e("Log", "Add successfully!!!");
                        connection.close();
                    } else {
                        addInterface.addError();
                        Log.e("Log", "Add unsuccessfully!!!");
                    }
                } else {
                    addInterface.connectFailed();
                    Log.e("Log", "Connection failed!!!");
                }
            } catch (Exception ex) {
                Log.e("ERUs", ex.getMessage());
            }
        } else {
            addInterface.addError();
            Log.e("Log", "Add unsuccessfully!!!");
        }
    }
}

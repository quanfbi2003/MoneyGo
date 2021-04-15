package com.dofl.qlct.presenter;

import android.util.Log;

import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.utils.JDBC;

import java.sql.Connection;
import java.sql.Statement;

public class AddPresenter {
    private final AddInterface addInterface;

    public AddPresenter(AddInterface addInterface) {
        this.addInterface = addInterface;
    }

    public void addRecord(Record record) {
        record.setQty(record.getN1Qty() + record.getN2Qty() + record.getN3Qty() + record.getN4Qty());
        if (record.getQty() != 0 && !record.getDescription().trim().isEmpty() && record.getTotal() != 0) {
            try {
                Connection connection = JDBC.getConnection();
                if (connection != null) {
                    String query = "INSERT INTO Record (Id, Total, Description, TimeCreate, DateCreate, " +
                            "Buyer, N1Qty, N2Qty, N3Qty, N4Qty, PackageNumber) VALUES (N\'" +
                            record.getId() + "\', \'" +
                            record.getTotal() + "\', N\'" + record.getDescription() + "\', N\'" +
                            record.getTimeCreate() + "\', N\'" + record.getDateCreate() + "\', \'" +
                            record.getBuyer() + "\', \'" + record.getN1Qty() + "\', \'" +
                            record.getN2Qty() + "\', \'" + record.getN3Qty() + "\', \'" +
                            record.getN4Qty() + "\', \'" + record.getPackageNumber() + "\')";

                    Log.e("Log", query);
                    Statement stmt = connection.createStatement();
                    int result = stmt.executeUpdate(query);//thực thi lệnh, trả về số dòng thực hiện dc

                    if (result == 1) {
                        addInterface.addSuccess(new Record(record.getId(), record.getTotal(),
                                record.getDescription(),
                                record.getTimeCreate(), record.getDateCreate(),
                                record.getBuyer(), record.getN1Qty(), record.getN2Qty(),
                                record.getN3Qty(), record.getN4Qty(), record.getPackageNumber()));
                        Log.e("Log", "Add successfully!!!");
                        connection.close();
                    } else {
                        addInterface.addError("Add unsuccessfully!!!");
                        Log.e("Log", "Add unsuccessfully!!!");
                    }
                } else {
                    addInterface.connectFailed();
                    Log.e("Log", "Connection failed!!!");
                }
            } catch (Exception ex) {
                Log.e("ERUs", ex.getMessage());
            }
        } else if (record.getQty() == 0) {
            addInterface.addError("Choose participants!!!");
            Log.e("Log", "Add unsuccessfully!!!");
        } else if (record.getDescription().trim().isEmpty()) {
            addInterface.addError("Description cannot be blank!!!");
            Log.e("Log", "Add unsuccessfully!!!");
        } else if (record.getTotal() == 0) {
            addInterface.addError("Total cannot be 0!!!");
            Log.e("Log", "Add unsuccessfully!!!");
        } else {
            addInterface.addError("Add unsuccessfully!!!");
            Log.e("Log", "Add unsuccessfully!!!");
        }
    }
}

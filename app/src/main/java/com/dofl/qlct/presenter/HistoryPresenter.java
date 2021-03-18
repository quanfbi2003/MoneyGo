package com.dofl.qlct.presenter;

import android.util.Log;

import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.utils.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HistoryPresenter {
    private HistoryInterface historyInterface;

    public HistoryPresenter(HistoryInterface historyInterface) {
        this.historyInterface = historyInterface;
    }

    public ArrayList<Record> getRecordHistory(Account account) {
        ArrayList<Record> recordArrayList = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            if (connection != null) {
                ResultSet resultSet;
                String query = "SELECT * FROM record WHERE buyer ='" + account.getId() + "' AND package_number = '" + account.getPackageNumber() + "'";
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(query);//thực thi lệnh, trả về số dòng thực hiện dc

                while (resultSet.next()) {
                    Record record = new Record();
                    record.setBuyer(account.getId());
                    record.setPackage_number(account.getPackageNumber());
                    record.setTotal(resultSet.getInt("total"));
                    record.setN1_qty(resultSet.getInt("n1_qty"));
                    record.setN1_total(resultSet.getDouble("n1_total"));
                    record.setN2_qty(resultSet.getInt("n2_qty"));
                    record.setN2_total(resultSet.getDouble("n2_total"));
                    record.setN3_qty(resultSet.getInt("n3_qty"));
                    record.setN3_total(resultSet.getDouble("n3_total"));
                    record.setN4_qty(resultSet.getInt("n4_qty"));
                    record.setN4_total(resultSet.getDouble("n4_total"));
                    record.setTime_create((resultSet.getString("time_create")));
                    record.setDate_create(resultSet.getString("date_create"));
                    record.setDescription(resultSet.getString("description"));

                    recordArrayList.add(record);
                }
            } else {
                historyInterface.connectFailed();
                Log.e("Log", "Connection failed!!!");
            }
        } catch (Exception ex) {
            Log.e("ERUs", ex.getMessage());
        }
        return recordArrayList;
    }
}

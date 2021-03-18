package com.dofl.qlct.presenter;

import android.util.Log;

import com.dofl.qlct.R;
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
                    record.setId(resultSet.getInt("id"));
                    record.setTotal(resultSet.getInt("total"));
                    record.setDescription(resultSet.getString("description"));
                    record.setTime_create((resultSet.getString("time_create")));
                    record.setDate_create(resultSet.getString("date_create"));
                    record.setBuyer(account.getId());
                    record.setN1_qty(resultSet.getInt("n1_qty"));
                    record.setN2_qty(resultSet.getInt("n2_qty"));
                    record.setN3_qty(resultSet.getInt("n3_qty"));
                    record.setN4_qty(resultSet.getInt("n4_qty"));
                    record.setPackage_number(account.getPackageNumber());
                    record.setIcon(R.mipmap.ic_launcher);
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

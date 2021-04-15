package com.dofl.qlct.presenter;

import android.util.Log;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.MPM;
import com.dofl.qlct.model.Maintenance;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.utils.Hash;
import com.dofl.qlct.presenter.utils.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UpdatePresenter {
    private final UpdateInterface updateInterface;

    public UpdatePresenter(UpdateInterface updateInterface) {
        this.updateInterface = updateInterface;
    }

    public List<Account> getListAccount() {
        List<Account> listAccount = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            if (connection != null) {
                ResultSet resultSet;
                String query = "SELECT * FROM Account";
                Statement statement = connection.createStatement();
                Log.e("Log: ", query);
                resultSet = statement.executeQuery(query);//thực thi lệnh, trả về số dòng thực hiện dc

                while (resultSet.next()) {
                    Account account = new Account();
                    account.setId(resultSet.getInt("Id"));
                    account.setPreviousMoney(resultSet.getInt("PreviousMoney"));
                    account.setMoneyPaid(resultSet.getInt("MoneyPaid"));
                    listAccount.add(account);
                }
            } else {
                updateInterface.reconnectListAccount();
                Log.e("Log", "Connection failed!!!");
            }
        } catch (Exception ex) {
            Log.e("ERUs", ex.getMessage());
        }
        return listAccount;
    }

    public List<Record> getListRecord(Account account) {
        List<Record> listRecord = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            if (connection != null) {
                ResultSet resultSet;
                String query = "SELECT * FROM Record WHERE PackageNumber = '" +
                        account.getPackageNumber() + "'";
                Statement statement = connection.createStatement();
                Log.e("Log: ", query);
                resultSet = statement.executeQuery(query);//thực thi lệnh, trả về số dòng thực hiện dc

                while (resultSet.next()) {
                    Record record = new Record();
                    record.setId(resultSet.getString("Id"));
                    record.setTotal(resultSet.getInt("Total"));
                    record.setDescription(resultSet.getString("Description"));
                    record.setTimeCreate((resultSet.getString("TimeCreate")));
                    record.setDateCreate(resultSet.getString("DateCreate"));
                    record.setBuyer(resultSet.getInt("Buyer"));
                    record.setN1Qty(resultSet.getInt("N1Qty"));
                    record.setN2Qty(resultSet.getInt("N2Qty"));
                    record.setN3Qty(resultSet.getInt("N3Qty"));
                    record.setN4Qty(resultSet.getInt("N4Qty"));
                    record.setPackageNumber(account.getPackageNumber());
                    record.setIcon(R.mipmap.ic_launcher);
                    listRecord.add(record);
                }
            } else {
                updateInterface.reconnectListRecord();
                Log.e("Log", "Connection failed!!!");
            }
        } catch (Exception ex) {
            Log.e("ERUs", ex.getMessage());
        }
        return listRecord;
    }

    public List<Record> getListPreviousRecord(Account account) {
        ArrayList<Record> recordArrayList = new ArrayList<>();
        try {
            Connection connection = JDBC.getConnection();
            if (connection != null) {
                ResultSet resultSet;
                String query = "SELECT * FROM Record WHERE PackageNumber = '" +
                        (account.getPackageNumber() - 1) + "'";
                Statement statement = connection.createStatement();
                Log.e("Log: ", query);
                resultSet = statement.executeQuery(query);//thực thi lệnh, trả về số dòng thực hiện dc

                while (resultSet.next()) {
                    Record record = new Record();
                    record.setId(resultSet.getString("Id"));
                    record.setTotal(resultSet.getInt("Total"));
                    record.setDescription(resultSet.getString("Description"));
                    record.setTimeCreate((resultSet.getString("TimeCreate")));
                    record.setDateCreate(resultSet.getString("DateCreate"));
                    record.setBuyer(resultSet.getInt("Buyer"));
                    record.setN1Qty(resultSet.getInt("N1Qty"));
                    record.setN2Qty(resultSet.getInt("N2Qty"));
                    record.setN3Qty(resultSet.getInt("N3Qty"));
                    record.setN4Qty(resultSet.getInt("N4Qty"));
                    record.setPackageNumber(account.getPackageNumber());
                    record.setIcon(R.mipmap.ic_launcher);
                    recordArrayList.add(record);
                }
            } else {
                updateInterface.reconnectListPreviousRecord();
                Log.e("Log", "Connection failed!!!");
            }
        } catch (Exception ex) {
            Log.e("ERUs", ex.getMessage());
        }
        return recordArrayList;
    }

    public MPM getPreviousMPM(Account account) {
        MPM previousMPM = new MPM();
        try {
            Connection connection = JDBC.getConnection();
            if (connection != null) {
                ResultSet resultSet;
                String query = "SELECT * FROM MoneyPerMonth WHERE PackageNumber = '" +
                        (account.getPackageNumber() - 1) + "'";
                Statement statement = connection.createStatement();
                Log.e("Log: ", query);
                resultSet = statement.executeQuery(query);//thực thi lệnh, trả về số dòng thực hiện dc

                if (resultSet.next()) {
                    previousMPM.setMonthOfYear(resultSet.getString("MonthOfYear"));
                    previousMPM.setMonth(resultSet.getInt("Month"));
                    previousMPM.setNumberOfElectricity(resultSet.getInt("NumberOfElectricity"));
                    previousMPM.setNumberOfWater(resultSet.getInt("NumberOfWater"));
                    previousMPM.setAirConditional((resultSet.getInt("AirConditional")));
                    previousMPM.setPackageNumber(account.getPackageNumber() - 1);
                    previousMPM.setStartDate(resultSet.getString("StartDate"));
                    previousMPM.setEndDate(resultSet.getString("EndDate"));
                }
            } else {
                updateInterface.reconnectPreviousMPM();
                Log.e("Log", "Connection failed!!!");
            }
        } catch (Exception ex) {
            Log.e("ERUs", ex.getMessage());
        }
        return previousMPM;
    }

    public MPM getPrePreviousMPM(Account account) {
        MPM prePreviousMPM = new MPM();
        try {
            Connection connection = JDBC.getConnection();
            if (connection != null) {
                ResultSet resultSet;
                String query = "SELECT * FROM MoneyPerMonth WHERE PackageNumber = '" +
                        (account.getPackageNumber() - 2) + "'";
                Statement statement = connection.createStatement();
                Log.e("Log: ", query);
                resultSet = statement.executeQuery(query);//thực thi lệnh, trả về số dòng thực hiện dc

                if (resultSet.next()) {
                    prePreviousMPM.setMonthOfYear(resultSet.getString("MonthOfYear"));
                    prePreviousMPM.setMonth(resultSet.getInt("Month"));
                    prePreviousMPM.setNumberOfElectricity(resultSet.getInt("NumberOfElectricity"));
                    prePreviousMPM.setNumberOfWater(resultSet.getInt("NumberOfWater"));
                    prePreviousMPM.setAirConditional((resultSet.getInt("AirConditional")));
                    prePreviousMPM.setPackageNumber(account.getPackageNumber() - 2);
                    prePreviousMPM.setStartDate(resultSet.getString("StartDate"));
                    prePreviousMPM.setEndDate(resultSet.getString("EndDate"));
                }
            } else {
                updateInterface.reconnectPrePreviousMPM();
                Log.e("Log", "Connection failed!!!");
            }
        } catch (Exception ex) {
            Log.e("ERUs", ex.getMessage());
        }
        return prePreviousMPM;
    }

    public Maintenance getMaintenance() {
        Maintenance maintenance = new Maintenance();
        try {
            Connection connection = JDBC.getConnection();
            if (connection != null) {
                ResultSet resultSet;
                String query = "SELECT * FROM Maintenance";
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(query);//thực thi lệnh, trả về số dòng thực hiện dc

                if (resultSet.next()) {
                    if (resultSet.getInt("Maintenance") == 1) {
                        maintenance.setMaintenance(true);
                    } else {
                        maintenance.setMaintenance(false);
                    }
                    maintenance.setnNetMoney(resultSet.getInt("NNetMoney"));

                    Log.e("Log", "Connect successfully!!!");
                    connection.close();

                } else {
                    updateInterface.reconnectMaintenance();
                    Log.e("Log", "Connection failed!!!");
                }
            } else {
                updateInterface.connectFailed();
                Log.e("Log", "Connection failed!!!");
            }
        } catch (Exception ex) {
            Log.e("ERUs", ex.getMessage());
        }
        return maintenance;
    }
}

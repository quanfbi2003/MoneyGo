package com.dofl.qlct.presenter;

import android.util.Log;

import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.MPM;
import com.dofl.qlct.model.Maintenance;
import com.dofl.qlct.presenter.utils.JDBC;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class SettlementPresenter {
    private final SettlementInterface settlementInterface;

    public SettlementPresenter(SettlementInterface settlementInterface) {
        this.settlementInterface = settlementInterface;
    }

    public void addMPM(List<Account> listAccount, MPM presentMPM, Maintenance maintenance) {
        if (presentMPM.getNumberOfElectricity() != 0 || presentMPM.getNumberOfWater() != 0) {
            try {
                Connection connection = JDBC.getConnection();
                if (connection != null) {
                    String query = "INSERT INTO MoneyPerMonth (MonthOfYear, Month, NumberOfElectricity," +
                            " NumberOfWater, AirConditional, PackageNumber, StartDate, EndDate) " +
                            "VALUES (N\'" +
                            presentMPM.getMonthOfYear() + "\', \'" + presentMPM.getMonth() + "\', \'" +
                            presentMPM.getNumberOfElectricity() + "\', N\'" + presentMPM.getNumberOfWater() + "\', \'" +
                            presentMPM.getAirConditional() + "\', \'" + presentMPM.getPackageNumber() + "\', N\'" +
                            presentMPM.getStartDate() + "\', N\'" + presentMPM.getEndDate() + "\')";

                    Log.e("Log", query);
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate(query);//thực thi lệnh, trả về số dòng thực hiện dc

                    query = "UPDATE Maintenance SET NNetMoney = \'" +
                            maintenance.getnNetMoney() + "\'";

                    Log.e("Log", query);
                    int result = stmt.executeUpdate(query);

                    for (Account account : listAccount) {
                        query = "UPDATE Account SET PreviousMoney = \'" +
                                account.getPreviousMoney() + "\', MoneyPaid = \'" +
                                account.getMoneyPaid() +"\', StartDate = N\'" +
                                account.getStartDate() + "\', PackageNumber = \'" +
                                account.getPackageNumber() + "\' WHERE Id = \'" +
                                account.getId() + "\'";

                        Log.e("Log", query);
                        stmt = connection.createStatement();
                        result = stmt.executeUpdate(query);
                    }
                    if (result == 1) {
                        settlementInterface.addSuccess();
                        Log.e("Log", "Add successfully!!!");
                        connection.close();
                    } else {
                        settlementInterface.addError("Add unsuccessfully!!!");
                        Log.e("Log", "Add unsuccessfully!!!");
                    }
                } else {
                    settlementInterface.connectFailed();
                    Log.e("Log", "Connection failed!!!");
                }
            } catch (Exception ex) {
                Log.e("ERUs", ex.getMessage());
            }
        } else {
            settlementInterface.addError("Add unsuccessfully!!!");
            Log.e("Log", "Add unsuccessfully!!!");
        }
    }
}

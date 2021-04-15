package com.dofl.qlct.presenter;

import android.util.Log;

import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.Maintenance;
import com.dofl.qlct.presenter.utils.JDBC;

import java.sql.Connection;
import java.sql.Statement;

public class PaymentPresenter {
    private final PaymentInterface paymentInterface;

    public PaymentPresenter(PaymentInterface paymentInterface) {
        this.paymentInterface = paymentInterface;
    }

    public void addMPM(Account account, Maintenance maintenance) {
        try {
            Connection connection = JDBC.getConnection();
            if (connection != null) {
                String query = "UPDATE Maintenance SET NNetMoney = \'" +
                        maintenance.getnNetMoney() + "\'";

                Log.e("Log", query);
                Statement stmt = connection.createStatement();
                int result = stmt.executeUpdate(query);
                if (account != null) {
                    query = "UPDATE Account SET MoneyPaid = \'" +
                            account.getMoneyPaid() + "\' WHERE Id = \'" + account.getId() + "\'";

                    Log.e("Log", query);
                    result = stmt.executeUpdate(query);
                }
                if (result == 1) {
                    paymentInterface.addSuccess();
                    Log.e("Log", "Add successfully!!!");
                    connection.close();
                } else {
                    paymentInterface.addError("Add unsuccessfully!!!");
                    Log.e("Log", "Add unsuccessfully!!!");
                }
            } else {
                paymentInterface.connectFailed();
                Log.e("Log", "Connection failed!!!");
            }
        } catch (Exception ex) {
            Log.e("ERUs", ex.getMessage());
        }

    }
}

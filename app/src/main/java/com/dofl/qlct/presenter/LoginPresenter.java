package com.dofl.qlct.presenter;

import android.util.Log;

import com.dofl.qlct.model.Account;
import com.dofl.qlct.presenter.utils.Hash;
import com.dofl.qlct.presenter.utils.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginPresenter {
    private LoginInterface loginInterface;

    public LoginPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }

    public void login(Account account) {
        try {
            Connection connection = JDBC.getConnection();
            if (connection != null) {
                ResultSet resultSet;
                String query = "SELECT * FROM account WHERE username ='" + account.getUsername() + "' AND password = '" + Hash.md5(account.getPassword()) + "'";
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(query);//thực thi lệnh, trả về số dòng thực hiện dc

                if (resultSet.next()) {
                    account.setId(resultSet.getInt("id"));
                    account.setDisplayName(resultSet.getString("display_name"));
                    account.setRole(resultSet.getString("role"));
                    account.setPackageNumber(resultSet.getInt("package_number"));
                    loginInterface.loginSuccess(account);
                    Log.e("Log", "Login successfully!!!");
                    connection.close();
                } else {
                    loginInterface.loginError();
                    Log.e("Log", "Login unsuccessfully!!!");
                }
            } else {
                loginInterface.connectFailed();
                Log.e("Log", "Connection failed!!!");
            }
        } catch (Exception ex) {
            Log.e("ERUs", ex.getMessage());
        }


    }
}

package com.dofl.qlct.presenter.utils;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static String DB_URL = "jdbc:mysql://freedb.tech/freedbtech_doflqlct?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static String USER_NAME = "freedbtech_quanjullian";
    private static String PASSWORD = "Q1U3A0N3";

    @SuppressLint("NewApi")
    public static Connection getConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            Log.e("Log", "Connect successfully!!!");
        } catch (SQLException se) {
            Log.e("ERROR1", "Không thể tải lớp Driver! " + se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR2", "Xuất hiện vấn đề truy cập trong khi tải! " + e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR3", "Không thể khởi tạo Driver! " + e.getMessage());
        }
        return connection;

    }
}

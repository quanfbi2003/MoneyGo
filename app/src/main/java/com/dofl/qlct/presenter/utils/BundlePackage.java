package com.dofl.qlct.presenter.utils;

import android.content.Intent;
import android.os.Bundle;

import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.Record;

public class BundlePackage {
    public static Bundle setBundleAccount(Account account) {
        Bundle bundle = new Bundle();
        bundle.putInt("account_id", account.getId());
        bundle.putString("username", account.getUsername());
        bundle.putString("role", account.getRole());
        bundle.putString("display_name", account.getDisplayName());
        bundle.putInt("package_number", account.getPackageNumber());
        return bundle;
    }

    public static Bundle setBundleRecord(Record record) {
        Bundle bundle = new Bundle();
        bundle.putInt("record_id", record.getId());
        bundle.putInt("total", record.getTotal());
        bundle.putString("description", record.getDescription());
        bundle.putString("time_create", record.getTime_create());
        bundle.putString("date_create", record.getDate_create());
        bundle.putInt("buyer", record.getBuyer());
        bundle.putInt("n1_qty", record.getN1_qty());
        bundle.putInt("n2_qty", record.getN2_qty());
        bundle.putInt("n3_qty", record.getN3_qty());
        bundle.putInt("n4_qty", record.getN4_qty());
        return bundle;
    }

    public static Account getBundleAccount(Intent intent) {
        Bundle bundle = intent.getExtras();
        Account account = null;
        if (bundle != null) {
            account = new Account(bundle.getInt("account_id"), bundle.getString("username"),
                    bundle.getString("role"), bundle.getString("display_name"),
                    bundle.getInt("package_number"));
        }
        return account;
    }

    public static Record getBundleRecord(Intent intent) {
        Record record = null;
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            record = new Record(bundle.getInt("record_id"), bundle.getInt("total"),
                    bundle.getString("description"), bundle.getString("time_create"),
                    bundle.getString("date_create"), bundle.getInt("buyer"),
                    bundle.getInt("n1_qty"), bundle.getInt("n2_qty"),
                    bundle.getInt("n3_qty"), bundle.getInt("n4_qty"),
                    bundle.getInt("package_number"));
        }
        return record;
    }
}

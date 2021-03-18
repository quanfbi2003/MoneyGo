package com.dofl.qlct.presenter.utils;

import android.content.Intent;
import android.os.Bundle;

import com.dofl.qlct.model.Account;

public class BundlePackage {
    public static Bundle setBundle(Account account) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", account.getId());
        bundle.putString("username", account.getUsername());
        bundle.putString("role", account.getRole());
        bundle.putString("displayName", account.getDisplayName());
        bundle.putInt("packageNumber", account.getPackageNumber());
        return bundle;
    }

    public static Account getBundle(Intent intent) {
        Account account = new Account();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            account = new Account(bundle.getInt("id"), bundle.getString("username"), bundle.getString("role"), bundle.getString("displayName"), bundle.getInt("packageNumber"));
        }
        return account;
    }
}

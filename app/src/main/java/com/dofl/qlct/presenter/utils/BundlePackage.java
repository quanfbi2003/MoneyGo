package com.dofl.qlct.presenter.utils;

import android.content.Intent;
import android.os.Bundle;

import com.dofl.qlct.model.Record;

public class BundlePackage {

    public static Bundle setBundleRecord(Record record) {
        Bundle bundle = new Bundle();
        bundle.putString("Id", record.getId());
        bundle.putInt("Total", record.getTotal());
        bundle.putString("Description", record.getDescription());
        bundle.putString("TimeCreate", record.getTimeCreate());
        bundle.putString("DateCreate", record.getDateCreate());
        bundle.putInt("Buyer", record.getBuyer());
        bundle.putInt("N1Qty", record.getN1Qty());
        bundle.putInt("N2Qty", record.getN2Qty());
        bundle.putInt("N3Qty", record.getN3Qty());
        bundle.putInt("N4Qty", record.getN4Qty());
        return bundle;
    }

    public static Record getBundleRecord(Intent intent) {
        Record record = null;
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            record = new Record(bundle.getString("Id"), bundle.getInt("Total"),
                    bundle.getString("Description"), bundle.getString("TimeCreate"),
                    bundle.getString("DateCreate"), bundle.getInt("Buyer"),
                    bundle.getInt("N1Qty"), bundle.getInt("N2Qty"),
                    bundle.getInt("N3Qty"), bundle.getInt("N4Qty"),
                    bundle.getInt("PackageNumber"));
        }
        return record;
    }
}

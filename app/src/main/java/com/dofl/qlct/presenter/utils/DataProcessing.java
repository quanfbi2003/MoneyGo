package com.dofl.qlct.presenter.utils;

import com.dofl.qlct.model.Record;

public class DataProcessing {
    public static Record processing(Record record) {
        record.setQty(record.getN1_qty() + record.getN2_qty() + record.getN3_qty() + record.getN4_qty());

        int n1_total = (int) (((double) record.getTotal() / record.getQty()) * record.getN1_qty());
        int n2_total = (int) (((double) record.getTotal() / record.getQty()) * record.getN2_qty());
        int n3_total = (int) (((double) record.getTotal() / record.getQty()) * record.getN3_qty());
        int n4_total = (int) (((double) record.getTotal() / record.getQty()) * record.getN4_qty());

        switch (record.getBuyer()) {
            case 1:
                n1_total -= record.getTotal();
                break;
            case 2:
                n2_total -= record.getTotal();
                break;
            case 3:
                n3_total -= record.getTotal();
                break;
            default:
                n4_total -= record.getTotal();
                break;
        }

        record.setN1_total(n1_total);
        record.setN2_total(n2_total);
        record.setN3_total(n3_total);
        record.setN4_total(n4_total);

        return record;
    }
}

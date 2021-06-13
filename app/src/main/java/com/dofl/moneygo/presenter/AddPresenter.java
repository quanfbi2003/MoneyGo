package com.dofl.moneygo.presenter;

import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.Record;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddPresenter {
    private final AddInterface addInterface;
    private final DatabaseReference databaseReference;

    public AddPresenter(AddInterface addInterface) {
        this.addInterface = addInterface;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void addRecord(Account account, MoneyPackage presentMoneyPackage, Record record) {
        record.setMoneyPackage(account.getPresentMoneyPackage());
        record.setSummaryPackage(presentMoneyPackage.getSummaryPackage());
        String key = databaseReference.child("Record").child(presentMoneyPackage.getRecordPackage())
                .push().getKey();
        databaseReference.child("Record").child(presentMoneyPackage.getRecordPackage())
                .child((record.getDateCreate())
                        .replaceAll("/", ":")
                        + " - " + record.getTimeCreate() + " - " + key)
                .setValue(record).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                addInterface.addSuccess();
            } else {
                addInterface.addError(Objects.requireNonNull(task.getException()).toString());
            }
        });
    }
}

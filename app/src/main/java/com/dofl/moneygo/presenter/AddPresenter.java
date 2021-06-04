package com.dofl.moneygo.presenter;

import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.Record;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPresenter {
    private final AddInterface addInterface;

    public AddPresenter(AddInterface addInterface) {
        this.addInterface = addInterface;

    }

    public void addRecord(MoneyPackage presentMoneyPackage, Record record) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("Record").child(presentMoneyPackage.getRecordPackage());
        String key = databaseReference.push().getKey();
        databaseReference.child((record.getDateCreate())
                .replaceAll("/", ":")
                + " - " + record.getTimeCreate() + " - " + key)
                .setValue(record).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                addInterface.addSuccess(record);
            } else {
                addInterface.addError(task.getException().toString());
            }
        });
    }

//    public void update(MoneyPackage presentMoneyPackage, Record record) {
}

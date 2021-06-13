package com.dofl.moneygo.presenter;

import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.Record;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class EditHistoryPresenter {
    private final EditHistoryInterface editHistoryInterface;
    private final DatabaseReference databaseReference;

    public EditHistoryPresenter(EditHistoryInterface editHistoryInterface) {
        this.editHistoryInterface = editHistoryInterface;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void editRecord(Account account, MoneyPackage presentMoneyPackage, Record recordNew,
                           Record record, String keyOld) {
//        databaseReference.child("Record").child(record.getMoneyPackage()).child(keyOld)
//                .removeValue();
        recordNew.setMoneyPackage(account.getPresentMoneyPackage());
        recordNew.setSummaryPackage(presentMoneyPackage.getSummaryPackage());
//        if (!record.getDateCreate().equalsIgnoreCase(recordNew.getDateCreate())
//                || !record.getTimeCreate().equalsIgnoreCase(recordNew.getTimeCreate())) {
//            String key = databaseReference.child("Record").child(presentMoneyPackage
//                    .getRecordPackage())
//                    .push().getKey();
//            databaseReference.child("Record").child(presentMoneyPackage.getRecordPackage())
//                    .child((recordNew.getDateCreate())
//                            .replaceAll("/", ":")
//                            + " - " + recordNew.getTimeCreate() + " - " + key)
//                    .setValue(recordNew).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    editHistoryInterface.addSuccess();
//                } else {
//                    editHistoryInterface.addError(Objects
//                            .requireNonNull(task.getException()).toString());
//                }
//            });
//        } else {
        databaseReference.child("Record").child(presentMoneyPackage.getRecordPackage())
                .child(keyOld)
                .setValue(recordNew).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                editHistoryInterface.editSuccess();
            } else {
                editHistoryInterface.editError(Objects
                        .requireNonNull(task.getException()).toString());
            }
        });
//        }
    }
}

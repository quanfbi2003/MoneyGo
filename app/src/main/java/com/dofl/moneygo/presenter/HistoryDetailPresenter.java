package com.dofl.moneygo.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class HistoryDetailPresenter {
    private final HistoryDetailInterface historyDetailInterface;
    private final DatabaseReference databaseReference;

    public HistoryDetailPresenter(HistoryDetailInterface historyDetailInterface) {
        this.historyDetailInterface = historyDetailInterface;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void removeRecord(String presentRecordPackage, String key) {
        databaseReference.child("Record").child(presentRecordPackage).child(key)
                .removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                historyDetailInterface.removeSuccess();
            } else {
                historyDetailInterface.removeError(Objects
                        .requireNonNull(task.getException()).toString());
            }
        });
    }
}

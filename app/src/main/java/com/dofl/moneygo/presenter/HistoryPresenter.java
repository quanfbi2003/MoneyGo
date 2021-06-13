package com.dofl.moneygo.presenter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.Record;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HistoryPresenter {
    private final DatabaseReference databaseReference;
    private final HistoryInterface historyInterface;
    private final Map<String, Record> presentRecordPackage;
    private final Map<String, Record> previousRecordPackage;

    public HistoryPresenter(HistoryInterface historyInterface) {
        this.historyInterface = historyInterface;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        presentRecordPackage = new HashMap<>();
        previousRecordPackage = new HashMap<>();
    }

    public void updateRecordPackage(MoneyPackage presentMoneyPackage, MoneyPackage previousMoneyPackage) {
        databaseReference.child("Record").child(presentMoneyPackage.getRecordPackage())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        Record record = snapshot.getValue(Record.class);
                        presentRecordPackage.put(snapshot.getKey(), record);
                        historyInterface.updatePresentRecordPackage(presentRecordPackage);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        Record record = snapshot.getValue(Record.class);
                        presentRecordPackage.replace(snapshot.getKey(), record);
                        historyInterface.updatePresentRecordPackage(presentRecordPackage);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Record record = snapshot.getValue(Record.class);
                        presentRecordPackage.remove(snapshot.getKey());
                        historyInterface.updatePresentRecordPackage(presentRecordPackage);
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        historyInterface.updateError(error.toString());
                    }
                });

        databaseReference.child("Record").child(previousMoneyPackage.getRecordPackage())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        Record record = snapshot.getValue(Record.class);
                        previousRecordPackage.put(snapshot.getKey(), record);
                        historyInterface.updatePreviousRecordPackage(previousRecordPackage);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        Record record = snapshot.getValue(Record.class);
                        previousRecordPackage.replace(snapshot.getKey(), record);
                        historyInterface.updatePreviousRecordPackage(previousRecordPackage);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Record record = snapshot.getValue(Record.class);
                        previousRecordPackage.remove(snapshot.getKey(), record);
                        historyInterface.updatePreviousRecordPackage(previousRecordPackage);
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        historyInterface.updateError(error.toString());
                    }
                });
        Log.e("Update5", "RecordPackageSuccess");
    }
}

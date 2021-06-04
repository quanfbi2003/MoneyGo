package com.dofl.moneygo.presenter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.Record;
import com.dofl.moneygo.model.RegisteredAccount;
import com.dofl.moneygo.model.Summary;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MenuPresenter {
    private final DatabaseReference databaseReference;
    private final MenuInterface menuInterface;
    private final Map<String, Record> presentRecordPackage;
    private final Map<String, Record> previousRecordPackage;
    private final RegisteredAccount registeredAccount;
    private Account account;
    private MoneyPackage presentMoneyPackage;
    private MoneyPackage previousMoneyPackage;
    private Summary presentSummaryPackage;
    private Summary previousSummaryPackage;

    public MenuPresenter(MenuInterface menuInterface) {
        this.menuInterface = menuInterface;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        registeredAccount = new RegisteredAccount();
        presentRecordPackage = new HashMap<>();
        previousRecordPackage = new HashMap<>();
    }

    public void updateRegisteredAccount() {
        databaseReference.child("Account").child("Registered Account")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        registeredAccount.setN1(snapshot.child("1").getValue().toString());
                        registeredAccount.setN2(snapshot.child("2").getValue().toString());
                        registeredAccount.setN3(snapshot.child("3").getValue().toString());
                        registeredAccount.setN4(snapshot.child("4").getValue().toString());
                        menuInterface.updateRegisteredAccountSuccess(registeredAccount);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        menuInterface.updateError(error.toString());
                    }
                });
    }

    public void updateAccount(Account acc) {
        String key = null;
        if (registeredAccount.getN1().equalsIgnoreCase(acc.getEmail())) {
            key = "1";
        }
        if (registeredAccount.getN2().equalsIgnoreCase(acc.getEmail())) {
            key = "2";
        }
        if (registeredAccount.getN3().equalsIgnoreCase(acc.getEmail())) {
            key = "3";
        }
        if (registeredAccount.getN4().equalsIgnoreCase(acc.getEmail())) {
            key = "4";
        }
        databaseReference.child("Account").child(key)
                .child("Account Details")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        account = snapshot.getValue(Account.class);
                        menuInterface.updateAccountSuccess(account);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        Account account = snapshot.getValue(Account.class);
                        menuInterface.updateAccountSuccess(account);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        menuInterface.updateError(error.toString());
                    }
                });


    }

    public void updateMoneyPackage() {
        databaseReference.child("Account").child(account.getId() + "")
                .child("Money Package")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        if (Objects.requireNonNull(snapshot.getKey())
                                .equalsIgnoreCase(account.getPresentMoneyPackage())) {
                            presentMoneyPackage = snapshot.getValue(MoneyPackage.class);
                            menuInterface.updatePresentMoneyPackageSuccess(presentMoneyPackage);
                        }
                        if (Objects.requireNonNull(snapshot.getKey())
                                .equalsIgnoreCase(account.getPreviousMoneyPackage())) {
                            previousMoneyPackage = snapshot.getValue(MoneyPackage.class);
                            menuInterface.updatePreviousMoneyPackageSuccess(previousMoneyPackage);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        if (snapshot.getKey()
                                .equalsIgnoreCase(account.getPresentMoneyPackage())) {
                            presentMoneyPackage = snapshot.getValue(MoneyPackage.class);
                            menuInterface.updatePresentMoneyPackageSuccess(presentMoneyPackage);
                        }
                        if (snapshot.getKey()
                                .equalsIgnoreCase(account.getPreviousMoneyPackage())) {
                            previousMoneyPackage = snapshot.getValue(MoneyPackage.class);
                            menuInterface.updatePreviousMoneyPackageSuccess(previousMoneyPackage);
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        menuInterface.updateError(error.toString());
                    }
                });
    }

    public void updateRecordPackage() {
        databaseReference.child("Record").child(presentMoneyPackage.getRecordPackage())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        Record record = snapshot.getValue(Record.class);
                        presentRecordPackage.put(snapshot.getKey(), record);
                        menuInterface.updatePresentRecordPackage(presentRecordPackage);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        Record record = snapshot.getValue(Record.class);
                        presentRecordPackage.replace(snapshot.getKey(), record);
                        menuInterface.updatePresentRecordPackage(presentRecordPackage);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        presentRecordPackage.remove(snapshot.getKey());
                        menuInterface.updatePresentRecordPackage(presentRecordPackage);
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        menuInterface.updateError(error.toString());
                    }
                });

        databaseReference.child("Record").child(previousMoneyPackage.getRecordPackage())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        Record record = snapshot.getValue(Record.class);
                        previousRecordPackage.put(snapshot.getKey(), record);
                        menuInterface.updatePreviousRecordPackage(previousRecordPackage);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        Record record = snapshot.getValue(Record.class);
                        previousRecordPackage.replace(snapshot.getKey(), record);
                        menuInterface.updatePreviousRecordPackage(previousRecordPackage);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Record record = snapshot.getValue(Record.class);
                        previousRecordPackage.remove(snapshot.getKey(), record);
                        menuInterface.updatePreviousRecordPackage(previousRecordPackage);
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        menuInterface.updateError(error.toString());
                    }
                });
        Log.e("Update7", "RecordPackageSuccess");
    }

    public void updateSummaryPackage() {
        databaseReference.child("Summary")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        if (snapshot.getKey()
                                .equalsIgnoreCase(presentMoneyPackage.getSummaryPackage())) {
                            presentSummaryPackage = snapshot.getValue(Summary.class);
                            menuInterface.updatePresentSummaryPackageSuccess(presentSummaryPackage);
                        }
                        if (snapshot.getKey()
                                .equalsIgnoreCase(previousMoneyPackage.getSummaryPackage())) {
                            previousSummaryPackage = snapshot.getValue(Summary.class);
                            menuInterface
                                    .updatePreviousSummaryPackageSuccess(previousSummaryPackage);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        if (snapshot.getKey()
                                .equalsIgnoreCase(presentMoneyPackage.getSummaryPackage())) {
                            presentSummaryPackage = snapshot.getValue(Summary.class);
                            menuInterface.updatePresentSummaryPackageSuccess(presentSummaryPackage);
                        }
                        if (snapshot.getKey()
                                .equalsIgnoreCase(previousMoneyPackage.getSummaryPackage())) {
                            previousSummaryPackage = snapshot.getValue(Summary.class);
                            menuInterface
                                    .updatePreviousSummaryPackageSuccess(previousSummaryPackage);
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        menuInterface.updateError(error.toString());
                    }
                });
    }
}

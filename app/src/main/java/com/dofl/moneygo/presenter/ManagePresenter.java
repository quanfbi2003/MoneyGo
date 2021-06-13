package com.dofl.moneygo.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.MoneyPackage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ManagePresenter {
    private final DatabaseReference databaseReference;
    private final ManageInterface manageInterface;
    private MoneyPackage n1MoneyPackage;
    private MoneyPackage n2MoneyPackage;
    private MoneyPackage n3MoneyPackage;
    private MoneyPackage n4MoneyPackage;

    public ManagePresenter(ManageInterface manageInterface) {
        this.manageInterface = manageInterface;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void updateMoneyPackage(Account account) {
        databaseReference.child("Account").child("1")
                .child("Money Package")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        if (Objects.requireNonNull(snapshot.getKey())
                                .equalsIgnoreCase(account.getPresentMoneyPackage())) {
                            n1MoneyPackage = snapshot.getValue(MoneyPackage.class);
                            manageInterface.updateN1MoneyPackageSuccess(n1MoneyPackage);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        if (Objects.requireNonNull(snapshot.getKey())
                                .equalsIgnoreCase(account.getPresentMoneyPackage())) {
                            n1MoneyPackage = snapshot.getValue(MoneyPackage.class);
                            manageInterface.updateN1MoneyPackageSuccess(n1MoneyPackage);
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
                        manageInterface.updateError(error.toString());
                    }
                });
        databaseReference.child("Account").child("2")
                .child("Money Package")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        if (Objects.requireNonNull(snapshot.getKey())
                                .equalsIgnoreCase(account.getPresentMoneyPackage())) {
                            n2MoneyPackage = snapshot.getValue(MoneyPackage.class);
                            manageInterface.updateN2MoneyPackageSuccess(n2MoneyPackage);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        if (Objects.requireNonNull(snapshot.getKey())
                                .equalsIgnoreCase(account.getPresentMoneyPackage())) {
                            n2MoneyPackage = snapshot.getValue(MoneyPackage.class);
                            manageInterface.updateN2MoneyPackageSuccess(n2MoneyPackage);
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
                        manageInterface.updateError(error.toString());
                    }
                });
        databaseReference.child("Account").child("3")
                .child("Money Package")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        if (Objects.requireNonNull(snapshot.getKey())
                                .equalsIgnoreCase(account.getPresentMoneyPackage())) {
                            n3MoneyPackage = snapshot.getValue(MoneyPackage.class);
                            manageInterface.updateN3MoneyPackageSuccess(n3MoneyPackage);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        if (Objects.requireNonNull(snapshot.getKey())
                                .equalsIgnoreCase(account.getPresentMoneyPackage())) {
                            n3MoneyPackage = snapshot.getValue(MoneyPackage.class);
                            manageInterface.updateN3MoneyPackageSuccess(n3MoneyPackage);
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
                        manageInterface.updateError(error.toString());
                    }
                });
        databaseReference.child("Account").child("4")
                .child("Money Package")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        if (Objects.requireNonNull(snapshot.getKey())
                                .equalsIgnoreCase(account.getPresentMoneyPackage())) {
                            n4MoneyPackage = snapshot.getValue(MoneyPackage.class);
                            manageInterface.updateN4MoneyPackageSuccess(n4MoneyPackage);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        if (Objects.requireNonNull(snapshot.getKey())
                                .equalsIgnoreCase(account.getPresentMoneyPackage())) {
                            n4MoneyPackage = snapshot.getValue(MoneyPackage.class);
                            manageInterface.updateN4MoneyPackageSuccess(n4MoneyPackage);
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
                        manageInterface.updateError(error.toString());
                    }
                });

        databaseReference.child("Neighbor Network").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                manageInterface.updateNeighborNetworkSuccess(Integer
                        .parseInt(Objects.requireNonNull(snapshot.getValue()).toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

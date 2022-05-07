package com.dofl.moneygo.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.RegisteredAccount;
import com.dofl.moneygo.model.Summary;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MenuPresenter {
    private final DatabaseReference databaseReference;
    private final MenuInterface menuInterface;
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
    }

    public void checkMaintenance() {
        databaseReference.child("Maintenance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (Objects.requireNonNull(snapshot.getValue()).toString()
                        .equalsIgnoreCase("true")) {
                    menuInterface.maintenance();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateRegisteredAccount() {
        databaseReference.child("Account").child("Registered Account")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String N1 = Objects.requireNonNull(snapshot.child("1")
                                .getValue()).toString();
                        String N2 = Objects.requireNonNull(snapshot.child("2")
                                .getValue()).toString();
                        String N3 = Objects.requireNonNull(snapshot.child("3")
                                .getValue()).toString();
                        String N4 = Objects.requireNonNull(snapshot.child("4")
                                .getValue()).toString();

                        registeredAccount.setN1(N1.split("\\|")[0]);
                        registeredAccount.setN2(N2.split("\\|")[0]);
                        registeredAccount.setN3(N3.split("\\|")[0]);
                        registeredAccount.setN4(N4.split("\\|")[0]);

                        registeredAccount.setNameN1(N1.split("\\|")[1]);
                        registeredAccount.setNameN2(N2.split("\\|")[1]);
                        registeredAccount.setNameN3(N3.split("\\|")[1]);
                        registeredAccount.setNameN4(N4.split("\\|")[1]);

                        registeredAccount.setShortNameN1(N1.split("\\|")[2]);
                        registeredAccount.setShortNameN2(N2.split("\\|")[2]);
                        registeredAccount.setShortNameN3(N3.split("\\|")[2]);
                        registeredAccount.setShortNameN4(N4.split("\\|")[2]);

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
        assert key != null;
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
                        if (Objects.requireNonNull(snapshot.getKey())
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

    //Bỏ đi
    public void updateSummaryPackage() {
        databaseReference.child("Summary")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        if (Objects.requireNonNull(snapshot.getKey())
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
                        if (Objects.requireNonNull(snapshot.getKey())
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

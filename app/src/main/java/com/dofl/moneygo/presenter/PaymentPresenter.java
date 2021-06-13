package com.dofl.moneygo.presenter;

import androidx.annotation.NonNull;

import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.MoneyPackage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class PaymentPresenter {
    private final DatabaseReference databaseReference;
    private final PaymentInterface paymentInterface;


    public PaymentPresenter(PaymentInterface paymentInterface) {
        this.paymentInterface = paymentInterface;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void payForN1(Account account, MoneyPackage moneyPackage, int money) {
        databaseReference.child("Account").child("1").child("Money Package")
                .child(account.getPresentMoneyPackage()).child("moneySent")
                .setValue(moneyPackage.getMoneySent() + money);
        databaseReference.child("Account").child("1").child("Notification").child("Payment")
                .setValue("Bạn đã được thanh toán: " + money + " đ\nSố tiền còn nợ của bạn là: "
                        + (moneyPackage.getPreviousMoney() - moneyPackage.getMoneySent() - money)
                        + " đ");
        databaseReference.child("Account").child("1").child("Money Package")
                .child(account.getPresentMoneyPackage()).child("totalMoney")
                .setValue(moneyPackage.getTotalMoney() - money)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            paymentInterface.addSuccess();
                        } else {
                            paymentInterface.addError(Objects
                                    .requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }

    public void payForN2(Account account, MoneyPackage moneyPackage, int money) {
        databaseReference.child("Account").child("2").child("Money Package")
                .child(account.getPresentMoneyPackage()).child("moneySent")
                .setValue(moneyPackage.getMoneySent() + money);
        databaseReference.child("Account").child("2").child("Notification").child("Payment")
                .setValue("Bạn đã được thanh toán: " + money + " đ\nSố tiền còn nợ của bạn là: "
                        + (moneyPackage.getPreviousMoney() - moneyPackage.getMoneySent() - money)
                        + " đ");
        databaseReference.child("Account").child("2").child("Money Package")
                .child(account.getPresentMoneyPackage()).child("totalMoney")
                .setValue(moneyPackage.getTotalMoney() - money)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            paymentInterface.addSuccess();
                        } else {
                            paymentInterface.addError(Objects
                                    .requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }

    public void payForN3(Account account, MoneyPackage moneyPackage, int money) {
        databaseReference.child("Account").child("3").child("Money Package")
                .child(account.getPresentMoneyPackage()).child("moneySent")
                .setValue(moneyPackage.getMoneySent() + money);
        databaseReference.child("Account").child("3").child("Notification").child("Payment")
                .setValue("Bạn đã được thanh toán: " + money + " đ\nSố tiền còn nợ của bạn là: "
                        + (moneyPackage.getPreviousMoney() - moneyPackage.getMoneySent() - money)
                        + " đ");
        databaseReference.child("Account").child("3").child("Money Package")
                .child(account.getPresentMoneyPackage()).child("totalMoney")
                .setValue(moneyPackage.getTotalMoney() - money)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            paymentInterface.addSuccess();
                        } else {
                            paymentInterface.addError(Objects
                                    .requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }

    public void payForN4(Account account, MoneyPackage moneyPackage, int money) {
        databaseReference.child("Account").child("4").child("Money Package")
                .child(account.getPresentMoneyPackage()).child("moneySent")
                .setValue(moneyPackage.getMoneySent() + money);
        databaseReference.child("Account").child("4").child("Notification").child("Payment")
                .setValue("Bạn đã được thanh toán: " + money + " đ\nSố tiền còn nợ của bạn là: "
                        + (moneyPackage.getPreviousMoney() - moneyPackage.getMoneySent() - money)
                        + " đ");
        databaseReference.child("Account").child("4").child("Money Package")
                .child(account.getPresentMoneyPackage()).child("totalMoney")
                .setValue(moneyPackage.getTotalMoney() - money)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            paymentInterface.addSuccess();
                        } else {
                            paymentInterface.addError(Objects
                                    .requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }

    public void payForHX(int neighborNetwork, int money) {
        databaseReference.child("Neighbor Network").setValue(neighborNetwork - money)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            paymentInterface.addSuccess();
                        } else {
                            paymentInterface.addError(Objects
                                    .requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }

}

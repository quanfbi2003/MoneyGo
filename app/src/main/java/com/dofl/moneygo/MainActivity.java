package com.dofl.moneygo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.Record;
import com.dofl.moneygo.model.Summary;
import com.dofl.moneygo.presenter.utils.ForegroundServices;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.functions.FirebaseFunctions;

public class MainActivity extends AppCompatActivity {
    private MoneyPackage moneyPackage1, moneyPackage2, moneyPackage3, moneyPackage4;
    private FirebaseFunctions mFunctions;
    private Button btnStartService, btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mFunctions = FirebaseFunctions.getInstance();

        btnStartService = findViewById(R.id.buttonStartService);
        btnStopService = findViewById(R.id.buttonStopService);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });

    }

    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundServices.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundServices.class);
        stopService(serviceIntent);
    }

    private void getRecord() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Record").child("-M_-iIbkYfcZ4anBcqr1")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {
                        Record record = snapshot.getValue(Record.class);
                        switch (record.getBuyer()) {
                            case 1:
                                moneyPackage1.setNumberOfRecord(moneyPackage1.getNumberOfRecord() + 1);
                                moneyPackage1.setMoneySpent(moneyPackage1.getMoneySpent() + record.getTotal());
                                moneyPackage1.setMoneyPaid(moneyPackage1.getMoneyPaid() + record.getN1Total() + record.getTotal());
                                moneyPackage2.setMoneyPaid(moneyPackage2.getMoneyPaid() + record.getN2Total());
                                moneyPackage3.setMoneyPaid(moneyPackage3.getMoneyPaid() + record.getN3Total());
                                moneyPackage4.setMoneyPaid(moneyPackage4.getMoneyPaid() + record.getN4Total());
                                break;
                            case 2:
                                moneyPackage2.setNumberOfRecord(moneyPackage2.getNumberOfRecord() + 1);
                                moneyPackage2.setMoneySpent(moneyPackage2.getMoneySpent() + record.getTotal());
                                moneyPackage2.setMoneyPaid(moneyPackage2.getMoneyPaid() + record.getN2Total() + record.getTotal());
                                moneyPackage1.setMoneyPaid(moneyPackage1.getMoneyPaid() + record.getN1Total());
                                moneyPackage3.setMoneyPaid(moneyPackage3.getMoneyPaid() + record.getN3Total());
                                moneyPackage4.setMoneyPaid(moneyPackage4.getMoneyPaid() + record.getN4Total());
                                break;
                            case 3:
                                moneyPackage3.setNumberOfRecord(moneyPackage3.getNumberOfRecord() + 1);
                                moneyPackage3.setMoneySpent(moneyPackage3.getMoneySpent() + record.getTotal());
                                moneyPackage3.setMoneyPaid(moneyPackage3.getMoneyPaid() + record.getN3Total() + record.getTotal());
                                moneyPackage2.setMoneyPaid(moneyPackage2.getMoneyPaid() + record.getN2Total());
                                moneyPackage1.setMoneyPaid(moneyPackage1.getMoneyPaid() + record.getN1Total());
                                moneyPackage4.setMoneyPaid(moneyPackage4.getMoneyPaid() + record.getN4Total());
                                break;
                            case 4:
                                moneyPackage4.setNumberOfRecord(moneyPackage4.getNumberOfRecord() + 1);
                                moneyPackage4.setMoneySpent(moneyPackage4.getMoneySpent() + record.getTotal());
                                moneyPackage4.setMoneyPaid(moneyPackage4.getMoneyPaid() + record.getN4Total() + record.getTotal());
                                moneyPackage2.setMoneyPaid(moneyPackage2.getMoneyPaid() + record.getN2Total());
                                moneyPackage3.setMoneyPaid(moneyPackage3.getMoneyPaid() + record.getN3Total());
                                moneyPackage1.setMoneyPaid(moneyPackage1.getMoneyPaid() + record.getN1Total());
                                break;
                        }
                        moneyPackage1.setPresentMoney(moneyPackage1.getMoneyPaid() - moneyPackage1.getMoneySpent() + 697500);
                        moneyPackage1.setTotalMoney(moneyPackage1.getPresentMoney() + moneyPackage1.getPreviousMoney());
                        moneyPackage2.setPresentMoney(moneyPackage2.getMoneyPaid() - moneyPackage2.getMoneySpent() + 697500);
                        moneyPackage2.setTotalMoney(moneyPackage2.getPresentMoney() + moneyPackage2.getPreviousMoney());
                        moneyPackage3.setPresentMoney(moneyPackage3.getMoneyPaid() - moneyPackage3.getMoneySpent() + 697500);
                        moneyPackage3.setTotalMoney(moneyPackage3.getPresentMoney() + moneyPackage3.getPreviousMoney());
                        moneyPackage4.setPresentMoney(moneyPackage4.getMoneyPaid() - moneyPackage4.getMoneySpent() + 697500);
                        moneyPackage4.setTotalMoney(moneyPackage4.getPresentMoney() + moneyPackage4.getPreviousMoney());
                        //TextView textView = findViewById(R.id.tv);
//                        textView.setText("N1\nTổng tiền nợ: " + moneyPackage1.getTotalMoney()
//                                + "\nTiền nợ tháng này: " + moneyPackage1.getPresentMoney()
//                                + "\nTổng số bản ghi: " + moneyPackage1.getNumberOfRecord()
//                                + "\nSố tiền đã chi: " + moneyPackage1.getMoneySpent()
//                                + "\nSố tiền phải trả: " + moneyPackage1.getMoneyPaid());
//                        textView.append("\nN2\nTổng tiền nợ: " + moneyPackage2.getTotalMoney()
//                                + "\nTiền nợ tháng này: " + moneyPackage2.getPresentMoney()
//                                + "\nTổng số bản ghi: " + moneyPackage2.getNumberOfRecord()
//                                + "\nSố tiền đã chi: " + moneyPackage2.getMoneySpent()
//                                + "\nSố tiền phải trả: " + moneyPackage2.getMoneyPaid());
//                        textView.append("\nN3\nTổng tiền nợ: " + moneyPackage3.getTotalMoney()
//                                + "\nTiền nợ tháng này: " + moneyPackage3.getPresentMoney()
//                                + "\nTổng số bản ghi: " + moneyPackage3.getNumberOfRecord()
//                                + "\nSố tiền đã chi: " + moneyPackage3.getMoneySpent()
//                                + "\nSố tiền phải trả: " + moneyPackage3.getMoneyPaid());
//                        textView.append("\nN4\nTổng tiền nợ: " + moneyPackage4.getTotalMoney()
//                                + "\nTiền nợ tháng này: " + moneyPackage4.getPresentMoney()
//                                + "\nTổng số bản ghi: " + moneyPackage4.getNumberOfRecord()
//                                + "\nSố tiền đã chi: " + moneyPackage4.getMoneySpent()
//                                + "\nSố tiền phải trả: " + moneyPackage4.getMoneyPaid());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot,
                                               @Nullable String previousChildName) {
                        Record record = snapshot.getValue(Record.class);
                        TextView textView = findViewById(R.id.text);
                        textView.append(previousChildName + "\n");

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Record record = snapshot.getValue(Record.class);
                        TextView textView = findViewById(R.id.text);
                        textView.append(record.getDescription() + "\n");
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot,
                                             @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void addAccount() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Account").child("4").child("Account Details").push()
                .setValue(
                        new Account(4, "nxlam0408@gmail.com",
                                null,
                                "member",
                                "Nguyễn Xuân Lâm",
                                "abcdef",
                                "abcdefg"
                        ));
    }

    private String addMoneyPackage() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Account").child("4").child("Money Package");

        String moneyPackage = databaseReference.push().getKey();

        assert moneyPackage != null;
        databaseReference.child(moneyPackage).setValue(
                new MoneyPackage("recordPackage",
                        "summaryPackage",
                        0, 0, 0, 0,
                        0, 0, 0));
        return moneyPackage;
    }

    private void addRecord(Record record) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Record").child("-M_-iIbkYfcZ4anBcqr1");
        String key = databaseReference.push().getKey();
        databaseReference.child((record.getDateCreate())
                .replaceAll("/", ":")
                + " - " + record.getTimeCreate() + " - " + key)
                .setValue(record);
    }

    private String addSummary() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Summary");
        String summaryPackage = databaseReference.push().getKey();

        assert summaryPackage != null;
        databaseReference.child("-M_JUyuJOpDOYMAN2S89")
                .setValue(
                        new Summary("startDate",
                                "endDate",
                                "monthOfYear",
                                0, 0, 0, 0,
                                0, 0, 0, 0));
        return summaryPackage;
    }
}
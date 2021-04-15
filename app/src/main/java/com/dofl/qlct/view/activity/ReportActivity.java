package com.dofl.qlct.view.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.GlobalVariable;
import com.dofl.qlct.model.MPM;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.utils.DataProcessing;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ReportActivity extends AppCompatActivity {
    private TextView name, startDate, endDate, total, qty, amountSpent, amountPaid, roomCharge;
    private TextView electricityBill, waterBill, lastAmountTV, lastAmount, presentAmountTV;
    private TextView presentAmount, totalAmount, totalAmountTV;
    private Account account;
    private MPM presentMPM, previousMPM, prePreviousMPM;
    private DrawerLayout drawerLayout;
    private List<Record> listRecord, listPreviousRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initValue();
        initData();
        initDataThisMonth();
        setOnClickFunctionButton();

    }


    /****************************Initial Value***************************/
    private void initValue() {
        account = DataProcessing.getAccount(((GlobalVariable) this.getApplication()).getAccount());
        listRecord = DataProcessing.
                getListRecord(((GlobalVariable) this.getApplication()).getListRecord());
        listPreviousRecord = DataProcessing.
                getListRecord(((GlobalVariable) this.getApplication()).getListPreviousRecord());
        presentMPM = DataProcessing.
                getMPM(((GlobalVariable) this.getApplication()).getPresentMPM());
        previousMPM = DataProcessing.
                getMPM(((GlobalVariable) this.getApplication()).getPreviousMPM());
        prePreviousMPM = DataProcessing.
                getMPM(((GlobalVariable) this.getApplication()).getPrePreviousMPM());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        name = findViewById(R.id.name);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        total = findViewById(R.id.tongSoTien);
        qty = findViewById(R.id.tongSoBanGhi);
        amountSpent = findViewById(R.id.tongSoTienDaChi);
        amountPaid = findViewById(R.id.tongSoTienPhaiTra);
        roomCharge = findViewById(R.id.tienPhong);
        electricityBill = findViewById(R.id.tienDien);
        waterBill = findViewById(R.id.tienNuoc);
        lastAmountTV = findViewById(R.id.soTienThangTruocTV);
        lastAmount = findViewById(R.id.soTienThangTruoc);
        presentAmountTV = findViewById(R.id.sotienThangNayTV);
        presentAmount = findViewById(R.id.sotienThangNay);
        totalAmountTV = findViewById(R.id.tongTienTV);
        totalAmount = findViewById(R.id.tongTien);
    }

    @SuppressLint("SetTextI18n")
    private void initDataThisMonth() {
        name.setText(account.getDisplayName());
        startDate.setText(account.getStartDate());
        endDate.setText(DataProcessing.getEditDate(new Date()));
        total.setText(DataProcessing.formatIntToString(DataProcessing.
                getTotalOfListRecord(listRecord)) + " đ");
        qty.setText(Integer.toString(DataProcessing.getTotalOfQty(account, listRecord)));
        amountSpent.setText(DataProcessing.formatIntToString(DataProcessing.
                getTotalOfAmountSpent(account, listRecord)) + " đ");
        amountPaid.setText(DataProcessing.formatIntToString(DataProcessing.
                getTotalOfAmountPaid(account, listRecord)) + " đ");
        roomCharge.setText(DataProcessing.formatIntToString(DataProcessing.
                getRoomCharge(presentMPM)) + " đ");
        electricityBill.setText("0 đ");
        waterBill.setText("0 đ");

        int previousMoney = account.getMoneyPaid() + account.getPreviousMoney() +
                DataProcessing.getTotalOfAmount(account, listPreviousRecord,
                prePreviousMPM, previousMPM);
        if (previousMoney >= 0) {
            lastAmount.setText(DataProcessing.formatIntToString(previousMoney) + " đ");
            lastAmountTV.setTextColor(Color.parseColor("#43A047"));
            lastAmount.setTextColor(Color.parseColor("#43A047"));
            lastAmountTV.setText("Tiền thừa tháng " + previousMPM.getMonth() + ": ");
        } else {
            lastAmount.setText(DataProcessing.
                    formatIntToString(previousMoney * -1) + " đ");
            lastAmountTV.setTextColor(Color.RED);
            lastAmount.setTextColor(Color.RED);
            lastAmountTV.setText("Tiền nợ tháng " + previousMPM.getMonth() + ": ");
        }

        int rA = DataProcessing.getTotalOfAmount(account, listRecord,
                previousMPM, presentMPM);
        if (rA < 0) {
            presentAmount.setTextColor(Color.RED);
            presentAmountTV.setTextColor(Color.RED);
            presentAmountTV.setText("Tiền nợ tháng này: ");
            presentAmount.setText(DataProcessing.formatIntToString(rA * -1) + " đ");

        } else {
            presentAmount.setTextColor(Color.parseColor("#43A047"));
            presentAmountTV.setTextColor(Color.parseColor("#43A047"));
            presentAmountTV.setText("Tiền thừa tháng này: ");
            presentAmount.setText(DataProcessing.formatIntToString(rA) + " đ");
        }

        int total = previousMoney + rA;
        if (total < 0) {
            totalAmount.setTextColor(Color.RED);
            totalAmountTV.setTextColor(Color.RED);
            totalAmountTV.setText("Tổng tiền nợ: ");
            totalAmount.setText(DataProcessing.formatIntToString(total* -1) + " đ");
        } else {
            totalAmount.setTextColor(Color.parseColor("#43A047"));
            totalAmountTV.setTextColor(Color.parseColor("#43A047"));
            totalAmountTV.setText("Tổng tiền thừa: ");
            totalAmount.setText(DataProcessing.formatIntToString(total) + " đ");
        }
    }

    @SuppressLint("SetTextI18n")
    private void initDataLastMonth() {
        name.setText(account.getDisplayName());
        startDate.setText(previousMPM.getStartDate());
        endDate.setText(previousMPM.getEndDate());
        total.setText(DataProcessing.formatIntToString(DataProcessing.
                getTotalOfListRecord(listPreviousRecord)) + " đ");
        qty.setText(Integer.toString(DataProcessing.getTotalOfQty(account, listPreviousRecord)));
        amountSpent.setText(DataProcessing.formatIntToString(DataProcessing.
                getTotalOfAmountSpent(account, listPreviousRecord)) + " đ");
        amountPaid.setText(DataProcessing.formatIntToString(DataProcessing.
                getTotalOfAmountPaid(account, listPreviousRecord)) + " đ");
        roomCharge.setText(DataProcessing.formatIntToString(DataProcessing.
                getRoomCharge(previousMPM)) + " đ");
        electricityBill.setText(DataProcessing.
                formatIntToString((previousMPM.getNumberOfElectricity()
                        - prePreviousMPM.getNumberOfElectricity()) * 4000 / 4) + " đ");
        waterBill.setText(DataProcessing.
                formatIntToString((previousMPM.getNumberOfWater()
                        - prePreviousMPM.getNumberOfWater()) * 20000 / 4) + " đ");

        if (account.getPreviousMoney() < 0) {
            lastAmount.setText(DataProcessing.
                    formatIntToString(account.getPreviousMoney()*-1)+" đ");
            lastAmountTV.setTextColor(Color.RED);
            lastAmount.setTextColor(Color.RED);
            lastAmountTV.setText("Tiền nợ tháng " + prePreviousMPM.getMonth() + ": ");
        } else {
            lastAmount.setText(DataProcessing.
                    formatIntToString(account.getPreviousMoney())+" đ");
            lastAmountTV.setTextColor(Color.parseColor("#43A047"));
            lastAmount.setTextColor(Color.parseColor("#43A047"));
            lastAmountTV.setText("Tiền thừa tháng " + prePreviousMPM.getMonth() + ": ");
        }

        int rA = DataProcessing.getTotalOfAmount(account, listPreviousRecord,
                prePreviousMPM, previousMPM);

        if (rA < 0) {
            presentAmount.setTextColor(Color.RED);
            presentAmountTV.setTextColor(Color.RED);
            presentAmountTV.setText("Tiền nợ tháng này: ");
            presentAmount.setText(DataProcessing.formatIntToString(rA * -1) + " đ");
        } else {
            presentAmount.setTextColor(Color.parseColor("#43A047"));
            presentAmountTV.setTextColor(Color.parseColor("#43A047"));
            presentAmountTV.setText("Tiền thừa tháng này: ");
            presentAmount.setText(DataProcessing.formatIntToString(rA) + " đ");
        }

        int total = account.getMoneyPaid() + account.getPreviousMoney() + rA;
        if (total < 0) {
            totalAmount.setTextColor(Color.RED);
            totalAmountTV.setTextColor(Color.RED);
            totalAmountTV.setText("Tổng tiền nợ: ");
            totalAmount.setText(DataProcessing.formatIntToString(total* -1) + " đ");
        } else {
            totalAmount.setTextColor(Color.parseColor("#43A047"));
            totalAmountTV.setTextColor(Color.parseColor("#43A047"));
            totalAmountTV.setText("Tổng tiền thừa: ");
            totalAmount.setText(DataProcessing.formatIntToString(total) + " đ");
        }
    }


    /****************************Override Toolbar Functions***************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.top_bar_info) {
            drawerLayout.openDrawer(GravityCompat.END);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /****************************OnClick Function Button***************************/
    private void setOnClickFunctionButton() {
        findViewById(R.id.lastMonth).setOnClickListener(v -> {
            initDataLastMonth();
            findViewById(R.id.lastMonth).setVisibility(View.INVISIBLE);
            findViewById(R.id.thisMonth).setVisibility(View.VISIBLE);
        });
        findViewById(R.id.thisMonth).setOnClickListener(v -> {
            initDataThisMonth();
            findViewById(R.id.thisMonth).setVisibility(View.INVISIBLE);
            findViewById(R.id.lastMonth).setVisibility(View.VISIBLE);
        });
    }
}
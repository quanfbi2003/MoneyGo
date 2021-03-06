package com.dofl.moneygo.view.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.FeeDetails;
import com.dofl.moneygo.model.GlobalVariable;
import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.Summary;
import com.dofl.moneygo.presenter.utils.DataProcessing;

import java.util.Date;
import java.util.Objects;

public class ReportActivity extends AppCompatActivity {
    private TextView name, startDate, endDate, totalMoneySpent, numberOfRecord;
    private TextView moneySpent, moneyPaid, roomCharge;
    private TextView electricityBill, waterBill, previousMoneyTV, previousMoney, presentMoneyTV;
    private TextView presentMoney, totalMoney, totalMoneyTV;
    private Account account;
    private DrawerLayout drawerLayout;
    private MoneyPackage presentMoneyPackage;
    private MoneyPackage previousMoneyPackage;
    private Summary presentSummaryPackage;
    private Summary previousSummaryPackage;

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
        account = ((GlobalVariable) this.getApplication()).getAccount();
        presentMoneyPackage = ((GlobalVariable) this.getApplication()).getPresentMoneyPackage();
        previousMoneyPackage = ((GlobalVariable) this.getApplication()).getPreviousMoneyPackage();
        presentSummaryPackage = ((GlobalVariable) this.getApplication()).getPresentSummaryPackage();
        previousSummaryPackage = ((GlobalVariable) this.getApplication())
                .getPreviousSummaryPackage();

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
        totalMoneySpent = findViewById(R.id.tongSoTien);
        numberOfRecord = findViewById(R.id.tongSoBanGhi);
        moneySpent = findViewById(R.id.tongSoTienDaChi);
        moneyPaid = findViewById(R.id.tongSoTienPhaiTra);
        roomCharge = findViewById(R.id.tienPhong);
        electricityBill = findViewById(R.id.tienDien);
        waterBill = findViewById(R.id.tienNuoc);
        previousMoneyTV = findViewById(R.id.soTienThangTruocTV);
        previousMoney = findViewById(R.id.soTienThangTruoc);
        presentMoneyTV = findViewById(R.id.sotienThangNayTV);
        presentMoney = findViewById(R.id.sotienThangNay);
        totalMoneyTV = findViewById(R.id.tongTienTV);
        totalMoney = findViewById(R.id.tongTien);
    }

    @SuppressLint("SetTextI18n")
    private void initDataThisMonth() {
        name.setText(account.getDisplayName());
        startDate.setText(DataProcessing.getExactDate(DataProcessing
                .getDate(presentSummaryPackage.getStartDate())));
        endDate.setText(DataProcessing.getEditDate(new Date()));
        totalMoneySpent.setText(DataProcessing
                .formatIntToString(presentSummaryPackage.getTotalMoneySpent()) + " ??");
        numberOfRecord.setText(Integer.toString(presentMoneyPackage.getNumberOfRecord()));
        moneySpent.setText(DataProcessing
                .formatIntToString(presentMoneyPackage.getMoneySpent()) + " ??");
        moneyPaid.setText(DataProcessing
                .formatIntToString(presentMoneyPackage.getMoneyPaid()) + " ??");
        roomCharge.setText(DataProcessing.formatIntToString((FeeDetails.ROOM_CHARGE
                + FeeDetails.INTERNET - FeeDetails.NEIGHBOR_INTERNET + FeeDetails.SERVICES
                + presentSummaryPackage.getAirConditional()) / 4) + " ??");
        electricityBill.setText("0 ??");
        waterBill.setText("0 ??");

        int previousMoneyValue = presentMoneyPackage.getPreviousMoney()
                - presentMoneyPackage.getMoneySent();
        if (previousMoneyValue <= 0) {
            previousMoney.setText(DataProcessing
                    .formatIntToString(previousMoneyValue * -1) + " ??");
            previousMoneyTV.setTextColor(Color.parseColor("#43A047"));
            previousMoney.setTextColor(Color.parseColor("#43A047"));
            previousMoneyTV.setText("Ti???n th???a th??ng " + previousSummaryPackage.getMonth() + ": ");
        } else {
            previousMoney.setText(DataProcessing.
                    formatIntToString(previousMoneyValue) + " ??");
            previousMoneyTV.setTextColor(Color.RED);
            previousMoney.setTextColor(Color.RED);
            previousMoneyTV.setText("Ti???n n??? th??ng " + previousSummaryPackage.getMonth() + ": ");
        }

        if (presentMoneyPackage.getPresentMoney() > 0) {
            presentMoney.setTextColor(Color.RED);
            presentMoneyTV.setTextColor(Color.RED);
            presentMoneyTV.setText("Ti???n n??? th??ng n??y: ");
            presentMoney.setText(DataProcessing
                    .formatIntToString(presentMoneyPackage.getPresentMoney()) + " ??");

        } else {
            presentMoney.setTextColor(Color.parseColor("#43A047"));
            presentMoneyTV.setTextColor(Color.parseColor("#43A047"));
            presentMoneyTV.setText("Ti???n th???a th??ng n??y: ");
            presentMoney.setText(DataProcessing
                    .formatIntToString(presentMoneyPackage.getPresentMoney() * -1) + " ??");
        }

        if (presentMoneyPackage.getTotalMoney() > 0) {
            totalMoney.setTextColor(Color.RED);
            totalMoneyTV.setTextColor(Color.RED);
            totalMoneyTV.setText("T???ng ti???n n???: ");
            totalMoney.setText(DataProcessing
                    .formatIntToString(presentMoneyPackage.getTotalMoney()) + " ??");
        } else {
            totalMoney.setTextColor(Color.parseColor("#43A047"));
            totalMoneyTV.setTextColor(Color.parseColor("#43A047"));
            totalMoneyTV.setText("T???ng ti???n th???a: ");
            totalMoney.setText(DataProcessing
                    .formatIntToString(presentMoneyPackage.getTotalMoney() * -1) + " ??");
        }
    }

    @SuppressLint("SetTextI18n")
    private void initDataLastMonth() {
        name.setText(account.getDisplayName());
        startDate.setText(DataProcessing.getExactDate(DataProcessing
                .getDate(previousSummaryPackage.getStartDate())));
        endDate.setText(DataProcessing.getEditDate(DataProcessing
                .getDate(previousSummaryPackage.getEndDate())));
        totalMoneySpent.setText(DataProcessing
                .formatIntToString(previousSummaryPackage.getTotalMoneySpent()) + " ??");
        numberOfRecord.setText(Integer.toString(previousMoneyPackage.getNumberOfRecord()));
        moneySpent.setText(DataProcessing
                .formatIntToString(previousMoneyPackage.getMoneySpent()) + " ??");
        moneyPaid.setText(DataProcessing
                .formatIntToString(previousMoneyPackage.getMoneyPaid()) + " ??");
        roomCharge.setText(DataProcessing.formatIntToString((FeeDetails.ROOM_CHARGE
                + FeeDetails.INTERNET - FeeDetails.NEIGHBOR_INTERNET + FeeDetails.SERVICES
                + previousSummaryPackage.getAirConditional()) / 4) + " ??");
        electricityBill.setText(DataProcessing
                .formatIntToString(previousSummaryPackage
                        .getTotalOfElectricity() * FeeDetails.ELECTRICITY / 4) + " ??");
        waterBill.setText(DataProcessing
                .formatIntToString(previousSummaryPackage
                        .getTotalOfWater() * FeeDetails.WATER / 4) + " ??");

        int previousMoneyValue = previousMoneyPackage.getPreviousMoney()
                - previousMoneyPackage.getMoneySent();
        if (previousMoneyValue <= 0) {
            previousMoney.setText(DataProcessing
                    .formatIntToString(previousMoneyValue * -1) + " ??");
            previousMoneyTV.setTextColor(Color.parseColor("#43A047"));
            previousMoney.setTextColor(Color.parseColor("#43A047"));
            previousMoneyTV
                    .setText("Ti???n th???a th??ng " + (previousSummaryPackage.getMonth() - 1) + ": ");
        } else {
            previousMoney.setText(DataProcessing.
                    formatIntToString(previousMoneyValue) + " ??");
            previousMoneyTV.setTextColor(Color.RED);
            previousMoney.setTextColor(Color.RED);
            previousMoneyTV
                    .setText("Ti???n n??? th??ng " + (previousSummaryPackage.getMonth() - 1) + ": ");
        }

        if (previousMoneyPackage.getPresentMoney() > 0) {
            presentMoney.setTextColor(Color.RED);
            presentMoneyTV.setTextColor(Color.RED);
            presentMoneyTV.setText("Ti???n n??? th??ng n??y: ");
            presentMoney.setText(DataProcessing
                    .formatIntToString(previousMoneyPackage.getPresentMoney()) + " ??");

        } else {
            presentMoney.setTextColor(Color.parseColor("#43A047"));
            presentMoneyTV.setTextColor(Color.parseColor("#43A047"));
            presentMoneyTV.setText("Ti???n th???a th??ng n??y: ");
            presentMoney.setText(DataProcessing
                    .formatIntToString(previousMoneyPackage.getPresentMoney() * -1) + " ??");
        }

        if (previousMoneyPackage.getTotalMoney() > 0) {
            totalMoney.setTextColor(Color.RED);
            totalMoneyTV.setTextColor(Color.RED);
            totalMoneyTV.setText("T???ng ti???n n???: ");
            totalMoney.setText(DataProcessing
                    .formatIntToString(previousMoneyPackage.getTotalMoney()) + " ??");
        } else {
            totalMoney.setTextColor(Color.parseColor("#43A047"));
            totalMoneyTV.setTextColor(Color.parseColor("#43A047"));
            totalMoneyTV.setText("T???ng ti???n th???a: ");
            totalMoney.setText(DataProcessing
                    .formatIntToString(previousMoneyPackage.getTotalMoney() * -1) + " ??");
        }
    }


    /****************************Override Toolbar Functions***************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
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
        findViewById(R.id.lastMonth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initValue();
                initDataLastMonth();
                findViewById(R.id.lastMonth).setVisibility(View.INVISIBLE);
                findViewById(R.id.thisMonth).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.thisMonth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initValue();
                initDataThisMonth();
                findViewById(R.id.thisMonth).setVisibility(View.INVISIBLE);
                findViewById(R.id.lastMonth).setVisibility(View.VISIBLE);
            }

        });
    }
}
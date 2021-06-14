package com.dofl.moneygo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.FeeDetails;
import com.dofl.moneygo.model.GlobalVariable;
import com.dofl.moneygo.presenter.SettlementInterface;
import com.dofl.moneygo.presenter.SettlementPresenter;
import com.dofl.moneygo.presenter.utils.DataProcessing;
import com.dofl.moneygo.presenter.utils.HideKeyboard;

import java.util.Objects;

public class SettlementActivity extends AppCompatActivity implements SettlementInterface {
    private SettlementPresenter settlementPresenter;
    private EditText txtSoDien, txtSoNuoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        initValue();
    }


    /****************************Initial Value***************************/
    private void initValue() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        checkNetworkStatus();
        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.settlement_view),
                SettlementActivity.this)).start();

        settlementPresenter = new SettlementPresenter(this);

        txtSoDien = findViewById(R.id.soDien);
        txtSoNuoc = findViewById(R.id.soNuoc);

        findViewById(R.id.quyetToan).setOnClickListener(v ->
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Cảnh báo!!!")
                        .setMessage("Bạn có chắc chắn tiếp tục?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            settlementPresenter.newMonth(
                                    ((GlobalVariable) this.getApplication()).getAccount(),
                                    ((GlobalVariable) this.getApplication()).getN1MoneyPackage(),
                                    ((GlobalVariable) this.getApplication()).getN2MoneyPackage(),
                                    ((GlobalVariable) this.getApplication()).getN3MoneyPackage(),
                                    ((GlobalVariable) this.getApplication()).getN4MoneyPackage(),
                                    ((GlobalVariable) this.getApplication())
                                            .getPresentSummaryPackage(),
                                    ((GlobalVariable) this.getApplication())
                                            .getPreviousSummaryPackage(),
                                    Integer.parseInt(txtSoDien.getText().toString()),
                                    Integer.parseInt(txtSoNuoc.getText().toString()),
                                    ((GlobalVariable) this.getApplication()).getNeighborNetwork());
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show());

        findViewById(R.id.thanhToan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Phòng 402\n" +
                        "Điện: " + ((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getNumberOfElectricity() + " - "
                        + ((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getTotalOfElectricity()
                        + " số - " + DataProcessing
                        .formatIntToString(((GlobalVariable) getApplication())
                                .getPreviousSummaryPackage().getTotalOfElectricity()
                                * FeeDetails.ELECTRICITY) + " VND\n" +
                        "Nước: " + ((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getNumberOfWater()
                        + " - " + ((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getTotalOfWater()
                        + " khối - " + DataProcessing
                        .formatIntToString(((GlobalVariable) getApplication())
                                .getPreviousSummaryPackage().getTotalOfWater()
                                * FeeDetails.WATER) + " VND\n" +
                        "Vệ sinh + Điện công cộng: " + DataProcessing
                        .formatIntToString(FeeDetails.SERVICES) + " VND\n" +
                        "Mạng: " + DataProcessing
                        .formatIntToString(FeeDetails.INTERNET) + " VND\n" +
                        "Bảo dưỡng điều hòa: " + DataProcessing
                        .formatIntToString(((GlobalVariable) getApplication())
                                .getPreviousSummaryPackage().getAirConditional()) + " VND\n" +
                        "Tiền phòng: " + DataProcessing
                        .formatIntToString(FeeDetails.ROOM_CHARGE) + " VND\n" +
                        "Tổng: " + DataProcessing
                        .formatIntToString(((GlobalVariable) getApplication())
                                .getPreviousSummaryPackage().getTotalMoneyPaid()) + " VND");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
    }


    /****************************Network Functions***************************/
    private void checkNetworkStatus() {
        if (!getNetworkStatus()) {
            new AlertDialog.Builder(SettlementActivity.this)
                    .setTitle("Thông báo!!!")
                    .setMessage("Không có kết nối mạng, chọn OK để đóng ứng dụng!!!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> System.exit(0))
                    .show();
        }
        getNetworkStatus();
    }

    private boolean getNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }


    /****************************Override Toolbar Functions***************************/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /****************************Interface Functions***************************/
    @Override
    public void success() {
        Toast.makeText(getApplicationContext(), "Quyết toán thành công!!!",
                Toast.LENGTH_SHORT).show();

        new AlertDialog.Builder(SettlementActivity.this)
                .setTitle("Thông báo!!!")
                .setMessage("Hệ thống cần khởi động lại, chọn OK để đóng ứng dụng!!!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok,
                        (dialog, which) -> {
                            settlementPresenter.setMaintenance();
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                        })
                .show();
    }

    @Override
    public void error(String msg) {
        new AlertDialog.Builder(SettlementActivity.this)
                .setTitle("Thông báo!!!")
                .setMessage("Hệ thống cần khởi động lại vì có lỗi xảy ra, chọn OK để đóng ứng dụng!!!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok,
                        (dialog, which) -> {
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                        })
                .show();
    }
}
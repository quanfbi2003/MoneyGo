package com.dofl.moneygo.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
        importValue();
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
                        .setTitle("C???nh b??o!!!")
                        .setMessage("B???n c?? ch???c ch???n ti???p t???c?")
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
                settlementPresenter.setPayment(((GlobalVariable) getApplication())
                        .getPreviousMoneyPackage());
                importValue();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Ph??ng 402\n" +
                        "??i???n: " + ((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getNumberOfElectricity() + " - "
                        + ((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getTotalOfElectricity()
                        + " s??? - " + DataProcessing
                        .formatIntToString(((GlobalVariable) getApplication())
                                .getPreviousSummaryPackage().getTotalOfElectricity()
                                * FeeDetails.ELECTRICITY) + " VND\n" +
                        "N?????c: " + ((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getNumberOfWater()
                        + " - " + ((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getTotalOfWater()
                        + " kh???i - " + DataProcessing
                        .formatIntToString(((GlobalVariable) getApplication())
                                .getPreviousSummaryPackage().getTotalOfWater()
                                * FeeDetails.WATER) + " VND\n" +
                        "V??? sinh + ??i???n c??ng c???ng: " + DataProcessing
                        .formatIntToString(FeeDetails.SERVICES) + " VND\n" +
                        "M???ng: " + DataProcessing
                        .formatIntToString(FeeDetails.INTERNET) + " VND\n" +
                        "B???o d?????ng ??i???u h??a: " + DataProcessing
                        .formatIntToString(((GlobalVariable) getApplication())
                                .getPreviousSummaryPackage().getAirConditional()) + " VND\n" +
                        "Ti???n ph??ng: " + DataProcessing
                        .formatIntToString(FeeDetails.ROOM_CHARGE) + " VND\n" +
                        "T???ng: " + DataProcessing
                        .formatIntToString(((GlobalVariable) getApplication())
                                .getPreviousSummaryPackage().getTotalMoneyPaid()) + " VND");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void importValue() {
        TextView txtDien = findViewById(R.id.txtDien);
        TextView txtNuoc = findViewById(R.id.txtNuoc);
        TextView txtDcc = findViewById(R.id.txtDcc);
        TextView txtMang = findViewById(R.id.txtMang);
        TextView txtDieuhoa = findViewById(R.id.txtDieuhoa);
        TextView txtTienPhong = findViewById(R.id.txtTienPhong);
        TextView txtPayment = findViewById(R.id.txtPayment);
        TextView txtTong = findViewById(R.id.txtTong);

        txtDien.setText("??i???n: " + ((GlobalVariable) getApplication())
                .getPreviousSummaryPackage().getNumberOfElectricity() + " - "
                + ((GlobalVariable) getApplication())
                .getPreviousSummaryPackage().getTotalOfElectricity()
                + " s??? - " + DataProcessing
                .formatIntToString(((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getTotalOfElectricity()
                        * FeeDetails.ELECTRICITY) + " VND");
        txtNuoc.setText("N?????c: " + ((GlobalVariable) getApplication())
                .getPreviousSummaryPackage().getNumberOfWater()
                + " - " + ((GlobalVariable) getApplication())
                .getPreviousSummaryPackage().getTotalOfWater()
                + " kh???i - " + DataProcessing
                .formatIntToString(((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getTotalOfWater()
                        * FeeDetails.WATER) + " VND");
        txtDcc.setText("VS + ??CC: " + DataProcessing
                .formatIntToString(FeeDetails.SERVICES) + " VND");
        txtMang.setText("M???ng: " + DataProcessing
                .formatIntToString(FeeDetails.INTERNET) + " VND");
        txtDieuhoa.setText("B???o d?????ng ??i???u h??a: " + DataProcessing
                .formatIntToString(((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getAirConditional()) + " VND");
        txtTienPhong.setText("Ti???n ph??ng: " + DataProcessing
                .formatIntToString(FeeDetails.ROOM_CHARGE) + " VND");
        txtPayment.setText("T???ng ti???n - "
                + ((GlobalVariable) getApplication()).getPreviousSummaryPackage().getPayment());
        txtTong.setText("T???ng: " + DataProcessing
                .formatIntToString(((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getTotalMoneyPaid()) + " VND");
    }


    /****************************Network Functions***************************/
    private void checkNetworkStatus() {
        if (!getNetworkStatus()) {
            new AlertDialog.Builder(SettlementActivity.this)
                    .setTitle("Th??ng b??o!!!")
                    .setMessage("Kh??ng c?? k???t n???i m???ng, ch???n OK ????? ????ng ???ng d???ng!!!")
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
        Toast.makeText(getApplicationContext(), "Quy???t to??n th??nh c??ng!!!",
                Toast.LENGTH_SHORT).show();

        new AlertDialog.Builder(SettlementActivity.this)
                .setTitle("Th??ng b??o!!!")
                .setMessage("H??? th???ng c???n kh???i ?????ng l???i, ch???n OK ????? ????ng ???ng d???ng!!!")
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
                .setTitle("Th??ng b??o!!!")
                .setMessage("H??? th???ng c???n kh???i ?????ng l???i v?? c?? l???i x???y ra, ch???n OK ????? ????ng ???ng d???ng!!!")
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
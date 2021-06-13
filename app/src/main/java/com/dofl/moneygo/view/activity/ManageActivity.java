package com.dofl.moneygo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.GlobalVariable;
import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.presenter.ManageInterface;
import com.dofl.moneygo.presenter.ManagePresenter;
import com.dofl.moneygo.view.adapter.GridViewMenuAdapter;

import java.util.Objects;

public class ManageActivity extends AppCompatActivity implements ManageInterface {
    private ManagePresenter managePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        initValue();
    }


    /****************************Initial Value***************************/
    private void initValue() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        checkNetworkStatus();

        managePresenter = new ManagePresenter(this);

        managePresenter.updateMoneyPackage(((GlobalVariable) this.getApplication()).getAccount());

        String[] functionName = {"Quyết toán", "Thanh toán"};
        int[] functionImage = {R.drawable.manage_menu_settlement, R.drawable.manage_menu_payment};
        ListView listView = findViewById(R.id.listView);
        GridViewMenuAdapter gridViewAdapterMainLayoutFunction =
                new GridViewMenuAdapter(this, functionName, functionImage);
        listView.setAdapter(gridViewAdapterMainLayoutFunction);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    Intent intent = new Intent(ManageActivity.this,
                            SettlementActivity.class);
                    startActivity(intent);
                    break;

                case 1:
                    Intent intent1 = new Intent(ManageActivity.this,
                            PaymentActivity.class);
                    startActivity(intent1);
                    break;
            }
        });
    }


    /****************************Network Functions***************************/
    private void checkNetworkStatus() {
        if (!getNetworkStatus()) {
            new AlertDialog.Builder(ManageActivity.this)
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
    public void updateN1MoneyPackageSuccess(MoneyPackage n1MoneyPackage) {
        ((GlobalVariable) this.getApplication()).setN1MoneyPackage(n1MoneyPackage);
    }

    @Override
    public void updateError(String msg) {
        Log.e("Log", msg);
    }

    @Override
    public void updateN2MoneyPackageSuccess(MoneyPackage n2MoneyPackage) {
        ((GlobalVariable) this.getApplication()).setN2MoneyPackage(n2MoneyPackage);
    }

    @Override
    public void updateN3MoneyPackageSuccess(MoneyPackage n3MoneyPackage) {
        ((GlobalVariable) this.getApplication()).setN3MoneyPackage(n3MoneyPackage);
    }

    @Override
    public void updateN4MoneyPackageSuccess(MoneyPackage n4MoneyPackage) {
        ((GlobalVariable) this.getApplication()).setN4MoneyPackage(n4MoneyPackage);
    }

    @Override
    public void updateNeighborNetworkSuccess(int neighborNetwork) {
        ((GlobalVariable) this.getApplication()).setNeighborNetwork(neighborNetwork);
    }
}
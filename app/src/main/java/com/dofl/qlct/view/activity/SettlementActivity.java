package com.dofl.qlct.view.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.GlobalVariable;
import com.dofl.qlct.model.MPM;
import com.dofl.qlct.model.Maintenance;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.SettlementInterface;
import com.dofl.qlct.presenter.SettlementPresenter;
import com.dofl.qlct.presenter.utils.DataProcessing;
import com.dofl.qlct.presenter.utils.HideKeyboard;

import java.util.ArrayList;
import java.util.List;

public class SettlementActivity extends AppCompatActivity implements SettlementInterface {
    private DrawerLayout drawerLayout;
    private Account account;
    private List<Account> listAccount;
    private MPM presentMPM, previousMPM, prePreviousMPM;
    private List<Record> listRecord, listPreviousRecord;
    private SettlementPresenter settlementPresenter;
    private EditText soDien, soNuoc;
    private AlertDialog confirm, processing;
    private Maintenance maintenance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        initValue();
        setOnClickFunctionButton();

    }


    /****************************Initial Value***************************/
    private void initValue() {
        account = DataProcessing.getAccount(((GlobalVariable) this.getApplication()).getAccount());
        presentMPM = DataProcessing.
                getMPM(((GlobalVariable) this.getApplication()).getPresentMPM());
        listRecord = DataProcessing.
                getListRecord(((GlobalVariable) this.getApplication()).getListRecord());
        listPreviousRecord = DataProcessing.
                getListRecord(((GlobalVariable) this.getApplication()).getListPreviousRecord());
        listAccount = DataProcessing.
                getListAccount(((GlobalVariable) this.getApplication()).getListAccount());
        previousMPM = DataProcessing.
                getMPM(((GlobalVariable) this.getApplication()).getPreviousMPM());
        prePreviousMPM = DataProcessing.
                getMPM(((GlobalVariable) this.getApplication()).getPrePreviousMPM());
        maintenance = DataProcessing.
                getMaintenance(((GlobalVariable) this.getApplication()).getMaintenance());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());

        settlementPresenter = new SettlementPresenter(this);

        soDien = findViewById(R.id.soDien);
        soNuoc = findViewById(R.id.soNuoc);

        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.add_view),
                SettlementActivity.this)).start();
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
        findViewById(R.id.quyetToan).setOnClickListener(v -> confirm =
                new AlertDialog.Builder(this)
                .setTitle("Cảnh báo!!!")
                .setMessage("Bạn có chắc chắn tiếp tục?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    confirm.dismiss();
                    processing = new AlertDialog.Builder(SettlementActivity.this)
                            .setTitle("Đang xử lý dữ liệu!!!")
                            .setMessage("Hệ thống đang xử lý dữ liệu, bạn vui lòng chờ xíu nhé!!!")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    presentMPM.
                            setNumberOfElectricity(Integer.parseInt(soDien.getText().toString()));
                    presentMPM.setNumberOfWater(Integer.parseInt(soNuoc.getText().toString()));
                    maintenance.setnNetMoney(maintenance.getnNetMoney() + 50000);

                    new Thread(() -> runOnUiThread(() -> settlementPresenter.addMPM(DataProcessing.
                            getNewListAccount(account, listAccount, listPreviousRecord,
                                    prePreviousMPM, previousMPM), presentMPM,
                            maintenance))).start();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());
    }


    /****************************Interface Functions***************************/
    @Override
    public void addSuccess() {
        Toast.makeText(getApplicationContext(), "Add successfully!!!",
                Toast.LENGTH_SHORT).show();
        ((GlobalVariable) this.getApplication()).setListAccount(DataProcessing.
                getNewListAccount(account, listAccount, listPreviousRecord,
                        prePreviousMPM, previousMPM));

        ((GlobalVariable) this.getApplication()).setAccount(DataProcessing.
                getAccount(DataProcessing.getNewAccount(account,
                ((GlobalVariable) this.getApplication()).getListAccount())));

        ((GlobalVariable) this.getApplication()).setPrePreviousMPM(DataProcessing.
                getMPM(previousMPM));

        ((GlobalVariable) this.getApplication()).setPreviousMPM(DataProcessing.getMPM(presentMPM));

        ((GlobalVariable) this.getApplication()).setPresentMPM(DataProcessing.
                getPresentMPM(((GlobalVariable) this.getApplication()).getAccount(),
                        presentMPM, 0, 0));

        ((GlobalVariable) this.getApplication()).
                setListPreviousRecord(DataProcessing.getListRecord(listRecord));
        ((GlobalVariable) this.getApplication()).setListRecord(new ArrayList<>());
        ((GlobalVariable) this.getApplication()).setMaintenance(DataProcessing.
                getMaintenance(maintenance));
        processing.dismiss();
        finish();
    }

    @Override
    public void addError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectFailed() {
        Toast.makeText(getApplicationContext(), "Connection failed!!!",
                Toast.LENGTH_SHORT).show();
    }

}
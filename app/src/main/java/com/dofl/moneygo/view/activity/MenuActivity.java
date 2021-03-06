package com.dofl.moneygo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.GlobalVariable;
import com.dofl.moneygo.model.MoneyPackage;
import com.dofl.moneygo.model.RegisteredAccount;
import com.dofl.moneygo.model.Summary;
import com.dofl.moneygo.presenter.MenuInterface;
import com.dofl.moneygo.presenter.MenuPresenter;
import com.dofl.moneygo.presenter.utils.DataProcessing;
import com.dofl.moneygo.presenter.utils.ForegroundServices;
import com.dofl.moneygo.view.adapter.GridViewMenuAdapter;

import java.util.Date;

public class MenuActivity extends AppCompatActivity implements MenuInterface {
    private DrawerLayout drawerLayout;
    private MenuPresenter menuPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initValue();
        initGridView();
    }


    /****************************Initial Value***************************/
    private void initValue() {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        menuPresenter = new MenuPresenter(this);
        menuPresenter.updateRegisteredAccount();
        menuPresenter.checkMaintenance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
    }

    private void initGridView() {
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewMenuAdapter(this));

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            if (checkNetworkStatus()) {
                switch (position) {
                    case 0:
                        if (checkNetworkStatus()) {
                            if (DataProcessing
                                    .compareValidateDate(((GlobalVariable) this.getApplication())
                                                    .getPresentSummaryPackage().getStartDate(),
                                            DataProcessing.getFormattedDate(new Date()))) {
                                Intent intent = new Intent(MenuActivity.this,
                                        AddActivity.class);
                                startActivity(intent);
                            } else {
                                new AlertDialog.Builder(this)
                                        .setTitle("T??? ch???i truy c???p!!!")
                                        .setMessage("Ch???c n??ng n??y b??? v?? hi???u ho?? cho ?????n " +
                                                ((GlobalVariable) this.getApplication())
                                                        .getPresentSummaryPackage().getStartDate())
                                        .setPositiveButton(android.R.string.ok, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        }
                        break;

                    case 1:
                        Intent intent1 = new Intent(MenuActivity.this,
                                HistoryActivity.class);
                        startActivity(intent1);
                        break;

                    case 2:
                        Intent intent2 = new Intent(MenuActivity.this,
                                ReportActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MenuActivity.this,
                                FeeDetailActivity.class);
                        startActivity(intent3);
                        break;
                    case 5:
                        if (((GlobalVariable) this.getApplication())
                                .getAccount().getRole()
                                .equalsIgnoreCase("administrator")) {
                            Intent intent5 = new Intent(MenuActivity.this,
                                    ManageActivity.class);
                            startActivity(intent5);
                        } else {
                            new AlertDialog.Builder(this)
                                    .setTitle("T??? ch???i truy c???p!!!")
                                    .setMessage("B???n kh??ng ????? quy???n ????? s??? d???ng ch???c n??ng n??y!!!")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        break;

                }
            }
        });
    }


    /****************************Initial Functions***************************/
    private void initFunction() {
        checkNetworkStatus();
    }

    private boolean checkNetworkStatus() {
        if (!getNetworkStatus()) {
            new AlertDialog.Builder(MenuActivity.this)
                    .setTitle("Th??ng b??o!!!")
                    .setMessage("Kh??ng c?? k???t n???i m???ng, ch???n OK ????? ????ng ???ng d???ng!!!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> finishAffinity())
                    .show();
        }
        return getNetworkStatus();
    }

    private boolean getNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
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
            findViewById(R.id.signout).setOnClickListener(v -> {
                SharedPreferences loginPreferences
                        = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
                loginPrefsEditor.putString("email", null);
                loginPrefsEditor.putString("password", null);
                loginPrefsEditor.apply();
                Intent serviceIntent = new Intent(this, ForegroundServices.class);
                stopService(serviceIntent);
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                MenuActivity.this.finish();
            });
        }

        if (item.getItemId() == R.id.top_bar_noti) {
            Intent serviceIntent = new Intent(this, ForegroundServices.class);
            stopService(serviceIntent);
        }
        return super.onOptionsItemSelected(item);
    }


    /****************************Interface Functions***************************/
    @Override
    public void updateRegisteredAccountSuccess(RegisteredAccount registeredAccount) {
        Log.e("Update1", "RegisteredAccountSuccess");
        ((GlobalVariable) this.getApplication()).setRegisteredAccount(registeredAccount);
        menuPresenter.updateAccount(((GlobalVariable) this.getApplication()).getAccount());

    }

    @Override
    public void updateAccountSuccess(Account account) {
        Log.e("Update2", "AccountSuccess");
        Intent serviceIntent = new Intent(this, ForegroundServices.class);
        serviceIntent.putExtra("accountId", account.getId() + "");
        serviceIntent.putExtra("username", account.getDisplayName() + "");
        ContextCompat.startForegroundService(this, serviceIntent);
        ((GlobalVariable) this.getApplication()).setAccount(account);
        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());
        menuPresenter.updateMoneyPackage();
    }

    @Override
    public void updatePresentMoneyPackageSuccess(MoneyPackage presentMoneyPackage) {
        Log.e("Update3", "PresentMoneyPackageSuccess");
        ((GlobalVariable) this.getApplication()).setPresentMoneyPackage(presentMoneyPackage);
        if (((GlobalVariable) this.getApplication()).getPresentMoneyPackage() != null
                && ((GlobalVariable) this.getApplication()).getPreviousMoneyPackage() != null) {
            menuPresenter.updateSummaryPackage();
        }
    }

    @Override
    public void updatePreviousMoneyPackageSuccess(MoneyPackage previousMoneyPackage) {
        Log.e("Update4", "PreviousMoneyPackageSuccess");
        ((GlobalVariable) this.getApplication()).setPreviousMoneyPackage(previousMoneyPackage);
        if (((GlobalVariable) this.getApplication()).getPresentMoneyPackage() != null
                && ((GlobalVariable) this.getApplication()).getPreviousMoneyPackage() != null) {
            menuPresenter.updateSummaryPackage();
        }
    }

    @Override
    public void updatePresentSummaryPackageSuccess(Summary presentSummaryPackage) {
        Log.e("Update6", "PresentSummaryPackageSuccess");
        ((GlobalVariable) this.getApplication()).setPresentSummaryPackage(presentSummaryPackage);
    }

    @Override
    public void updatePreviousSummaryPackageSuccess(Summary previousSummaryPackage) {
        Log.e("Update5", "PreviousSummaryPackageSuccess");
        ((GlobalVariable) this.getApplication()).setPreviousSummaryPackage(previousSummaryPackage);
    }

    @Override
    public void updateError(String msg) {
        Log.e("Log", msg);
        initFunction();
    }

    @Override
    public void maintenance() {
        new AlertDialog.Builder(MenuActivity.this)
                .setTitle("Th??ng b??o!!!")
                .setMessage("H??? th???ng ??ang b???o tr??, ch???n OK ????? ????ng ???ng d???ng!!!")
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
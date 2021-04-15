package com.dofl.qlct.view.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.GlobalVariable;
import com.dofl.qlct.model.Maintenance;
import com.dofl.qlct.presenter.PaymentInterface;
import com.dofl.qlct.presenter.PaymentPresenter;
import com.dofl.qlct.presenter.utils.DataProcessing;
import com.dofl.qlct.presenter.utils.HideKeyboard;

import java.util.List;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity implements PaymentInterface {
    private DrawerLayout drawerLayout;
    private List<Account> listAccount;
    private Account updateAccount;
    private AlertDialog confirm, processing;
    private Maintenance maintenance;
    private PaymentPresenter paymentPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initValue();
        setOnClickFunctionButton();
    }


    /****************************Init Value***************************/
    private void initValue() {
        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.add_view),
                PaymentActivity.this)).start();

        listAccount = DataProcessing.
                getListAccount(((GlobalVariable) this.getApplication()).getListAccount());
        Account account = DataProcessing.getAccount(((GlobalVariable) this.getApplication()).getAccount());
        maintenance = DataProcessing.
                getMaintenance(((GlobalVariable) this.getApplication()).getMaintenance());

        paymentPresenter = new PaymentPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        findViewById(R.id.update).setOnClickListener(v -> confirm = new AlertDialog.Builder(this)
                .setTitle("Cảnh báo!!!")
                .setMessage("Bạn có chắc chắn tiếp tục?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    confirm.dismiss();
                    Spinner spinner = findViewById(R.id.name);
                    EditText money = findViewById(R.id.money);
                    boolean ok = false;
                    switch (spinner.getSelectedItem().toString()) {
                        case "Quân Jullian":
                            for (Account accountTemp : listAccount) {
                                if (accountTemp.getId() == 1) {
                                    updateAccount = accountTemp;
                                    updateAccount.setMoneyPaid(updateAccount.getMoneyPaid() +
                                            Integer.parseInt(money.getText().toString()));
                                    ok = true;
                                    break;
                                }
                            }
                            break;
                        case "Nguyễn Doãn Sơn":
                            for (Account accountTemp : listAccount) {
                                if (accountTemp.getId() == 2) {
                                    updateAccount = accountTemp;
                                    updateAccount.setMoneyPaid(updateAccount.getMoneyPaid() +
                                            Integer.parseInt(money.getText().toString()));
                                    ok = true;
                                    break;
                                }
                            }
                            break;
                        case "Vũ Trường Sơn":
                            for (Account accountTemp : listAccount) {
                                if (accountTemp.getId() == 3) {
                                    updateAccount = accountTemp;
                                    updateAccount.setMoneyPaid(updateAccount.getMoneyPaid() +
                                            Integer.parseInt(money.getText().toString()));
                                    ok = true;
                                    break;
                                }
                            }
                            break;
                        case "Nguyễn Xuân Lâm":
                            for (Account accountTemp : listAccount) {
                                if (accountTemp.getId() == 4) {
                                    updateAccount = accountTemp;
                                    updateAccount.setMoneyPaid(updateAccount.getMoneyPaid() +
                                            Integer.parseInt(money.getText().toString()));
                                    ok = true;
                                    break;
                                }
                            }
                            break;
                        case "Mạng Hàng Xóm":
                            if (maintenance.getnNetMoney() == 0) {
                                Toast.makeText(this, "Hàng xóm đã trả rồi mà!!!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                maintenance.setnNetMoney(maintenance.getnNetMoney() -
                                        Integer.parseInt(money.getText().toString()));
                                ok = true;
                            }
                            break;

                    }
                    if (ok) {
                        processing = new AlertDialog.Builder(PaymentActivity.this)
                                .setTitle("Đang cập nhật dữ liệu!!!")
                                .setMessage("Hệ thống đang cập nhật dữ liệu, bạn vui lòng chờ xíu nhé!!!")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                        new Thread(() -> runOnUiThread(() -> paymentPresenter.addMPM(updateAccount, maintenance))).start();
                    }

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
        if (updateAccount != null) {
            for (int i = 0; i < listAccount.size(); i++) {
                if (updateAccount.getId() == listAccount.get(i).getId()) {
                    listAccount.get(i).setMoneyPaid(updateAccount.getMoneyPaid());
                    ((GlobalVariable) this.getApplication()).
                            setListAccount(DataProcessing.getListAccount(listAccount));
                    break;
                }
            }
        } else {
            ((GlobalVariable) this.getApplication()).setMaintenance(DataProcessing.
                    getMaintenance(maintenance));
        }
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
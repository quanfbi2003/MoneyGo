package com.dofl.moneygo.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.GlobalVariable;
import com.dofl.moneygo.presenter.PaymentInterface;
import com.dofl.moneygo.presenter.PaymentPresenter;
import com.dofl.moneygo.presenter.utils.DataProcessing;
import com.dofl.moneygo.presenter.utils.HideKeyboard;

import java.util.Objects;

public class PaymentActivity extends AppCompatActivity implements PaymentInterface {
    private PaymentPresenter paymentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initValue();
    }


    /****************************Initial Value***************************/
    private void initValue() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        checkNetworkStatus();
        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.payment_view),
                PaymentActivity.this)).start();

        importValue();
        paymentPresenter = new PaymentPresenter(this);

        findViewById(R.id.update).setOnClickListener(v ->
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Cảnh báo!!!")
                        .setMessage("Bạn có chắc chắn tiếp tục?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            Spinner spinner = findViewById(R.id.name);
                            EditText money = findViewById(R.id.money);
                            switch (spinner.getSelectedItem().toString()) {
                                case "Quân Jullian":
                                    paymentPresenter.payForN1(((GlobalVariable) this
                                                    .getApplication()).getAccount(),
                                            ((GlobalVariable) this
                                                    .getApplication()).getN1MoneyPackage(),
                                            Integer.parseInt(money.getText().toString()));
                                    break;
                                case "Nguyễn Doãn Sơn":
                                    paymentPresenter.payForN2(((GlobalVariable) this
                                                    .getApplication()).getAccount(),
                                            ((GlobalVariable) this
                                                    .getApplication()).getN2MoneyPackage(),
                                            Integer.parseInt(money.getText().toString()));
                                    break;
                                case "Vũ Trường Sơn":
                                    paymentPresenter.payForN3(((GlobalVariable) this
                                                    .getApplication()).getAccount(),
                                            ((GlobalVariable) this
                                                    .getApplication()).getN3MoneyPackage(),
                                            Integer.parseInt(money.getText().toString()));
                                    break;
                                case "Nguyễn Xuân Lâm":
                                    paymentPresenter.payForN4(((GlobalVariable) this
                                                    .getApplication()).getAccount(),
                                            ((GlobalVariable) this
                                                    .getApplication()).getN4MoneyPackage(),
                                            Integer.parseInt(money.getText().toString()));
                                    break;
                                case "Mạng Hàng Xóm":
                                    paymentPresenter.payForHX(((GlobalVariable) this
                                                    .getApplication()).getNeighborNetwork(),
                                            Integer.parseInt(money.getText().toString()));
                                    break;
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show());
    }

    @SuppressLint("SetTextI18n")
    private void importValue() {
        TextView txtQuan = findViewById(R.id.txtQuan);
        TextView valQuan = findViewById(R.id.valQuan);
        TextView txtDoan = findViewById(R.id.txtDoan);
        TextView valDoan = findViewById(R.id.valDoan);
        TextView txtSon = findViewById(R.id.txtSon);
        TextView valSon = findViewById(R.id.valSon);
        TextView txtLam = findViewById(R.id.txtLam);
        TextView valLam = findViewById(R.id.valLam);
        TextView txtHx = findViewById(R.id.txtHx);
        TextView valHx = findViewById(R.id.valHx);
        Spinner spinner = findViewById(R.id.name);
        EditText money = findViewById(R.id.money);

        spinner.setSelection(0);
        money.setText("");

        int n1MoneyPaid = ((GlobalVariable) this.getApplication())
                .getN1MoneyPackage().getPreviousMoney()
                - ((GlobalVariable) this.getApplication())
                .getN1MoneyPackage().getMoneySent();
        if (n1MoneyPaid <= 0) {
            valQuan.setText(DataProcessing
                    .formatIntToString(n1MoneyPaid * -1) + " đ");
            txtQuan.setTextColor(Color.parseColor("#43A047"));
            valQuan.setTextColor(Color.parseColor("#43A047"));
            txtQuan.setText("Số tiền còn thừa:");
        } else {
            valQuan.setText(DataProcessing.
                    formatIntToString(n1MoneyPaid) + " đ");
            txtQuan.setTextColor(Color.RED);
            valQuan.setTextColor(Color.RED);
            txtQuan.setText("Số tiền còn nợ:");
        }

        int n2MoneyPaid = ((GlobalVariable) this.getApplication())
                .getN2MoneyPackage().getPreviousMoney()
                - ((GlobalVariable) this.getApplication())
                .getN2MoneyPackage().getMoneySent();
        if (n2MoneyPaid <= 0) {
            valDoan.setText(DataProcessing
                    .formatIntToString(n2MoneyPaid * -1) + " đ");
            txtDoan.setTextColor(Color.parseColor("#43A047"));
            valDoan.setTextColor(Color.parseColor("#43A047"));
            txtDoan.setText("Số tiền còn thừa:");
        } else {
            valDoan.setText(DataProcessing.
                    formatIntToString(n2MoneyPaid) + " đ");
            txtDoan.setTextColor(Color.RED);
            valDoan.setTextColor(Color.RED);
            txtDoan.setText("Số tiền còn nợ:");
        }

        int n3MoneyPaid = ((GlobalVariable) this.getApplication())
                .getN3MoneyPackage().getPreviousMoney()
                - ((GlobalVariable) this.getApplication())
                .getN3MoneyPackage().getMoneySent();
        if (n3MoneyPaid <= 0) {
            valSon.setText(DataProcessing
                    .formatIntToString(n3MoneyPaid * -1) + " đ");
            txtSon.setTextColor(Color.parseColor("#43A047"));
            valSon.setTextColor(Color.parseColor("#43A047"));
            txtSon.setText("Số tiền còn thừa:");
        } else {
            valSon.setText(DataProcessing.
                    formatIntToString(n3MoneyPaid) + " đ");
            txtSon.setTextColor(Color.RED);
            valSon.setTextColor(Color.RED);
            txtSon.setText("Số tiền còn nợ:");
        }

        int n4MoneyPaid = ((GlobalVariable) this.getApplication())
                .getN4MoneyPackage().getPreviousMoney()
                - ((GlobalVariable) this.getApplication())
                .getN4MoneyPackage().getMoneySent();
        if (n4MoneyPaid <= 0) {
            valLam.setText(DataProcessing
                    .formatIntToString(n4MoneyPaid * -1) + " đ");
            txtLam.setTextColor(Color.parseColor("#43A047"));
            valLam.setTextColor(Color.parseColor("#43A047"));
            txtLam.setText("Số tiền còn thừa:");
        } else {
            valLam.setText(DataProcessing.
                    formatIntToString(n4MoneyPaid) + " đ");
            txtLam.setTextColor(Color.RED);
            valLam.setTextColor(Color.RED);
            txtLam.setText("Số tiền còn nợ:");
        }

        int hxPaid = ((GlobalVariable) this.getApplication()).getNeighborNetwork();
        if (hxPaid <= 0) {
            valHx.setText(DataProcessing
                    .formatIntToString(hxPaid * -1) + " đ");
            txtHx.setTextColor(Color.parseColor("#43A047"));
            valHx.setTextColor(Color.parseColor("#43A047"));
            txtHx.setText("Số tiền còn thừa:");
        } else {
            valHx.setText(DataProcessing.
                    formatIntToString(hxPaid) + " đ");
            txtHx.setTextColor(Color.RED);
            valHx.setTextColor(Color.RED);
            txtHx.setText("Số tiền còn nợ:");
        }
    }

    /****************************Network Functions***************************/
    private void checkNetworkStatus() {
        if (!getNetworkStatus()) {
            new AlertDialog.Builder(PaymentActivity.this)
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
    public void addSuccess() {
        Toast.makeText(getApplicationContext(), "Thanh toán thành công!!!",
                Toast.LENGTH_SHORT).show();
        importValue();
    }

    @Override
    public void addError(String msg) {
        Log.e("Log", msg);
    }
}
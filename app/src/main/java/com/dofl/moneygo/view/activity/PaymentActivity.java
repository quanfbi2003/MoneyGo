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
import com.dofl.moneygo.view.adapter.SpinnerPaymentAdapter;

import java.util.Objects;

public class PaymentActivity extends AppCompatActivity implements PaymentInterface {
    private PaymentPresenter paymentPresenter;
    private Spinner spinner;

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
        spinner = findViewById(R.id.name);
        spinner.setAdapter(
                new SpinnerPaymentAdapter(PaymentActivity.this,
                        R.layout.spinner_payment_layout,
                        R.id.textView_item_name,
                        ((GlobalVariable) this.getApplication())
                                .getRegisteredAccount()));
        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.payment_view),
                PaymentActivity.this)).start();

        importValue();
        paymentPresenter = new PaymentPresenter(this);

        findViewById(R.id.update).setOnClickListener(v ->
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Cảnh báo!!!")
                        .setMessage("Bạn có chắc chắn tiếp tục?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            EditText money = findViewById(R.id.money);

                            switch ((int) spinner.getSelectedItemId()) {
                                case 0:
                                    paymentPresenter.payForN1(((GlobalVariable) this
                                                    .getApplication()).getAccount(),
                                            ((GlobalVariable) this
                                                    .getApplication()).getN1MoneyPackage(),
                                            Integer.parseInt(money.getText().toString()));
                                    break;
                                case 1:
                                    paymentPresenter.payForN2(((GlobalVariable) this
                                                    .getApplication()).getAccount(),
                                            ((GlobalVariable) this
                                                    .getApplication()).getN2MoneyPackage(),
                                            Integer.parseInt(money.getText().toString()));
                                    break;
                                case 2:
                                    paymentPresenter.payForN3(((GlobalVariable) this
                                                    .getApplication()).getAccount(),
                                            ((GlobalVariable) this
                                                    .getApplication()).getN3MoneyPackage(),
                                            Integer.parseInt(money.getText().toString()));
                                    break;
                                case 3:
                                    paymentPresenter.payForN4(((GlobalVariable) this
                                                    .getApplication()).getAccount(),
                                            ((GlobalVariable) this
                                                    .getApplication()).getN4MoneyPackage(),
                                            Integer.parseInt(money.getText().toString()));
                                    break;
                                case 4:
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
        TextView txtN1 = findViewById(R.id.txtN1);
        TextView valN1 = findViewById(R.id.valN1);
        TextView txtN2 = findViewById(R.id.txtN2);
        TextView valN2 = findViewById(R.id.valN2);
        TextView txtN3 = findViewById(R.id.txtN3);
        TextView valN3 = findViewById(R.id.valN3);
        TextView txtN4 = findViewById(R.id.txtN4);
        TextView valN4 = findViewById(R.id.valN4);
        TextView txtHx = findViewById(R.id.txtHx);
        TextView valHx = findViewById(R.id.valHx);
        Spinner spinner = findViewById(R.id.name);
        EditText money = findViewById(R.id.money);
        TextView nameN1 = findViewById(R.id.nameN1);
        TextView nameN2 = findViewById(R.id.nameN2);
        TextView nameN3 = findViewById(R.id.nameN3);
        TextView nameN4 = findViewById(R.id.nameN4);

        nameN1.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN1());
        nameN2.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN2());
        nameN3.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN3());
        nameN4.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN4());

        spinner.setSelection(0);
        money.setText("");

        int n1MoneyPaid = ((GlobalVariable) this.getApplication())
                .getN1MoneyPackage().getPreviousMoney()
                - ((GlobalVariable) this.getApplication())
                .getN1MoneyPackage().getMoneySent();
        if (n1MoneyPaid <= 0) {
            valN1.setText(DataProcessing
                    .formatIntToString(n1MoneyPaid * -1) + " đ");
            txtN1.setTextColor(Color.parseColor("#43A047"));
            valN1.setTextColor(Color.parseColor("#43A047"));
            txtN1.setText("Số tiền còn thừa:");
        } else {
            valN1.setText(DataProcessing.
                    formatIntToString(n1MoneyPaid) + " đ");
            txtN1.setTextColor(Color.RED);
            valN1.setTextColor(Color.RED);
            txtN1.setText("Số tiền còn nợ:");
        }

        int n2MoneyPaid = ((GlobalVariable) this.getApplication())
                .getN2MoneyPackage().getPreviousMoney()
                - ((GlobalVariable) this.getApplication())
                .getN2MoneyPackage().getMoneySent();
        if (n2MoneyPaid <= 0) {
            valN2.setText(DataProcessing
                    .formatIntToString(n2MoneyPaid * -1) + " đ");
            txtN2.setTextColor(Color.parseColor("#43A047"));
            valN2.setTextColor(Color.parseColor("#43A047"));
            txtN2.setText("Số tiền còn thừa:");
        } else {
            valN2.setText(DataProcessing.
                    formatIntToString(n2MoneyPaid) + " đ");
            txtN2.setTextColor(Color.RED);
            valN2.setTextColor(Color.RED);
            txtN2.setText("Số tiền còn nợ:");
        }

        int n3MoneyPaid = ((GlobalVariable) this.getApplication())
                .getN3MoneyPackage().getPreviousMoney()
                - ((GlobalVariable) this.getApplication())
                .getN3MoneyPackage().getMoneySent();
        if (n3MoneyPaid <= 0) {
            valN3.setText(DataProcessing
                    .formatIntToString(n3MoneyPaid * -1) + " đ");
            txtN3.setTextColor(Color.parseColor("#43A047"));
            valN3.setTextColor(Color.parseColor("#43A047"));
            txtN3.setText("Số tiền còn thừa:");
        } else {
            valN3.setText(DataProcessing.
                    formatIntToString(n3MoneyPaid) + " đ");
            txtN3.setTextColor(Color.RED);
            valN3.setTextColor(Color.RED);
            txtN3.setText("Số tiền còn nợ:");
        }

        int n4MoneyPaid = ((GlobalVariable) this.getApplication())
                .getN4MoneyPackage().getPreviousMoney()
                - ((GlobalVariable) this.getApplication())
                .getN4MoneyPackage().getMoneySent();
        if (n4MoneyPaid <= 0) {
            valN4.setText(DataProcessing
                    .formatIntToString(n4MoneyPaid * -1) + " đ");
            txtN4.setTextColor(Color.parseColor("#43A047"));
            valN4.setTextColor(Color.parseColor("#43A047"));
            txtN4.setText("Số tiền còn thừa:");
        } else {
            valN4.setText(DataProcessing.
                    formatIntToString(n4MoneyPaid) + " đ");
            txtN4.setTextColor(Color.RED);
            valN4.setTextColor(Color.RED);
            txtN4.setText("Số tiền còn nợ:");
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
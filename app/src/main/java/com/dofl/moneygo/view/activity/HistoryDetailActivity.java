package com.dofl.moneygo.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.GlobalVariable;
import com.dofl.moneygo.model.Record;
import com.dofl.moneygo.presenter.HistoryDetailInterface;
import com.dofl.moneygo.presenter.HistoryDetailPresenter;
import com.dofl.moneygo.presenter.utils.DataProcessing;

import java.util.Objects;

public class HistoryDetailActivity extends AppCompatActivity implements HistoryDetailInterface {
    private String key;
    private Record record;
    private HistoryDetailPresenter historyDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        initValue();
        setOnClickFunctionButton();

    }


    /****************************Initial Value***************************/
    @SuppressLint("SetTextI18n")
    private void initValue() {
        key = getIntent().getStringExtra("key");
        record = (Record) getIntent().getSerializableExtra("record");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        checkNetworkStatus();
        historyDetailPresenter = new HistoryDetailPresenter(this);

        TextView date = findViewById(R.id.date);
        TextView time = findViewById(R.id.time);
        TextView description = findViewById(R.id.description);
        TextView total = findViewById(R.id.total);

        date.setText(DataProcessing.getExactDate(DataProcessing.getDate(record.getDateCreate())));
        time.setText(record.getTimeCreate());
        description.setText(record.getDescription());
        total.setText(DataProcessing.formatIntToString(record.getTotal()) + " ??");

        TextView nameN1 = findViewById(R.id.textViewNameN1);
        TextView nameN2 = findViewById(R.id.textViewNameN2);
        TextView nameN3 = findViewById(R.id.textViewNameN3);
        TextView nameN4 = findViewById(R.id.textViewNameN4);

        nameN1.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN1());
        nameN2.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN2());
        nameN3.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN3());
        nameN4.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN4());

        TextView soTienDaMuaN1 = findViewById(R.id.textViewSTDMN1);
        TextView soTienConNoN1 = findViewById(R.id.textViewSTCNN1);
        TextView soTienConThuaN1 = findViewById(R.id.textViewSTCTN1);
        TextView tyLeThamGiaN1 = findViewById(R.id.textViewTLTGN1);

        if (record.getBuyer() == 1) {
            soTienDaMuaN1.setText(DataProcessing.formatIntToString(record.getTotal()) + " ??");
            soTienConNoN1.setText("0 ??");
            soTienConThuaN1.setText(DataProcessing.
                    formatIntToString(record.getN1Total() * (-1)) + " ??");
        } else {
            soTienDaMuaN1.setText("0 ??");
            soTienConNoN1.setText(DataProcessing.formatIntToString(record.getN1Total()) + " ??");
            soTienConThuaN1.setText("0 ??");
        }
        tyLeThamGiaN1.setText(record.getN1Qty() + "/" + record.getQty());

        TextView soTienDaMuaN2 = findViewById(R.id.textViewSTDMN2);
        TextView soTienConNoN2 = findViewById(R.id.textViewSTCNN2);
        TextView soTienConThuaN2 = findViewById(R.id.textViewSTCTN2);
        TextView tyLeThamGiaN2 = findViewById(R.id.textViewTLTGN2);

        if (record.getBuyer() == 2) {
            soTienDaMuaN2.setText(DataProcessing.formatIntToString(record.getTotal()) + " ??");
            soTienConNoN2.setText("0 ??");
            soTienConThuaN2.setText(DataProcessing.formatIntToString(record.getN2Total()) + " ??");
        } else {
            soTienDaMuaN2.setText("0 ??");
            soTienConNoN2.setText(DataProcessing.formatIntToString(record.getN2Total()) + " ??");
            soTienConThuaN2.setText("0 ??");
        }
        tyLeThamGiaN2.setText(record.getN2Qty() + "/" + record.getQty());

        TextView soTienDaMuaN3 = findViewById(R.id.textViewSTDMN3);
        TextView soTienConNoN3 = findViewById(R.id.textViewSTCNN3);
        TextView soTienConThuaN3 = findViewById(R.id.textViewSTCTN3);
        TextView tyLeThamGiaN3 = findViewById(R.id.textViewTLTGN3);

        if (record.getBuyer() == 3) {
            soTienDaMuaN3.setText(DataProcessing.formatIntToString(record.getTotal()) + " ??");
            soTienConNoN3.setText("0 ??");
            soTienConThuaN3.setText(DataProcessing.formatIntToString(record.getN3Total()) + " ??");
        } else {
            soTienDaMuaN3.setText("0 ??");
            soTienConNoN3.setText(DataProcessing.formatIntToString(record.getN3Total()) + " ??");
            soTienConThuaN3.setText("0 ??");
        }
        tyLeThamGiaN3.setText(record.getN3Qty() + "/" + record.getQty());

        TextView soTienDaMuaN4 = findViewById(R.id.textViewSTDMN4);
        TextView soTienConNoN4 = findViewById(R.id.textViewSTCNN4);
        TextView soTienConThuaN4 = findViewById(R.id.textViewSTCTN4);
        TextView tyLeThamGiaN4 = findViewById(R.id.textViewTLTGN4);

        if (record.getBuyer() == 4) {
            soTienDaMuaN4.setText(DataProcessing.formatIntToString(record.getTotal()) + " ??");
            soTienConNoN4.setText("0 ??");
            soTienConThuaN4.setText(DataProcessing.formatIntToString(record.getN4Total()) + " ??");
        } else {
            soTienDaMuaN4.setText("0 ??");
            soTienConNoN4.setText(DataProcessing.formatIntToString(record.getN4Total()) + " ??");
            soTienConThuaN4.setText("0 ??");
        }
        tyLeThamGiaN4.setText(record.getN4Qty() + "/" + record.getQty());
    }


    /****************************Network Functions***************************/
    private void checkNetworkStatus() {
        if (!getNetworkStatus()) {
            new AlertDialog.Builder(HistoryDetailActivity.this)
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_history_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(HistoryDetailActivity.this, AddActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /****************************OnClick Function Button***************************/
    @SuppressLint("SetTextI18n")
    private void setOnClickFunctionButton() {
        findViewById(R.id.edit).setOnClickListener(v -> {
            if (((GlobalVariable) getApplication()).getAccount().getId() == record.getBuyer()
                    && record.getSummaryPackage()
                    .equalsIgnoreCase(((GlobalVariable) getApplication())
                            .getPresentMoneyPackage().getSummaryPackage())) {
                Intent intent = new Intent(HistoryDetailActivity.this,
                        EditHistoryActivity.class);
                intent.putExtra("key", key);
                intent.putExtra("record", record);
                startActivity(intent);
                this.finish();
            } else {
                Toast.makeText(this.getApplicationContext(), "B???n kh??ng ????? quy???n ????? s???a b???n ghi n??y!!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.delete).setOnClickListener(v -> {
            if (((GlobalVariable) getApplication()).getAccount().getId() == record.getBuyer()
                    && record.getSummaryPackage()
                    .equalsIgnoreCase(((GlobalVariable) getApplication())
                            .getPresentMoneyPackage().getSummaryPackage())) {
                new AlertDialog.Builder(HistoryDetailActivity.this)
                        .setTitle("C???nh b??o!!!")
                        .setMessage("B???n c?? ch???c ch???n mu???n x??a b???n ghi!!!")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes,
                                (dialog, which) -> historyDetailPresenter
                                        .removeRecord(((GlobalVariable) getApplication())
                                                .getPresentMoneyPackage().getRecordPackage(), key))
                        .setNegativeButton(android.R.string.no, null)
                        .show();

            } else {
                Toast.makeText(this.getApplicationContext(), "B???n kh??ng ????? quy???n ????? x??a b???n ghi n??y!!!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    /****************************Interface Functions***************************/
    @Override
    public void removeSuccess() {
        Toast.makeText(getApplicationContext(), "Delete successfully!!!",
                Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void removeError(String msg) {
        Toast.makeText(getApplicationContext(), "Delete unsuccessfully!!!",
                Toast.LENGTH_SHORT).show();
    }


}
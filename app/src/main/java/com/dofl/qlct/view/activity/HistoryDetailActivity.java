package com.dofl.qlct.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.HistoryDetailInterface;
import com.dofl.qlct.presenter.HistoryDetailPresenter;
import com.dofl.qlct.presenter.utils.BundlePackage;
import com.dofl.qlct.presenter.utils.DataProcessing;

import java.util.Objects;

public class HistoryDetailActivity extends AppCompatActivity implements HistoryDetailInterface {
    private DrawerLayout drawerLayout;
    private Account account;
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
        account = BundlePackage.getBundleAccount(getIntent());
        record = BundlePackage.getBundleRecord(getIntent());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        historyDetailPresenter = new HistoryDetailPresenter(this);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        record = DataProcessing.processing(record);

        TextView date = findViewById(R.id.date);
        TextView time = findViewById(R.id.time);
        TextView description = findViewById(R.id.description);
        TextView total = findViewById(R.id.total);

        date.setText(record.getDate_create());
        time.setText(record.getTime_create());
        description.setText(record.getDescription());
        total.setText(record.getTotal() + " đ");

        TextView soTienDaMuaN1 = findViewById(R.id.textViewSTDMN1);
        TextView soTienConNoN1 = findViewById(R.id.textViewSTCNN1);
        TextView soTienConThuaN1 = findViewById(R.id.textViewSTCTN1);
        TextView tyLeThamGiaN1 = findViewById(R.id.textViewTLTGN1);

        if (record.getBuyer() == 1) {
            soTienDaMuaN1.setText(record.getTotal() + " đ");
            soTienConNoN1.setText("0 đ");
            soTienConThuaN1.setText(record.getN1_total() * (-1) + " đ");
        } else {
            soTienDaMuaN1.setText("0 đ");
            soTienConNoN1.setText(record.getN1_total() + " đ");
            soTienConThuaN1.setText("0 đ");
        }
        tyLeThamGiaN1.setText(record.getN1_qty() + "/" + record.getQty());

        TextView soTienDaMuaN2 = findViewById(R.id.textViewSTDMN2);
        TextView soTienConNoN2 = findViewById(R.id.textViewSTCNN2);
        TextView soTienConThuaN2 = findViewById(R.id.textViewSTCTN2);
        TextView tyLeThamGiaN2 = findViewById(R.id.textViewTLTGN2);

        if (record.getBuyer() == 2) {
            soTienDaMuaN2.setText(record.getTotal() + " đ");
            soTienConNoN2.setText("0 đ");
            soTienConThuaN2.setText(record.getN2_total() + " đ");
        } else {
            soTienDaMuaN2.setText("0 đ");
            soTienConNoN2.setText(record.getN2_total() + " đ");
            soTienConThuaN2.setText("0 đ");
        }
        tyLeThamGiaN2.setText(record.getN2_qty() + "/" + record.getQty());

        TextView soTienDaMuaN3 = findViewById(R.id.textViewSTDMN3);
        TextView soTienConNoN3 = findViewById(R.id.textViewSTCNN3);
        TextView soTienConThuaN3 = findViewById(R.id.textViewSTCTN3);
        TextView tyLeThamGiaN3 = findViewById(R.id.textViewTLTGN3);

        if (record.getBuyer() == 3) {
            soTienDaMuaN3.setText(record.getTotal() + " đ");
            soTienConNoN3.setText("0 đ");
            soTienConThuaN3.setText(record.getN3_total() + " đ");
        } else {
            soTienDaMuaN3.setText("0 đ");
            soTienConNoN3.setText(record.getN3_total() + " đ");
            soTienConThuaN3.setText("0 đ");
        }
        tyLeThamGiaN3.setText(record.getN3_qty() + "/" + record.getQty());

        TextView soTienDaMuaN4 = findViewById(R.id.textViewSTDMN4);
        TextView soTienConNoN4 = findViewById(R.id.textViewSTCNN4);
        TextView soTienConThuaN4 = findViewById(R.id.textViewSTCTN4);
        TextView tyLeThamGiaN4 = findViewById(R.id.textViewTLTGN4);

        if (record.getBuyer() == 4) {
            soTienDaMuaN4.setText(record.getTotal() + " đ");
            soTienConNoN4.setText("0 đ");
            soTienConThuaN4.setText(record.getN4_total() + " đ");
        } else {
            soTienDaMuaN4.setText("0 đ");
            soTienConNoN4.setText(record.getN4_total() + " đ");
            soTienConThuaN4.setText("0 đ");
        }
        tyLeThamGiaN4.setText(record.getN4_qty() + "/" + record.getQty());
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

        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(HistoryDetailActivity.this, HistoryActivity.class);
            intent.putExtras(BundlePackage.setBundleAccount(account));
            startActivity(intent);
            this.finish();
            return true;
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
            Intent intent = new Intent(HistoryDetailActivity.this, EditHistoryActivity.class);
            intent.putExtras(BundlePackage.setBundleAccount(account));
            intent.putExtras(BundlePackage.setBundleRecord(record));
            startActivity(intent);
            this.finish();
        });

        findViewById(R.id.delete).setOnClickListener(v -> {
            historyDetailPresenter.deleteRecord(record);
        });
    }


    /****************************Interface Functions***************************/
    @Override
    public void connectFailed() {
        Toast.makeText(getApplicationContext(), "Connection failed!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteSuccess() {
        Toast.makeText(getApplicationContext(), "Delete successfully!!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HistoryDetailActivity.this, HistoryActivity.class);
        intent.putExtras(BundlePackage.setBundleAccount(account));
        startActivity(intent);
        this.finish();
    }

    @Override
    public void deleteError() {
        Toast.makeText(getApplicationContext(), "Delete unsuccessfully!!!", Toast.LENGTH_SHORT).show();
    }
}

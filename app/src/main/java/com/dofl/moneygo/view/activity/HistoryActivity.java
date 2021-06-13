package com.dofl.moneygo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.GlobalVariable;
import com.dofl.moneygo.model.Record;
import com.dofl.moneygo.presenter.HistoryInterface;
import com.dofl.moneygo.presenter.HistoryPresenter;
import com.dofl.moneygo.view.adapter.ViewPagerHistoryAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity implements HistoryInterface {
    private ViewPagerHistoryAdapter adapter;
    private Map<String, Record> presentRecordPackage;
    private Map<String, Record> previousRecordPackage;
    private int selectedMonth;
    private ImageView left;
    private ImageView right;
    private TextView month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initValue();
    }

    private void initValue() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        checkNetworkStatus();

        presentRecordPackage = new HashMap<>();
        previousRecordPackage = new HashMap<>();

        selectedMonth = 1;

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerHistoryAdapter(getSupportFragmentManager(), 4);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.top_bar_info);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.top_bar_info);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.top_bar_info);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(R.drawable.top_bar_info);

        viewPager.setCurrentItem(((GlobalVariable) this.getApplication()).getAccount().getId() - 1);

        HistoryPresenter historyPresenter = new HistoryPresenter(this);
        historyPresenter.updateRecordPackage(((GlobalVariable) this.getApplication())
                .getPresentMoneyPackage(), ((GlobalVariable) this.getApplication())
                .getPreviousMoneyPackage());

        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        month = findViewById(R.id.month);

        left.setOnClickListener(v -> {
            if (selectedMonth == 1) {
                left.setBackgroundColor(Color.parseColor("#888888"));
                right.setBackgroundColor(Color.parseColor("#E0E6E4"));
                month.setText(((GlobalVariable) getApplication())
                        .getPreviousSummaryPackage().getMonthOfYear());
                selectedMonth = 0;
                adapter.update(this.previousRecordPackage);
            }
        });

        right.setOnClickListener(v -> {
            if (selectedMonth == 0) {
                right.setBackgroundColor(Color.parseColor("#888888"));
                left.setBackgroundColor(Color.parseColor("#E0E6E4"));
                month.setText(((GlobalVariable) getApplication())
                        .getPresentSummaryPackage().getMonthOfYear());
                selectedMonth = 1;
                adapter.update(this.presentRecordPackage);
            }
        });
    }


    /****************************Network Functions***************************/
    private void checkNetworkStatus() {
        if (!getNetworkStatus()) {
            new AlertDialog.Builder(HistoryActivity.this)
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_history_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(HistoryActivity.this, AddActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /****************************Interface Functions***************************/
    @Override
    public void updatePresentRecordPackage(Map<String, Record> presentRecordPackage) {
        this.presentRecordPackage = presentRecordPackage;
        if (selectedMonth == 1) {
            adapter.update(this.presentRecordPackage);
        }
    }

    @Override
    public void updatePreviousRecordPackage(Map<String, Record> previousRecordPackage) {
        this.previousRecordPackage = previousRecordPackage;
        if (selectedMonth == 0) {
            adapter.update(this.previousRecordPackage);
        }
    }

    @Override
    public void updateError(String msg) {
        Log.e("Log", msg);
        checkNetworkStatus();
    }
}
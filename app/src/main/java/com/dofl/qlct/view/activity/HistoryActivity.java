package com.dofl.qlct.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.GlobalVariable;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.utils.BundlePackage;
import com.dofl.qlct.presenter.utils.DataProcessing;
import com.dofl.qlct.view.adapter.ListViewAdapterHistoryLayout;

import java.util.List;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    private Account account;
    private List<Record> listRecord;
    private DrawerLayout drawerLayout;
    private Button quan, doan, son, lam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initValue();
        initListView();
        setOnClickFunctionButton();
    }


    /****************************Init Value***************************/
    private void initValue() {
        account = DataProcessing.getAccount(((GlobalVariable) this.getApplication()).getAccount());


        listRecord = DataProcessing.
                getListRecord(((GlobalVariable) this.getApplication()).getListRecord());

        quan = findViewById(R.id.quan);
        doan = findViewById(R.id.doan);
        son = findViewById(R.id.son);
        lam = findViewById(R.id.lam);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initListView() {
        ListView listView = findViewById(R.id.list);
        ListViewAdapterHistoryLayout listViewAdapterHistoryLayout =
                new ListViewAdapterHistoryLayout(this,
                DataProcessing.getRecordPackage(account, listRecord));
        listView.setAdapter(listViewAdapterHistoryLayout);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(HistoryActivity.this,
                    HistoryDetailActivity.class);
            intent.putExtras(BundlePackage.setBundleRecord(DataProcessing.
                    getRecordPackage(account, listRecord).get(position)));
            intent.putExtra("AccountId", account.getId());
            startActivity(intent);

            Thread updateThread = new Thread(() -> {

                while (true) {
                    runOnUiThread(() -> {
                        Log.e("Log", "notifyDataSetChanged");
                        listRecord = DataProcessing.
                                getListRecord(((GlobalVariable) this.getApplication()).
                                        getListRecord());
                        listViewAdapterHistoryLayout.update(DataProcessing.
                                getRecordPackage(account, listRecord));

                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            updateThread.setDaemon(true);
            updateThread.start();
        });
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
        quan.setOnClickListener(v -> {
            account.setId(1);
            initListView();
        });
        doan.setOnClickListener(v -> {
            account.setId(2);
            initListView();
        });
        son.setOnClickListener(v -> {
            account.setId(3);
            initListView();
        });
        lam.setOnClickListener(v -> {
            account.setId(4);
            initListView();
        });
    }

}
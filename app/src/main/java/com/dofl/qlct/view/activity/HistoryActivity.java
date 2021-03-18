package com.dofl.qlct.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.HistoryInterface;
import com.dofl.qlct.presenter.HistoryPresenter;
import com.dofl.qlct.presenter.utils.BundlePackage;
import com.dofl.qlct.view.adapter.ListViewAdapterHistoryLayout;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity implements HistoryInterface {

    private HistoryPresenter historyPresenter;
    private Account account;
    private ArrayList<Record> recordArrayList;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initValue();
        initListView();
    }


    /****************************Init Value***************************/
    private void initValue() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        account = BundlePackage.getBundle(getIntent());
        historyPresenter = new HistoryPresenter(this);
        recordArrayList = historyPresenter.getRecordHistory(account);
    }

    private void initListView() {
        ListView listView = findViewById(R.id.list);
        ListViewAdapterHistoryLayout listViewAdapterHistoryLayout = new ListViewAdapterHistoryLayout(this, recordArrayList);
        listView.setAdapter(listViewAdapterHistoryLayout);
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


    /****************************Interface Functions***************************/
    @Override
    public void connectFailed() {
        Toast.makeText(getApplicationContext(), "Connection failed!!!", Toast.LENGTH_SHORT).show();
    }
}
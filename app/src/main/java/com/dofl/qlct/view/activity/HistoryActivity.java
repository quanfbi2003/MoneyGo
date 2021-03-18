package com.dofl.qlct.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.HistoryInterface;
import com.dofl.qlct.presenter.HistoryPresenter;
import com.dofl.qlct.view.adapter.ListViewAdapterHistoryLayout;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements HistoryInterface {

    private ListView listView;
    private HistoryPresenter historyPresenter;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        initValue();
        initListView();
    }

    /****************************Init Value***************************/
    private void initValue() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            account = new Account(bundle.getInt("id"), bundle.getString("username"), bundle.getString("role"), bundle.getString("displayName"), bundle.getInt("packageNumber"));
        }
        historyPresenter = new HistoryPresenter(this);
    }

    private void initListView() {
        listView = findViewById(R.id.list);
        ListViewAdapterHistoryLayout listViewAdapterHistoryLayout = new ListViewAdapterHistoryLayout(this, historyPresenter.getRecordHistory(account));
        listView.setAdapter(listViewAdapterHistoryLayout);
    }

    @Override
    public ArrayList<Record> getRecordHistory() {
        return null;
    }

    @Override
    public void connectFailed() {

    }
}
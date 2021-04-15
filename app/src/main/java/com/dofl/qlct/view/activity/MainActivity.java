package com.dofl.qlct.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.GlobalVariable;
import com.dofl.qlct.presenter.UpdateInterface;
import com.dofl.qlct.presenter.UpdatePresenter;
import com.dofl.qlct.presenter.utils.DataProcessing;
import com.dofl.qlct.view.adapter.GridViewAdapterMainLayoutFunction;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements UpdateInterface {

    private DrawerLayout drawerLayout;
    private Account account;
    private UpdatePresenter updatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initValue();
        initGridView();
    }


    /****************************Initial Value***************************/
    private void initValue() {
        account = DataProcessing.getAccount(((GlobalVariable) this.getApplication()).getAccount());
        updatePresenter = new UpdatePresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());

        Thread updateThread = new Thread(() -> {
            while (true) {
                updateResources();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }

    private void initGridView() {
        String[] functionName = {"Thêm mới", "Lịch sử ghi chép", "Báo cáo tổng quan",
                "Bảng giá dịch vụ", "Phân tích tài chính", "Quản trị hệ thống"};
        int[] functionImage = {R.drawable.main_menu_add, R.drawable.main_menu_search_history,
                R.drawable.main_menu_report, R.drawable.main_menu_fee_detail,
                R.drawable.main_menu_analyze, R.drawable.main_menu_manage};
        GridView gridView = findViewById(R.id.gridView);
        GridViewAdapterMainLayoutFunction gridViewAdapterMainLayoutFunction
                = new GridViewAdapterMainLayoutFunction(this, functionName, functionImage);
        gridView.setAdapter(gridViewAdapterMainLayoutFunction);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    if (DataProcessing.compareValidateDate(account.getStartDate(),
                            DataProcessing.getExactDate(new Date()))) {
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    startActivity(intent);}
                    else {
                        new AlertDialog.Builder(this)
                                .setTitle("Từ chối truy cập!!!")
                                .setMessage("Chức năng này bị vô hiệu hoá cho đến " +
                                        account.getStartDate())
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    break;

                case 1:
                    Intent intent1 = new Intent(MainActivity.this,
                            HistoryActivity.class);
                    startActivity(intent1);
                    break;

                case 2:
                    Intent intent2 = new Intent(MainActivity.this,
                            ReportActivity.class);
                    startActivity(intent2);
                    break;
                case 3:
                    Intent intent3 = new Intent(MainActivity.this,
                            FeeDetailActivity.class);
                    startActivity(intent3);
                    break;
                case 5:
                    if (account.getRole().equalsIgnoreCase("administrator")) {
                        Intent intent5 = new Intent(MainActivity.this,
                                ManageActivity.class);
                        startActivity(intent5);
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("Từ chối truy cập!!!")
                                .setMessage("Bạn không đủ quyền để sử dụng chức năng này!!!")
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    break;

            }
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


    /****************************Interface Functions***************************/
    @Override
    public void connectFailed() {
        Toast.makeText(getApplicationContext(), "Connection failed!!!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void reconnectMaintenance() {
        new Thread(() -> {
            ((GlobalVariable) this.getApplication()).
                    setMaintenance(updatePresenter.getMaintenance());
            if (((GlobalVariable) this.getApplication()).getMaintenance().isMaintenance()) {
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo!!!")
                        .setMessage("Server đang bảo trì, " +
                                "vui lòng liên hệ với quản trị viên để biết thêm chi tiết...")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.ok,
                                (dialog, which) -> System.exit(0))
                        .show();
            }
        }).start();
    }

    @Override
    public void reconnectListAccount() {

    }

    @Override
    public void reconnectListRecord() {
        new Thread(() ->
                ((GlobalVariable) this.getApplication()).setListRecord(updatePresenter.
                        getListRecord(account))).start();
    }

    @Override
    public void reconnectListPreviousRecord() {

    }

    @Override
    public void reconnectPreviousMPM() {

    }

    @Override
    public void reconnectPrePreviousMPM() {

    }


    /****************************Update Resources***************************/
    public void updateResources() {
        new Thread(() ->
                ((GlobalVariable) this.getApplication()).setListRecord(updatePresenter.
                        getListRecord(account))).start();
        new Thread(() -> {
            ((GlobalVariable) this.getApplication()).
                    setMaintenance(updatePresenter.getMaintenance());
            if (((GlobalVariable) this.getApplication()).getMaintenance().isMaintenance()) {
                new AlertDialog.Builder(this)
                        .setTitle("Thông báo!!!")
                        .setMessage("Server đang bảo trì, " +
                                "vui lòng liên hệ với quản trị viên để biết thêm chi tiết...")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.ok,
                                (dialog, which) -> System.exit(0))
                        .show();
            }
        }).start();

    }
}
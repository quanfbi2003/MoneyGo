package com.dofl.qlct.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.presenter.utils.BundlePackage;
import com.dofl.qlct.view.adapter.GridViewAdapterMainLayoutFunction;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initValue();
        initGridView();
    }


    /****************************Initial Value***************************/
    private void initValue() {
        account = BundlePackage.getBundle(getIntent());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());
    }

    private void initGridView() {
        String[] functionName = {"Thêm mới", "Lịch sử ghi chép", "Báo cáo tổng quan", "Bảng giá dịch vụ", "Phân tích tài chính", "Quản trị hệ thống"};
        int[] functionImage = {R.drawable.main_menu_add, R.drawable.main_menu_search_history, R.drawable.main_menu_report, R.drawable.main_menu_fee_detail, R.drawable.main_menu_analyze, R.drawable.main_menu_manage};
        GridView gridView = findViewById(R.id.gridView);
        GridViewAdapterMainLayoutFunction gridViewAdapterMainLayoutFunction = new GridViewAdapterMainLayoutFunction(this, functionName, functionImage);
        gridView.setAdapter(gridViewAdapterMainLayoutFunction);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    intent.putExtras(BundlePackage.setBundle(account));
                    startActivity(intent);
                    break;

                case 1:
                    Intent intent1 = new Intent(MainActivity.this, HistoryActivity.class);
                    intent1.putExtras(BundlePackage.setBundle(account));
                    startActivity(intent1);
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
}
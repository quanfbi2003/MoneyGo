package com.dofl.qlct.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.view.adapter.GridViewAdapterMainLayoutFunction;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private final String[] functionName = {"Thêm mới", "Lịch sử ghi chép", "Báo cáo tổng quan", "Bảng giá dịch vụ", "Phân tích tài chính", "Quản trị hệ thống"};
    private final int[] functionImage = {R.drawable.main_menu_add, R.drawable.main_menu_search_history, R.drawable.main_menu_report, R.drawable.main_menu_fee_detail, R.drawable.main_menu_analyze, R.drawable.main_menu_manage};
    private GridView gridView;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = findViewById(R.id.gridView);
        GridViewAdapterMainLayoutFunction gridViewAdapterMainLayoutFunction = new GridViewAdapterMainLayoutFunction(this, functionName, functionImage);
        gridView.setAdapter(gridViewAdapterMainLayoutFunction);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });
    }

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
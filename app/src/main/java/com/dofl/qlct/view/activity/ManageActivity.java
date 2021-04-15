package com.dofl.qlct.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.GlobalVariable;
import com.dofl.qlct.presenter.utils.DataProcessing;
import com.dofl.qlct.view.adapter.GridViewAdapterMainLayoutFunction;

public class ManageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        initValue();
        initListView();
    }


    /****************************Initial Value***************************/
    private void initValue() {
        Account account = DataProcessing.
                getAccount(((GlobalVariable) this.getApplication()).getAccount());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());
    }

    private void initListView() {
        String[] functionName = {"Quyết toán", "Thanh toán"};
        int[] functionImage = {R.drawable.manage_menu_settlement, R.drawable.manage_menu_payment};
        ListView listView = findViewById(R.id.listView);
        GridViewAdapterMainLayoutFunction gridViewAdapterMainLayoutFunction =
                new GridViewAdapterMainLayoutFunction(this, functionName, functionImage);
        listView.setAdapter(gridViewAdapterMainLayoutFunction);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    Intent intent = new Intent(ManageActivity.this,
                            SettlementActivity.class);
                    startActivity(intent);
                    break;

                case 1:
                    Intent intent1 = new Intent(ManageActivity.this,
                            PaymentActivity.class);
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
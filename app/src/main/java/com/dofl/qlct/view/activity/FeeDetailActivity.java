package com.dofl.qlct.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.GlobalVariable;
import com.dofl.qlct.model.MPM;
import com.dofl.qlct.presenter.utils.DataProcessing;

import java.util.Objects;

public class FeeDetailActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_detail);

        initValue();
    }


    /****************************Initial Value***************************/
    @SuppressLint("SetTextI18n")
    private void initValue() {
        Account account = DataProcessing.
                getAccount(((GlobalVariable) this.getApplication()).getAccount());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MPM presentMPM = DataProcessing.
                getMPM(((GlobalVariable) this.getApplication()).getPresentMPM());

        TextView tienDieuHoa = findViewById(R.id.tienDieuHoa);
        TextView month = findViewById(R.id.month);
        tienDieuHoa.setText(DataProcessing.formatIntToString(presentMPM.getAirConditional()));
        month.setText(presentMPM.getMonthOfYear());
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
}
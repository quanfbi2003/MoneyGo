package com.dofl.qlct.view.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {
    private EditText dateText;
    private EditText timeText;
    private Calendar myCalendar;
    private DrawerLayout drawerLayout;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        onCreateToolbar();
        onCreateValue();
        onCreateDateAndTime();


    }


    /****************************Toolbar***************************/
    private void onCreateToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /****************************Value***************************/
    private void onCreateValue() {
        EditText moneyText = findViewById(R.id.sotien);
        moneyText.setSelection(0);
    }


    /****************************Date And Time***************************/
    private void onCreateDateAndTime() {
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        TimePickerDialog.OnTimeSetListener t = (view, hourOfDay, minute) -> {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            updateLabel();
        };
        dateText = findViewById(R.id.date);
        dateText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, d,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
        dateText.setKeyListener(null);

        timeText = findViewById(R.id.time);
        timeText.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this, t,
                    myCalendar.get(Calendar.HOUR_OF_DAY),
                    myCalendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        });
        timeText.setKeyListener(null);
        updateLabel();
    }

    @SuppressLint("SetTextI18n")
    private void updateLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("u-dd/MM/yyyy", Locale.getDefault());
        String[] list = {"Hôm nay", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy", "Chủ nhật", "Hôm qua"};

        String[] date = simpleDateFormat.format(myCalendar.getTime()).split("-");
        String[] date1 = date[1].split("/");
        String[] date1now = simpleDateFormat.format(new Date().getTime()).split("-");
        String[] date1now1 = date1now[1].split("/");
        if (date[1].equalsIgnoreCase(date1now[1])) {
            dateText.setText(list[0] + " - " + date[1]);
        } else if (date1[2].equalsIgnoreCase(date1now1[2]) && date1[1].equalsIgnoreCase(date1now1[1]) && Integer.parseInt(date1now1[0]) - Integer.parseInt(date1[0]) == 1) {
            dateText.setText(list[8] + " - " + date[1]);
        } else {
            dateText.setText(list[Integer.parseInt(date[0])] + " - " + date[1]);
        }

        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeText.setText(simpleTimeFormat.format(myCalendar.getTime()));
    }


    /****************************Image Button***************************/
    private void setClicked(CheckBox checkBox, ImageView imageViewUp, ImageView imageViewDown) {
        checkBox.setChecked(true);
        imageViewUp.setEnabled(true);
        imageViewDown.setEnabled(true);
    }

    public void setN1Clicked(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN1);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN1);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN1);
        setClicked(checkBox, imageViewUp, imageViewDown);
    }

    public void setN2Clicked(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN2);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN2);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN2);
        setClicked(checkBox, imageViewUp, imageViewDown);
    }

    public void setN3Clicked(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN3);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN3);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN3);
        setClicked(checkBox, imageViewUp, imageViewDown);
    }

    public void setN4Clicked(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN4);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN4);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN4);
        setClicked(checkBox, imageViewUp, imageViewDown);
    }

}
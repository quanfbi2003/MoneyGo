package com.dofl.qlct.view.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.AddInterface;
import com.dofl.qlct.presenter.AddPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddActivity extends AppCompatActivity implements AddInterface {
    private EditText dateText;
    private EditText timeText;
    private Calendar myCalendar;
    private DrawerLayout drawerLayout;
    private Account account;
    private AddPresenter addPresenter;
    private Date date;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        onCreateToolbar();
        onCreateValue();
        onCreateDateAndTime();
        setImageBehavior();
        setImageButtonListener();
        setOnClickFunctionButton();
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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            account = new Account(bundle.getInt("id"), bundle.getString("username"), bundle.getString("role"), bundle.getString("displayName"), bundle.getInt("packageNumber"));
        }
        addPresenter = new AddPresenter(this);
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
        this.date = myCalendar.getTime();
        String[] date = simpleDateFormat.format(this.date).split("-");
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


    /****************************Image Value Button***************************/
    private void setImageStatus(boolean status, ImageView imageViewUp, ImageView imageViewDown) {
        if (status) {
            imageViewUp.setBackgroundColor(Color.WHITE);
            imageViewDown.setBackgroundColor(Color.WHITE);
        } else {
            imageViewUp.setBackgroundColor(Color.GRAY);
            imageViewDown.setBackgroundColor(Color.GRAY);
        }

    }

    @SuppressLint("SetTextI18n")
    private void setImageButtonListener() {
        findViewById(R.id.imageViewUpN1).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN1);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN1);
                textView.setText(Integer.toString(Integer.parseInt(String.valueOf(textView.getText())) + 1));
            } else {
                Toast.makeText(getApplicationContext(), "Choose participants to use this function!!!", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.imageViewDownN1).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN1);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN1);
                int temp = Integer.parseInt(String.valueOf(textView.getText()));
                if (temp == 0) {
                    Toast.makeText(getApplicationContext(), "Quantity cannot be less than 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer.parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(), "Choose participants to use this function!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.imageViewUpN2).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN2);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN2);
                textView.setText(Integer.toString(Integer.parseInt(String.valueOf(textView.getText())) + 1));
            } else {
                Toast.makeText(getApplicationContext(), "Choose participants to use this function!", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.imageViewDownN2).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN2);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN2);
                int temp = Integer.parseInt(String.valueOf(textView.getText()));
                if (temp == 0) {
                    Toast.makeText(getApplicationContext(), "Quantity cannot be less than 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer.parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(), "Choose participants to use this function!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.imageViewUpN3).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN3);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN3);
                textView.setText(Integer.toString(Integer.parseInt(String.valueOf(textView.getText())) + 1));
            } else {
                Toast.makeText(getApplicationContext(), "Choose participants to use this function!", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.imageViewDownN3).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN3);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN3);
                int temp = Integer.parseInt(String.valueOf(textView.getText()));
                if (temp == 0) {
                    Toast.makeText(getApplicationContext(), "Quantity cannot be less than 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer.parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(), "Choose participants to use this function!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.imageViewUpN4).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN4);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN4);
                textView.setText(Integer.toString(Integer.parseInt(String.valueOf(textView.getText())) + 1));
            } else {
                Toast.makeText(getApplicationContext(), "Choose participants to use this function!", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.imageViewDownN4).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN4);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN4);
                int temp = Integer.parseInt(String.valueOf(textView.getText()));
                if (temp == 0) {
                    Toast.makeText(getApplicationContext(), "Quantity cannot be less than 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer.parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(), "Choose participants to use this function!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /****************************CheckBox***************************/
    public void setCheckBoxClickN1(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN1);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN1);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN1);
        setImageStatus(checkBox.isChecked(), imageViewUp, imageViewDown);
    }

    public void setCheckBoxClickN2(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN2);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN2);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN2);
        setImageStatus(checkBox.isChecked(), imageViewUp, imageViewDown);
    }

    public void setCheckBoxClickN3(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN3);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN3);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN3);
        setImageStatus(checkBox.isChecked(), imageViewUp, imageViewDown);
    }

    public void setCheckBoxClickN4(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN4);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN4);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN4);
        setImageStatus(checkBox.isChecked(), imageViewUp, imageViewDown);
    }


    /****************************Image Info Button***************************/
    private void setImageClick(CheckBox checkBox, ImageView imageViewUp, ImageView imageViewDown) {
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
            setImageStatus(false, imageViewUp, imageViewDown);
        } else {
            checkBox.setChecked(true);
            setImageStatus(true, imageViewUp, imageViewDown);
        }

    }

    public void setImageClickN1(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN1);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN1);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN1);
        setImageClick(checkBox, imageViewUp, imageViewDown);
    }

    public void setImageClickN2(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN2);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN2);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN2);
        setImageClick(checkBox, imageViewUp, imageViewDown);
    }

    public void setImageClickN3(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN3);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN3);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN3);
        setImageClick(checkBox, imageViewUp, imageViewDown);
    }

    public void setImageClickN4(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN4);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN4);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN4);
        setImageClick(checkBox, imageViewUp, imageViewDown);
    }

    /****************************Image Behavior Button***************************/
    @SuppressLint("ClickableViewAccessibility")
    private void imageBehavior(ImageView imageView) {
        imageView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    ImageView view = (ImageView) v;
                    //overlay is black with transparency of 0x77 (119)
                    view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                    view.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL: {
                    ImageView view = (ImageView) v;
                    //clear the overlay
                    view.getDrawable().clearColorFilter();
                    view.invalidate();
                    break;
                }
            }
            return false;
        });
    }

    public void setImageBehavior() {
        imageBehavior(findViewById(R.id.imageViewInfN1));
        imageBehavior(findViewById(R.id.imageViewInfN2));
        imageBehavior(findViewById(R.id.imageViewInfN3));
        imageBehavior(findViewById(R.id.imageViewInfN4));

//        imageBehavior(findViewById(R.id.imageViewUpN1));
//        imageBehavior(findViewById(R.id.imageViewUpN2));
//        imageBehavior(findViewById(R.id.imageViewUpN3));
//        imageBehavior(findViewById(R.id.imageViewUpN4));
//
//        imageBehavior(findViewById(R.id.imageViewDownN1));
//        imageBehavior(findViewById(R.id.imageViewDownN2));
//        imageBehavior(findViewById(R.id.imageViewDownN3));
//        imageBehavior(findViewById(R.id.imageViewDownN4));
    }


    /****************************Interface Functions***************************/
    @Override
    public void addSuccess() {
        Toast.makeText(getApplicationContext(), "Add successfully!!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddActivity.this, AddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", account.getId());
        bundle.putString("username", account.getUsername());
        bundle.putString("role", account.getRole());
        bundle.putString("displayName", account.getDisplayName());
        bundle.putInt("packageNumber", account.getPackageNumber());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void addError() {
        Toast.makeText(getApplicationContext(), "Add unsuccessfully!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectFailed() {
        Toast.makeText(getApplicationContext(), "Connection failed!!!", Toast.LENGTH_SHORT).show();
    }


    /****************************OnClick Function Button***************************/
    private void setOnClickFunctionButton() {
        findViewById(R.id.cancel).setOnClickListener(v -> {
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", account.getId());
            bundle.putString("username", account.getUsername());
            bundle.putString("role", account.getRole());
            bundle.putString("displayName", account.getDisplayName());
            bundle.putInt("packageNumber", account.getPackageNumber());
            intent.putExtras(bundle);
            startActivity(intent);
        });
        findViewById(R.id.save).setOnClickListener(v -> {
            int qty = 0;
            EditText sotien = findViewById(R.id.sotien);
            EditText time = findViewById(R.id.time);
            EditText description = findViewById(R.id.description);
            CheckBox checkBoxN1 = findViewById(R.id.checkBoxN1);
            TextView textViewValueN1 = findViewById(R.id.textViewValueN1);
            CheckBox checkBoxN2 = findViewById(R.id.checkBoxN2);
            TextView textViewValueN2 = findViewById(R.id.textViewValueN2);
            CheckBox checkBoxN3 = findViewById(R.id.checkBoxN3);
            TextView textViewValueN3 = findViewById(R.id.textViewValueN3);
            CheckBox checkBoxN4 = findViewById(R.id.checkBoxN4);
            TextView textViewValueN4 = findViewById(R.id.textViewValueN4);
            Record record = new Record();
            record.setTotal(Integer.parseInt(sotien.getText().toString()));
            record.setBuyer(account.getId());

            if (checkBoxN1.isChecked()) {
                int temp = Integer.parseInt(textViewValueN1.getText().toString());
                qty += temp;
                record.setN1_qty(temp);
            } else {
                record.setN1_qty(0);
            }
            if (checkBoxN2.isChecked()) {
                int temp = Integer.parseInt(textViewValueN2.getText().toString());
                qty += temp;
                record.setN2_qty(temp);
            } else {
                record.setN2_qty(0);
            }
            if (checkBoxN3.isChecked()) {
                int temp = Integer.parseInt(textViewValueN3.getText().toString());
                qty += temp;
                record.setN3_qty(temp);
            } else {
                record.setN3_qty(0);
            }
            if (checkBoxN4.isChecked()) {
                int temp = Integer.parseInt(textViewValueN4.getText().toString());
                qty += temp;
                record.setN4_qty(temp);
            } else {
                record.setN4_qty(0);
            }
            record.setQty(qty);

            record.setTime_create(time.getText().toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("u-dd/MM/yyyy", Locale.getDefault());
            String[] list = {"Hôm nay", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy", "Chủ nhật", "Hôm qua"};
            String[] date = simpleDateFormat.format(this.date).split("-");
            record.setDate_create(list[Integer.parseInt(date[0])] + " - " + date[1]);
            record.setPackage_number(account.getPackageNumber());
            record.setDescription(description.getText().toString());

            addPresenter.addRecord(record);

            Intent intent = new Intent(AddActivity.this, AddActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", account.getId());
            bundle.putString("username", account.getUsername());
            bundle.putString("role", account.getRole());
            bundle.putString("displayName", account.getDisplayName());
            bundle.putInt("packageNumber", account.getPackageNumber());
            intent.putExtras(bundle);
            startActivity(intent);
        });


    }
}
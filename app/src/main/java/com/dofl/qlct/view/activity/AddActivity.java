package com.dofl.qlct.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.dofl.qlct.model.GlobalVariable;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.AddInterface;
import com.dofl.qlct.presenter.AddPresenter;
import com.dofl.qlct.presenter.utils.DataProcessing;
import com.dofl.qlct.presenter.utils.HideKeyboard;

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

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initValue();
        onCreateDateAndTime();
        setImageBehavior();
        setImageButtonListener();
        setOnClickFunctionButton();
        setOnPressFunction();
    }


    /****************************Initial Value***************************/
    private void initValue() {
        account = DataProcessing.getAccount(((GlobalVariable) this.getApplication()).getAccount());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EditText total = findViewById(R.id.total);
        total.setSelection(0);

        addPresenter = new AddPresenter(this);

        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.add_view),
                AddActivity.this)).start();
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


    /****************************OnCreate Date And Time***************************/
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
        dateText.setText(DataProcessing.getEditDate(myCalendar.getTime()));

        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm",
                Locale.getDefault());
        timeText.setText(simpleTimeFormat.format(myCalendar.getTime()));
    }


    /****************************Image Value Button***************************/
    private void setImageStatus(boolean status, ImageView imageViewUp, ImageView imageViewDown,
                                TextView textViewValue) {
        if (status) {
            imageViewUp.setBackgroundColor(Color.WHITE);
            imageViewDown.setBackgroundColor(Color.WHITE);
            textViewValue.setText("1");
        } else {
            imageViewUp.setBackgroundColor(Color.GRAY);
            imageViewDown.setBackgroundColor(Color.GRAY);
            textViewValue.setText("0");
        }

    }

    @SuppressLint("SetTextI18n")
    private void setImageButtonListener() {
        findViewById(R.id.imageViewUpN1).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN1);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN1);
                textView.setText(Integer.toString(Integer.parseInt(String.
                        valueOf(textView.getText())) + 1));
            } else {
                Toast.makeText(getApplicationContext(),
                        "Choose participants to use this function!!!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.imageViewDownN1).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN1);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN1);
                int temp = Integer.parseInt(String.valueOf(textView.getText()));
                if (temp == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Quantity cannot be less than 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer
                            .parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Choose participants to use this function!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.imageViewUpN2).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN2);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN2);
                textView.setText(Integer.toString(Integer.
                        parseInt(String.valueOf(textView.getText())) + 1));
            } else {
                Toast.makeText(getApplicationContext(),
                        "Choose participants to use this function!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.imageViewDownN2).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN2);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN2);
                int temp = Integer.parseInt(String.valueOf(textView.getText()));
                if (temp == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Quantity cannot be less than 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer.
                            parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Choose participants to use this function!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.imageViewUpN3).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN3);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN3);
                textView.setText(Integer.toString(Integer.
                        parseInt(String.valueOf(textView.getText())) + 1));
            } else {
                Toast.makeText(getApplicationContext(),
                        "Choose participants to use this function!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.imageViewDownN3).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN3);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN3);
                int temp = Integer.parseInt(String.valueOf(textView.getText()));
                if (temp == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Quantity cannot be less than 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer.
                            parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Choose participants to use this function!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.imageViewUpN4).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN4);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN4);
                textView.setText(Integer.toString(Integer.
                        parseInt(String.valueOf(textView.getText())) + 1));
            } else {
                Toast.makeText(getApplicationContext(),
                        "Choose participants to use this function!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.imageViewDownN4).setOnClickListener(v -> {
            CheckBox checkBox = findViewById(R.id.checkBoxN4);
            if (checkBox.isChecked()) {
                TextView textView = findViewById(R.id.textViewValueN4);
                int temp = Integer.parseInt(String.valueOf(textView.getText()));
                if (temp == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Quantity cannot be less than 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer.
                            parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Choose participants to use this function!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    /****************************CheckBox***************************/
    public void setCheckBoxClickN1(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN1);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN1);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN1);
        TextView textViewValue = findViewById(R.id.textViewValueN1);
        setImageStatus(checkBox.isChecked(), imageViewUp, imageViewDown, textViewValue);
    }

    public void setCheckBoxClickN2(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN2);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN2);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN2);
        TextView textViewValue = findViewById(R.id.textViewValueN2);
        setImageStatus(checkBox.isChecked(), imageViewUp, imageViewDown, textViewValue);
    }

    public void setCheckBoxClickN3(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN3);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN3);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN3);
        TextView textViewValue = findViewById(R.id.textViewValueN3);
        setImageStatus(checkBox.isChecked(), imageViewUp, imageViewDown, textViewValue);
    }

    public void setCheckBoxClickN4(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN4);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN4);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN4);
        TextView textViewValue = findViewById(R.id.textViewValueN4);
        setImageStatus(checkBox.isChecked(), imageViewUp, imageViewDown, textViewValue);
    }


    /****************************Image Info Button***************************/
    private void setImageClick(CheckBox checkBox, ImageView imageViewUp, ImageView imageViewDown,
                               TextView textViewValue) {
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
            setImageStatus(false, imageViewUp, imageViewDown, textViewValue);
        } else {
            checkBox.setChecked(true);
            setImageStatus(true, imageViewUp, imageViewDown, textViewValue);
        }

    }

    public void setImageClickN1(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN1);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN1);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN1);
        TextView textViewValue = findViewById(R.id.textViewValueN1);
        setImageClick(checkBox, imageViewUp, imageViewDown, textViewValue);
    }

    public void setImageClickN2(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN2);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN2);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN2);
        TextView textViewValue = findViewById(R.id.textViewValueN2);
        setImageClick(checkBox, imageViewUp, imageViewDown, textViewValue);
    }

    public void setImageClickN3(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN3);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN3);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN3);
        TextView textViewValue = findViewById(R.id.textViewValueN3);
        setImageClick(checkBox, imageViewUp, imageViewDown, textViewValue);
    }

    public void setImageClickN4(View view) {
        CheckBox checkBox = findViewById(R.id.checkBoxN4);
        ImageView imageViewUp = findViewById(R.id.imageViewUpN4);
        ImageView imageViewDown = findViewById(R.id.imageViewDownN4);
        TextView textViewValue = findViewById(R.id.textViewValueN4);
        setImageClick(checkBox, imageViewUp, imageViewDown, textViewValue);
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
    }


    /****************************Interface Functions***************************/
    @Override
    public void addSuccess(Record record) {
        record.setIcon(R.mipmap.ic_launcher);
        ((GlobalVariable) this.getApplication()).addRecord(record);
        reset();
        Toast.makeText(getApplicationContext(), "Add successfully!!!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectFailed() {
        Toast.makeText(getApplicationContext(), "Connection failed!!!",
                Toast.LENGTH_SHORT).show();
    }


    /****************************OnClick Function Button***************************/
    @SuppressLint({"SetTextI18n", "ShowToast"})
    private void setOnClickFunctionButton() {
        findViewById(R.id.cancel).setOnClickListener(v -> this.finish());

        findViewById(R.id.save).setOnClickListener(v -> {
            EditText total = findViewById(R.id.total);
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
            record.setTotal(DataProcessing.formatStringToInt(total.getText().toString()));
            record.setBuyer(account.getId());

            if (checkBoxN1.isChecked()) {
                int temp = Integer.parseInt(textViewValueN1.getText().toString());
                record.setN1Qty(temp);
            } else {
                record.setN1Qty(0);
            }
            if (checkBoxN2.isChecked()) {
                int temp = Integer.parseInt(textViewValueN2.getText().toString());
                record.setN2Qty(temp);
            } else {
                record.setN2Qty(0);
            }
            if (checkBoxN3.isChecked()) {
                int temp = Integer.parseInt(textViewValueN3.getText().toString());
                record.setN3Qty(temp);
            } else {
                record.setN3Qty(0);
            }
            if (checkBoxN4.isChecked()) {
                int temp = Integer.parseInt(textViewValueN4.getText().toString());
                record.setN4Qty(temp);
            } else {
                record.setN4Qty(0);
            }

            record.setTimeCreate(time.getText().toString());
            record.setDateCreate(DataProcessing.getExactDate(myCalendar.getTime()));
            record.setPackageNumber(account.getPackageNumber());
            record.setDescription(DataProcessing.
                    formatDescription(description.getText().toString().trim()));

            if (DataProcessing.compareValidateDate(account.getStartDate(),
                    record.getDateCreate())) {
                addPresenter.addRecord(DataProcessing.setIdOfRecord(record));
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Ngày nhập đã quá hạn!!!")
                        .setMessage("Ngày bạn nhập đã quá hạn. Vui lòng chọn từ ngày " +
                                account.getStartDate() + " đến Hôm nay để tiếp tục!!!")
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

        });
    }

    @SuppressLint("SetTextI18n")
    private void reset() {
        EditText total = findViewById(R.id.total);
        EditText time = findViewById(R.id.time);
        EditText dateText = findViewById(R.id.date);
        EditText description = findViewById(R.id.description);
        CheckBox checkBoxN1 = findViewById(R.id.checkBoxN1);
        CheckBox checkBoxN2 = findViewById(R.id.checkBoxN2);
        CheckBox checkBoxN3 = findViewById(R.id.checkBoxN3);
        CheckBox checkBoxN4 = findViewById(R.id.checkBoxN4);

        total.setText("000");
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm",
                Locale.getDefault());
        time.setText(simpleTimeFormat.format(new Date().getTime()));
        dateText.setText(DataProcessing.getEditDate(new Date()));
        description.setText("");
        checkBoxN1.setChecked(true);
        checkBoxN2.setChecked(true);
        checkBoxN3.setChecked(true);
        checkBoxN4.setChecked(true);
        setImageClickN1(findViewById(R.id.add_view));
        setImageClickN2(findViewById(R.id.add_view));
        setImageClickN3(findViewById(R.id.add_view));
        setImageClickN4(findViewById(R.id.add_view));
    }


    /****************************OnPress Function***************************/
    private void setOnPressFunction() {
        EditText total = findViewById(R.id.total);
        total.setOnFocusChangeListener((v, hasFocus) -> {
            if (total.getText().length() > 13) {
                Toast.makeText(AddActivity.this,
                        "Value cannot be above 10,000,000,000!!!", Toast.LENGTH_SHORT).show();
            } else if (!total.getText().toString().equalsIgnoreCase("")) {
                total.setText(DataProcessing.formatIntToString(DataProcessing.
                        formatStringToInt(total.getText().toString())));
            }
        });
    }
}
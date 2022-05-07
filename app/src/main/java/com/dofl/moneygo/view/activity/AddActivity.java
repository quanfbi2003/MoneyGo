package com.dofl.moneygo.view.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.GlobalVariable;
import com.dofl.moneygo.model.Record;
import com.dofl.moneygo.presenter.AddInterface;
import com.dofl.moneygo.presenter.AddPresenter;
import com.dofl.moneygo.presenter.utils.DataProcessing;
import com.dofl.moneygo.presenter.utils.HideKeyboard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddActivity extends AppCompatActivity implements AddInterface {
    private EditText dateText;
    private EditText timeText;
    private Calendar myCalendar;
    private AddPresenter addPresenter;
    private TextView nameN1, nameN2, nameN3, nameN4;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        checkNetworkStatus();
        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.add_view),
                AddActivity.this)).start();

        EditText total = findViewById(R.id.total);
        total.setSelection(0);

        nameN1 = findViewById(R.id.textViewNameN1);
        nameN2 = findViewById(R.id.textViewNameN2);
        nameN3 = findViewById(R.id.textViewNameN3);
        nameN4 = findViewById(R.id.textViewNameN4);

        nameN1.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN1());
        nameN2.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN2());
        nameN3.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN3());
        nameN4.setText(((GlobalVariable) this.getApplication()).getRegisteredAccount().getNameN4());


        addPresenter = new AddPresenter(this);
    }


    /****************************Network Functions***************************/
    private boolean checkNetworkStatus() {
        if (!getNetworkStatus()) {
            new AlertDialog.Builder(AddActivity.this)
                    .setTitle("Thông báo!!!")
                    .setMessage("Không có kết nối mạng, chọn OK để đóng ứng dụng!!!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> System.exit(0))
                    .show();
        }
        return getNetworkStatus();
    }

    private boolean getNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }


    /****************************Override Toolbar Functions***************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.history) {
            startActivity(new Intent(AddActivity.this, HistoryActivity.class));
            finish();
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
            datePickerDialog.getDatePicker().setMinDate(Objects.requireNonNull(DataProcessing
                    .getDate(((GlobalVariable) this.getApplication())
                            .getPresentSummaryPackage().getStartDate())).getTime() - 1000);
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
                        "Hãy chọn người tham gia để sử dụng chức năng này!!!",
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
                            "Số lượng không thể nhỏ hơn 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer
                            .parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hãy chọn người tham gia để sử dụng chức năng này!!!",
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
                        "Hãy chọn người tham gia để sử dụng chức năng này!!!",
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
                            "Số lượng không thể nhỏ hơn 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer.
                            parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hãy chọn người tham gia để sử dụng chức năng này!!!",
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
                        "Hãy chọn người tham gia để sử dụng chức năng này!!!",
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
                            "Số lượng không thể nhỏ hơn 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer.
                            parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hãy chọn người tham gia để sử dụng chức năng này!!!",
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
                        "Hãy chọn người tham gia để sử dụng chức năng này!!!",
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
                            "Số lượng không thể nhỏ hơn 0!!!", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(Integer.toString(Integer.
                            parseInt(String.valueOf(textView.getText())) - 1));
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hãy chọn người tham gia để sử dụng chức năng này!!!",
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
    public void addSuccess() {
        reset();
        Toast.makeText(getApplicationContext(), "Thêm bản ghi thành công!!!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
            record.setBuyer(((GlobalVariable) this.getApplication()).getAccount().getId());

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
            record.setDateCreate(DataProcessing.getFormattedDate(myCalendar.getTime()));
            record.setDescription(DataProcessing.
                    formatDescription(description.getText().toString().trim()));

            if (checkNetworkStatus()) {
                addPresenter.addRecord(((GlobalVariable) this.getApplication())
                                .getAccount(),
                        ((GlobalVariable) this.getApplication())
                                .getPresentMoneyPackage(),
                        DataProcessing.setTotalOfRecord(record));
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
    @SuppressLint("SetTextI18n")
    private void setOnPressFunction() {
        EditText total = findViewById(R.id.total);
        total.setOnFocusChangeListener((v, hasFocus) -> {
            if (total.getText().length() > 13) {
                Toast.makeText(AddActivity.this,
                        "Value cannot be above 10,000,000,000!!!", Toast.LENGTH_SHORT).show();
                total.setText("000");
                total.setSelection(0);
            } else if (!(total.getText().toString().equalsIgnoreCase(""))) {
                total.setText(DataProcessing.formatIntToString(DataProcessing.
                        formatStringToInt(total.getText().toString())));
                if (total.getText().toString().equalsIgnoreCase("0")) {
                    total.setText("000");
                    total.setSelection(0);
                }
            }
        });
    }
}
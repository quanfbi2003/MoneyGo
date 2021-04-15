package com.dofl.qlct.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.dofl.qlct.model.GlobalVariable;
import com.dofl.qlct.model.Record;
import com.dofl.qlct.presenter.EditHistoryInterface;
import com.dofl.qlct.presenter.EditHistoryPresenter;
import com.dofl.qlct.presenter.utils.BundlePackage;
import com.dofl.qlct.presenter.utils.DataProcessing;
import com.dofl.qlct.presenter.utils.HideKeyboard;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class EditHistoryActivity extends AppCompatActivity implements EditHistoryInterface {
    private DrawerLayout drawerLayout;
    private Account account;
    private Record record;
    private EditHistoryPresenter editHistoryPresenter;
    private EditText dateText;
    private EditText timeText;
    private Calendar myCalendar;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_history);

        initValue();
        importValue();
        setImageBehavior();
        setImageButtonListener();
        setOnClickFunctionButton();
        onCreateDateAndTime();
        setOnPressFunction();
    }


    /****************************Initial Value***************************/
    private void initValue() {
        account = DataProcessing.getAccount(((GlobalVariable) this.getApplication()).getAccount());
        record = BundlePackage.getBundleRecord(getIntent());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        TextView textView = findViewById(R.id.displayName);
        textView.setText(account.getDisplayName());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EditText total = findViewById(R.id.total);
        total.setSelection(0);

        editHistoryPresenter = new EditHistoryPresenter(this);

        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.edit_history_view),
                EditHistoryActivity.this)).start();
    }

    @SuppressLint("SetTextI18n")
    private void importValue() {
        EditText total = findViewById(R.id.total);
        EditText time = findViewById(R.id.time);
        EditText dateText = findViewById(R.id.date);
        EditText description = findViewById(R.id.description);
        CheckBox checkBoxN1 = findViewById(R.id.checkBoxN1);
        CheckBox checkBoxN2 = findViewById(R.id.checkBoxN2);
        CheckBox checkBoxN3 = findViewById(R.id.checkBoxN3);
        CheckBox checkBoxN4 = findViewById(R.id.checkBoxN4);
        TextView textViewValueN1 = findViewById(R.id.textViewValueN1);
        TextView textViewValueN2 = findViewById(R.id.textViewValueN2);
        TextView textViewValueN3 = findViewById(R.id.textViewValueN3);
        TextView textViewValueN4 = findViewById(R.id.textViewValueN4);

        total.setText(DataProcessing.formatIntToString(record.getTotal()));
        time.setText(record.getTimeCreate());
        dateText.setText(record.getDateCreate());
        description.setText(record.getDescription());

        if (record.getN1Qty() != 0) {
            checkBoxN1.setChecked(false);
            setImageClickN1(findViewById(R.id.edit_history_view));
            textViewValueN1.setText(Integer.toString(record.getN1Qty()));
        } else {
            checkBoxN1.setChecked(true);
            setImageClickN1(findViewById(R.id.edit_history_view));
        }
        if (record.getN2Qty() != 0) {
            checkBoxN2.setChecked(false);
            setImageClickN2(findViewById(R.id.edit_history_view));
            textViewValueN2.setText(Integer.toString(record.getN2Qty()));
        } else {
            checkBoxN2.setChecked(true);
            setImageClickN2(findViewById(R.id.edit_history_view));
        }
        if (record.getN3Qty() != 0) {
            checkBoxN3.setChecked(false);
            setImageClickN3(findViewById(R.id.edit_history_view));
            textViewValueN3.setText(Integer.toString(record.getN3Qty()));
        } else {
            checkBoxN3.setChecked(true);
            setImageClickN3(findViewById(R.id.edit_history_view));
        }
        if (record.getN4Qty() != 0) {
            checkBoxN4.setChecked(false);
            setImageClickN4(findViewById(R.id.edit_history_view));
            textViewValueN4.setText(Integer.toString(record.getN4Qty()));
        } else {
            checkBoxN4.setChecked(true);
            setImageClickN4(findViewById(R.id.edit_history_view));
        }
    }


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
            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(EditHistoryActivity.this, d,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });
        dateText.setKeyListener(null);

        timeText = findViewById(R.id.time);
        timeText.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog =
                    new TimePickerDialog(EditHistoryActivity.this, t,
                    myCalendar.get(Calendar.HOUR_OF_DAY),
                    myCalendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        });
        timeText.setKeyListener(null);
    }

    @SuppressLint("SetTextI18n")
    private void updateLabel() {
        dateText.setText(DataProcessing.getEditDate(myCalendar.getTime()));

        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm",
                Locale.getDefault());
        timeText.setText(simpleTimeFormat.format(myCalendar.getTime()));
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
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(EditHistoryActivity.this,
                    HistoryActivity.class);
            startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                textView.setText(Integer.toString(Integer.
                        parseInt(String.valueOf(textView.getText())) + 1));
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
                    textView.setText(Integer.toString(Integer.
                            parseInt(String.valueOf(textView.getText())) - 1));
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
    public void editSuccess(Record record) {
        ((GlobalVariable) this.getApplication()).editRecord(record);
        Toast.makeText(getApplicationContext(), "Edit successfully!!!",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditHistoryActivity.this, HistoryActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void editError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectFailed() {
        Toast.makeText(getApplicationContext(), "Connection failed!!!",
                Toast.LENGTH_SHORT).show();
    }


    /****************************OnClick Function Button***************************/
    @SuppressLint("SetTextI18n")
    private void setOnClickFunctionButton() {
        findViewById(R.id.cancel).setOnClickListener(v -> this.finish());

        findViewById(R.id.save).setOnClickListener(v -> {
            EditText total = findViewById(R.id.total);
            EditText description = findViewById(R.id.description);
            CheckBox checkBoxN1 = findViewById(R.id.checkBoxN1);
            TextView textViewValueN1 = findViewById(R.id.textViewValueN1);
            CheckBox checkBoxN2 = findViewById(R.id.checkBoxN2);
            TextView textViewValueN2 = findViewById(R.id.textViewValueN2);
            CheckBox checkBoxN3 = findViewById(R.id.checkBoxN3);
            TextView textViewValueN3 = findViewById(R.id.textViewValueN3);
            CheckBox checkBoxN4 = findViewById(R.id.checkBoxN4);
            TextView textViewValueN4 = findViewById(R.id.textViewValueN4);
            record.setTotal(DataProcessing.formatStringToInt(total.getText().toString()));

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
            record.setDescription(description.getText().toString());

            if (DataProcessing.compareValidateDate(account.getStartDate(),
                    record.getDateCreate())) {
                editHistoryPresenter.editRecord(record);

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


    /****************************OnPress Function***************************/
    private void setOnPressFunction() {
        EditText total = findViewById(R.id.total);
        total.setOnFocusChangeListener((v, hasFocus) -> {
            if (total.getText().length() > 13) {
                Toast.makeText(EditHistoryActivity.this,
                        "Value cannot be above 10,000,000,000!!!", Toast.LENGTH_SHORT).show();
            } else if (!total.getText().toString().equalsIgnoreCase("")) {
                total.setText(DataProcessing.formatIntToString(DataProcessing.
                        formatStringToInt(total.getText().toString())));
            }
        });
    }
}
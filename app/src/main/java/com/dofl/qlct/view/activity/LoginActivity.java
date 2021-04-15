package com.dofl.qlct.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.model.GlobalVariable;
import com.dofl.qlct.model.MPM;
import com.dofl.qlct.presenter.LoginInterface;
import com.dofl.qlct.presenter.LoginPresenter;
import com.dofl.qlct.presenter.UpdateInterface;
import com.dofl.qlct.presenter.UpdatePresenter;
import com.dofl.qlct.presenter.utils.DataProcessing;
import com.dofl.qlct.presenter.utils.HideKeyboard;

public class LoginActivity extends AppCompatActivity implements LoginInterface, UpdateInterface {
    private LoginPresenter loginPresenter;
    private UpdatePresenter updatePresenter;
    private SharedPreferences loginPreferences;
    private Account account;
    private EditText username, password;
    private AlertDialog logining, updating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initValue();
        setOnClickLoginListener();
    }


    /****************************Initial Value***************************/
    @SuppressLint("CommitPrefEdits")
    private void initValue() {
        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.login_view),
                LoginActivity.this)).start();

        loginPresenter = new LoginPresenter(this);
        updatePresenter = new UpdatePresenter(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        String usernameSave = loginPreferences.getString("username", "");
        String passwordSave = loginPreferences.getString("password", "");
        Log.e("Log loginPreferences", usernameSave + " " + passwordSave);
        if (!usernameSave.trim().isEmpty() & !passwordSave.trim().isEmpty()) {
            account = new Account(usernameSave, passwordSave);
            username.setText(usernameSave);
            password.setText("********");
            Log.e("Log", "Đang đăng nhập...");
            logining = new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Thông báo!!!")
                    .setMessage("Đang đăng nhập...")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            new Thread(() -> runOnUiThread(() -> {
                ((GlobalVariable) this.getApplication()).
                        setMaintenance(updatePresenter.getMaintenance());
                if (((GlobalVariable) this.getApplication()).getMaintenance().isMaintenance()) {
                    logining.dismiss();
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Thông báo!!!")
                            .setMessage("Server đang bảo trì, " +
                                    "vui lòng liên hệ với quản trị viên để biết thêm chi tiết...")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.ok,
                                    (dialog, which) -> System.exit(0))
                            .show();
                } else {
                    loginPresenter.login(account);
                }
            })).start();
        }
    }


    /****************************Interface Functions***************************/
    @Override
    public void loginSuccess(Account account) {
        Toast.makeText(getApplicationContext(), "Login successfully!!!",
                Toast.LENGTH_SHORT).show();
        logining.dismiss();
        Log.e("Log", "Đang tải dữ liệu...");
        updating = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Thông báo!!!")
                .setMessage("Đang tải dữ liệu...")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        ((GlobalVariable) this.getApplication()).setAccount(account);

        String usernameSave = loginPreferences.getString("username", "");
        String passwordSave = loginPreferences.getString("password", "");
        Log.e("Log loginPreferences", usernameSave + " " + passwordSave);
        if (usernameSave.trim().isEmpty() || passwordSave.trim().isEmpty()) {
            SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

            loginPrefsEditor.putString("username", username.getText().toString());
            loginPrefsEditor.putString("password", password.getText().toString());
            loginPrefsEditor.apply();
        }

        new Thread(() -> runOnUiThread(this::updateResources)).start();
    }

    @Override
    public void loginError() {
        Toast.makeText(getApplicationContext(), "Incorrect username or password!!!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectFailed() {
        Toast.makeText(getApplicationContext(), "Connection failed!!!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void reconnectMaintenance() {
        Thread maintenanceThread = new Thread(() -> {
            ((GlobalVariable) this.getApplication()).
                    setMaintenance(updatePresenter.getMaintenance());
            if (((GlobalVariable) this.getApplication()).getMaintenance().isMaintenance()) {
                logining.dismiss();
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Thông báo!!!")
                        .setMessage("Server đang bảo trì, " +
                                "vui lòng liên hệ với quản trị viên để biết thêm chi tiết...")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.ok,
                                (dialog, which) -> System.exit(0))
                        .show();
            }
        });
        maintenanceThread.start();
        try {
            maintenanceThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reconnectListAccount() {
        Thread listAccountThread = null;
        if (account.getRole().equalsIgnoreCase("administrator")) {
            listAccountThread = new Thread(() -> ((GlobalVariable) this.getApplication()).
                    setListAccount(updatePresenter.getListAccount()));
        }
        try {
            if (listAccountThread != null) {
                listAccountThread.start();
                listAccountThread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reconnectListRecord() {
        Thread listRecordThread = new Thread(() ->
                ((GlobalVariable) this.getApplication()).setListRecord(updatePresenter.
                        getListRecord(account)));
        listRecordThread.start();
        try {
            listRecordThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reconnectListPreviousRecord() {
        Thread listPreviousRecordThread = new Thread(() ->
                ((GlobalVariable) this.getApplication()).setListPreviousRecord(updatePresenter.
                        getListPreviousRecord(account)));
        listPreviousRecordThread.start();
        try {
            listPreviousRecordThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reconnectPreviousMPM() {
        Thread previousMPMThread = new Thread(() -> {
            MPM previousMPM = updatePresenter.getPreviousMPM(account);
            ((GlobalVariable) this.getApplication()).setPreviousMPM(previousMPM);
            ((GlobalVariable) this.getApplication()).setPresentMPM(DataProcessing.
                    getPresentMPM(account, previousMPM, 0, 0));
        });

        previousMPMThread.start();
        try {
            previousMPMThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reconnectPrePreviousMPM() {
        Thread prePreviousMPMThread = new Thread(() ->
                ((GlobalVariable) this.getApplication()).setPrePreviousMPM(updatePresenter.
                        getPrePreviousMPM(account)));
        prePreviousMPMThread.start();
        try {
            prePreviousMPMThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /****************************Update Resources***************************/
    public void updateResources() {
        Thread listRecordThread = new Thread(() ->
                ((GlobalVariable) this.getApplication()).setListRecord(updatePresenter.
                        getListRecord(account)));
        Thread previousMPMThread = new Thread(() -> {
            MPM previousMPM = updatePresenter.getPreviousMPM(account);
            ((GlobalVariable) this.getApplication()).setPreviousMPM(previousMPM);
            ((GlobalVariable) this.getApplication()).setPresentMPM(DataProcessing.
                    getPresentMPM(account, previousMPM, 0, 0));
        });
        Thread prePreviousMPMThread = new Thread(() ->
                ((GlobalVariable) this.getApplication()).setPrePreviousMPM(updatePresenter.
                        getPrePreviousMPM(account)));
        Thread listPreviousRecordThread = new Thread(() ->
                ((GlobalVariable) this.getApplication()).setListPreviousRecord(updatePresenter.
                        getListPreviousRecord(account)));
        Thread listAccountThread = null;
        if (account.getRole().equalsIgnoreCase("administrator")) {
            listAccountThread = new Thread(() -> ((GlobalVariable) this.getApplication()).
                    setListAccount(updatePresenter.getListAccount()));
        }
        listRecordThread.start();
        previousMPMThread.start();
        prePreviousMPMThread.start();
        listPreviousRecordThread.start();
        if (listAccountThread != null) {
            listAccountThread.start();
        }
        try {
            listRecordThread.join();
            previousMPMThread.join();
            prePreviousMPMThread.join();
            listPreviousRecordThread.join();
            if (listAccountThread != null) {
                listAccountThread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        updating.dismiss();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    /****************************OnClick Button Listener***************************/
    private void setOnClickLoginListener() {
        findViewById(R.id.login).setOnClickListener(v -> {
            TextView textViewUsername = findViewById(R.id.username);
            TextView textViewPassword = findViewById(R.id.password);
            String username = textViewUsername.getText().toString();
            String password = textViewPassword.getText().toString();
            Account account = new Account(username, password);
            logining = new AlertDialog.Builder(this)
                    .setTitle("Thông báo!!!")
                    .setMessage("Đang đăng nhập...")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            loginPresenter.login(account);
            textViewUsername.setText("");
            textViewPassword.setText("");
        });
    }


}
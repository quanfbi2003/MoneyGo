package com.dofl.moneygo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dofl.moneygo.R;
import com.dofl.moneygo.model.Account;
import com.dofl.moneygo.model.GlobalVariable;
import com.dofl.moneygo.presenter.LoginInterface;
import com.dofl.moneygo.presenter.LoginPresenter;
import com.dofl.moneygo.presenter.utils.HideKeyboard;

public class LoginActivity extends AppCompatActivity implements LoginInterface {
    private EditText txtEmail, txtPassword;
    private LoginPresenter loginPresenter;
    private SharedPreferences loginPreferences;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initValue();
        initFunction();
        setOnClickLoginListener();

    }

    /****************************Initial Values***************************/
    private void initValue() {
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);

        loginPresenter = new LoginPresenter(this, this);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);


    }


    /****************************Initial Functions***************************/
    private void initFunction() {
        checkNetworkStatus();

        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.login_layout),
                LoginActivity.this)).start();

        String emailSave = loginPreferences.getString("email", "");
        String passwordSave = loginPreferences.getString("password", "");
//        Log.e("Log loginPreferences", emailSave + " " + passwordSave);

        if (!emailSave.trim().isEmpty() & !passwordSave.trim().isEmpty()) {
            account = new Account(emailSave, passwordSave);
            txtEmail.setText(emailSave);
            txtPassword.setText(passwordSave);
//            Log.e("Log", "Đang đăng nhập...");

            loginPresenter.login(account);
        }
    }

    private void checkNetworkStatus() {
        if (!getNetworkStatus()) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Thông báo!!!")
                    .setMessage("Không có kết nối mạng, chọn OK để đóng ứng dụng!!!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> System.exit(0))
                    .show();
        }
    }

    private boolean getNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }


    /****************************OnClick Button Listener***************************/
    private void setOnClickLoginListener() {
        findViewById(R.id.login).setOnClickListener(v -> {
            account = new Account(txtEmail.getText().toString(),
                    txtPassword.getText().toString());
            loginPresenter.login(account);
        });
    }


    /****************************Interface Functions***************************/
    @Override
    public void loginSuccess() {
        ((GlobalVariable) this.getApplication()).initGlobalVariable();
        Toast.makeText(getApplicationContext(), "Đăng nhập thành công!!!",
                Toast.LENGTH_SHORT).show();

        String emailSave = loginPreferences.getString("email", "");
        String passwordSave = loginPreferences.getString("password", "");
//        Log.e("Log loginPreferences", emailSave + " " + passwordSave);
        if (emailSave.trim().isEmpty() || passwordSave.trim().isEmpty()) {
            SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

            loginPrefsEditor.putString("email", txtEmail.getText().toString());
            loginPrefsEditor.putString("password", txtPassword.getText().toString());
            loginPrefsEditor.apply();
        }

        if (account.getEmail().equalsIgnoreCase("dreamofqjlife@gmail.com")) {
            loginPresenter.openSystem();
        }

        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        account.setPassword(null);
        ((GlobalVariable) this.getApplication()).setAccount(account);
        startActivity(intent);
        finish();

    }

    @Override
    public void loginError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        txtPassword.setText("");
    }
}
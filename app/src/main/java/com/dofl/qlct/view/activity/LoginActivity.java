package com.dofl.qlct.view.activity;

import android.annotation.SuppressLint;
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
import com.dofl.qlct.presenter.LoginInterface;
import com.dofl.qlct.presenter.LoginPresenter;
import com.dofl.qlct.presenter.utils.BundlePackage;
import com.dofl.qlct.presenter.utils.HideKeyboard;

public class LoginActivity extends AppCompatActivity implements LoginInterface {
    private LoginPresenter loginPresenter;
    private SharedPreferences loginPreferences;

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
        new Thread(() -> HideKeyboard.setupUI(findViewById(R.id.login_view), LoginActivity.this)).start();
        loginPresenter = new LoginPresenter(this);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        String usernameSave = loginPreferences.getString("username", "");
        String passwordSave = loginPreferences.getString("password", "");
        Log.e("Log loginPreferences", usernameSave + " " + passwordSave);
        if (!usernameSave.trim().isEmpty() & !passwordSave.trim().isEmpty()) {
            Account account = new Account(usernameSave, passwordSave);
            loginPresenter.login(account);
        }
    }


    /****************************Interface Functions***************************/
    @Override
    public void loginSuccess(Account account) {
        Toast.makeText(getApplicationContext(), "Login successfully!!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtras(BundlePackage.setBundleAccount(account));

        String usernameSave = loginPreferences.getString("username", "");
        String passwordSave = loginPreferences.getString("password", "");
        Log.e("Log loginPreferences", usernameSave + " " + passwordSave);
        if (usernameSave.trim().isEmpty() || passwordSave.trim().isEmpty()) {
            SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
            EditText username = findViewById(R.id.username);
            EditText password = findViewById(R.id.password);
            loginPrefsEditor.putString("username", username.getText().toString());
            loginPrefsEditor.putString("password", password.getText().toString());
            loginPrefsEditor.apply();
        }
        startActivity(intent);
        this.finish();
    }

    @Override
    public void loginError() {
        Toast.makeText(getApplicationContext(), "Incorrect username or password!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectFailed() {
        Toast.makeText(getApplicationContext(), "Connection failed!!!", Toast.LENGTH_SHORT).show();
    }


    /****************************OnClick Button Listener***************************/
    private void setOnClickLoginListener() {
        findViewById(R.id.login).setOnClickListener(v -> {
            TextView textViewUsername = findViewById(R.id.username);
            TextView textViewPassword = findViewById(R.id.password);
            String username = textViewUsername.getText().toString();
            String password = textViewPassword.getText().toString();
            Account account = new Account(username, password);
            loginPresenter.login(account);
            textViewUsername.setText("");
            textViewPassword.setText("");
        });
    }
}
package com.dofl.qlct.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dofl.qlct.R;
import com.dofl.qlct.model.Account;
import com.dofl.qlct.presenter.LoginInterface;
import com.dofl.qlct.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginInterface {
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initValue();
        setOnClickLoginListener();
    }


    /****************************Initial Value***************************/
    private void initValue() {
        loginPresenter = new LoginPresenter(this);
    }


    /****************************Interface Functions***************************/
    @Override
    public void loginSuccess(Account account) {
        Toast.makeText(getApplicationContext(), "Login successfully!!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
        });

    }
}
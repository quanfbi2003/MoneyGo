package com.dofl.moneygo.presenter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.dofl.moneygo.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    private final LoginInterface loginInterface;
    private FirebaseAuth firebaseAuth;
    private Context context;

    public LoginPresenter(Context context, LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
        firebaseAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public void login(Account account) {
        if (account.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@" +
                "([\\w]+\\.)+[\\w]+[\\w]$")) {
            firebaseAuth.signInWithEmailAndPassword(account.getEmail(), account.getPassword())
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            Log.e("SignIn", "signInWithEmailAndPassword: "
//                                    + task.isSuccessful());
                            if (task.isSuccessful()) {
                                loginInterface.loginSuccess();
                            } else {
                                loginInterface.loginError("Email hoặc mật khẩu chưa đúng!!!");
                            }
                        }
                    });
        } else {
            loginInterface.loginError("Email không hợp lệ!!!");
        }
    }
}

package com.brascelok.masterialdesignloginsignup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.btn_login) Button btnLogin;
    @InjectView(R.id.link_sign_up) TextView tvLinkSignUp;
    @InjectView(R.id.input_email) EditText etEmail;
    @InjectView(R.id.input_password) EditText etPassword;
    @InjectView(R.id.layout_login_father) ScrollView layoutFather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        tvLinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void login(){
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        if (!validate(email, pass)){
            onLoginFailed();
            return;
        }
        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoginSuccessed();
                progressDialog.dismiss();
            }
        }, 3000);
    }

    private void signUp(){
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    boolean validate(String email, String pass){
        boolean valid = true;
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Enter valid email");
            valid = false;
        }else{
            etEmail.setError(null);
        }
        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10){
            etPassword.setError("Password is between 4 and 10");
            valid = false;
        }else{
            etPassword.setError(null);
        }
        return valid;
    }

    void onLoginFailed(){
        Snackbar.make(layoutFather, "Login failed", Snackbar.LENGTH_SHORT).show();
        btnLogin.setEnabled(true);
    }

    void onLoginSuccessed(){
        btnLogin.setEnabled(true);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP){
            if (resultCode == RESULT_OK){
                this.finish();
            }
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

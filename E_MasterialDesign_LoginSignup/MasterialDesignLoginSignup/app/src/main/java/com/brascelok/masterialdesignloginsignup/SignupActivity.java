package com.brascelok.masterialdesignloginsignup;

import android.app.ProgressDialog;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {

    @InjectView(R.id.et_reg_name)
    EditText etName;
    @InjectView(R.id.et_reg_email)
    EditText etEmail;
    @InjectView(R.id.et_reg_password)
    EditText etPassword;
    @InjectView(R.id.btn_reg)
    Button btnReg;
    @InjectView(R.id.tv_login)
    TextView tvLogin;
    @InjectView(R.id.layout_reg_father)
    ScrollView layoutFather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void signup(){
        String nameReg = etName.getText().toString();
        String emailReg = etEmail.getText().toString();
        String passReg = etPassword.getText().toString();
        if (!validate(nameReg, emailReg, passReg)){
            onRegFailed();
            return;
        }
        btnReg.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating account...");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onRegSuccess();
                progressDialog.dismiss();
            }
        }, 3000);
    }

    boolean validate(String name, String email, String pass){
        boolean isValid = true;
        if (name.isEmpty() || name.length() < 3){
            etName.setError("at least 3 characters");
            isValid = false;
        }else{
            etName.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Enter valid email");
            isValid = false;
        }else{
            etName.setError(null);
        }
        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10){
            etPassword.setError("Password is between 4 and 10");
            isValid = false;
        }else{
            etPassword.setError(null);
        }
        return isValid;
    }

    void onRegSuccess(){
        btnReg.setEnabled(true);
        setResult(RESULT_OK);
        finish();
    }
    void onRegFailed(){
        Snackbar.make(layoutFather, "Register failed", Snackbar.LENGTH_SHORT).show();
        btnReg.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}

package com.brascelok.butterknife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_pass)
    EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init butterknife
        // must be have
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "sddsffs", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

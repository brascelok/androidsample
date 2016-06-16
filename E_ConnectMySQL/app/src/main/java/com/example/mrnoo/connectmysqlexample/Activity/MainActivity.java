package com.example.mrnoo.connectmysqlexample.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mrnoo.connectmysqlexample.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addnew = (Button) findViewById(R.id.btn_addnew);
        Button viewall = (Button) findViewById(R.id.btn_viewall);
        addnew.setOnClickListener(this);
        viewall.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addnew:
                Intent intent = new Intent(this, NewProductActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_viewall:
                Intent intent1 = new Intent(this, AllProductActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}

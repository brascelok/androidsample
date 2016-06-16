package com.example.mrnoo.blackroseloginfexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        loadUserInfo();

        Button btn = (Button) findViewById(R.id.btn_logout);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(HomePage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadUserInfo(){
        String accName = getIntent().getExtras().getString(VariableUtilities.INTENT_ACCNAME);
        String accId = getIntent().getExtras().getString(VariableUtilities.INTENT_ACCID);
        TextView tvName = (TextView) findViewById(R.id.name);
        ImageView imgAvatar = (ImageView) findViewById(R.id.avatar);
        tvName.setText(accName);
        String profileImgUrl = "https://graph.facebook.com/" + accId + "/picture?type=large";
        Glide.with(HomePage.this).load(profileImgUrl).into(imgAvatar);
    }
}

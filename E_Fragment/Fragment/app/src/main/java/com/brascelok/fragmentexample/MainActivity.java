package com.brascelok.fragmentexample;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration config = getResources().getConfiguration();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE){
            LanscapeFragment lanscapeFragment = new LanscapeFragment();
            fragmentTransaction.replace(android.R.id.content, lanscapeFragment);
        }else{
            PortraintFragment portraintFragment = new PortraintFragment();
            fragmentTransaction.replace(android.R.id.content, portraintFragment);
        }
        fragmentTransaction.commit();
    }
}

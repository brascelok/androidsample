package com.example.mrnoo.blackroseloginfexample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mrnoo on 14/06/2016.
 */
public class TokenUtil {

    private Activity gActivity;

    public TokenUtil(Activity pActivity) {
        this.gActivity = pActivity;
    }

    public void saveToken(String pToken){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(gActivity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(VariableUtilities.FB_TOKEN, pToken);
        editor.apply();
    }

    public String getToken(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(gActivity);
        return sharedPreferences.getString(VariableUtilities.FB_TOKEN, null);
    }

    public void clearToken(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(gActivity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

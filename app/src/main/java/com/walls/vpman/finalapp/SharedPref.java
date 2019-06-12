package com.walls.vpman.finalapp;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPref {
    SharedPreferences mySharedPref;

    public SharedPref(Context context) {
        mySharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);

    }

    public void setSkipState(Boolean state) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("SKIP", state);
        editor.commit();
    }

    public boolean looadSkipState() {
        Boolean state = mySharedPref.getBoolean("SKIP", false);
        return state;
    }
}



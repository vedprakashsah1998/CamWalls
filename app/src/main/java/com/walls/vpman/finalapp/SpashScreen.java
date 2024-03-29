package com.walls.vpman.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SpashScreen extends AppCompatActivity {

    private static int splash=1100;
    SharedPref1 pref1;
    Animation fromtop, frombottom;

    ImageView view;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);

        pref1=new SharedPref1(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        view=findViewById(R.id.logo);
        textView=findViewById(R.id.CamWalls007);


        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        view.startAnimation(frombottom);
        textView.startAnimation(fromtop);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pref1.looadFirstState()==true)
                {   Intent intent=new Intent(SpashScreen.this,IntroActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
                else
                {
                    Intent intent=new Intent(SpashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        },splash);

    }
}

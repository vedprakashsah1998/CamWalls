package com.walls.vpman.finalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

private ViewPager viewPager;
private SliderAdapter sliderAdapter;
private SharedPref1 pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref=new SharedPref1(this);
        setContentView(R.layout.activity_intro);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager=findViewById(R.id.slideviewpager);
        sliderAdapter=new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        pref.setFirstState(false);

        Button skip=findViewById(R.id.skip1);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IntroActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

    }
}

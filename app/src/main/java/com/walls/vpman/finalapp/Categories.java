package com.walls.vpman.finalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.gms.ads.InterstitialAd;

public class Categories extends AppCompatActivity {

    private InterstitialAd interstitialAd;

    Toolbar toolbar;

    Adapter1 adapter1;
    private String[] cats = {
            "Marvel", "Animals", "Backgrounds", "Nature", "Fashion", "Cars", "Sports", "Science", "Travel",
            "Religion",
            "People", "Industry", "Music"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        toolbar = findViewById(R.id.header);


        adapter1 = new Adapter1(this);
        GridView gridView = findViewById(R.id.gridViewCategory);


        gridView.setAdapter(adapter1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.
                LayoutParams.FLAG_FULLSCREEN);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("search", cats[position]);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);*/
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });


    }

    @Override
    public void onBackPressed() {
       /* Intent intent = new Intent(Categories.this, MainActivity.class);
        startActivity(intent);*/
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onBackPressed();
    }


}

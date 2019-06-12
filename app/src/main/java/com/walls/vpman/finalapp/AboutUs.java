package com.walls.vpman.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView back1=findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(AboutUs.this,MainActivity.class);
                startActivity(intent);*/
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ImageView instagram=findViewById(R.id.instagram);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUs.this, WebActivity.class);
                String url="https://www.instagram.com/vedprakash.sah.378/?hl=en";
                intent.putExtra("url",url);

                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ImageView facebook=findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AboutUs.this, WebActivity.class);
                String url="https://www.facebook.com/vedprakash.sah.378";
                intent.putExtra("url",url);

                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        ImageView linkedin=findViewById(R.id.linkedin);
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AboutUs.this, WebActivity.class);
                String url="https://www.linkedin.com/in/ved-p-907709105/";
                intent.putExtra("url",url);

                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        ImageView instagram1=findViewById(R.id.instagram1);
        instagram1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUs.this, WebActivity.class);
                String url="https://www.instagram.com/priyansh_photography/?hl=en";
                intent.putExtra("url",url);

                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ImageView facebook1=findViewById(R.id.facebook1);
        facebook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUs.this, WebActivity.class);
                String url="https://www.facebook.com/rawclickers/";
                intent.putExtra("url",url);

                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ImageView pixabay=findViewById(R.id.pixabay);
        pixabay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUs.this, WebActivity.class);
                String url="https://pixabay.com/";
                intent.putExtra("url",url);

                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ImageView flaticon=findViewById(R.id.flaticon);
        flaticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AboutUs.this, WebActivity.class);
                String url="https://www.flaticon.com/";
                intent.putExtra("url",url);

                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        ImageView mail=findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","vp.mannu.kr@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Cam Walls");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        TextView textView=findViewById(R.id.privacy);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vpmannukr.wixsite.com/camwalls"));
                startActivity(browserIntent);
            }
        });

        if (textView != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        Linkify.addLinks(textView, Linkify.WEB_URLS);
       // textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
       /* Intent intent=new Intent(AboutUs.this,MainActivity.class);
        startActivity(intent);*/
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onBackPressed();
    }
}

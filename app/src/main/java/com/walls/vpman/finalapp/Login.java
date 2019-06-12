package com.walls.vpman.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN=1;
    private String TAG="Login";
    private FirebaseAuth mAuth;
    private SharedPref pref;
    public static final String skip="Skip";

    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        pref=new SharedPref(this);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.google);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.
                LayoutParams.FLAG_FULLSCREEN);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseApp.initializeApp(Login.this);
                firebaseAuth.addAuthStateListener(authStateListener);


            }
        });

        final AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.activity_login)
                .setGoogleButtonId(R.id.google)
                .build();
        AuthUI.getInstance().createSignInIntentBuilder()
                .setAuthMethodPickerLayout(customLayout).build();
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);


                    finish();
                    Toast.makeText(Login.this, user.getDisplayName(), Toast.LENGTH_LONG).show();
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAuthMethodPickerLayout(customLayout).setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
         AlphaAnimation fadeIn = new AlphaAnimation(1.0f , 0.0f ) ;
         AlphaAnimation fadeOut = new AlphaAnimation( 0.0f , 1.0f ) ;

        TextView tv =  findViewById(R.id.CamWall);
            tv.startAnimation(fadeIn);
            tv.startAnimation(fadeOut);
        fadeIn.setDuration(1000);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(2200+fadeIn.getStartOffset());


        AlphaAnimation fadeIn1 = new AlphaAnimation(1.0f , 0.0f ) ;
        AlphaAnimation fadeOut1 = new AlphaAnimation( 0.0f , 1.0f ) ;
        TextView tv1 =  findViewById(R.id.Login);
        tv1.startAnimation(fadeIn1);
        tv1.startAnimation(fadeOut1);
        fadeIn1.setDuration(2000);
        fadeIn1.setFillAfter(true);
        fadeOut1.setDuration(2000);
        fadeOut1.setFillAfter(true);
        fadeOut1.setStartOffset(1200+fadeIn1.getStartOffset());

        AlphaAnimation fadeIn2 = new AlphaAnimation(1.0f , 0.0f ) ;
        AlphaAnimation fadeOut2 = new AlphaAnimation( 0.0f , 1.0f ) ;
        TextView tv2 =  findViewById(R.id.Login1);
        tv2.startAnimation(fadeIn2);
        tv2.startAnimation(fadeOut2);
        fadeIn2.setDuration(2000);
        fadeIn2.setFillAfter(true);
        fadeOut2.setDuration(2000);
        fadeOut2.setFillAfter(true);
        fadeOut2.setStartOffset(2500+fadeIn2.getStartOffset());

        final Button skip=findViewById(R.id.skip);
        skip.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             if (pref.looadSkipState()==false)
             {
                 pref.setSkipState(true);
                 Intent intent=new Intent(Login.this,MainActivity.class);
                 intent.putExtra("Skip","true");
                 startActivity(intent);
                 finish();
             }



            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {

            if (resultCode==RESULT_OK)
            {
                Toast.makeText(this,"Signed in",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


                finish();
            }
            else
            {
              //  Toast.makeText(this,"Signed in",Toast.LENGTH_LONG).show();
            }

            }
        }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    protected void onPause() {
        super.onPause();
        if (authStateListener!=null)
        {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}




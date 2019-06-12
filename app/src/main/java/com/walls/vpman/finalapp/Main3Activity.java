package com.walls.vpman.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;


import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main3Activity extends AppCompatActivity {

    Toolbar toolbar;
    private int STORAGE_PERMISSION_CODE = 1;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
    private List<Wall> walls;
    int check=0;
    List<String> finalwall = new ArrayList<>();
    List<String> strinwall=new ArrayList<>();
    SliderAdapter1 adapter1;
    private String img;
    TextView textView, textView1;
    List<String> slides = new ArrayList<>();
    private int pos;
    private String JsonUrl = "https://pixabay.com/api/?key=11708241-4f427f9d829eb00e4ff78f36c&q= &image_type=photo&per_page=150&safesearch=true";
    private FloatingActionButton fab, floatingActionButton, floatingActionButton1, fab1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main3);
        toolbar = findViewById(R.id.header2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fab1 = findViewById(R.id.fab071);

        ImageView back4 = findViewById(R.id.back4);
        textView = findViewById(R.id.tvshare1);
        textView1 = findViewById(R.id.tvwall1);
        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        Bundle bundle = getIntent().getExtras();
        img = bundle.getString("img");
        final String query = bundle.getString("query");
         pos = bundle.getInt("position");
        String key = bundle.getString("key");
        //ImageView imageView=findViewById(R.id.imgFull);
        //Glide.with(this).load(img).into(imageView);

        if (!query.equals("Marvel"))
        {
            JsonUrl = "https://pixabay.com/api/?key=11708241-4f427f9d829eb00e4ff78f36c&q="+query+" &image_type=photo&per_page=150&safesearch=true";
            loadWall();
        }

        else
        {
            loadFirebase();

        }



        Toast.makeText(this,"Slide To change wallpaper",Toast.LENGTH_LONG).show();

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);


        final ViewPager viewPager=findViewById(R.id.imgFull);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                img=strinwall.get(position);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationfab();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationfab1();
            }
        });


        floatingActionButton = findViewById(R.id.wallpaper1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LoadToast lt = new LoadToast(Main3Activity.this);
                lt.setText("Setting...");
                lt.setTranslationY(1000);
                lt.setBorderColor(Color.TRANSPARENT);
                lt.setBorderWidthDp(4);
                lt.show();

                Glide.with(getApplicationContext()).asBitmap().load(img).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/jpeg");
                        String shareMessage = "\nCam Walls\nDownload the application For awesome wallpapers.\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n";
                        share.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "title");
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);
                        OutputStream outstream;
                        try {
                            outstream = getContentResolver().openOutputStream(uri);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                            outstream.close();
                        } catch (Exception e) {
                            System.err.println(e.toString());
                        }
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(Main3Activity.this);
                        startActivity(wallpaperManager.getCropAndSetWallpaperIntent(uri));
                        lt.success();
                    }
                });
            }
        });
        floatingActionButton1 = findViewById(R.id.share1);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ProgressBar progressBar = findViewById(R.id.progress3);
                progressBar.setVisibility(View.VISIBLE);

                boolean granted = checkWriteExternalPermission();
                if (granted == true) {
                    Glide.with(getApplicationContext()).asBitmap()
                            .load(img)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    Intent share = new Intent(Intent.ACTION_SEND);
                                    share.setType("image/jpeg");

                                    ContentValues values = new ContentValues();
                                    values.put(MediaStore.Images.Media.TITLE, "title");
                                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                    Uri uri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            values);
                                    OutputStream outstream;
                                    try {
                                        outstream = Main3Activity.this.getContentResolver().openOutputStream(uri);
                                        resource.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                                        outstream.close();
                                    } catch (Exception e) {
                                        System.err.println(e.toString());
                                    }

                                    share.putExtra(Intent.EXTRA_STREAM, uri);
                                    share.setType("text/plain");
                                    share.putExtra(Intent.EXTRA_SUBJECT, "Cam Walls");
                                    String shareMessage = "\nDownload this application from PlayStore\n\n";
                                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.walls.vpman.finalapp";
                                    share.putExtra(Intent.EXTRA_TEXT, "Cam Walls" + shareMessage);
                                    startActivity(Intent.createChooser(share, "Share Image"));
                                    ProgressBar progressBar = findViewById(R.id.progress3);
                                    progressBar.setVisibility(View.GONE);

                                }
                            });
                } else {
                    requestStoragePermission();
                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onBackPressed();
    }

    private void animationfab() {

        if (isOpen) {
           /* OvershootInterpolator interpolator=new OvershootInterpolator();
            ViewCompat.animate(fab).rotation(360f).withLayer().setDuration(2000).setInterpolator(interpolator).start();
    */        // fab.startAnimation(rotateForward);
            floatingActionButton.startAnimation(fabClose);
            floatingActionButton1.startAnimation(fabClose);
            textView.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            fab1.hide();
            floatingActionButton.setClickable(false);
            floatingActionButton1.setClickable(false);

            isOpen = false;
        } else {
            floatingActionButton.startAnimation(fabOpen);
            floatingActionButton1.startAnimation(fabOpen);
           /* OvershootInterpolator interpolator=new OvershootInterpolator();
            ViewCompat.animate(fab).rotation(180f).withLayer().setDuration(2000).setInterpolator(interpolator).start();*/
            floatingActionButton.show();
            floatingActionButton1.show();
            textView.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.VISIBLE);
            fab1.show();
            floatingActionButton.setClickable(true);
            floatingActionButton1.setClickable(true);
            isOpen = true;
        }

    }

    private void animationfab1() {

        if (isOpen) {

            floatingActionButton.startAnimation(fabClose);
            floatingActionButton1.startAnimation(fabClose);
            textView.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            fab1.hide();
            floatingActionButton.setClickable(false);
            floatingActionButton1.setClickable(false);

            isOpen = false;
        } else {
            floatingActionButton.startAnimation(fabOpen);
            floatingActionButton1.startAnimation(fabOpen);

            floatingActionButton.show();
            floatingActionButton1.show();
            textView.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.VISIBLE);
            floatingActionButton.setClickable(true);
            fab.show();
            floatingActionButton1.setClickable(true);
            isOpen = true;
        }

    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Main3Activity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }

                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkWriteExternalPermission() {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private void loadWall() {


       /* ProgressBar progressBar1=findViewById(R.id.progress1);
        progressBar1.setVisibility(View.VISIBLE);*/
        walls = new ArrayList<>();
        adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);
        final ViewPager view = findViewById(R.id.imgFull);
        view.setAdapter(adapter1);


                            for (int j=0;j<Main2Activity.walls.size();j++)
                            {
                                strinwall.add(Main2Activity.walls.get(j).getOriginal());
                            }

                            adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);

                            view.setAdapter(adapter1);
                            view.setCurrentItem(pos);
                            img= strinwall.get(pos);



    }


    public void loadFirebase()
    {
        walls = new ArrayList<>();
        slides=new ArrayList<>();

        adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);
        final ViewPager view = findViewById(R.id.imgFull);
        view.setAdapter(adapter1);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Marvel");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String url=dataSnapshot.getValue(String.class);
                walls.add(new Wall(url,url));
               /* adapter1 = new Adapter(Main2Activity.this, walls);
                view.setAdapter(adapter1);*/

                for (int j=0;j<Main2Activity.walls.size();j++)
                {
                    strinwall.add((Main2Activity.walls.get(j).getOriginal()));
                }


                strinwall.add(url);
                adapter1.notifyDataSetChanged();

                if (pos<strinwall.size())
                {
                    view.setCurrentItem(pos);
                    img=strinwall.get(pos);
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}

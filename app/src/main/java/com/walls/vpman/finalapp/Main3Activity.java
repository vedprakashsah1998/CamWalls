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
import android.app.Activity;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


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

import java.io.OutputStream;
import java.util.ArrayList;
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
    private String img,type;
    TextView textView, textView1;
    List<String> slides = new ArrayList<>();
    private int pos;
    private String JsonUrl = "https://pixabay.com/api/?key=11708241-4f427f9d829eb00e4ff78f36c&q= &image_type=photo&per_page=150&safesearch=true";
    private FloatingActionButton fab, floatingActionButton, floatingActionButton1, fab1;
RelativeLayout r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main3);
        toolbar = findViewById(R.id.header2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        r=findViewById(R.id.Rlt);
        fab1 = findViewById(R.id.fab071);

       // View view=findViewById(R.id.content).getRootView();

        /*final Activity activity = new Activity();
        final View content = findViewById(R.id.content).getRootView();
      //  Window window=activity.getWindow();
        if (content.getWidth() > 0) {
            Bitmap image = BlurBuilder.blur(content);
            getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), image));
        } else {
            content.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                Bitmap image = BlurBuilder.blur(content);
                getWindow().setBackgroundDrawable(new BitmapDrawable(getResources(), image));
            });
        }*/

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
        type=bundle.getString("type");
      //  String key = bundle.getString("key");
        //ImageView imageView=findViewById(R.id.imgFull);
        //Glide.with(this).load(img).into(imageView);

        if (!query.equals("Marvel"))
        {
          //  JsonUrl = "https://pixabay.com/api/?key=11708241-4f427f9d829eb00e4ff78f36c&q="+query+" &image_type=photo&per_page=150&safesearch=true";
           // loadUnsplash();

          loadWall();
           /* loadUnsplashCar();
            loadUnsplashBlack();
            loadUnsplashEntertainment();
            loadUnsplashFashion();
            loadUnsplashMountain();
            loadUnsplashMusic();
            loadUnsplashReligion();
            loadUnsplashTravel();*/
          // loadUnsplashCar();
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


        fab.setOnClickListener(v -> showDialog(Main3Activity.this));






    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onBackPressed();
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
        //walls = new ArrayList<>();
        strinwall=new ArrayList<>();
        adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);
        final ViewPager view = findViewById(R.id.imgFull);
        view.setAdapter(adapter1);

                        if (type.equals("Pixabay"))
                        {
                            for (int j=0;j<Main2Activity.walls.size();j++)
                            {
                                strinwall.add(Main2Activity.walls.get(j).getOriginal());
                            }
                            adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);

                        }else if (type.equals("UnsplashCar"))
                        {
                            for (int k=0;k<Collection.photosCar.size();k++)
                            {
                                strinwall.add(String.valueOf(Collection.photosCar.get(k).getUrls().getFull()));

                            }
                            adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);

                        }
                        else if (type.equals("UnsplashMountain"))
                        {
                            for (int k=0;k<Collection.photosMountain.size();k++)
                            {
                                strinwall.add(String.valueOf(Collection.photosMountain.get(k).getUrls().getFull()));

                            }
                            adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);
                        }
                        else if (type.equals("UnsplashEntertainment"))
                        {
                            for (int k=0;k<Collection.photosEntertainment.size();k++)
                            {
                                strinwall.add(String.valueOf(Collection.photosEntertainment.get(k).getUrls().getFull()));

                            }
                            adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);
                        }
                        else if (type.equals("UnsplashTravel"))
                        {
                            for (int k=0;k<Collection.photosTravel.size();k++)
                            {
                                strinwall.add(String.valueOf(Collection.photosTravel.get(k).getUrls().getFull()));

                            }
                            adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);
                        }
                        else if (type.equals("UnsplashFashion"))
                        {
                            for (int k=0;k<Collection.photosFashion.size();k++)
                            {
                                strinwall.add(String.valueOf(Collection.photosFashion.get(k).getUrls().getFull()));

                            }
                            adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);
                        }
                        else if (type.equals("UnsplashMusic"))
                        {
                            for (int k=0;k<Collection.photosMusic.size();k++)
                            {
                                strinwall.add(String.valueOf(Collection.photosMusic.get(k).getUrls().getFull()));

                            }
                            adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);
                        }
                        else if (type.equals("UnsplashBlack"))
                        {
                            for (int k=0;k<Collection.photosBlack.size();k++)
                            {
                                strinwall.add(String.valueOf(Collection.photosBlack.get(k).getUrls().getFull()));

                            }
                            adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);
                        }
                        else if (type.equals("UnsplashReligion"))
                        {
                            for (int k=0;k<Collection.photosReligion.size();k++)
                            {
                                strinwall.add(String.valueOf(Collection.photosReligion.get(k).getUrls().getFull()));

                            }
                            adapter1 = new SliderAdapter1(Main3Activity.this, strinwall);
                        }

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

    public void showDialog(Activity activity)
    {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.set_wall_design);
        FloatingActionButton downFab=dialog.findViewById(R.id.downFab);
        downFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        FloatingActionButton designShare=dialog.findViewById(R.id.designShare);
        designShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean granted=checkWriteExternalPermission();
                if (granted==true)
                {
                  /*  ProgressBar progressBar=view.findViewById(R.id.progress6);
                    progressBar.setVisibility(View.VISIBLE);*/

                    Glide.with(Main3Activity.this).asBitmap()
                            .load(img)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                                {
                                    // Toast.makeText(getActivity(),"1",Toast.LENGTH_LONG).show();

                                    Intent share = new Intent(Intent.ACTION_SEND);
                                    share.setType("image/jpeg");

                                    ContentValues values = new ContentValues();
                                    values.put(MediaStore.Images.Media.TITLE, "title");
                                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                    Uri uri = Main3Activity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
                                    String shareMessage= "\nDownload this application from PlayStore\n\n";
                                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.walls.vpman.finalapp";
                                    share.putExtra(Intent.EXTRA_TEXT, "Cam Walls"+shareMessage);
                                    startActivity(Intent.createChooser(share, "Share Image"));
                                    // ProgressBar progressBar=view.findViewById(R.id.progress6);
                                    /* progressBar.setVisibility(View.GONE);*/
                                }
                            });
                }
                else
                {
                    Toast.makeText(Main3Activity.this,"2",Toast.LENGTH_LONG).show();
                    requestStoragePermission();
                }



            }
        });

        FloatingActionButton designWall=dialog.findViewById(R.id.designWallpaper);
        designWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LoadToast lt = new LoadToast(Main3Activity.this);
                lt.setText("Setting...");
                lt.setTranslationY(1000);
                lt.setBorderColor(Color.BLACK);
                lt.setBackgroundColor(Color.BLUE);
                lt.setBorderWidthDp(4);

                lt.show();

                Glide.with(Main3Activity.this).asBitmap().load(img).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/jpeg");
                        String shareMessage = "\nCam Walls\nDownload the application For awesome wallpapers.\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n";
                        share.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "title");
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                        Uri uri = Main3Activity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);
                        OutputStream outstream;
                        try {
                            outstream = Main3Activity.this.getContentResolver().openOutputStream(uri);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                            outstream.close();
                        } catch (Exception e) {
                            System.err.println(e.toString());
                        }
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(Main3Activity.this);
                        startActivity(wallpaperManager.getCropAndSetWallpaperIntent(uri));
                        lt.success();
                      /*  try {


                            WallpaperManager.getInstance(getActivity()).setBitmap(resource);
                            lt.success();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                });

            }
        });

    }


}

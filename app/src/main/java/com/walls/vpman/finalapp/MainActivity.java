package com.walls.vpman.finalapp;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static int splash = 200;
    private ViewPager viewPager;
    String query1,JsonUrl;
    private DrawerLayout mDrawereLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    AdView adView;
    private SharedPref pref;
    private InterstitialAd interstitialAd;
    private int STORAGE_PERMISSION_CODE = 1;

    boolean doubleBackToExitPressedOnce = false;

    SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref=new SharedPref(this);


        setContentView(R.layout.activity_main);


        mDrawereLayout = findViewById(R.id.Main);
        toolbar = findViewById(R.id.appbar);
        viewPager = findViewById(R.id.view_Pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        MobileAds.initialize(this, "ca-app-pub-2659801811473026~7642733565");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2659801811473026/2384745446");
        interstitialAd.loadAd(new AdRequest.Builder().build());










        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.
                LayoutParams.FLAG_FULLSCREEN);


        setSupportActionBar(toolbar);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        drawerToggle = new ActionBarDrawerToggle(this, mDrawereLayout, R.string.open, R.string.close);
        mDrawereLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        final NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);



        View headerview = navigationView.getHeaderView(0);
        TextView profilename =headerview.findViewById(R.id.email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String skip=null;
        try {
            skip = getIntent().getExtras().getString("Skip");
        } catch (Exception e) {
        }
        if (user != null)
        {
            String email = user.getEmail();
            ImageView view = headerview.findViewById(R.id.profile);
            Uri img = user.getPhotoUrl();
            Glide.with(this).load(img).into(view);
            profilename.setText(email);


        }
        else
        {
            if(skip==null && pref.looadSkipState()==false){
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        }



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mDrawereLayout.openDrawer(GravityCompat.START);
                mDrawereLayout.openDrawer(GravityCompat.START);
            }
        });

        Menu menu = navigationView.getMenu();

        MenuItem logout = menu.findItem(R.id.login);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            logout.setTitle("Logout");
        }
        else
        {
            logout.setTitle("Login");
        }

        requestStoragePermission();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
            item.setChecked(true);
            mDrawereLayout.closeDrawers();
        }

        if (id == R.id.category) {

          /*  interstitialAd.loadAd(new AdRequest.Builder().build());

            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            }
            else
            {
                Intent intent = new Intent(getApplicationContext(), Categories.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
               // finish();
            }

            interstitialAd.setAdListener(new AdListener() {

                @Override
                public void onAdClosed() {
                    Intent intent = new Intent(getApplicationContext(), Categories.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                  //  finish();

                    super.onAdClosed();
                }

                @Override
                public void onAdLoaded() {
                    interstitialAd.show();
                    super.onAdLoaded();
                }
            });*/
            Intent intent = new Intent(getApplicationContext(), Categories.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            item.setChecked(true);
            mDrawereLayout.closeDrawers();
        }

    /*    if (id==R.id.photography)
        {
            Intent intent = new Intent(getApplicationContext(), Photography.class);
            startActivity(intent);

        }*/

        if (id == R.id.login) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (item.getTitle()=="Login")
                    {
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                    else
                    {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(MainActivity.this,"Signout",Toast.LENGTH_LONG).show();
                        item.setTitle("Login");
                    }

                }
            }, splash);
            mDrawereLayout.closeDrawers();
        }

        if (id==R.id.rate_us)
        {
            Intent intent = new Intent(MainActivity.this, WebActivity.class);
            String url="https://play.google.com/store/apps/details?id=com.walls.vpman.finalapp";
            intent.putExtra("url",url);
            startActivity(intent);
        }

        if (id==R.id.feedback)
        {
            Intent intent = new Intent(MainActivity.this, WebActivity.class);
            String url="https://play.google.com/store/apps/details?id=com.walls.vpman.finalapp";
            intent.putExtra("url",url);
            startActivity(intent);
        }



        if (id==R.id.share2)
        {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Cam Walls");
                String shareMessage= "\nDownload this application from PlayStore\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.walls.vpman.finalapp";
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Cam Walls"+shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        if (id==R.id.report)
        {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","vp.mannu.kr@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Cam Walls");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }
        if (id == R.id.aboutus) {
            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            // finish();
        }


        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawereLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawereLayout.closeDrawer(GravityCompat.START);
        } /*else {
            super.onBackPressed();

        }*/

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to close Cam Walls", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);






}

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed so that you can share the image to your friend")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }

                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == STORAGE_PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else
                {
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

       // SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {

            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setIconifiedByDefault(false);
            searchView.setQueryHint("Search Free Photos");
           /* searchView.setQuery("", false);
            searchView.clearFocus();*/
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSupportActionBar().setTitle(query);
                query1 = query;
                JsonUrl = "https://pixabay.com/api/?key=11708241-4f427f9d829eb00e4ff78f36c&q="+query+"&image_type=photo&per_page=150&safesearch=true";
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("search", query);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
      /*  searchView.setQuery("", false);
//        searchView.setIconified(true);
        searchView.clearFocus();*/

    }

    @Override
    protected void onStart() {

        toolbar.setTitle("Cam Walls");
        super.onStart();
    }
}

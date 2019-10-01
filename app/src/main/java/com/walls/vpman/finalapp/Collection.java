package com.walls.vpman.finalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.kc.unsplash.Unsplash;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;

import java.util.List;

public class Collection extends AppCompatActivity {

    Adapter2 adapter2,adapter3,adapter4,adapter5,adapter6,adapter7,adapter8,adapter9;
    private final String CLIENT_ID = "d3a92adcee2ef1d4cee1b52e80ae2c7f8ca95494ece74c74ae9c396fe8ba941a";
    private Unsplash unsplash,unsplash1,unsplash2,unsplash3,unsplash4,unsplash5,unsplash6,unsplash7;
    Toolbar toolbar;
   static List<Photo> photosCar,photosMountain,photosEntertainment,photosTravel,photosFashion,photosMusic,photosBlack,photosReligion;
   // String[] query007 = {"Car","Mountain","Entertainment","Travel","Fashion","Music","Black","Religion"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        toolbar=findViewById(R.id.header1);
        unsplash = new Unsplash(CLIENT_ID);
        unsplash1=new Unsplash(CLIENT_ID);
        unsplash2=new Unsplash(CLIENT_ID);
        unsplash3=new Unsplash(CLIENT_ID);
        unsplash4=new Unsplash(CLIENT_ID);
        unsplash5=new Unsplash(CLIENT_ID);
        unsplash6=new Unsplash(CLIENT_ID);
        unsplash7=new Unsplash(CLIENT_ID);







        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.
                LayoutParams.FLAG_FULLSCREEN);


      //  setSupportActionBar(toolbar);

        GridView gridView=findViewById(R.id.gridViewCars);
        GridView gridView1=findViewById(R.id.gridViewCollection);
        GridView gridView2=findViewById(R.id.gridViewEntertainment);
        GridView gridView3=findViewById(R.id.gridViewTravel);
        GridView gridView4=findViewById(R.id.gridViewFashion);
        GridView gridView5=findViewById(R.id.gridViewAmoled);
        GridView gridView6=findViewById(R.id.gridViewScience);
        GridView gridView7=findViewById(R.id.gridViewReligion);
       String query = "Car";



        unsplash.searchPhotos(query,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {

            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());
                adapter2=new Adapter2(Collection.this,photosCar);

               photosCar = results.getResults();
               adapter2.setPhotos(photosCar);

                gridView.setAdapter(adapter2);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent=new Intent(Collection.this,Main3Activity.class);
            Photo photo=photosCar.get(position);
            intent.putExtra("type","UnsplashCar");
            intent.putExtra("img",photo.getUrls().getFull());
           // Log.d("photo",photo.getUrls().getFull());
          //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("query",query);
            intent.putExtra("position",position);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });








        String query1="Mountain";

        unsplash1.searchPhotos(query1,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());
                adapter3=new Adapter2(Collection.this,photosMountain);
                 photosMountain = results.getResults();
                adapter3.setPhotos(photosMountain);


                gridView1.setAdapter(adapter3);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });
        gridView1.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent=new Intent(Collection.this,Main3Activity.class);
            Photo photo=photosCar.get(position);
            intent.putExtra("type","UnsplashMountain");
            intent.putExtra("img",photo.getUrls().getFull());
            // Log.d("photo",photo.getUrls().getFull());
            //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("query",query);
            intent.putExtra("position",position);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        });
    String query2="Entertainment";

        unsplash2.searchPhotos(query2,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());
                adapter4=new Adapter2(Collection.this,photosEntertainment);

                photosEntertainment = results.getResults();
                adapter4.setPhotos(photosEntertainment);

                gridView2.setAdapter(adapter4);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

        gridView2.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent=new Intent(Collection.this,Main3Activity.class);
            Photo photo=photosCar.get(position);
            intent.putExtra("type","UnsplashEntertainment");
            intent.putExtra("img",photo.getUrls().getFull());
            // Log.d("photo",photo.getUrls().getFull());
            //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("query",query);
            intent.putExtra("position",position);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });

        String query3="Travel";
        unsplash3.searchPhotos(query3,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());
                adapter5=new Adapter2(Collection.this,photosTravel);
                 photosTravel = results.getResults();
                adapter5.setPhotos(photosTravel);


                gridView3.setAdapter(adapter5);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

        gridView3.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent=new Intent(Collection.this,Main3Activity.class);
            Photo photo=photosCar.get(position);
            intent.putExtra("type","UnsplashTravel");
            intent.putExtra("img",photo.getUrls().getFull());
            // Log.d("photo",photo.getUrls().getFull());
            //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("query",query);
            intent.putExtra("position",position);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        });


        String query4="Fashion";
        unsplash4.searchPhotos(query4,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());
                adapter6=new Adapter2(Collection.this,photosFashion);

               photosFashion = results.getResults();
                adapter6.setPhotos(photosFashion);
                gridView4.setAdapter(adapter6);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

        gridView4.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent=new Intent(Collection.this,Main3Activity.class);
            Photo photo=photosCar.get(position);
            intent.putExtra("type","UnsplashFashion");
            intent.putExtra("img",photo.getUrls().getFull());
            // Log.d("photo",photo.getUrls().getFull());
            //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("query",query);
            intent.putExtra("position",position);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        });

        String query5="Music";
        unsplash5.searchPhotos(query5,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());
                adapter7=new Adapter2(Collection.this,photosMusic);
                 photosMusic = results.getResults();
                adapter7.setPhotos(photosMusic);

                gridView5.setAdapter(adapter7);

            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

gridView5.setOnItemClickListener((parent, view, position, id) -> {
    Intent intent=new Intent(Collection.this,Main3Activity.class);
    Photo photo=photosCar.get(position);
    intent.putExtra("type","UnsplashMusic");
    intent.putExtra("img",photo.getUrls().getFull());
    // Log.d("photo",photo.getUrls().getFull());
    //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("query",query);
    intent.putExtra("position",position);
    startActivity(intent);
    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

});
        String query6="Black";
        unsplash6.searchPhotos(query6,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());
                adapter8=new Adapter2(Collection.this,photosBlack);

                photosBlack = results.getResults();
                adapter8.setPhotos(photosBlack);

                gridView6.setAdapter(adapter8);


            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });
        gridView6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Collection.this,Main3Activity.class);
                Photo photo=photosCar.get(position);
                intent.putExtra("type","UnsplashBlack");
                intent.putExtra("img",photo.getUrls().getFull());
                // Log.d("photo",photo.getUrls().getFull());
                //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("query",query);
                intent.putExtra("position",position);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        });

        String query7="Religion";
        unsplash7.searchPhotos(query7,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());
                adapter9=new Adapter2(Collection.this,photosReligion);

                 photosReligion = results.getResults();
                adapter9.setPhotos(photosReligion);
                gridView7.setAdapter(adapter9);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

        gridView7.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent=new Intent(Collection.this,Main3Activity.class);
            Photo photo=photosCar.get(position);
            intent.putExtra("type","UnsplashReligion");
            intent.putExtra("img",photo.getUrls().getFull());
            // Log.d("photo",photo.getUrls().getFull());
            //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("query",query);
            intent.putExtra("position",position);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

        });
        ImageView back2=findViewById(R.id.back2);
        back2.setOnClickListener(v -> {
            /*Intent intent=new Intent(Collection.this,MainActivity.class);
            startActivity(intent);*/
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        /*Intent intent=new Intent(Collection.this,MainActivity.class);
        startActivity(intent);*/
        finish();
        super.onBackPressed();
    }
}

package com.walls.vpman.finalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
        adapter2=new Adapter2(this);
        adapter3=new Adapter2(this);
        adapter4=new Adapter2(this);
        adapter5=new Adapter2(this);
        adapter6=new Adapter2(this);
        adapter7=new Adapter2(this);
        adapter8=new Adapter2(this);
        adapter9=new Adapter2(this);
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
        gridView.setAdapter(adapter2);
        gridView1.setAdapter(adapter3);
        gridView2.setAdapter(adapter4);
        gridView3.setAdapter(adapter5);
        gridView4.setAdapter(adapter6);
        gridView5.setAdapter(adapter7);
        gridView6.setAdapter(adapter8);
        gridView7.setAdapter(adapter9);

        String query = "Car";



            unsplash.searchPhotos(query,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {

                @Override
                public void onComplete(SearchResults results) {
                    Log.d("Photos", "Total Results Found " + results.getTotal());

                    List<Photo> photos = results.getResults();
                    adapter2.setPhotos(photos);
                }

                @Override
                public void onError(String error) {
                    Log.d("Unsplash", error);
                }
            });


        String query1="Mountain";

        unsplash1.searchPhotos(query1,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());

                List<Photo> photos = results.getResults();
                adapter3.setPhotos(photos);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

    String query2="Entertainment";

        unsplash2.searchPhotos(query2,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());

                List<Photo> photos = results.getResults();
                adapter4.setPhotos(photos);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

        String query3="Travel";
        unsplash3.searchPhotos(query3,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());

                List<Photo> photos = results.getResults();
                adapter5.setPhotos(photos);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

        String query4="Fashion";
        unsplash4.searchPhotos(query4,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());

                List<Photo> photos = results.getResults();
                adapter6.setPhotos(photos);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

        String query5="Music";
        unsplash5.searchPhotos(query5,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());

                List<Photo> photos = results.getResults();
                adapter7.setPhotos(photos);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });


        String query6="Black";
        unsplash6.searchPhotos(query6,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());

                List<Photo> photos = results.getResults();
                adapter8.setPhotos(photos);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

        String query7="Religion";
        unsplash7.searchPhotos(query7,1,20,"portrait", new Unsplash.OnSearchCompleteListener() {
            @Override
            public void onComplete(SearchResults results) {
                Log.d("Photos", "Total Results Found " + results.getTotal());

                List<Photo> photos = results.getResults();
                adapter9.setPhotos(photos);
            }

            @Override
            public void onError(String error) {
                Log.d("Unsplash", error);
            }
        });

        ImageView back2=findViewById(R.id.back2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Collection.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Collection.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}

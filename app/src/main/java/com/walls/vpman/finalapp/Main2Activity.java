package com.walls.vpman.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    Adapter adapter1;
    static List<Wall> walls;
    AdView adView;
    int check=0;
    private int pos=0;
    List<String> slides = new ArrayList<>();
    List<String> finalwall = new ArrayList<>();
    String showWall = null;
    private String query1,JsonUrl="https://pixabay.com/api/?key=11708241-4f427f9d829eb00e4ff78f36cq= &image_type=photo&per_page=150&safesearch=true";
    private static int splash=500;
    SearchView searchView = null;
    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
      /*  adView = new AdView(getApplicationContext());
        adView.setAdSize(AdSize.BANNER);*/
        setContentView(R.layout.activity_main2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final String query = getIntent().getExtras().getString("search");
        if (!query.equals("Marvel"))
        {
            JsonUrl="https://pixabay.com/api/?key=11708241-4f427f9d829eb00e4ff78f36c&q="+query+"&image_type=photo&per_page=150&safesearch=true";
            loadWall();
        }
       else
        {
            loadFirebase();
        }


       /* ShimmerFrameLayout container =findViewById(R.id.shimmer_view_container);
        container.startShimmer();*/
   /*     MobileAds.initialize(getApplicationContext(), "ca-app-pub-2659801811473026~7642733565");
        adView.setAdUnitId("ca-app-pub-2659801811473026/9441108388");
        adView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);*/



      /*  adView.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                Toast.makeText(Main2Activity.this,String.valueOf(i),Toast.LENGTH_LONG).show();
                super.onAdFailedToLoad(i);
            }
        });*/



        TextView textView=findViewById(R.id.tv);
        textView.setText(query);

        ImageView back9=findViewById(R.id.back9);
        back9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });



        GridView gridView=findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AnimatorSet set=(AnimatorSet) AnimatorInflater.loadAnimator(Main2Activity.this,R.animator.flip_view);

                set.setTarget(adapter1);
                set.start();



                Intent intent=new Intent(Main2Activity.this,Main3Activity.class);
                intent.putExtra("img",walls.get(position).getOriginal());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("query",query);
                intent.putExtra("position",position);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        });

        gridView.setAdapter(adapter1);
    }


    private void loadWall() {



        walls=new ArrayList<>();
        adapter1=new Adapter(this,walls);
        final GridView view=findViewById(R.id.gridView);
        view.setAdapter(adapter1);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, JsonUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                           Log.e("mil gaya",String.valueOf(obj));
                            JSONArray wallArray = obj.getJSONArray("hits");
                            for (int i = 0; i < wallArray.length(); i++) {
                                JSONObject wallObj = wallArray.getJSONObject(i);
                                Wall wall = new Wall(wallObj.getString("largeImageURL"),wallObj.getString("webformatURL"));
                                walls.add(wall);
                            }

                          Collections.shuffle(walls);

                            adapter1 = new Adapter(Main2Activity.this, walls);

                            view.setAdapter(adapter1);


                        } catch (JSONException e) {
                            Log.e("Nhi mila ",String.valueOf(e));
                            e.getStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){

                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                                Log.d(TAG, "Error: " + error
                                        + "\nStatus Code " + error.networkResponse.statusCode
                                        + "\nResponse Data " + error.networkResponse.data
                                        + "\nCause " + error.getCause()
                                        + "\nmessage" + error.getMessage());
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                        Log.e("Nhi mila ",String.valueOf(error));

                    }
                });
       /* stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });*/
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        super.onBackPressed();
    }

    public void loadFirebase()
    {
        walls=new ArrayList<>();
        adapter1=new Adapter(Main2Activity.this,walls);
        final GridView view=findViewById(R.id.gridView);
        view.setAdapter(adapter1);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Marvel");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String url=dataSnapshot.getValue(String.class);
                walls.add(new Wall(url,url));
                Collections.shuffle(walls);

               adapter1.notifyDataSetChanged();

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

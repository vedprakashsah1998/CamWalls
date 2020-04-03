package com.walls.vpman.finalapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kc.unsplash.Unsplash;
import com.makeramen.roundedimageview.RoundedImageView;
import com.walls.vpman.finalapp.AdapterBody.PopAdapter;
import com.walls.vpman.finalapp.AdapterBody.RecsAdapter;
import com.walls.vpman.finalapp.Main2Activity;
import com.walls.vpman.finalapp.Model.ModelData1;
import com.walls.vpman.finalapp.R;
import com.walls.vpman.finalapp.Utils;
import com.walls.vpman.finalapp.Wall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SearchActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener
{


    ImageView back,back1;
    RecyclerView recyclerView;
    MaterialTextView textView,textView1;
    PopAdapter popAdapter;
   public static List<Wall> walls;

    String Url;

    boolean isClicked=true;

    private long mRequestStartTime;
    RoundedImageView imageView;


    private boolean isHideToolbarView = false;
    private RelativeLayout titleAppbar;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
   public static List<ModelData1> listData;
    private Unsplash unsplash;
    private final String CLIENT_ID="fcd5073926c7fdd11b9eb62887dbd6398eafbb8f3c56073035b141ad57d1ab5f";
    private final String CLIENT_ID1="d3a92adcee2ef1d4cee1b52e80ae2c7f8ca95494ece74c74ae9c396fe8ba941a";
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        toolbar = findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(this);
        back=findViewById(R.id.back9);
        back1=findViewById(R.id.back10);
        titleAppbar=findViewById(R.id.title_appbar);
        textView=findViewById(R.id.tv009);
        textView1=findViewById(R.id.tv);
        imageView=findViewById(R.id.roundImage);
        recyclerView=findViewById(R.id.recyclerView991);


        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

        Intent intent = getIntent();
        query=intent.getStringExtra("search");
        String Landscape=intent.getStringExtra("text");




            Url="https://api.pexels.com/v1/search?query="+query+"&per_page=80&page=3";
            LoadImage();
            // LoadJSon();



        textView.setText(query);
        textView1.setText(query);
/*        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PopularList.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/

        //    loadImage();
        back.setOnClickListener(view -> {

            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });
        back1.setOnClickListener(view -> {

            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });



    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(i) / (float) maxScroll;
        if (percentage == 1f && isHideToolbarView) {
            imageView.setVisibility(View.GONE);
            titleAppbar.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            imageView.setVisibility(View.VISIBLE);
            titleAppbar.setVisibility(View.GONE);
            back.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    public void LoadImage()
    {
        listData=new ArrayList<>();

        mRequestStartTime = System.currentTimeMillis();
        if (query!=null)
        {
             Url="https://api.pexels.com/v1/search?query="+query+"&per_page=80&page=3";
            StringRequest stringRequest=new StringRequest(Request.Method.GET, Url, response -> {
                Log.d("response", response);




                try {
                    JSONObject obj = new JSONObject(response);
                    Log.d("mil gaya",String.valueOf(obj));
                    int totalRes=obj.getInt("total_results");
                /*if (totalRes<=2)
                {
                    UnSplash();
                }*/
                    Log.d("werg", String.valueOf(totalRes));

                    JSONArray wallArray = obj.getJSONArray("photos");
                    for (int i = 0; i < wallArray.length(); i++)
                    {
                        JSONObject wallobj=wallArray.getJSONObject(i);
                        JSONObject photographer=new JSONObject(String.valueOf(wallobj));
                        JSONObject ProfileUrl=new JSONObject(String.valueOf(wallobj));
                        JSONObject jsonObject=wallobj.getJSONObject("src");
                        JSONObject object=new JSONObject(String.valueOf(jsonObject));
                        ModelData1 modelData1=new ModelData1(object.getString("large2x"),photographer.getString("photographer"),object.getString("large"),object.getString("original"));
                        listData.add(modelData1);
                    }
                    popAdapter=new PopAdapter(SearchActivity.this,listData,query);
                    LinearLayoutManager linearLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setNestedScrollingEnabled(true);
                    int itemViewType = 0;
                    recyclerView.getRecycledViewPool().setMaxRecycledViews(itemViewType, 0);
                    recyclerView.setAdapter(popAdapter);

                    if (!listData.isEmpty())
                    {
                        Random random=new Random();
                        int n = random.nextInt(listData.size());
                        Log.d("regr", String.valueOf(listData.get(n)));
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.error(Utils.getRandomDrawbleColor());
                        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                                .signature(new ObjectKey(System.currentTimeMillis())).encodeQuality(70);
                        requestOptions.priority(Priority.IMMEDIATE);
                        requestOptions.skipMemoryCache(false);
                        requestOptions.onlyRetrieveFromCache(true);


                        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                        requestOptions.placeholder(Utils.getRandomDrawbleColor());
                        requestOptions.centerCrop();


                        RequestOptions requestOptions1 = new RequestOptions();
                        requestOptions1.diskCacheStrategy(DiskCacheStrategy.ALL)
                                .signature(new ObjectKey(System.currentTimeMillis())).encodeQuality(70);
                        requestOptions1.onlyRetrieveFromCache(true);
                        requestOptions1.skipMemoryCache(false);
                        requestOptions1.priority(Priority.IMMEDIATE);
                        requestOptions1.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                        requestOptions1.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                        requestOptions1.diskCacheStrategy(DiskCacheStrategy.DATA);
                        requestOptions1.placeholder(Utils.getRandomDrawbleColor());
                        requestOptions1.centerCrop();

                        if (deviceOnWifi()) {
                            Glide.with(SearchActivity.this).load(listData.get(n).getLarge2x()).preload(500,500);
                            Glide.with(SearchActivity.this)
                                    .load(listData.get(n).getLarge2x())
                                    .thumbnail(
                                            Glide.with(SearchActivity.this).load(listData.get(n).getLarge())
                                    )
                                    .apply(requestOptions)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            //  progressBar.setVisibility(View.VISIBLE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            // progressBar.setVisibility(View.GONE);
                                            //   holder.progressBar.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(imageView);
                        }
                        else {

                            Glide.with(SearchActivity.this)
                                    .load(listData.get(n).getLarge2x())
                                    .thumbnail(
                                            Glide.with(SearchActivity.this).load(listData.get(n).getLarge())
                                    )
                                    .apply(requestOptions1)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            // progressBar.setVisibility(View.VISIBLE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            //  progressBar.setVisibility(View.GONE);
                                            //   holder.progressBar.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(imageView);

                        }
                    }
                    else
                    {
                        Toast.makeText(this, "No item is found", Toast.LENGTH_LONG).show();
                    }








                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }, error -> {

                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }

            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization","563492ad6f91700001000001fadf003cdea34296ac384a82c83646f0");
                    return params;
                }
            };

            stringRequest.setShouldCache(false);

            RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(this, "Network Failure", Toast.LENGTH_SHORT).show();
        }
    }



  /*  public void loadFirebase()
    {
        walls=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        int itemViewType = 0;
        recyclerView.getRecycledViewPool().setMaxRecycledViews(itemViewType, 0);
        recyclerView.setAdapter(popAdapter);


  *//*      final GridView view=findViewById(R.id.gridView);
        view.setAdapter(adapter1);*//*
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Marvel");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String url=dataSnapshot.getValue(String.class);
                walls.add(new Wall(url,url));
                Random random=new Random();
                int n = random.nextInt(walls.size());
                Log.d("regr", String.valueOf(walls.get(n)));
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.error(Utils.getRandomDrawbleColor());
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                requestOptions.bitmapTransform(new BlurTransformation(122));
                Glide.with(SearchActivity.this)
                        .load(walls.get(n).getOriginal())
                        .apply(requestOptions)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
                Collections.shuffle(walls);



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
    }*/

    private boolean deviceOnWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) SearchActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return networkInfo.isConnected();
    }

}

package com.walls.vpman.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.ServerError;
import com.android.volley.VolleyError;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.walls.vpman.finalapp.AdapterBody.DataAdapter;
import com.walls.vpman.finalapp.AdapterBody.RecsAdapter;
import com.walls.vpman.finalapp.Model.WallArray;
import com.walls.vpman.finalapp.Model.WallList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;

public class Main2Activity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener
{

    RecsAdapter recsAdapter;
    static List<Wall> walls;
    private ArrayList<WallList> data;
    private DataAdapter dataAdapter;
    private boolean isHideToolbarView = false;
    private RelativeLayout titleAppbar;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    TextView textView,textView1;
    private String JsonUrl="https://pixabay.com/api/?key=11708241-4f427f9d829eb00e4ff78f36cq= &image_type=photo&per_page=150&safesearch=true";
    public static final String API_KEY="11708241-4f427f9d829eb00e4ff78f36c";
    String TAG;
    String query;
    ImageView imageView,back9,back10;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
      /*  adView = new AdView(getApplicationContext());
        adView.setAdSize(AdSize.BANNER);*/
        setContentView(R.layout.activity_main2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        query = getIntent().getExtras().getString("search");
        if (!query.equals("Marvel"))
        {
            JsonUrl="https://pixabay.com/api/?key=11708241-4f427f9d829eb00e4ff78f36c&q="+query+"&image_type=photo&per_page=150&safesearch=true";
           loadWall();
           // LoadJSon();
        }
       else
        {
            loadFirebase();
        }





        appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(this);
        titleAppbar=findViewById(R.id.title_appbar);
        textView=findViewById(R.id.tv);
        textView1=findViewById(R.id.tv009);
        textView1.setText(query);
        recyclerView=findViewById(R.id.my_recycler_view);
        textView.setText(query);
        imageView=findViewById(R.id.imageView009);
         back9=findViewById(R.id.back9);
        back9.setOnClickListener(v -> {

            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });
        back10=findViewById(R.id.back10);
        back10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });



        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        mLayoutmanager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutmanager);
     /*   GridView gridView=findViewById(R.id.gridView);
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

        gridView.setAdapter(adapter1);*/


        Animation animation = AnimationUtils.loadAnimation(Main2Activity.this, R.anim.myanim);
        imageView.startAnimation(animation);

    }


    private void loadWall() {



        walls=new ArrayList<>();
/*
        adapter1=new Adapter(this,walls);
*/
        recsAdapter=new RecsAdapter(this,walls,query);
        final RecyclerView recyclerView1=findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager mLayoutmanager1;
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setNestedScrollingEnabled(true);
        mLayoutmanager1=new GridLayoutManager(this,2);
        recyclerView1.setLayoutManager(mLayoutmanager1);

        recyclerView1.setAdapter(recsAdapter);
/*
        final GridView view=findViewById(R.id.gridView);
*/
/*
        view.setAdapter(adapter1);
*/
        StringRequest stringRequest=new StringRequest(Request.Method.GET, JsonUrl,
                response -> {

                    try {
                        JSONObject obj = new JSONObject(response);
                       Log.e("mil gaya",String.valueOf(obj));
                        JSONArray wallArray = obj.getJSONArray("hits");
                        for (int i = 0; i < wallArray.length(); i++) {
                        JSONObject wallObj = wallArray.getJSONObject(i);
                        Wall wall = new Wall(wallObj.getString("largeImageURL"),wallObj.getString("webformatURL"));

                        walls.add(wall);
                    }
                        Random random=new Random();
                        int n = random.nextInt(walls.size());
                        Log.d("regr", String.valueOf(walls.get(n)));
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
                            Glide.with(Main2Activity.this).load(walls.get(n).getOriginal()).preload(500,500);
                            Glide.with(Main2Activity.this)
                                    .load(walls.get(n).getOriginal())
                                    .thumbnail(
                                            Glide.with(Main2Activity.this).load(walls.get(n).getOriginal())
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

                            Glide.with(Main2Activity.this)
                                    .load(walls.get(n).getOriginal())
                                    .thumbnail(
                                            Glide.with(Main2Activity.this).load(walls.get(n).getOriginal())
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
                     /*
                        Glide.with(Main2Activity.this)
                                .load(walls.get(n).getOriginal())
                                .apply(requestOptions)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(imageView);*/

                    Collections.shuffle(walls);

                    /*    adapter1 = new Adapter(Main2Activity.this, walls);
                     */
                    recsAdapter=new RecsAdapter(Main2Activity.this,walls,query);




                    recyclerView1.setAdapter(recsAdapter);

/*
                        view.setAdapter(adapter1);
*/


                    } catch (JSONException e) {
                        Log.e("Nhi mila ",String.valueOf(e));
                        e.getStackTrace();
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

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }

    @Override
    public void onBackPressed() {

        finish();
        supportFinishAfterTransition();
        super.onBackPressed();
    }



    public void loadFirebase()
    {
        walls=new ArrayList<>();
        recsAdapter=new RecsAdapter(Main2Activity.this,walls,query);
      //  adapter1=new Adapter(Main2Activity.this,walls);
        final RecyclerView recyclerView2=findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager mLayoutmanager2;
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setNestedScrollingEnabled(true);
        mLayoutmanager2=new GridLayoutManager(this,2);
        recyclerView2.setLayoutManager(mLayoutmanager2);

        recyclerView2.setAdapter(recsAdapter);
  /*      final GridView view=findViewById(R.id.gridView);
        view.setAdapter(adapter1);*/
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
                Glide.with(Main2Activity.this)
                        .load(walls.get(n).getOriginal())
                        .apply(requestOptions)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView);
                Collections.shuffle(walls);


               recsAdapter.notifyDataSetChanged();

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

    public void LoadJSon()
    {
        String image_type="photo";
        int per_page=100;
        Boolean safesearch=true;
        JsonPlaceHolderApi jsonPlaceHolderApi=ApiClient.getApiClient().create(JsonPlaceHolderApi.class);

        Call<WallArray> wallCall=jsonPlaceHolderApi.getWall(API_KEY,query,image_type,per_page,safesearch);
        Log.d("johkj",wallCall.request().url()+"");
        wallCall.enqueue(new Callback<WallArray>() {
            @Override
            public void onResponse(Call<WallArray> call, retrofit2.Response<WallArray> response) {

                Log.d("bhai",response.body().getWallLists().toString()+"f");


                dataAdapter=new DataAdapter(Main2Activity.this,response.body().getWallLists());
                final GridView view=findViewById(R.id.gridView);
                Collections.shuffle(response.body().getWallLists());
                view.setAdapter(dataAdapter);


            }

            @Override
            public void onFailure(Call<WallArray> call, Throwable t) {

                Log.d("BHAI",t.toString());
            }
        });

    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(i) / (float) maxScroll;
        if (percentage == 1f && isHideToolbarView) {
            imageView.setVisibility(View.GONE);
            titleAppbar.setVisibility(View.VISIBLE);
            back9.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            imageView.setVisibility(View.VISIBLE);
            titleAppbar.setVisibility(View.GONE);
            back9.setVisibility(View.GONE);
            textView1.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    private boolean deviceOnWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Main2Activity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return networkInfo.isConnected();
    }
}

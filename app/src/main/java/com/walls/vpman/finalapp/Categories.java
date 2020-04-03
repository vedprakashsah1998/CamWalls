package com.walls.vpman.finalapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.textview.MaterialTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.walls.vpman.finalapp.Activity.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Categories extends AppCompatActivity {

    private InterstitialAd interstitialAd;

    Toolbar toolbar;

    /*Adapter1 adapter1;
    private String[] cats = {
            "Marvel", "Animals", "Backgrounds", "Nature", "Fashion", "Cars", "Sports", "Science", "Travel",
            "Religion",
            "People", "Industry", "Music"
    };*/

    String APIKEY="563492ad6f91700001000001951134cf8ed846e1ae33bdab2d865c13";
    String APIKEY1="563492ad6f91700001000001d8ea9ef64de341f0997a8e0d8856a469";
    String APIKEY2="563492ad6f91700001000001fadf003cdea34296ac384a82c83646f0";
    String APIKEY3="563492ad6f9170000100000167dc026e80d04332a8b282c32968aed6";

    CircularImageView Landscape, Cityscape, Seascape, Twilight, Food, DroneView;
    String query, query1, query2, query3, query4, query5, query6, query7, query8,query9,query10,query11,query12,query13,query14,query15,query16,query17;
    MaterialTextView Landscape1, Cityscape1, Seascape1, Twilight1, Food1, DroneView1;

    RoundedImageView Nature,Bokeh,Star,Drone_View,Mountain,Dark,Sky,Forest,Photography,Technology,Beach,Adventure;
    MaterialTextView NatureText,BokehText,StarText,Drone_ViewText,MountainText,DarkText,SkyText,ForestText,PhotographyText,TechnologyText,BeachText,AdventureText;
    String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Landscape = findViewById(R.id.Landscape);
        Cityscape = findViewById(R.id.Cityscape);
        Seascape = findViewById(R.id.Seascape);
        Twilight = findViewById(R.id.Twilight);
        Food = findViewById(R.id.Food);
        DroneView = findViewById(R.id.DronView);
        Landscape1 = findViewById(R.id.landScape);
        Cityscape1 = findViewById(R.id.cityScape);
        Seascape1 = findViewById(R.id.seaScape);
        Twilight1 = findViewById(R.id.twiLight);
        Food1 = findViewById(R.id.fOOD);
        DroneView1 = findViewById(R.id.DroneView991);

        Nature=findViewById(R.id.Nature);
        Bokeh=findViewById(R.id.Bokeh);
        Star=findViewById(R.id.Star);
        Drone_View=findViewById(R.id.DroneView);
        Mountain=findViewById(R.id.Mountain);
        Dark=findViewById(R.id.Dark);
        Sky=findViewById(R.id.sky);
        Forest=findViewById(R.id.Forest);
        Photography=findViewById(R.id.PhotoGraphy);
        Technology=findViewById(R.id.Technology);
        Beach=findViewById(R.id.Beach);
        Adventure=findViewById(R.id.adventure);

        NatureText=findViewById(R.id.photoGrapherName);
        BokehText=findViewById(R.id.photoGrapherName1);
        StarText=findViewById(R.id.photoGrapherName2);
        Drone_ViewText=findViewById(R.id.photoGrapherName3);
        MountainText=findViewById(R.id.photoGrapherName4);
        DarkText=findViewById(R.id.photoGrapherName5);
        SkyText=findViewById(R.id.photoGrapherName6);
        ForestText=findViewById(R.id.photoGrapherName7);
        PhotographyText=findViewById(R.id.photoGrapherName8);
        TechnologyText=findViewById(R.id.photoGrapherName9);
        BeachText=findViewById(R.id.photoGrapherName10);
        AdventureText=findViewById(R.id.photoGrapherName11);

        RequestOptions requestOptions = new RequestOptions();
        // requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new ObjectKey(System.currentTimeMillis())).encodeQuality(70);
        requestOptions.priority(Priority.IMMEDIATE);
        requestOptions.skipMemoryCache(false);
        requestOptions.onlyRetrieveFromCache(true);
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.priority(Priority.HIGH);
        requestOptions.isMemoryCacheable();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);

        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        //   requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.centerCrop();

        query = "Landscape";
         Url = "https://api.pexels.com/v1/search?query=" + query + "&per_page=80&page=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Landscape.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Landscape);
                    }



                }
                Landscape.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query);
                    startActivity(intent);
                });



            } catch (Exception e) {
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
                params.put("Authorization", APIKEY);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


        query1 = "Cityscape";
         Url = "https://api.pexels.com/v1/search?query=" + query1 + "&per_page=80&page=2";
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Cityscape.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Cityscape);
                    }


                }

                Cityscape.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query1);
                    startActivity(intent);
                });



            } catch (Exception e) {
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
                params.put("Authorization", APIKEY);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue1 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue1.add(stringRequest1);

        query2 = "Seascape";
        Url = "https://api.pexels.com/v1/search?query=" + query2 + "&per_page=80&page=3";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Seascape.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Seascape);
                    }


                }
                Seascape.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query2);
                    startActivity(intent);
                });



            } catch (Exception e) {
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
                params.put("Authorization", APIKEY);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue2 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue2.add(stringRequest2);

        query3 = "Twilight";
        Url = "https://api.pexels.com/v1/search?query=" + query3 + "&per_page=80&page=1";
        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Twilight.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Twilight);
                    }


                }
                Twilight.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query3);
                    startActivity(intent);
                });



            } catch (Exception e) {
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
                params.put("Authorization", APIKEY1);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue3 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue3.add(stringRequest3);

        query4 = "Food";
        Url = "https://api.pexels.com/v1/search?query=" + query4 + "&per_page=80&page=4";
        StringRequest stringRequest4 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Food.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Food);
                    }


                }
                Food.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query4);
                    startActivity(intent);
                });


            } catch (Exception e) {
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
                params.put("Authorization", APIKEY1);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue4 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue4.add(stringRequest4);

        query5 = "Drone View";
        Url = "https://api.pexels.com/v1/search?query=" + query5 + "&per_page=80&page=5";
        StringRequest stringRequest5 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        DroneView.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(DroneView);
                    }


                }
                DroneView.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query5);
                    startActivity(intent);
                });


            } catch (Exception e) {
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
                params.put("Authorization", APIKEY1);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue5 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue5.add(stringRequest5);


        query6 = "Nature";
        Url = "https://api.pexels.com/v1/search?query=" + query6 + "&per_page=80&page=5";
        StringRequest stringRequest6 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    NatureText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Nature.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Nature);
                    }


                }
                Nature.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query6);
                    startActivity(intent);
                });


            } catch (Exception e) {
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
                params.put("Authorization", APIKEY2);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue6 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue6.add(stringRequest6);




        query7 = "Bokeh";
        Url = "https://api.pexels.com/v1/search?query=" + query7 + "&per_page=80&page=5";
        StringRequest stringRequest7 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    BokehText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Bokeh.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Bokeh);
                    }



                }
                Bokeh.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query7);
                    startActivity(intent);
                });



            } catch (Exception e) {
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
                params.put("Authorization", APIKEY2);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue7 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue7.add(stringRequest7);

        query8 = "Star";
        Url = "https://api.pexels.com/v1/search?query=" + query8 + "&per_page=80&page=5";
        StringRequest stringRequest8 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    StarText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Star.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Star);
                    }



                }
                Star.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query8);
                    startActivity(intent);
                });


            } catch (Exception e) {
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
                params.put("Authorization", APIKEY2);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue8 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue8.add(stringRequest8);



        query9 = "Drone View";
        Url = "https://api.pexels.com/v1/search?query=" + query9 + "&per_page=80&page=5";
        StringRequest stringRequest9 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    Drone_ViewText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Drone_View.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Drone_View);
                    }
                    Drone_View.setOnClickListener(v -> {
                        Intent intent=new Intent(Categories.this,SearchActivity.class);
                        intent.putExtra("search",query9);
                        startActivity(intent);
                    });


                }



            } catch (Exception e) {
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
                params.put("Authorization", APIKEY2);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue9 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue9.add(stringRequest9);


        query10 = "Mountain";
        Url = "https://api.pexels.com/v1/search?query=" + query10 + "&per_page=80&page=5";
        StringRequest stringRequest10 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    MountainText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Mountain.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Mountain);
                    }



                }
                Mountain.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query10);
                    startActivity(intent);
                });


            } catch (Exception e) {
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
                params.put("Authorization", APIKEY3);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue10 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue10.add(stringRequest10);


        query11 = "Dark";
        Url = "https://api.pexels.com/v1/search?query=" + query11 + "&per_page=80&page=5";
        StringRequest stringRequest11 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    DarkText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Dark.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Dark);
                    }



                }
                Dark.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query11);
                    startActivity(intent);
                });


            } catch (Exception e) {
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
                params.put("Authorization", APIKEY3);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue11 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue11.add(stringRequest11);


        query12 = "Sky";
        Url = "https://api.pexels.com/v1/search?query=" + query12 + "&per_page=80&page=5";
        StringRequest stringRequest12 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    SkyText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Sky.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Sky);
                    }



                }

                Sky.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query12);
                    startActivity(intent);
                });

            } catch (Exception e) {
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
                params.put("Authorization", APIKEY3);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue12 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue12.add(stringRequest12);

        query13 = "Forest";
        Url = "https://api.pexels.com/v1/search?query=" + query13 + "&per_page=80&page=5";
        StringRequest stringRequest13 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    ForestText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Forest.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Forest);
                    }



                }

                Forest.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query13);
                    startActivity(intent);
                });

            } catch (Exception e) {
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
                params.put("Authorization", APIKEY);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue13 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue13.add(stringRequest13);



        query14 = "Photography";
        Url = "https://api.pexels.com/v1/search?query=" + query14 + "&per_page=80&page=5";
        StringRequest stringRequest14 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    PhotographyText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Photography.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Photography);
                    }



                }
                Photography.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query14);
                    startActivity(intent);
                });


            } catch (Exception e) {
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
                params.put("Authorization", APIKEY1);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue14 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue14.add(stringRequest14);

        query15 = "Technology";
        Url = "https://api.pexels.com/v1/search?query=" + query15 + "&per_page=80&page=5";
        StringRequest stringRequest15 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    TechnologyText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Technology.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Technology);
                    }



                }
                Technology.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query15);
                    startActivity(intent);
                });


            } catch (Exception e) {
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
                params.put("Authorization", APIKEY2);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue15 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue15.add(stringRequest15);


        query16 = "Beach";
        Url = "https://api.pexels.com/v1/search?query=" + query16 + "&per_page=80&page=5";
        StringRequest stringRequest16 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    BeachText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Beach.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Beach);
                    }



                }
                Beach.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query16);
                    startActivity(intent);
                });


            } catch (Exception e) {
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
                params.put("Authorization", APIKEY3);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue16 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue16.add(stringRequest16);


        query17 = "Adventure";
        Url = "https://api.pexels.com/v1/search?query=" + query17 + "&per_page=80&page=5";
        StringRequest stringRequest17 = new StringRequest(Request.Method.GET, Url, response -> {
            Log.d("response", response);


            try {
                JSONObject obj = new JSONObject(response);
                Log.d("mil gaya", String.valueOf(obj));
                JSONArray wallArray = obj.getJSONArray("photos");
                for (int i = 0; i < wallArray.length(); i++) {
                    JSONObject wallobj = wallArray.getJSONObject(i);
                    JSONObject photographer = new JSONObject(String.valueOf(wallobj));
                    JSONObject ProfileUrl = new JSONObject(String.valueOf(wallobj));
                    JSONObject jsonObject = wallobj.getJSONObject("src");
                    JSONObject object = new JSONObject(String.valueOf(jsonObject));
                    AdventureText.setText(photographer.getString("photographer"));
                    LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
                        @Override
                        protected int sizeOf(String key, Bitmap image) {
                            return image.getByteCount() / 1024;
                        }
                    };
                    Bitmap image = memCache.get("imagefile");
                    if (image != null) {
                        //Bitmap exists in cache.
                        Adventure.setImageBitmap(image);
                    } else {
                        Glide.with(Categories.this)
                                .load(object.getString("large2x"))
                                .thumbnail(
                                        Glide.with(Categories.this).load(object.getString("large"))
                                )
                                .apply(requestOptions)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //  spinKitView.setVisibility(View.GONE);


                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        // spinKitView.setVisibility(View.GONE);

                                        return false;
                                    }
                                })

                                .into(Adventure);
                    }



                }
                Adventure.setOnClickListener(v -> {
                    Intent intent=new Intent(Categories.this,SearchActivity.class);
                    intent.putExtra("search",query17);
                    startActivity(intent);
                });


            } catch (Exception e) {
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
                params.put("Authorization", APIKEY);
                return params;
            }
        };

        stringRequest.setShouldCache(false);

        RequestQueue requestQueue17 = Volley.newRequestQueue(Categories.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue17.add(stringRequest17);





    }

    @Override
    public void onBackPressed() {
       /* Intent intent = new Intent(Categories.this, MainActivity.class);
        startActivity(intent);*/
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onBackPressed();
    }


}

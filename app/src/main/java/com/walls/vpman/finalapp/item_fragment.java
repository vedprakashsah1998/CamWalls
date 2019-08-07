package com.walls.vpman.finalapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DatabaseReference;
import com.walls.vpman.finalapp.Model.WallArray;
import com.walls.vpman.finalapp.Model.WallList;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.widget.ShareActionProvider;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import retrofit2.Call;
import retrofit2.Callback;


public class item_fragment extends Fragment {

    View view;


VerticalViewPageAdapter verticalViewPageAdapter;



    int check=0;

    private ArrayList<WallList> data;

    List<String> finalwall = new ArrayList<>();
    List<String> slides = new ArrayList<>();


    List<String> Name = new ArrayList<>();
    List<String> slides1 = new ArrayList<>();
    List<String> urlPhotographeer = new ArrayList<>();
    List<String> user = new ArrayList<>();

    private static int splash = 200;
    //private String img;
    String setWall = null;
    String photoName = null;
    String photoUrl = null;
    private Bitmap bitmap;
    private List<String> walList=new ArrayList<>();
    SliderAdapter1 adapter1;
    private int pos=0;
    private ShareActionProvider shareActionProvider;

    Animation fabOpen,fabClose,rotateForward,rotateBackward;
    boolean isOpen=false;

    TextView share1,wally;

    Button favourite;

    String TAG;



    FloatingActionButton fab,floatingActionButton,floatingActionButton1,fab1;
    private int STORAGE_PERMISSION_CODE = 1;

    public static final String API_KEY="11708241-4f427f9d829eb00e4ff78f36c";

    private String JsonUrl = "https://pixabay.com/api/?key=11708241-4f427f9d829eb00e4ff78f36c&q= &image_type=photo&per_page=100&safesearch=true";
private String Url="https://api.pexels.com/v1/curated?query=flower&per_page=80&page=10";
    private List<Wall> walls = new ArrayList<>();

    DatabaseReference databaseReference;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_photo, container, false);

      /*  final Activity activity = getActivity();
        final View content = activity.findViewById(android.R.id.content).getRootView();
        Window window=activity.getWindow();
        if (content.getWidth() > 0) {
            Bitmap image = BlurBuilder.blur(content);
            window.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));
        } else {
            content.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                Bitmap image = BlurBuilder.blur(content);
                window.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));
            });
        }*/
        share1=view.findViewById(R.id.tvshare);
        wally=view.findViewById(R.id.tvwall);
      //  favourite=view.findViewById(R.id.favourite);

        fab=view.findViewById(R.id.fab);
        fab1=view.findViewById(R.id.fab07);

        floatingActionButton = view.findViewById(R.id.wallpaper);
        floatingActionButton1 = view.findViewById(R.id.share);


        verticalViewPageAdapter=view.findViewById(R.id.imgFull007);

        verticalViewPageAdapter.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pos=position;
                setWall=finalwall.get(pos);
               /* photoName=Name.get(pos);
                photoUrl=user.get(pos);*/
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });








        Toast.makeText(getActivity(),"Swipe Up to change wallpaper",Toast.LENGTH_LONG).show();






        int p = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (p == PackageManager.PERMISSION_DENIED) {

        } else {
        }







         fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //animationfab();

                showDialog(getActivity());
            }
        });





        verticalViewPageAdapter.setCurrentItem(pos);



        return view;
    }


    public static item_fragment newInstance(String text) {
        item_fragment f = new item_fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

    public void loadWall() {




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                walls = new ArrayList<>();
                // adapter1=new Adapter(this,walls);
                // final ImageView view=findViewById(R.id.imgView);
             //   imageView = view.findViewById(R.id.imgView1);

                verticalViewPageAdapter=view.findViewById(R.id.imgFull007);

              //  verticalViewPageAdapter.setScrollDurationFactor(0.1f);

                verticalViewPageAdapter.setOverScrollMode(View.OVER_SCROLL_NEVER);


                slides=new ArrayList<>();/*
                slides1=new ArrayList<>();
                urlPhotographeer=new ArrayList<>();*/
                adapter1 = new SliderAdapter1(getActivity(), slides);
               // final ViewPager view = view.findViewById(R.id.imgFull);
                verticalViewPageAdapter.setAdapter(adapter1);

                //   view.setAdapter(adapter1);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, JsonUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                  /*  JSONObject obj = new JSONObject(response);
                                    //  Log.d("mil gaya",String.valueOf(obj));
                                    JSONArray wallArray = obj.getJSONArray("photos");
                                    for (int i = 0; i < wallArray.length(); i++)
                                    {
                                        JSONObject wallobj=wallArray.getJSONObject(i);
                                        JSONObject photographer=new JSONObject(String.valueOf(wallobj));
                                        JSONObject ProfileUrl=new JSONObject(String.valueOf(wallobj));
                                        JSONObject jsonObject=wallobj.getJSONObject("src");
                                        JSONObject object=new JSONObject(String.valueOf(jsonObject));
                                     //   Log.d("milGaya", String.valueOf(photographer));
                                        slides.add(object.getString("large2x"));
                                        slides1.add(photographer.getString("photographer"));
                                        urlPhotographeer.add(ProfileUrl.getString("url"));

                                    }

                                    if (check==0)
                                    {
                                        Collections.shuffle(slides);
                                        Collections.shuffle(slides1);
                                        Collections.shuffle(urlPhotographeer);
                                        check=1;
                                        Name=slides1;
                                        user=urlPhotographeer;
                                        finalwall=slides;
                                        adapter1 = new SliderAdapter2(getActivity(), slides);
                                    }
                                    else
                                    {
                                        adapter1 = new SliderAdapter2(getActivity(), finalwall);
                                    }




                                    verticalViewPageAdapter.setAdapter(adapter1);
                                    verticalViewPageAdapter.setCurrentItem(pos);
                                    setWall=finalwall.get(pos);
                                    photoName=Name.get(pos);
                                    photoUrl=user.get(pos);*/
                                    JSONObject obj = new JSONObject(response);
                                    Log.d("mila", String.valueOf(obj));
                                    JSONArray wallArray = obj.getJSONArray("hits");
                                    for (int i = 0; i < wallArray.length(); i++)
                                    {
                                        JSONObject wallObj = wallArray.getJSONObject(i);



                                        slides.add(wallObj.getString("largeImageURL"));
                                        // Toast.makeText(Main3Activity.this,wallObj.getString("largeImageURL"),Toast.LENGTH_LONG).show();
                                    }
                                    if (check==0)
                                    {
                                        Collections.shuffle(slides);
                                        check=1;
                                        finalwall=slides;
                                        adapter1 = new SliderAdapter1(getActivity(), slides);
                                    }
                                    else
                                    {
                                        adapter1 = new SliderAdapter1(getActivity(), finalwall);
                                    }




                                    verticalViewPageAdapter.setAdapter(adapter1);
                                    verticalViewPageAdapter.setCurrentItem(pos);
                                    setWall=finalwall.get(pos);
                                } catch (JSONException e) {
                                    Log.e("Nhi mila1 ", String.valueOf(e));
                                    e.getStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {


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

                                Log.e("Nhi mila ", String.valueOf(error));

                            }
                        });


                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);


            }
        },splash);



    }




    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }

                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == STORAGE_PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else
            {
             //   Toast.makeText(getActivity(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkWriteExternalPermission()
    {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = getContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private boolean isNetworkAvailable() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
    }

    @Override
    public void onStart() {

        boolean c = isNetworkAvailable();
        if (c == true) {
            ImageView imageView = getActivity().findViewById(R.id.nointernet);
            imageView.setVisibility(View.GONE);
        /*    EditText editText = getActivity().findViewById(R.id.edit_search);
            editText.setVisibility(View.VISIBLE);*/
            TextView editText1 = getActivity().findViewById(R.id.textNo);
            editText1.setVisibility(View.GONE);
            Button button = getActivity().findViewById(R.id.material_button);
            button.setVisibility(View.GONE);
            //Toast.makeText(getActivity(),"connected",Toast.LENGTH_SHORT).show();
           loadWall();
            //LoadJSon();
        } else {
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
            ImageView imageView = getActivity().findViewById(R.id.nointernet);
            imageView.setVisibility(View.VISIBLE);
          /*  EditText editText = getActivity().findViewById(R.id.edit_search);
            editText.setVisibility(View.GONE);*/
            TextView editText1 = getActivity().findViewById(R.id.textNo);
            editText1.setVisibility(View.VISIBLE);
            Button button = getActivity().findViewById(R.id.material_button);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStart();
                }
            });
        }


        super.onStart();
    }

public void LoadJSon()
{
    String image_type="photo";
    int per_page=100;
    Boolean safesearch=true;
    JsonPlaceHolderApi jsonPlaceHolderApi=ApiClient.getApiClient().create(JsonPlaceHolderApi.class);

    Call<WallArray> wallCall=jsonPlaceHolderApi.getWall(API_KEY,image_type,"",per_page,safesearch);
    wallCall.enqueue(new Callback<WallArray>() {
        @Override
        public void onResponse(Call<WallArray> call, retrofit2.Response<WallArray> response) {

          /*  if (response.isSuccessful()&&response.body().getOriginal()!=null)
            {*/

          WallArray wallArray=response.body();
         // data=new ArrayList<>(Arrays.asList(wallArray.getWallLists()));
                slides=new ArrayList<>();

              /*  if (!slides.isEmpty())
                {
                    slides.clear();
                }*/
              // slides=response.body().getOriginal();

             //   walls=response.body().getOriginal();
                verticalViewPageAdapter=view.findViewById(R.id.imgFull007);

             /*   adapter1 = new SliderAdapter1(getActivity(), slides);
               // verticalViewPageAdapter.setAdapter(adapter1);

                if (check==0)
                {
                    Collections.shuffle(slides);
                    check=1;
                    finalwall=slides;
                    adapter1 = new SliderAdapter1(getActivity(), slides);
                    adapter1.notifyDataSetChanged();
                }
                else
                {
                    adapter1 = new SliderAdapter1(getActivity(), finalwall);
                    adapter1.notifyDataSetChanged();
                }*/




                verticalViewPageAdapter.setAdapter(adapter1);
                verticalViewPageAdapter.setCurrentItem(pos);
                setWall=finalwall.get(pos);

          //  }

        }

        @Override
        public void onFailure(Call<WallArray> call, Throwable t) {

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
        /*TextView PhotoGrapherName=dialog.findViewById(R.id.photoGrapher);
        PhotoGrapherName.setText("photo by: "+photoName);

        Toast.makeText(activity,photoUrl,Toast.LENGTH_LONG).show();
        Log.d("url",photoUrl);

        PhotoGrapherName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("url",photoUrl);
                activity.startActivity(intent);
            }
        });*/

        FloatingActionButton designShare=dialog.findViewById(R.id.designShare);
        designShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean granted=checkWriteExternalPermission();
                if (granted==true)
                {
                  /*  ProgressBar progressBar=view.findViewById(R.id.progress6);
                    progressBar.setVisibility(View.VISIBLE);*/

                    Glide.with(getActivity()).asBitmap()
                            .load(setWall)
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
                                    Uri uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            values);
                                    OutputStream outstream;
                                    try {
                                        outstream = getActivity().getContentResolver().openOutputStream(uri);
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
                    Toast.makeText(getActivity(),"2",Toast.LENGTH_LONG).show();
                    requestStoragePermission();
                }



            }
        });

        FloatingActionButton designWall=dialog.findViewById(R.id.designWallpaper);
        designWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LoadToast lt = new LoadToast(getActivity());
                lt.setText("Setting...");
                lt.setTranslationY(1000);
                lt.setBorderColor(Color.BLACK);
                lt.setBackgroundColor(Color.BLUE);
                lt.setBorderWidthDp(4);

                lt.show();

                Glide.with(getActivity()).asBitmap().load(setWall).into(new SimpleTarget<Bitmap>() {
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
                        Uri uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);
                        OutputStream outstream;
                        try {
                            outstream = getActivity().getContentResolver().openOutputStream(uri);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                            outstream.close();
                        } catch (Exception e) {
                            System.err.println(e.toString());
                        }
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getActivity());
                        startActivity(wallpaperManager.getCropAndSetWallpaperIntent(uri));
                        verticalViewPageAdapter.setCurrentItem(pos);
                        lt.success();

                    }
                });

            }
        });

    }

}

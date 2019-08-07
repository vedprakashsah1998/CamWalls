package com.walls.vpman.finalapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter2 extends PagerAdapter
{
    Context context;
    List<String> slides=new ArrayList<>();
    List<String> slides1=new ArrayList<>();

    LayoutInflater inflater;

    ImageView imageView;
    TextView photographerName;

    public SliderAdapter2(Context context, List<String> slides) {
        this.context = context;
        this.slides=slides;
    }


    @Override
    public int getCount() {
        return (slides.size());
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.slide_layout2,null);
        imageView=view.findViewById(R.id.imgFull1007);
/*        photographerName=view.findViewById(R.id.photographerName);
        String PhotoGrapherName=slides1.get(position);
        photographerName.setText(PhotoGrapherName);
        Toast.makeText(context, slides1.get(position), Toast.LENGTH_SHORT).show();*/
        final ProgressBar progressBar=view.findViewById(R.id.progressBar007);

/*
        imageView.setImageResource(Integer.parseInt(String.valueOf(slides)));
*/

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new ObjectKey(System.currentTimeMillis())).encodeQuality(70);
        requestOptions.priority(Priority.IMMEDIATE);
        requestOptions.skipMemoryCache(false);
        requestOptions.onlyRetrieveFromCache(true);
        requestOptions.isMemoryCacheable();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.centerCrop();
        //   Glide.with(context).load(walls.get(position).getWebformatURL()).into(view1);



        RequestOptions requestOptions1=new RequestOptions();
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

     /*   AndroidNetworking.get(slides.get(position))
                .setPriority(com.androidnetworking.common.Priority.HIGH)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP).addPathParameter(requestOptions)
                .setBitmapConfig(Bitmap.Config.ARGB_8888).build().getAsBitmap(new BitmapRequestListener() {
            @Override
            public void onResponse(Bitmap response) {
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError(ANError anError) {
                progressBar.setVisibility(View.VISIBLE);

            }
        });

        imageView.setImageUrl(slides.get(position));*/

       // Picasso.get().load(slides.get(position)).placeholder(Utils.getRandomDrawbleColor()).into(imageView);

        if (deviceOnWifi()) {
            Glide.with(context).load(slides.get(position)).preload(500,500);
            Glide.with(context)
                    .load(slides.get(position))
                    .thumbnail(
                            Glide.with(context).load(slides.get(position))
                    )
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .into(imageView);
        }
        else {

            Glide.with(context)
                    .load(slides.get(position))
                    .thumbnail(
                            Glide.with(context).load(slides.get(position))
                    )
                    .apply(requestOptions1)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .into(imageView);

        }



        ViewPager vp= (ViewPager) container;


        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {

        container.removeView((View) object);
    }


    private boolean deviceOnWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return networkInfo.isConnected();
    }
}

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import jp.wasabeef.glide.transformations.BlurTransformation;

public class SliderAdapter1 extends PagerAdapter
{
    Context context;
    List<String> slides=new ArrayList<>();
    LayoutInflater inflater;

    ImageView imageView;

    public SliderAdapter1(Context context, List<String> slides) {
        this.context = context;
        this.slides=slides;
    }


    @Override
    public int getCount() {
        return slides.size();
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
        View view=inflater.inflate(R.layout.slide_layout1,null);
        imageView=view.findViewById(R.id.imgFull1);
        final ProgressBar progressBar=view.findViewById(R.id.progress35);

        /*float radius = 20f;

        View decorView = ((AppCompatActivity)context).getWindow().getDecorView();
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView =  decorView.findViewById(android.R.id.content);
        //Set drawable to draw in the beginning of each blurred frame (Optional).
        //Can be used in case your layout has a lot of transparent space and your content
        //gets kinda lost after after blur is applied.
        Drawable windowBackground = decorView.getBackground();
        BlurView blurView=new BlurView(context);

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(context))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true);*/
     /*   View decorView = ((AppCompatActivity)context).getWindow().getDecorView();

        ViewGroup rootView =  decorView.findViewById(android.R.id.content);

        Blurry.with(context).radius(10)
                .sampling(8)
                .color(Color.argb(66, 255, 255, 0))
                .async().onto(rootView);*/
       /* Animation zoomin, zoomout;

        zoomin = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
        zoomout = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
        imageView.startAnimation(zoomin);
        imageView.startAnimation(zoomout);*/
/*
        imageView.setImageResource(Integer.parseInt(String.valueOf(slides)));
*/

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new ObjectKey(System.currentTimeMillis())).encodeQuality(70);
        requestOptions.priority(Priority.IMMEDIATE);
        requestOptions.transform(new BlurBuilder(context));
        requestOptions.skipMemoryCache(false);
        requestOptions.transform(new BlurTransformation());
        requestOptions.onlyRetrieveFromCache(true);
        requestOptions.isMemoryCacheable();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
       // requestOptions.bitmapTransform(new BlurTransformation(122));
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.centerCrop();




        RequestOptions requestOptions1=new RequestOptions();
        requestOptions1.diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new ObjectKey(System.currentTimeMillis())).encodeQuality(70);
        requestOptions1.onlyRetrieveFromCache(true);
       // requestOptions1.bitmapTransform(new BlurTransformation(122));
        requestOptions1.transform(new BlurBuilder(context));
        requestOptions1.skipMemoryCache(false);
        requestOptions1.priority(Priority.IMMEDIATE);
        requestOptions1.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        requestOptions1.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions1.diskCacheStrategy(DiskCacheStrategy.DATA);
        requestOptions1.placeholder(Utils.getRandomDrawbleColor());
        requestOptions1.centerCrop();



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
                    .transform(new BlurBuilder(context))
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
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
                    .transform(new BlurBuilder(context))

                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(imageView);

        }
      /*  BlurKeyFrameManager man = new BlurKeyFrameManager();
        man.addKeyFrame(new BlurKeyFrame(2,4,0,3000));
        man.addKeyFrame(new BlurKeyFrame(2,8,0,3000));
        man.addKeyFrame(new BlurKeyFrame(2, 12, 0, 3000));
        man.addKeyFrame(new BlurKeyFrame(2, 16, 0, 3000));
        man.addKeyFrame(new BlurKeyFrame(2, 20, 0, 3000));
        BlurKeyFrameTransitionAnimation animation = new BlurKeyFrameTransitionAnimation(context,man);
        animation.start(imageView);*/








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

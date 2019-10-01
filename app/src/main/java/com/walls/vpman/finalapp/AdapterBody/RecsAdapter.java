package com.walls.vpman.finalapp.AdapterBody;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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
import com.squareup.picasso.Picasso;
import com.walls.vpman.finalapp.Main3Activity;
import com.walls.vpman.finalapp.R;
import com.walls.vpman.finalapp.Utils;
import com.walls.vpman.finalapp.Wall;

import java.util.ArrayList;
import java.util.List;

public class RecsAdapter extends RecyclerView.Adapter<RecsAdapter.MyViewHolder> {
    Context context;
    private List<Wall> walls = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    String query;
    public RecsAdapter(Context context, List<Wall> walls,String query) {
        this.context = context;
        this.walls = walls;
        this.query=query;
    }

    @NonNull
    @Override
    public RecsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_photo, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecsAdapter.MyViewHolder holder, final int position) {

        //holder.view1.startAnimation(holder.fromtop);

       /* holder.animator.setTarget(holder.view1);
        holder.animator.start();*/

       /* holder.view1.startAnimation(holder.zoomin);
        holder.view1.startAnimation(holder.zoomout);*/

      /*  holder.zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

                holder.view1.startAnimation(holder.zoomout);

            }
        });
        holder.zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            holder.view1.startAnimation(holder.zoomin);
            }
        });*/
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new ObjectKey(System.currentTimeMillis())).encodeQuality(70);
        requestOptions.priority(Priority.IMMEDIATE);
        requestOptions.skipMemoryCache(false);
        requestOptions.onlyRetrieveFromCache(true);


        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.centerCrop();

        holder.relativeLayout.setOnClickListener(v -> {


            Intent intent = new Intent(context, Main3Activity.class);
            intent.putExtra("type","Pixabay");
            intent.putExtra("img",walls.get(position).getOriginal());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("query",query);
            intent.putExtra("position",position);
           context. startActivity(intent);
            //context.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });

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
            Glide.with(context).load(walls.get(position)).preload(500, 500);
            Glide.with(context)
                    .load(walls.get(position).getWebformatURL())
                    .thumbnail(
                            Glide.with(context).load(walls.get(position).getWebformatURL())
                    )
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.view1);
        } else {

            Glide.with(context)
                    .load(walls.get(position).getWebformatURL())
                    .thumbnail(
                            Glide.with(context).load(walls.get(position).getWebformatURL())
                    )
                    .apply(requestOptions1)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.view1);

        }

    }

    @Override
    public int getItemCount() {
        return walls.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView view1;
        OnItemClickListener onItemClickListener;
        ProgressBar progressBar;
        Animation fromtop, frombottom;
        RelativeLayout relativeLayout;
        Animation zoomin, zoomout;
        Animator animator;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            view1 = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progress09);
            relativeLayout = itemView.findViewById(R.id.listPhoto007);
            fromtop = AnimationUtils.loadAnimation(context, R.anim.fromtop);
            frombottom = AnimationUtils.loadAnimation(context, R.anim.frombottom);
            zoomin = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
            zoomout = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
            animator= AnimatorInflater.loadAnimator(context,R.animator.zoom_in);
            this.onItemClickListener = onItemClickListener;
        }


        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    private boolean deviceOnWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return networkInfo.isConnected();
    }
}

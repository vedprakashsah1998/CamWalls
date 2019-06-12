package com.walls.vpman.finalapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kc.unsplash.models.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter2 extends BaseAdapter
{

    private List<Photo> photos;
    private Context context;

    public Adapter2(Context context) {
        photos=new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        final Photo photo = photos.get(position);

        if (convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.collection,parent,false);
        }
        else
        {
            view=convertView;
        }

        ImageView view1=view.findViewById(R.id.collection1);

        Picasso.get().load(photo.getUrls().getSmall()).into(view1);

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Main3Activity.class);
                intent.putExtra("img",photo.getUrls().getFull());
                context.startActivity(intent);
            }
        });


        // Glide.with(context).load(walls.get(position).getWebformatURL()).into(view1);
        return view;
    }

    public void setPhotos(List<Photo> photos)
    {
        this.photos = photos;
        notifyDataSetChanged();

    }
}

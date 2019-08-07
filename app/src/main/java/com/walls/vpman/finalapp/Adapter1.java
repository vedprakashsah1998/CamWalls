package com.walls.vpman.finalapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.textview.MaterialTextView;
import com.makeramen.roundedimageview.RoundedImageView;

public class Adapter1 extends BaseAdapter
{



   private Context context;

    public Adapter1(Context context) {
        this.context = context;
    }

    private  Integer[] walls={
            R.drawable.marvel1,
            R.drawable.animal,
            R.drawable.background,
            R.drawable.nature,
            R.drawable.fashion,
            R.drawable.car,
            R.drawable.sport,
            R.drawable.science,
            R.drawable.travel,
            R.drawable.religion,
            R.drawable.people,
            R.drawable.industry,
            R.drawable.music,
            };

    private  String[] cats={
            "Marvel","Animals","Backgrounds","Nature","Fashion","Cars","Sports","Science","Travel",
            "Religion",
            "People","Industry","Music"
    };


    @Override
    public int getCount() {
        return walls.length;
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
        if (convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.cateogories,parent,false);
        }
        else
        {
            view=convertView;
        }
        RoundedImageView view1=view.findViewById(R.id.animal);
        view1.setImageResource(walls[position]);
        MaterialTextView textView=view.findViewById(R.id.tv2);
        textView.setText(cats[position]);
        Animation fromtop, frombottom;





        fromtop = AnimationUtils.loadAnimation(context, R.anim.fromtop);
        frombottom = AnimationUtils.loadAnimation(context, R.anim.frombottom);

        view1.startAnimation(frombottom);
        textView.startAnimation(fromtop);

        return view;
    }
}

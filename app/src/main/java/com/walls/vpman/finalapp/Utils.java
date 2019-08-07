package com.walls.vpman.finalapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.Random;

public class Utils
{

    public static ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#cbc6c4")),
                    new ColorDrawable(Color.parseColor("#9fa8a3")),
                    new ColorDrawable(Color.parseColor("#b6b0ad")),
                    new ColorDrawable(Color.parseColor("#9e9999")),
                    new ColorDrawable(Color.parseColor("#817c7d")),
                    new ColorDrawable(Color.parseColor("#e5efde")),
                    new ColorDrawable(Color.parseColor("#c5d5cb")),
                    new ColorDrawable(Color.parseColor("#c3cec3")),
                    new ColorDrawable(Color.parseColor("#b2beb5"))
            };

    public static ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }
}

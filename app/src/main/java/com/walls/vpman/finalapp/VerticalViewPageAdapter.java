package com.walls.vpman.finalapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class VerticalViewPageAdapter extends ViewPager
{

    public VerticalViewPageAdapter(@NonNull Context context) {
        super(context);
        init();
    }

    public VerticalViewPageAdapter(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        // The majority of the magic happens here
        setPageTransformer(true, new VerticalPageTransformer());
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer
    {

        private static final float MIN_SCALE=0.65f;
        @Override
        public void transformPage(@NonNull View view, float position) {
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);

               // view.setTranslationY(view.getHeight() * position);

                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);
                //set Y position to swipe in from top
               /* float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);*/

             /*  view.setScaleX(1);
               view.setScaleY(1);*/

            }
           /* else if (position<=1)
            {
                view.setAlpha(1-position);
                view.setTranslationX(view.getWidth() * -position);
                view.setTranslationY(0);
                float scaleFactor=MIN_SCALE+(1-Math.abs(position));
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
            }*/
            else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }
}

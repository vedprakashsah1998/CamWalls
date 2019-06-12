package com.walls.vpman.finalapp;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter
{

    private final List<Fragment> fragmentList=new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return item_fragment.newInstance("item_fragment, Instance 1");
            default: return item_fragment.newInstance("item_fragment, Default");
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

}

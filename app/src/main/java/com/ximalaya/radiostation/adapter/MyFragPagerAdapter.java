package com.lanou.radiostation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class MyFragPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> list;

    public MyFragPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }




    @Override
    public int getCount() {
        return list.size();
    }
}

package com.lanou.radiostation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.lanou.radiostation.fragment.BaseFragment;

import java.util.List;

/**
 * Created by user on 2016/7/23.
 */
public class MyBasePageAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> list;

    public MyBasePageAdapter(FragmentManager fm, List<BaseFragment> list) {
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

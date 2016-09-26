package com.lanou.radiostation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanou.radiostation.fragment.BaseFragment;

import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class FindAdapter extends FragmentPagerAdapter{

    List<BaseFragment> list;

    public FindAdapter(FragmentManager fm, List<BaseFragment> list) {
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

package com.lanou.radiostation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by user on 2016/8/3.
 */
public class MyAdapter extends FragmentStatePagerAdapter {
    private List<String> mlist;//标题集合
    private List<Fragment> list;//内容集合
    public MyAdapter(FragmentManager fm, List<String> mlist, List<Fragment> list) {
        super(fm);
        this.mlist = mlist;
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

    //设置标题展示内容
    @Override
    public CharSequence getPageTitle(int position) {
        return mlist.get(position);
    }
}

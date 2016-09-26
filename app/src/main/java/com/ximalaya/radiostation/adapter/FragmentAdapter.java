package com.lanou.radiostation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
       this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList!=null?fragmentList.get(position):null;
    }

    @Override
    public int getCount() {
        return fragmentList!=null?fragmentList.size():0;
    }

}

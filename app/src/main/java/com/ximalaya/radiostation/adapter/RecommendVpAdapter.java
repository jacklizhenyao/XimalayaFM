package com.lanou.radiostation.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class RecommendVpAdapter extends PagerAdapter{

    List<ImageView> list;

    public RecommendVpAdapter(List<ImageView> list) {
        this.list = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup parent = (ViewGroup) list.get(position).getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setListener( List<ImageView> list){
        this.list = list;
        notifyDataSetChanged();
    }
}

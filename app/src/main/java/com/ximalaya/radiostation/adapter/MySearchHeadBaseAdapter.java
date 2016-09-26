package com.lanou.radiostation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.Search;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by user on 2016/7/29.
 */
public class MySearchHeadBaseAdapter extends BaseAdapter{

    Context context;
    List<Search.AlbumResultList> list;

    public MySearchHeadBaseAdapter(Context context, List<Search.AlbumResultList> list) {
//    public MySearchHeadBaseAdapter(Context context, List<String> lista) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
//        Log.i("=========", "getCount: "+list.size());
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.activity_search_item_01,null);
            holder.tv = (TextView) view.findViewById(R.id.search_item_01_tv_content);
            holder.tv1 = (TextView) view.findViewById(R.id.search_item_01_tv);
            holder.iv = (ImageView) view.findViewById(R.id.search_item_01_iv);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
            ImageLoaderUtils.getImageByloader(list.get(i).imgPath,holder.iv);
            holder.tv.setText(list.get(i).keyword);
            holder.tv1.setText(list.get(i).category);

        return view;
    }

    class ViewHolder{
        TextView tv,tv1;
        ImageView iv;

    }
}

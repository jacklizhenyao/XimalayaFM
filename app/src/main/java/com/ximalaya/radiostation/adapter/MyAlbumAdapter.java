package com.lanou.radiostation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;


import com.lanou.radiostation.bean.Album;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by user on 2016/7/23.
 */
public class MyAlbumAdapter extends BaseAdapter {
    private List<Album.Data> list;
 private Context context;

    public MyAlbumAdapter(List<Album.Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
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

        ViewHolder holder=null;
        if(view==null){
            view =View.inflate(context, R.layout.fire_fragment_item,null);
            holder =new ViewHolder();
            holder.ivpic = (ImageView) view.findViewById(R.id.fire_iv_item);
            holder .title = (TextView) view.findViewById(R.id.fire_tv_item_title);
            holder. context = (TextView) view.findViewById(R.id.fire_tv_item_context);
       view.setTag(holder);
        }else {
           holder = (ViewHolder) view.getTag();
        }
        ImageLoaderUtils.getImageByloader(list.get(i).albumCoverUrl290,holder.ivpic);
        holder.title.setText(list.get(i).title);
        holder.context.setText(list.get(i).intro);
        return view;
    }



    class ViewHolder{
     ImageView ivpic;
        TextView title;
        TextView context;

    }

    /**
     * 调用该方法  刷新adapter数据
     * @param list
     */
    public void setList(List<Album.Data> list){
        this.list = list;
        notifyDataSetChanged();
    }


}

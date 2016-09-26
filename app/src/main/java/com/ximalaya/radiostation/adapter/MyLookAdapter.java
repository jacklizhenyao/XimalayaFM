package com.lanou.radiostation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.Attention;
import com.lanou.radiostation.bean.DownLoad;

import java.util.List;
import java.util.Vector;

/**
 * Created by user on 2016/7/30.
 */
public class MyLookAdapter extends BaseAdapter {

  private   Context context;
    private Vector<String> list;

    public MyLookAdapter(Vector<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
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
        ViewHolder holder =null;
        if(view ==null){
            view = View.inflate(context, R.layout.look_item,null);
            holder=new ViewHolder();
            holder.tvtitle = (TextView) view.findViewById(R.id.look_item_tv_title);
            view.setTag(holder);

        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tvtitle.setText(list.get(i));
        return view;
    }

    class ViewHolder{
        ImageView ivPic;
        TextView tvtitle;
        TextView tvcontext;
    }

    public void setListener(Vector<String> list){
        this.list = list;
        notifyDataSetChanged();
    }
}

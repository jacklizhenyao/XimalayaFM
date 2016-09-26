package com.lanou.radiostation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.Search;

import java.util.List;

/**
 * Created by user on 2016/7/29.
 */
public class MySearchBaseAdapter extends BaseAdapter{

    private final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_COUNT = 2;
    Context context;
    List<Search.QueryResultList> list;


    public MySearchBaseAdapter(Context context, List<Search.QueryResultList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * 该方法返回多少个不同的布局
     */
    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return TYPE_COUNT;
    }

    /**
     * 根据position返回相应的Item
     */
    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        int po = position;
        if (po == 0)
            return TYPE_ONE;
        else
            return TYPE_TWO;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.activity_search_item,null);
            holder.tv = (TextView) view.findViewById(R.id.search_item_tv);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.tv.setText(list.get(i).keyword);
        return view;
    }

    class ViewHolder{
        TextView tv;
    }


    public void setListener(List<Search.QueryResultList> list){
        this.list = list;
        notifyDataSetChanged();
    }
}

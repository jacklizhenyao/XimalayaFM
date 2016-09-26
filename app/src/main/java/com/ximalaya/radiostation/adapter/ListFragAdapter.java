package com.lanou.radiostation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.ListFrag;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by user on 2016/8/1.
 */
public class ListFragAdapter extends BaseAdapter{
    Context context;
    List<ListFrag.News> lvList;

    public ListFragAdapter(Context context, List<ListFrag.News> lvList) {
        this.context = context;
        this.lvList = lvList;
    }

    @Override
    public int getCount() {
        return lvList == null? 0 : lvList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {


        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.frag_list_item,null);
            holder.iv = (ImageView) view.findViewById(R.id.frag_list_item_iv);
            holder.tvTitle = (TextView) view.findViewById(R.id.frag_list_tv_title);
            holder.tv_01 = (TextView) view.findViewById(R.id.frag_list_tv_01);
            holder.tv_02 = (TextView) view.findViewById(R.id.frag_list_tv_02);
            view.setTag(holder);
        }else{

            holder = (ViewHolder) view.getTag();
        }

                ImageLoaderUtils.getImageByloader(lvList.get(i).coverPath,holder.iv);
                holder.tvTitle.setText(lvList.get(i).title);
                holder.tv_01.setText("1 "+lvList.get(i).firstKResults.get(0).title);
                holder.tv_02.setText("2 "+lvList.get(i).firstKResults.get(1).title);


        return view;
    }

    class ViewHolder{
        TextView tv_01,tv_02,tvTitle;
        ImageView iv;
    }

    public void setListener(List<ListFrag.News> lvList){
        this.lvList = lvList;
        notifyDataSetChanged();
    }
}

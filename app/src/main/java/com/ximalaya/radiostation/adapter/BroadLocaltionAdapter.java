package com.lanou.radiostation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.ActivityBroad;
import com.lanou.radiostation.bean.BroadLocaltion;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by user on 2016/8/3.
 */
public class BroadLocaltionAdapter extends BaseAdapter{

    Context context;
    List<BroadLocaltion.Datas> list;

    public BroadLocaltionAdapter(Context context, List<BroadLocaltion.Datas> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view =  View.inflate(context, R.layout.activity_broad_item,null);
            holder.iv = (ImageView) view.findViewById(R.id.activity_broad_item_iv);
            holder.name = (TextView) view.findViewById(R.id.activity_broad_item_name);
            holder.programName = (TextView) view.findViewById(R.id.activity_broad_item_programname);
            holder.playCount = (TextView) view.findViewById(R.id.activity_broad_item_playcount);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        ImageLoaderUtils.getImageByloader(list.get(i).coverSmall,holder.iv);
        holder.name.setText(list.get(i).name);
        holder.programName.setText("正在直播 : "+list.get(i).programName);
        double playCount = list.get(i).playCount;
        holder.playCount .setText(new DecimalFormat("##0.0").format(playCount / 10000) + "万次" );

        return view;
    }
    class ViewHolder{
        ImageView iv;
        TextView name,programName,playCount;
    }

    public void setList(List<BroadLocaltion.Datas> list){
        this.list = list;
        notifyDataSetChanged();
    }

}

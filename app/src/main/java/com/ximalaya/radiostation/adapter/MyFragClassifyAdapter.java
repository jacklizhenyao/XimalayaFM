package com.lanou.radiostation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.FragClassify;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by user on 2016/7/29.
 */
public class MyFragClassifyAdapter extends BaseAdapter{

    Context context;
    List<FragClassify.Data> list;

    public MyFragClassifyAdapter(Context context, List<FragClassify.Data> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size():0;
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
            view = View.inflate(context, R.layout.frag_allcallify_item,null);
            holder.iv = (ImageView) view.findViewById(R.id.frag_allcallify_item_iv);
            holder.tvTitle = (TextView) view.findViewById(R.id.frag_allcallify_item_title_tv);
            holder.tvContent = (TextView) view.findViewById(R.id.frag_allcallify_item_title_content);
            holder.tvTime = (TextView) view.findViewById(R.id.frag_allcallify_item_time);
            holder.tvPlay = (TextView) view.findViewById(R.id.frag_allcallify_item_play);
            holder.tvcomment = (TextView) view.findViewById(R.id.frag_allcallify_item_comment);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(list.get(i).title);
        holder.tvContent.setText(list.get(i).intro);
        ImageLoaderUtils.getImageByloader(list.get(i).albumCoverUrl290,holder.iv);
        double i1 = list.get(i).playsCounts / 10000;
        holder.tvPlay.setText(new DecimalFormat("##0.0").format(i1)+"万次");
        holder.tvTime.setText("评分: 5分");
        holder.tvcomment.setText(list.get(i).tracks+"集");
        return view;
    }
    class ViewHolder{
        TextView tvTitle,tvPlay,tvTime,tvcomment;
        TextView tvContent;
        ImageView iv;
    }

    public void setListener(List<FragClassify.Data> list){
        this.list = list;
        notifyDataSetChanged();

    }

}

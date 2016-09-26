package com.lanou.radiostation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.Allclassify_01;
import com.lanou.radiostation.bean.Allclassify_01_lv;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by user on 2016/7/29.
 */
public class AllClassBaseAdapter_01 extends BaseAdapter {

    Context context;
    List<Allclassify_01_lv.Data> list;

    public AllClassBaseAdapter_01(List<Allclassify_01_lv.Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size()>0?list.size():0;
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
            view = View.inflate(context, R.layout.frag_allcallify_01_item,null);
            holder.iv = (ImageView) view.findViewById(R.id.frag_allcallify_01_item_iv);
            holder.tvTitle = (TextView) view.findViewById(R.id.frag_allcallify_01_item_title_tv);
            holder.tvContent = (TextView) view.findViewById(R.id.frag_allcallify_item_01_title_content);
            holder.tvPlay = (TextView) view.findViewById(R.id.frag_allcallify_01_item_play);
            holder.tvcomment = (TextView) view.findViewById(R.id.frag_allcallify_01_item_comment);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(list.get(i).title);
        holder.tvContent.setText(list.get(i).intro);
        ImageLoaderUtils.getImageByloader(list.get(i).albumCoverUrl290,holder.iv);
        double i1 = list.get(i).playsCounts / 10000;
        holder.tvPlay.setText(new DecimalFormat("##0.0").format(i1)+"万次");
        holder.tvcomment.setText(list.get(i).tracks+"集");
        return view;




    }





class ViewHolder {

    TextView tvTitle,tvPlay,tvTime,tvcomment;
    TextView tvContent;
    ImageView iv;
}

    public void setListener(List<Allclassify_01_lv.Data> list){
        this.list = list;
        notifyDataSetChanged();
    }
}

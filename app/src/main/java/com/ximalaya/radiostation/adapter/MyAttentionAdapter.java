package com.lanou.radiostation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.Attention;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by user on 2016/7/29.
 */
public class MyAttentionAdapter extends BaseAdapter {

    private Context context;
    private List<Attention.Data> list;

    public MyAttentionAdapter(Context context, List<Attention.Data> list) {
        this.context = context;
        this.list = list;
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
 ViewHolder holder=null;
        if(view==null){
            view = View.inflate(context, R.layout.attention_lv_item,null);
            holder=new ViewHolder();
            holder.ivplay = (ImageView) view.findViewById(R.id.attention_all_iv_item);
            holder.tvtitle = (TextView) view .findViewById(R.id.attention_tv_item_title);
            holder.tvcontext = (TextView) view.findViewById(R.id.attention_all_tv_item_context);
            holder.count = (TextView) view .findViewById(R.id.attention_tvcount_item);
          view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        ImageLoaderUtils.getImageByloader(list.get(i).coverSmall, holder.ivplay);
        holder.tvcontext.setText(list.get(i).title);
        holder.tvtitle .setText(list.get(i).nickname);
        holder.count.setText(list.get(i).playtimes+"");
        return view;
    }

    class ViewHolder{
     ImageView ivplay;
        TextView tvtitle;
        TextView tvcontext;
       TextView count;

    }
    public void setListener(List<Attention.Data> list){
        this.list = list;
        notifyDataSetChanged();
    }
}

package com.lanou.radiostation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.Particulars;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by user on 2016/8/5.
 */
public class MyParticularsAdapter extends BaseAdapter{

    private Context context;
    private List<Particulars.Data> mLvList;

    public MyParticularsAdapter(Context context, List<Particulars.Data> mLvList) {
        this.context = context;
        this.mLvList = mLvList;
    }

    @Override
    public int getCount() {
        return mLvList != null ? mLvList.size() : 0;
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
            view = View.inflate(context, R.layout.activity_particulars_item,null);
            holder = new ViewHolder();
            holder.iv = (ImageView) view.findViewById(R.id.particulars_item_iv);
            holder.tvTitle = (TextView) view.findViewById(R.id.particulars_item_tv_title);
            holder.tvZhubo = (TextView) view.findViewById(R.id.particulars_item_zhubo);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        ImageLoaderUtils.getImageByloader(mLvList.get(i).coverSmall,holder.iv);
        holder.tvTitle.setText(mLvList.get(i).title);
        holder.tvZhubo.setText(mLvList.get(i).nickname);
        return view;
    }
    class ViewHolder{
        ImageView iv;
        TextView tvTitle,tvZhubo;
    }

    public void setList(List<Particulars.Data> mLvList){
        this.mLvList = mLvList;
        notifyDataSetChanged();
    }

}

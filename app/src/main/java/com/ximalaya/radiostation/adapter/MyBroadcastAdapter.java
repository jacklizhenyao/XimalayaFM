package com.lanou.radiostation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.Radio;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by user on 2016/7/25.
 */
public class MyBroadcastAdapter extends BaseAdapter {
    private Context context;
    private List<Radio.Result> list;
    public MyBroadcastAdapter(Context context, List<Radio.Result> list) {
        this.context = context;
        this.list =list;
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
 if(view==null){
     view =View.inflate(context, R.layout.broadcast_all_item,null);
     holder =new ViewHolder();
    holder.ivpic = (ImageView) view.findViewById(R.id.broad_all_iv_item);
     holder.tvtitle = (TextView) view.findViewById(R.id.broad_all_tv_item_title);
     holder.tvcontext = (TextView) view.findViewById(R.id.broad_tv_item_context);
     holder.tvcount = (TextView) view.findViewById(R.id.broad_tvcount_item);
     view.setTag(holder);
}else {
     holder = (ViewHolder) view.getTag();
 }
        ImageLoaderUtils.getImageByloader(list.get(i).radioCoverSmall,holder.ivpic);
        holder.tvtitle.setText(list.get(i).rname);
        holder.tvcontext.setText(list.get(i).programName);
        double count = list.get(i).radioPlayCount;
        holder.tvcount.setText(new DecimalFormat("##0.00").format(count/10000)
                +"万次");
        return view;
    }


    class ViewHolder{
      ImageView ivpic;
        TextView tvtitle;
        TextView tvcontext;
        TextView tvcount;

    }

    /**
     * 调用该方法  刷新adapter数据
     * @param list
     */
    public void setList(List<Radio.Result> list){
        this.list = list;
        notifyDataSetChanged();
    }

}

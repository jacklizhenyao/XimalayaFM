package com.lanou.radiostation.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AttentionActivity;
import com.lanou.radiostation.activity.RegisterActivity;
import com.lanou.radiostation.bean.Ancher;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by user on 2016/7/27.
 */
public class MyAnchorAdapter extends BaseAdapter  {

    ImageButton anchor_item_ib;
    //private ImageView ivPicleft,ivPiccenter,ivPicright;
  private   List<Ancher.Data> list;
    private Context context;

    public MyAnchorAdapter(List<Ancher.Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list ==null) {
            return 0;
        }else{
            return list.size();

        }
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            view = View.inflate(context, R.layout.ancher_list_item,null);
            holder =new ViewHolder();
     anchor_item_ib = (ImageButton) view.findViewById(R.id.anchor_item_ib);
            anchor_item_ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context, RegisterActivity.class);
                    Toast.makeText(context, "亲！登陆后才可以关注哦", Toast.LENGTH_SHORT).show();
                   context.startActivity(intent);
                }
            });
            holder.ivPicleft = (ImageView) view.findViewById(R.id.fragment_anchor_item_left_iv);
            holder.ivPiccenter = (ImageView) view.findViewById(R.id.fragment_anchor_item_center_iv);
            holder.ivPicright = (ImageView) view.findViewById(R.id.fragment_anchor_item_right_iv);
            holder.tvtitleleft = (TextView) view.findViewById(R.id.fragment_anchor_item_left_tv);
            holder.tvtitlecenter = (TextView) view.findViewById(R.id.fragment_anchor_item_center_tv);
            holder.tvtitleright = (TextView) view.findViewById(R.id.fragment_anchor_item_right_tv);
         holder.anchor_title = (TextView) view.findViewById(R.id.anchor_title);

            view.setTag(holder);
        }else {
          holder = (ViewHolder) view.getTag();
        }
       ImageLoaderUtils .getImageByloader(list.get(i).list.get(0).smallLogo,holder.ivPicleft);
       ImageLoaderUtils .getImageByloader(list.get(i).list.get(1).smallLogo,holder.ivPiccenter);
       ImageLoaderUtils .getImageByloader(list.get(i).list.get(2).smallLogo,holder.ivPicright);
        holder.tvtitleleft.setText(list.get(i).list.get(0).nickname);
        holder.tvtitlecenter.setText(list.get(i).list.get(1).nickname);
        holder.tvtitleright.setText(list.get(i).list.get(2).nickname);
        holder.anchor_title.setText(list.get(i).title);
        holder .ivPicleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, AttentionActivity.class);
                intent.putExtra("smallLogo",list.get(i).list.get(0).smallLogo);
               intent.putExtra("title",list.get(i).title);
                intent.putExtra("nickname",list.get(i).list.get(0).nickname);
                intent.putExtra("position",0);
                intent.putExtra("uid",list.get(i).list.get(0).uid);
                intent.putExtra("verifyTitle",list.get(i).list.get(0).verifyTitle);
                context.startActivity(intent);
            }
        });
        holder. ivPiccenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, AttentionActivity.class);
                intent.putExtra("smallLogo",list.get(i).list.get(1).smallLogo);
                intent.putExtra("title",list.get(i).title);
                intent.putExtra("nickname",list.get(i).list.get(1).nickname);
                intent.putExtra("position",0);
                intent.putExtra("uid",list.get(i).list.get(1).uid);
                intent.putExtra("verifyTitle",list.get(i).list.get(1).verifyTitle);
                context.startActivity(intent);
            }
        });
        holder. ivPicright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, AttentionActivity.class);
                intent.putExtra("smallLogo",list.get(i).list.get(2).smallLogo);
                intent.putExtra("title",list.get(i).title);
                intent.putExtra("nickname",list.get(i).list.get(2).nickname);
                intent.putExtra("position",0);
                intent.putExtra("uid",list.get(i).list.get(2).uid);
                intent.putExtra("verifyTitle",list.get(i).list.get(2).verifyTitle);
                Toast.makeText(context, list.get(i).list.get(2).nickname, Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
        return view;
    }








    class ViewHolder{
        ImageView ivPicleft;
        ImageView ivPiccenter;
        ImageView ivPicright;
        TextView  tvtitleleft;
        TextView  tvtitlecenter;
        TextView  tvtitleright;
 TextView anchor_title;


    }

    public void setList(List<Ancher.Data> list){
        this.list =list;
        notifyDataSetChanged();
    }

}

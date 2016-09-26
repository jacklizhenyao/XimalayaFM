package com.lanou.radiostation.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_recom;

import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class FragSubscriptionAdapter extends BaseAdapter {
    List<Bean_frag_dingyueting_recom> list;
    Context context;
    public FragSubscriptionAdapter(Context context,  List<Bean_frag_dingyueting_recom> list) {
        this.list = list;
        this.context=context;
    }

    @Override
    public int getCount() {

        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int i) {
        return list!=null?list.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return list!=null?i:0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewholder =null ;
        if(view==null){
            view=View.inflate(context, R.layout.item_frag_dingyueting_subscription, null);
            viewholder=new ViewHolder();
           viewholder.imageView_album=(ImageView)view.findViewById(R.id.item_frag_dingyueting_subscription_imageview_album_background);//专辑图片
            viewholder.textView_subscription_title =(TextView)
                    view.findViewById(R.id.item_frag_dingyueting_subscription_textview_recom_title);//专辑标题
            viewholder.textView_subscription=(TextView)
                    view.findViewById(R.id.item_frag_dingyueting_subscription_textview_recom);//专题推荐
            viewholder.updateTime=(TextView)
                    view.findViewById(R.id.item_frag_dingyueting_subscription_textview_updataTime);//播放次数
            view.setTag(viewholder);
        }else{
            viewholder=(ViewHolder) view.getTag();
        }


        viewholder.textView_subscription_title.setText(list.get(i).data.tracks.list.get(0).title);
        viewholder.textView_subscription.setText(list.get(i).data.album.title);
        viewholder.updateTime.setText(list.get(i).data.album.updatedAtStr);
        Bitmap imagebitmap= BitmapFactory.decodeByteArray(list.get(i).data.tracks.imageBytes, 0, list.get(i).data.tracks.imageBytes.length);
        viewholder.imageView_album.setImageBitmap(imagebitmap);
        return view;
    }



    private void showDialog(Context context){
        //inflate() 可以把一个布局的资源文件转换成一个View对象
        View view = View.inflate(context, R.layout.dialog_frag_dingyueting_recom, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //给dialog设置一个自定义的布局
        builder.setView(view);
        //点击dialog以为的部分  是否可以关闭对话框
        builder.setCancelable(true);
//				builder.show();
        final AlertDialog alertDialog = builder.show();
//        view.findViewById(R.id.dialog_notification_cancle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
        view.findViewById(R.id.dialog_notification_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    class ViewHolder{
    ImageView imageView_album;
    TextView textView_subscription_title;
    TextView textView_subscription;
    TextView updateTime;
}
}

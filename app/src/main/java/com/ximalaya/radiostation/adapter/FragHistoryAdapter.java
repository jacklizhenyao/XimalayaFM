package com.lanou.radiostation.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.activity.BroadCastPlayActivity;
import com.lanou.radiostation.activity.PlayBackActivity;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_history;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;

import java.net.Socket;
import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class FragHistoryAdapter extends BaseAdapter {
    List<Bean_frag_dingyueting_history> list;
    Context context;

    public FragHistoryAdapter(Context context,List<Bean_frag_dingyueting_history> list) {
        this.list = list;
        this.context=context;
        MusicSong.historyList=list;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder viewholder =null ;
        if(view==null){
            view=View.inflate(context, R.layout.item_frag_dingyueting_history, null);
            viewholder=new ViewHolder();
            viewholder.play_last_time =(TextView)
                    view.findViewById(R.id.item_frag_dingyueting_history_textview_last_playtime);//播放次数
            viewholder.album=(ImageView)view.findViewById(R.id.item_frag_dingyueting_history_imageview_album_background);
            viewholder.author=(TextView)view.findViewById(R.id.item_frag_dingyueting_history_textview_album_title);
            viewholder.title=(TextView)view.findViewById(R.id.item_frag_dingyueting_history_textview_title);
            viewholder.linearLayout=(LinearLayout)view.findViewById(R.id.item_frag_dingyueting_history_ll);
            viewholder.iv_radio=(ImageView)view.findViewById(R.id.item_frag_dingyueting_history_iv_radio);
            view.setTag(viewholder);
        }else{
            viewholder=(ViewHolder) view.getTag();
        }
        if (list.get(i).tag.equals("guangbo")){
            viewholder.iv_radio.setVisibility(View.VISIBLE);
        }else {
            viewholder.iv_radio.setVisibility(View.GONE);
        }
            Bitmap imagebitmap= BitmapFactory.decodeByteArray(list.get(i).imageBytes, 0, list.get(i).imageBytes.length);
            viewholder.album.setImageBitmap(imagebitmap);
            viewholder.title.setText(list.get(i).title);
            viewholder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 if (list.get(i).tag.equals("guangbo")){
                     Intent intent1 = new Intent(context, BroadCastPlayActivity.class);
                     intent1.putExtra("title",list.get(i).author);
                     intent1.putExtra("picUrl",list.get(i).coverSmall);
                     intent1.putExtra("programName",list.get(i).title);
                     intent1.putExtra("programName",list.get(i).title);
                     intent1.putExtra("id",list.get(i).albumId);
                     context.startActivity(intent1);
                     if (i==0){
                         return;
                     }
                     MusicConstant.radio_id=list.get(i).albumId;
                     MusicSong.tag = "guangbo";
                 }else {
                     String title=list.get(i).title;
                     String picUrl=list.get(i).coverSmall;
                     String titlebottom=list.get(i).title;
                     String nickname=list.get(i).nickname;
                     int albumId=list.get(i).albumId;
                     if (i==0&&MusicConstant.IS_PLAYING){
                         Intent intent1 = new Intent(context,PlayBackActivity.class);
                         intent1.putExtra("picUrl",picUrl);
                         intent1.putExtra("title",title);
                         intent1.putExtra("titlebottom", titlebottom);
                         intent1.putExtra("nickname",nickname);
                         intent1.putExtra("albumId",albumId);
                         MusicConstant.ISPlay = true;
                         context.startActivity(intent1);
                         return;
                     }
                     MusicSong.albumID=list.get(i).albumId;
                     Intent intent1 = new Intent(context,PlayBackActivity.class);
                     intent1.putExtra("picUrl",picUrl);
                     intent1.putExtra("title",title);
                     intent1.putExtra("titlebottom", titlebottom);
                     intent1.putExtra("nickname",nickname);
                     intent1.putExtra("albumId",albumId);
                     context.startActivity(intent1);
                     MusicSong.tag="history";
                 }
                    MusicConstant.ISPlay = true;
                    MusicSong.positon_history = i;
                    Intent intent = new Intent();
                    intent.setAction("haha");
                    intent.putExtra("type", MusicConstant.SWITCH_PROGRESS);
                    context.sendBroadcast(intent);
                }
            });
            viewholder.author.setText(list.get(i).author);
            viewholder.play_last_time.setText(list.get(i).play_time_last);
            return view;

    }
//    getDataListener getDataListener;
//    public void setOnGetDataLister(getDataListener lister){
//        this.getDataListener=lister;
//    }
//public interface getDataListener{
//    public void getPosition(int i);
//}

    class ViewHolder{
        TextView play_last_time;
        TextView title;
        TextView author;
        ImageView album;
        LinearLayout linearLayout;
        ImageView iv_radio;
    }

}

package com.lanou.radiostation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.activity.MainActivity;
import com.lanou.radiostation.activity.PlayBackActivity;
import com.lanou.radiostation.bean.AlbumJup;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_history;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_recom;
import com.lanou.radiostation.bean.DownLoad;
import com.lanou.radiostation.db.RSDBDao;
import com.lanou.radiostation.util.GetBitMapByteArray;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/25.
 */
public class AlbumAdapter extends BaseAdapter{
    Context context;
    List<AlbumJup> list;
   private ImageButton album_item_ib_download;
    List<DownLoad.Data> downlist=new ArrayList<>();
    private Click click;
    private RSDBDao rsdbDao ;
    private Cursor cursor;
    private String tableName="dingyueting_history_table";
    GetBitMapByteArray getBitMapByteArray=new GetBitMapByteArray();
    public AlbumAdapter(Context context, List<AlbumJup> list) {
        this.context = context;
        this.list = list;
        rsdbDao=new RSDBDao(context);
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
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
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.activity_album_item,null);
            album_item_ib_download= (ImageButton) view.findViewById(R.id.album_item_ib_download) ;
            holder.rl = (RelativeLayout) view.findViewById(R.id.album_item_rl);
            holder.ll = (LinearLayout) view.findViewById(R.id.album_item_ll);
            holder.tv = (TextView) view.findViewById(R.id.album_item_title);
            holder.iv = (ImageView) view.findViewById(R.id.album_item_iv);
            holder.album_item_left_play = (TextView) view.findViewById(R.id.album_item_left_play);
            holder.album_item_center_time = (TextView) view.findViewById(R.id.album_item_center_time);
            holder.album_item_right_comment = (TextView) view.findViewById(R.id.album_item_right_comment);

            view.setTag(holder);

        }else{
            holder = (ViewHolder) view.getTag();
        }
       album_item_ib_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downFile(list.get(i).data.tracks.list.get(i).playUrl32,list.get(i).data.tracks.list.get(i).title);


            }
        });



        holder.tv.setText(list.get(0).data.tracks.list.get(i).title);
        ImageLoaderUtils.getImageByloader(list.get(i).data.tracks.list.get(i).coverSmall,holder.iv);
        holder.album_item_left_play.setText(list.get(i).data.tracks.list.get(i).duration+"万");
        holder.album_item_center_time.setText(formatTime(list.get(i).data.tracks.list.get(i).playtimes)+"");
        holder.album_item_right_comment.setText(list.get(i).data.tracks.list.get(i).duration+"");


        if (list.get(i).data.tracks.list.get(i).onclck){
            holder.tv.setTextColor(Color.RED);
            holder.tv.setTextColor(Color.parseColor("#FA593B"));
        }else{
            holder.tv.setTextColor(Color.BLACK);
        }

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MusicSong.albumID=list.get(i).data.album.albumId;
                MusicConstant.PLAYING_POSITION = i;
                MusicSong.tag="album";
                Intent intent1 = new Intent(context,PlayBackActivity.class);
                intent1.putExtra("picUrl",list.get(0).data.tracks.list.get(i).coverLarge);
                intent1.putExtra("title",list.get(0).data.tracks.list.get(i).title);
                intent1.putExtra("titlebottom",AlbumActivity.title);
                intent1.putExtra("nickname",AlbumActivity.nickname);
                intent1.putExtra("albumId",list.get(0).data.album.albumId);
                context.startActivity(intent1);

                holder.tv.setTextColor(Color.BLACK);

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).data.tracks.list.get(i).onclck) {
                        list.get(i).data.tracks.list.get(i).onclck = false;
                    }

                }
                list.get(i).data.tracks.list.get(i).onclck = true;

                MusicConstant.ISPlay = true;
                notifyDataSetChanged();
                click.Onclick();
                setInsertData(list,i);

            }
        });




        return view;
    }

    private  void setInsertData(List<AlbumJup> list, final int i){

        final Bean_frag_dingyueting_history bean_frag_dingyueting_history=new Bean_frag_dingyueting_history();
        bean_frag_dingyueting_history.playUrl32=list.get(i).data.tracks.list.get(i).playUrl32;
        bean_frag_dingyueting_history.albumId=list.get(i).data.album.albumId;
        bean_frag_dingyueting_history.author=list.get(i).data.album.nickname;
        bean_frag_dingyueting_history.coverSmall=list.get(i).data.album.coverLarge;
        bean_frag_dingyueting_history.title=list.get(i).data.tracks.list.get(i).title;
        bean_frag_dingyueting_history.nickname=list.get(i).data.album.nickname;
        getBitMapByteArray.setData(list.get(i).data.album.coverLarge, 100, new GetBitMapByteArray.RequestCallBack() {
            @Override
            public void onSuccess(byte[] result) {
                setSharedPreData(i,result);
                bean_frag_dingyueting_history.imageBytes=result;
                insertDistinct(bean_frag_dingyueting_history); Intent intent = new Intent();
                intent.setAction("haha");
                intent.putExtra("type", MusicConstant.PLAY_GO_ON);
                context.sendBroadcast(intent);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void insertDistinct(Bean_frag_dingyueting_history bean_frag_dingyueting_history) {
        cursor= rsdbDao.select(tableName);
        int albumId= bean_frag_dingyueting_history.albumId;
        while (cursor.moveToNext()){
//            Log.e("cursor",cursor.getString(cursor.getColumnIndex("albumId"))+"");

            if (albumId==cursor.getInt(cursor.getColumnIndex("albumId"))) {
                rsdbDao.delete(tableName,albumId+"");
                rsdbDao.insert(bean_frag_dingyueting_history);
                return;
            }
        }
        rsdbDao.insert(bean_frag_dingyueting_history);
    }

    public class ViewHolder{

        TextView tv;
        ImageView iv;
        TextView album_item_left_play;
        TextView album_item_center_time;
        TextView album_item_right_comment;
        RelativeLayout rl;
        LinearLayout ll;

    }

    public void setListener (List<AlbumJup> list){

        this.list = list;
        notifyDataSetChanged();
    }
    SharedPreferences sharedPreferences;
    private void setSharedPreData(int i, byte[] imageData) {

//        int size =bitmap.getWidth() *bitmap.getHeight() * 4;
//        //创建一个字节数组输出流,流的大小为size
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
//        //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
//        //将字节数组输出流转化为字节数组byte[]
//        byte[] imageData = baos.toByteArray();
        //demo为sharedPreferences自定义名称
        //Activity.MODE_PRIVATE 1、当前应用可以使用 2、其他应用程序也可以使用
        sharedPreferences = context.getSharedPreferences("Rs_sharePre", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String imageString = new String(Base64.encode(imageData,Base64.DEFAULT));
        editor.putString("play_pic_content", imageString);
        editor.putString("title",list.get(0).data.tracks.list.get(i).title);
        editor.putString("coverLarge",list.get(0).data.tracks.list.get(i).coverLarge);
        editor.putString("nickname",list.get(i).data.album.nickname);
        editor.putString("playUrl32",list.get(0).data.tracks.list.get(i).playUrl32);
        editor.putInt("albumId",list.get(0).data.album.albumId);
        editor.commit();
    }
    public static String formatTime(int time){
        //对毫秒进行时间的格式化
        if (time / 1000 % 60 < 10) {
            return time/1000/60+":0"+time/1000%60;
        }else{
            return time/1000/60+":"+time/1000%60;
        }

    }

    public interface Click{
        void Onclick();
    }

    public void setOnclick(Click click){

        this.click = click;
    }


    /**
     * 下载歌曲
     */
    private void downFile(String url, final String download){
        //获取sd卡路径
        String sd = Environment.
                getExternalStorageDirectory().getPath();
        //定义保存下载文件的路径
        String target = sd + "/music/" +download+".mp3";

        HttpUtils utils = new HttpUtils();
        utils.download(
                url,
                target,
                true,
                true,
                new RequestCallBack<File>() {

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        Toast.makeText(context, download+"下载成功", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        error.printStackTrace();

                    }


                });
    }

}

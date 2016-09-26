package com.lanou.radiostation.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.activity.MainActivity;
import com.lanou.radiostation.activity.MyApplication;
import com.lanou.radiostation.activity.PlayBackActivity;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_history;
import com.lanou.radiostation.bean.PlayData;
import com.lanou.radiostation.db.RSDBDao;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;

import java.io.IOException;
import java.util.Random;

/**
 * Created by user on 2016/7/27.
 */




public class ServiceBroadCast extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {
 final public static  int UPDATE_NOTIFICATION=0x1234;
    ImageView imageView, imageView_playOrPause;
    TextView textView_nickname;
    TextView textView_title;
    NotificationManager notificationManager;
    public final static  int   NOTIFICATION_CANCLE=100;
    RemoteViews remoteViews;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MusicConstant.PROGRESS = mediaPlayer.getCurrentPosition();
            MusicConstant.IS_PLAYING = mediaPlayer.isPlaying();
            handler.sendEmptyMessageDelayed(0, 500);
            switch(msg.what){
                case UPDATE_NOTIFICATION:
                    PlayData playData= (PlayData) msg.obj;
//                    imageView=new ImageView(getApplicationContext());
//                    imageView_playOrPause=new ImageView(getApplicationContext());
//                    textView_nickname=new TextView(getApplicationContext());
//                     textView_title=new TextView(getApplicationContext());
                    Bitmap imagebitmap= BitmapFactory.decodeByteArray(playData.image,0, playData.image.length);
//                    imageView.setImageBitmap(imagebitmap);
//                    imageView_playOrPause.setPressed(true);
//                    textView_nickname.setText(playData.nickname);
//                    textView_title.setText(playData.title);
                    Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.notify_btn_light_pause_normal);

                    remoteViews = new RemoteViews(getPackageName(),
                            R.layout.remoteviews_notification);
                    remoteViews.setImageViewBitmap(R.id.widget_album, imagebitmap);
                    remoteViews.setTextViewText(R.id.widget_title, playData.title);
                    remoteViews.setTextViewText(R.id.widget_artist, playData.nickname);
                    if (MusicConstant.IS_PLAYING) {
                        remoteViews.setImageViewResource(R.id.widget_play,  R.mipmap.notify_btn_light_pause_normal);
                    }else {
                        remoteViews.setImageViewResource(R.id.widget_play,  R.mipmap.notify_btn_light_play_normal);
                    }
                    setNotification();
                    handler.removeMessages(UPDATE_NOTIFICATION);
            }
        }
    };

    /**
     * 设置通知
     */
    @SuppressLint("NewApi")
    private void setNotification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent playActivity = new Intent(this, PlayBackActivity.class);
        PlayData playData=getSharedPreData(getApplicationContext());
        playActivity.putExtra("picUrl",playData.coverLarge);
        playActivity.putExtra("title",playData.title);
        playActivity.putExtra("titlebottom", playData.title);
        playActivity.putExtra("nickname",AlbumActivity.nickname);
        playActivity.putExtra("albumId",playData.albumId);
        // 点击跳转到主界面
        PendingIntent intent_go = PendingIntent.getActivity(this,5, playActivity,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notice, intent_go);

        //设置通知关闭按钮
        Intent close = new Intent();
        close.setAction("close");
        close.putExtra("type",NOTIFICATION_CANCLE);
        // 4个参数context, requestCode, intent, flags
        PendingIntent intent_close = PendingIntent.getBroadcast(this, 0, close,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_close, intent_close);

//        // 设置上一曲
//        Intent prv = new Intent();
//        prv.setAction("shangyiqu");
//        PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, prv,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.widget_prev, intent_prev);

//        // 设置播放
        if (!MusicConstant.IS_PLAYING) {
            Intent playorpause = new Intent();
            playorpause.putExtra("type",10);
            playorpause.setAction("play");
            PendingIntent intent_play = PendingIntent.getBroadcast(this, 2,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_play, intent_play);
        }
        if (MusicConstant.IS_PLAYING) {
            Intent playorpause = new Intent();
            playorpause.putExtra("type",MusicConstant.PAUSE);
            playorpause.setAction("pause");
            PendingIntent intent_play = PendingIntent.getBroadcast(this, 6,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_play, intent_play);
        }

//        // 下一曲
//        Intent next = new Intent();
//        next.setAction("xiayiqu");
//        PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, next,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.widget_next, intent_next);


        builder.setSmallIcon(R.mipmap.ting); // 设置顶部图标 有的系统可能为了统一主题风格 会显示系统默认的图标
        builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
//        builder.setDefaults(Notification.DEFAULT_LIGHTS);
        Notification notify = builder.build();
        notify.contentView = remoteViews; // 设置下拉图标
        notify.bigContentView = remoteViews; // 防止显示不完全,需要添加apisupport
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        //以下需要使用的灯光、声音、震动 如果设置了builder 就不需要设置 Notification了
//        //使用默认的声音
//        notify.defaults |= Notification.DEFAULT_SOUND;
        //使用默认的震动
//        notify.defaults |= Notification.DEFAULT_VIBRATE;
//        //使用闪光灯
//        notify.defaults|=Notification.DEFAULT_LIGHTS;
        //        notify.icon = R.mipmap.o_yinyue;
        //        notify.flags = Notification.FLAG_AUTO_CANCEL;//像推送消息一样可以被清除 默认则不被清除
        notificationManager.notify(0, notify);
    }
    protected PlayData getSharedPreData(Context context){
        PlayData playData=new PlayData();
       SharedPreferences sharedPreferences = context.getSharedPreferences("Rs_sharePre", Activity.MODE_PRIVATE);
        String string = sharedPreferences.getString("play_pic_content","");
        playData.image = Base64.decode(string.getBytes(), Base64.DEFAULT);
        playData.playUrl32=sharedPreferences.getString("playUrl32","");
        playData.title=sharedPreferences.getString("title","");
        playData.albumId=sharedPreferences.getInt("albumId",0);
        playData.coverLarge=sharedPreferences.getString("coverLarge","");
        playData.nickname=sharedPreferences.getString("nickname","");
        return playData;
    }

    RSDBDao rsdbDao;
    Cursor  cursor;
    // 声明一个全局mediaPlayer变量
    MediaPlayer mediaPlayer;
    // 声明一个广播的对象
    MusicServiceBroadcastReceiver receiver;
    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new MusicServiceBroadcastReceiver();
        // 完成广播的动态注册
        IntentFilter filter = new IntentFilter();
        filter.addAction("haha");
        filter.addAction("hehe");
        filter.addAction("back");
        filter.addAction("close");
        filter.addAction("pause");
        filter.addAction("play");
        registerReceiver(receiver, filter);

        mediaPlayer = new MediaPlayer();
        // 绑定监听事件
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        rsdbDao=new RSDBDao(this);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 根据传入的path来播放本地音乐
     * @param path
     */
    private void play(String path) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 回复播放状态
     */
    private void playGoOn() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    /**
     * 暂停播放状态
     */
    private void pause() {

        if (MusicSong.tag.equals("guangbo")) {
           updataDB(MusicConstant.radio_id ,System.currentTimeMillis());
        }else {
            if (mediaPlayer != null && mediaPlayer.isPlaying()&&MusicSong.albumID!=MusicConstant.pre_albumID&&MusicConstant.pre_albumID!=0) {

                updataDB(MusicSong.albumID);

            }else {
                updataDB(MusicConstant.pre_albumID);

            }
        }
        mediaPlayer.pause();
    }


    /**
     * 播放下一曲
     * 在该方法中控制，当前音乐的播放模式
     */
    private void nextMusic() {
        switch (MusicConstant.CURRENT_MODE) {
            case MusicConstant.LOOPING:
                MusicConstant.PLAYING_POSITION++;
                break;
            case MusicConstant.RANDOM:
                Random random = new Random();
                MusicConstant.PLAYING_POSITION = random.nextInt(MusicSong.path.length());
                break;
            case MusicConstant.SINGLE:
                break;
            default:
                break;
        }
		/*
		 * 1、如果播放音频的位置变量 小于 播放列表的长度 继续播放当前位置的音频 2、如果 大于 把位置变量赋值为0，从从开始播放
		 */
        if (MusicConstant.PLAYING_POSITION < MusicSong.path.length()) {
        } else {
            MusicConstant.PLAYING_POSITION = 0;
        }
        play(MusicSong.path);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        //准备完成后给静态变量赋值
        MusicConstant.DURATION = mediaPlayer.getDuration();
        if (MusicSong.tag.equals("history") && MusicSong.historyList.get(MusicSong.positon_history).duration_play_time_last != null) {

            if (MusicSong.tag.equals("history") && MusicSong.tag != null) {

                switchProgress();
            }

//        Intent intent = new Intent();
//        intent.setAction("UPdataBroadcastReceiver");
//        sendBroadcast(intent);
        }

        if (getSharedPreData(getApplicationContext()).image.length!=0){
            PlayData  playData=getSharedPreData(getApplicationContext());
            Message message=new Message();
            message.obj=playData;
            message.what=UPDATE_NOTIFICATION;
            handler.sendMessage(message);
        }

        /**
         * 接收广播，处理对应的动作
         *
         */


    }
    private void updataDB(int id){
        String time=getCurremtTime();
        if (time==null){
            time= MusicConstant.PROGRESS+"";
        }
        rsdbDao.update(time,id);
    }
    private void updataDB(int id,long time){
        String timeStr=String.valueOf(time);
        rsdbDao.update(timeStr,id);
        cursor=rsdbDao.select(tableName);
    }
    private String getCurremtTime() {
        if (mediaPlayer.isPlaying()){
            long time=mediaPlayer.getCurrentPosition();
            String timeStr=String.valueOf(time);
            return timeStr;
        }
        return null;
    }
    private String tableName="dingyueting_history_table";
    private void insertDistinct(int albumId) {
        Bean_frag_dingyueting_history historyData = new Bean_frag_dingyueting_history();
        Cursor cursor_ID;
        cursor_ID= rsdbDao.select(tableName,albumId+"");
        while (cursor_ID.moveToNext()){
            historyData.albumId=cursor_ID.getInt(cursor_ID.getColumnIndex("albumId"));
            historyData.author=cursor_ID.getString(cursor_ID.getColumnIndex("author"));
            historyData.coverSmall=cursor_ID.getString(cursor_ID.getColumnIndex("coverSmall"));
            historyData.title=cursor_ID.getString(cursor_ID.getColumnIndex("title"));
            historyData.playUrl32=cursor_ID.getString(cursor_ID.getColumnIndex("playUr"));
            historyData.imageBytes=cursor_ID.getBlob(cursor_ID.getColumnIndex("image"));
            historyData.play_time_last=cursor_ID.getString(cursor_ID.getColumnIndex("play_time_last"));
            historyData.tag=cursor_ID.getString(cursor_ID.getColumnIndex("tag"));
            if (historyData.tag.equals("guangbo")){

            }else {
                historyData.play_time_last=null;
            }
        }
        rsdbDao.delete(tableName,albumId+"");
        rsdbDao.insert(historyData);
    }
    /**
     * 改变当前音乐的播放进度
     */
    private void switchProgress(){

        Integer time=Integer.parseInt(MusicSong.historyList.get(MusicSong.positon_history).duration_play_time_last);
        mediaPlayer.seekTo(time);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class MusicServiceBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 对应传过来的type值，进行不同的消息处理
            switch (intent.getIntExtra("type", 0)) {
                case MusicConstant.PLAY_GO_ON:

                    if (MusicSong.albumID != MusicConstant.pre_albumID && MusicConstant.pre_albumID != 0) {
                        //判断上一首歌是广播还是歌曲
                        //不一样的广播频道
                        cursor=rsdbDao.select(tableName);
                        if (cursor.getCount()>0){
                            cursor=rsdbDao.getMaxId(tableName);
                            String tag=null;
                            int maxId=0;
                            while (cursor.moveToNext()){
                                maxId=cursor.getInt(cursor.getColumnIndex("maxId"));
                            }
                            cursor=rsdbDao.select(tableName,maxId-1);
                            while (cursor.moveToNext()){
                                tag=cursor.getString(cursor.getColumnIndex("tag"));
                                if (tag.equals("mp3")){
                                    updataDB(MusicConstant.pre_albumID);
                                }else {
                                    updataDB(MusicConstant.radio_id,System.currentTimeMillis());
                                }
                                break;
                            }

                        }
                        //如果上一首歌 为广播 更新上广播的时间
                        //否者 更新歌曲

                    } else if (MusicSong.tag.equals("album") && MusicSong.albumID != MusicConstant.pre_albumID && MusicConstant.pre_albumID != 0) {
                        updataDB(MusicConstant.pre_albumID);
                    }
                    if (MusicSong.tag.equals("guangbo")&&MusicConstant.radio_id!=MusicConstant.getRadio_id_pre&&MusicConstant.getRadio_id_pre==0){
//第一次进来的时候
                        cursor=rsdbDao.select(tableName);
                        if (cursor.getCount()>0){
                                updataDB(MusicSong.albumID);
                        }
                        updataDB(MusicConstant.radio_id,System.currentTimeMillis());
                    }
                    if (MusicSong.tag.equals("guangbo")&&MusicConstant.radio_id!=MusicConstant.getRadio_id_pre&&MusicConstant.getRadio_id_pre!=0){
                       //不一样的广播频道
                        cursor=rsdbDao.select(tableName);
                        if (cursor.getCount()>0){
                           cursor=rsdbDao.getMaxId(tableName);
                            String tag=null;
                            int maxId=0;
                            while (cursor.moveToNext()){
                               maxId=cursor.getInt(cursor.getColumnIndex("maxId"));
                            }
                            cursor=rsdbDao.select(tableName,maxId-1);
                            while (cursor.moveToNext()){
                                tag=cursor.getString(cursor.getColumnIndex("tag"));
                                if (tag.equals("mp3")){
                                    updataDB(MusicSong.albumID);
                                    updataDB(MusicConstant.radio_id,System.currentTimeMillis());
                                }else {
                                    updataDB(MusicConstant.radio_id,System.currentTimeMillis());
                                }
                                break;
                            }

                        }

                    }
                    if (MusicSong.tag.equals("guangbo")&&MusicConstant.radio_id==MusicConstant.getRadio_id_pre&&MusicConstant.getRadio_id_pre!=0){
                        //第二次进来时候 同一个广播
                        cursor=rsdbDao.select(tableName);
                        if (cursor.getCount()>0){
                            cursor=rsdbDao.getMaxId(tableName);
                            String tag=null;
                            int maxId=0;
                            while (cursor.moveToNext()){
                                maxId=cursor.getInt(cursor.getColumnIndex("maxId"));
                            }
                            cursor=rsdbDao.select(tableName,maxId-1);
                            while (cursor.moveToNext()){
                                tag=cursor.getString(cursor.getColumnIndex("tag"));
                                if (tag.equals("mp3")){
                                    updataDB(MusicSong.albumID);
                                }
                                break;
                            }
                        }
                        updataDB(MusicConstant.radio_id,System.currentTimeMillis());
                    }


                    play(MusicSong.list.get(MusicConstant.PLAYING_POSITION));

                    handler.sendEmptyMessage(0);
                    break;
                case MusicConstant.PAUSE:
                    if (getSharedPreData(context).image.length!=0){
                        PlayData  playData=getSharedPreData(context);
                        Message message=new Message();
                        message.obj=playData;
                        message.what=UPDATE_NOTIFICATION;
                        handler.sendMessage(message);
                    }
                    handler.removeMessages(0);
                    pause();
                    break;
                case 10:
                    if (getSharedPreData(context).image.length!=0){
                        PlayData  playData=getSharedPreData(context);
                        Message message=new Message();
                        message.obj=playData;
                        message.what=UPDATE_NOTIFICATION;
                        handler.sendMessage(message);
                    }
                    if (MusicSong.tag.equals("history") && MusicSong.albumID == MusicConstant.pre_albumID) {
                        rsdbDao.update(null, MusicSong.albumID);
                    }
                    handler.sendEmptyMessage(0);
                    playGoOn();
                    break;
                case MusicConstant.NEXT_MUSIC:
                    break;
                case MusicConstant.SWITCH_PROGRESS:
                    if (MusicSong.albumID != MusicConstant.pre_albumID && MusicConstant.pre_albumID != 0) {
                        updataDB(MusicConstant.pre_albumID);
                        insertDistinct(MusicSong.albumID);
                    }
                    if (MusicSong.tag.equals("history") && MusicSong.albumID == MusicConstant.pre_albumID) {
                        if (isRadioByMaxId()){
                            //判断数据库中的最大id 是否为广播
                            //如果是广播，删除当前的id 插入值最前 同时给赋值播放时间为空
                            insertDistinct(MusicSong.albumID);
                            updataDB(MusicConstant.radio_id,System.currentTimeMillis());
                        }else {
                            rsdbDao.update(null, MusicSong.albumID); //否则直接更新
                        }
                    }
                    if (MusicSong.tag.equals("history") && MusicSong.albumID != MusicConstant.pre_albumID&& MusicConstant.pre_albumID != 0) {
                        if (isRadioByMaxId()){
                            //判断数据库中的最大id 是否为广播
                            //如果是广播，删除当前的id 插入值最前 同时给赋值播放时间为空
                            updataDB(MusicConstant.radio_id,System.currentTimeMillis());
                        }else {
                            updataDB(MusicConstant.pre_albumID);
                            updataDB(MusicConstant.radio_id,System.currentTimeMillis());
                        }
                        insertDistinct(MusicSong.albumID);
                    }
                    if (MusicSong.tag.equals("guangbo")&&MusicConstant.radio_id==MusicConstant.getRadio_id_pre&&MusicConstant.getRadio_id_pre!=0){
                       //从历史中点击时 重复点击当前 这条消息
                        //判断上一条是否微广播
                        if(isRadioByMaxId()){
                            updataDB(MusicConstant.radio_id,System.currentTimeMillis());
                        }else {
                            //不是广播 把音乐停掉 更新播放时间
                            updataDB(MusicSong.albumID);
                            //置顶操作
                            insertDistinct(MusicConstant.radio_id);
                        }

                    }
                    if (MusicSong.tag.equals("guangbo")&&MusicConstant.radio_id!=MusicConstant.getRadio_id_pre&&MusicConstant.getRadio_id_pre!=0){
                       //从历史中点击时，切换到下一个时
                        if(isRadioByMaxId()){
                            updataDB(MusicConstant.getRadio_id_pre,System.currentTimeMillis());
                        }else {
                            //不是广播 把音乐停掉 更新播放时间
                            updataDB(MusicSong.albumID);
                            //置顶操作
                        }
                        insertDistinct(MusicConstant.radio_id);
                    }


                    // 重置mediaPlayer资源
                    play(MusicSong.historyList.get(MusicSong.positon_history).playUrl32);
                    handler.sendEmptyMessage(0);
                    break;

                case 11:
                    mediaPlayer.seekTo(MusicConstant.PROGRESS);
                    handler.sendEmptyMessage(0);
                    break;
                case NOTIFICATION_CANCLE:
                    notificationManager.cancel(0);
                    break;
                default:
                    String action = intent.getAction();
                    if (action.equals("back")) {
                        Toast.makeText(ServiceBroadCast.this, "退出", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(ServiceBroadCast.this, "暂停", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    }

    private boolean isRadioByMaxId(){
            cursor=rsdbDao.getMaxId(tableName);
            String tag=null;
            int maxId=0;
            while (cursor.moveToNext()){
                maxId=cursor.getInt(cursor.getColumnIndex("maxId"));
            }
            cursor=rsdbDao.select(tableName,maxId);
            while (cursor.moveToNext()){
                tag=cursor.getString(cursor.getColumnIndex("tag"));
        }
        return tag.equals("guangbo");
    }
}

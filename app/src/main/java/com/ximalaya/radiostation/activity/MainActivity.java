package com.lanou.radiostation.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.PlayData;
import com.lanou.radiostation.fragment.DownFragment;
import com.lanou.radiostation.fragment.FindFragment;
import com.lanou.radiostation.fragment.Frag_dingyueting;
import com.lanou.radiostation.fragment.MeFragment;
import com.lanou.radiostation.service.ServiceBroadCast;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;

import java.io.ByteArrayOutputStream;

public class MainActivity extends FragmentActivity {

    private RadioGroup main_bottom_rg;
    private RadioButton find, take, down, me;

    private  ImageView  imageView_initImage ;
    private FindFragment findFragment;
    private Frag_dingyueting takeFragment;
    private DownFragment downFragment;
    private MeFragment meFragment;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private PlayData playData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        application = new MyApplication();

        playData=new PlayData();
        supportFragmentManager = getSupportFragmentManager();

        Intent intent = new Intent(this, ServiceBroadCast.class);
        startService(intent);

        initView();
    }

    private void initView() {
        main_bottom_rg = (RadioGroup) findViewById(R.id.main_bottom_rg);
        find = (RadioButton) findViewById(R.id.main_bottom_rb_find);
        take = (RadioButton) findViewById(R.id.main_bottom_rb_take);
        down = (RadioButton) findViewById(R.id.main_bottom_rb_down);
        me = (RadioButton) findViewById(R.id.main_bottom_rb_me);
        find.setChecked(true);
        imageView_initImage = (ImageView) findViewById(R.id.main_play_iv_background);
        findFragment = new FindFragment();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        findFragment = new FindFragment();
        fragmentTransaction.add(R.id.main_fl_show, findFragment);
        fragmentTransaction.commit();

        imageView_initImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playData=getSharedPreData();
                if (playData.image.length!=0){
                    Intent intent1 = new Intent(MainActivity.this,PlayBackActivity.class);
                    MusicConstant.ISPlay=true;
                    intent1.putExtra("picUrl",playData.coverLarge);
                    intent1.putExtra("title",playData.title);
                    intent1.putExtra("titlebottom",playData.title);
                    intent1.putExtra("nickname",playData.nickname);
                    intent1.putExtra("albumId",playData.albumId);
                    startActivity(intent1);
                    if (MusicConstant.IS_PLAYING){

                    }else {
                        MusicSong.list.clear();
                        MusicConstant.PLAYING_POSITION = 0;
                        MusicSong.list.add(playData.playUrl32);
                        Intent intent=new Intent();
                        intent.setAction("haha");
                        intent.putExtra("type", MusicConstant.PLAY_GO_ON);
                        sendBroadcast(intent);
                    }
                }


            }
        });
        main_bottom_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                fragmentTransaction = supportFragmentManager.beginTransaction();
                hideFragment(fragmentTransaction);
                switch (i) {
                    case R.id.main_bottom_rb_find:
                        if (findFragment == null) {

                            fragmentTransaction.add(R.id.main_fl_show, findFragment);
                        } else {
                            fragmentTransaction.show(findFragment);
                        }
                        break;
                    case R.id.main_bottom_rb_take:
                        if (takeFragment == null) {
                            takeFragment = new Frag_dingyueting();
                            fragmentTransaction.add(R.id.main_fl_show, takeFragment);
                        } else {
                            fragmentTransaction.show(takeFragment);
                        }
                        break;
                    case R.id.main_bottom_rb_down:
                        if (downFragment == null) {
                            downFragment = new DownFragment();
                            fragmentTransaction.add(R.id.main_fl_show, downFragment);
                        } else {
                            fragmentTransaction.show(downFragment);
                        }
                        break;
                    case R.id.main_bottom_rb_me:
                        if (meFragment == null) {
                            meFragment = new MeFragment();
                            fragmentTransaction.add(R.id.main_fl_show, meFragment);
                        } else {
                            fragmentTransaction.show(meFragment);
                        }
                        break;
                }
                fragmentTransaction.commit();
            }
        });
        animation = new RotateAnimation(
                0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
    }
    RotateAnimation animation;
    @Override
    protected void onResume() {
        super.onResume();
        playData=getSharedPreData();
        if (playData.image.length==0){
            imageView_initImage.setBackgroundResource(R.mipmap.ic_play_pause);
        }else {
            if (playData.image.length>0){
                Bitmap imagebitmap= BitmapFactory.decodeByteArray(playData.image,0, playData.image.length);
                imageView_initImage.setImageBitmap(imagebitmap);
                if (MusicConstant.IS_PLAYING){
                    animation.setDuration(3000);
                    // 设置动画重复的次数
                    animation.setRepeatCount(Animation.INFINITE);
                    // 设置动画重复的模式
                    animation.setRepeatMode(Animation.INFINITE);
                    // 设置动画结束以后的状态
                    animation.setFillAfter(false);
                    LinearInterpolator lin = new LinearInterpolator();
                    animation.setInterpolator(lin);
                    imageView_initImage.setAnimation(animation);

                    animation.start();
                }else {
                    if (animation.hasStarted()){
                        animation.cancel();
                    }
                }
            }
        }
    }

    //这个方法的作用是隐藏已经存在的所有的fragment
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (findFragment != null) {
            fragmentTransaction.hide(findFragment);
        }
        if (takeFragment != null) {
            fragmentTransaction.hide(takeFragment);
        }
        if (downFragment != null) {
            fragmentTransaction.hide(downFragment);
        }
        if (meFragment != null) {
            fragmentTransaction.hide(meFragment);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            // 创建退出对话框
//            AlertDialog isExit = new AlertDialog.Builder(this).create();
//            // 设置对话框标题
//            isExit.setTitle("系统提示");
//            // 设置对话框消息
//            isExit.setMessage("确定要退出吗");
//            // 添加选择按钮并注册监听
//            isExit.setButton("确定", listener);
//            isExit.setButton2("取消", listener);
//            // 显示对话框
//            isExit.show();


            // 1. 布局文件转换为View对象
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.back_dialog, null);

// 2. 新建对话框对象
            final Dialog dialog = new android.support.v7.app.AlertDialog.Builder(MainActivity.this).create();
            dialog.setCancelable(false);
            dialog.show();
            dialog.getWindow().setContentView(layout);
            TextView queding = (TextView) layout.findViewById(R.id.back_dialog_queding);
            TextView back_dialog_quxiao = (TextView) layout.findViewById(R.id.back_dialog_quxiao);
            TextView back_dialog_zuixiaohua = (TextView) layout.findViewById(R.id.back_dialog_zuixiaohua);
            queding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.cancel(0);
                    finish();
                }
            });
            back_dialog_quxiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            back_dialog_zuixiaohua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        }

        return false;

    }
//    /**监听对话框里面的button点击事件*/
//    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
//    {
//        public void onClick(DialogInterface dialog, int which)
//        {
//            switch (which)
//            {
//                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
//                    finish();
//                    break;
//                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

SharedPreferences sharedPreferences;

    protected PlayData getSharedPreData(){
        PlayData playData=new PlayData();
        sharedPreferences = getSharedPreferences("Rs_sharePre", Activity.MODE_PRIVATE);
        String string = sharedPreferences.getString("play_pic_content","");
        playData.image = Base64.decode(string.getBytes(), Base64.DEFAULT);
        playData.playUrl32=sharedPreferences.getString("playUrl32","");
        playData.title=sharedPreferences.getString("title","");
        playData.albumId=sharedPreferences.getInt("albumId",0);
        playData.coverLarge=sharedPreferences.getString("coverLarge","");
        playData.nickname=sharedPreferences.getString("nickname","");
        return playData;
    }
}

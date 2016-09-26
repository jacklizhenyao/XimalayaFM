package com.lanou.radiostation.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.radiostation.R;
import com.lanou.radiostation.db.RSDBDao;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;

import cn.sharesdk.framework.ShareSDK;

public class PlayBackActivity extends Activity {

    private ImageButton play_back_down,play_back_timing;
    private ImageView play_back_iv, play_back_iv_01;
    private String picUrl;
    private String title;
    private TextView titleTv;
    private ImageButton play_back_play_ib;
    private String titlebottom;
    private String nickname1;
    private TextView play_back_tv_titlebottom, nickname,play_tv_playing_time,play_tv_zong_time;
    private SeekBar play_back_sb;
    private int albumId;
    private boolean flag=true;
    private boolean flagPlaying=true;
    private EditText play_back_et;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (MusicConstant.DURATION > 1) {
                play_tv_playing_time.setText(formatTime(MusicConstant.PROGRESS));
                play_tv_zong_time.setText(formatTime(MusicConstant.DURATION));
                play_back_sb.setMax(MusicConstant.DURATION);
                play_back_sb.setProgress(MusicConstant.PROGRESS);
            }
            if (MusicConstant.IS_PLAYING){
                play_back_play_ib.setSelected(true);
            }else {
                play_back_play_ib.setSelected(false);
            }
            handler.sendEmptyMessageDelayed(0, 200);
        }
    };
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);
        if (MusicConstant.IS_PLAYING){
           MusicConstant.ISPlay=true;
        }
        picUrl = getIntent().getStringExtra("picUrl");
        title = getIntent().getStringExtra("title");
        titlebottom = getIntent().getStringExtra("titlebottom");
        nickname1 = getIntent().getStringExtra("nickname");
        albumId=getIntent().getIntExtra("albumId",0);
        if (TextUtils.isEmpty(picUrl) && TextUtils.isEmpty(title)) {
            Toast.makeText(PlayBackActivity.this, "空", Toast.LENGTH_SHORT).show();
            return;
        }

        handler.sendEmptyMessage(0);


        initView();
    }
private RSDBDao rsdbDao=new RSDBDao(this);
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicConstant.ISPlay=false;
        MusicConstant.pre_albumID=albumId;
    }

    private void initView() {

        if(!isNetworkConnected(this)){
            Toast.makeText(PlayBackActivity.this,"网络异常，请检查网络！",Toast.LENGTH_LONG).show();
        }
        play_back_timing = (ImageButton) findViewById(R.id.play_back_timing);
        play_back_sb = (SeekBar) findViewById(R.id.play_back_sb);
        play_back_play_ib = (ImageButton) findViewById(R.id.play_back_play_ib);
        nickname = (TextView) findViewById(R.id.nickname);
        titleTv = (TextView) findViewById(R.id.play_title_tv);
        play_back_tv_titlebottom = (TextView) findViewById(R.id.play_back_tv_titlebottom);
        play_back_iv = (ImageView) findViewById(R.id.play_back_iv);
        play_back_iv_01 = (ImageView) findViewById(R.id.play_back_iv_01);
        play_back_down = (ImageButton) findViewById(R.id.play_back_down);
        play_back_et = (EditText) findViewById(R.id.play_back_et);
        play_tv_playing_time = (TextView) findViewById(R.id.play_tv_playing_time);
        play_tv_zong_time = (TextView) findViewById(R.id.play_tv_zong_time);

        ImageLoaderUtils.getImageByloader(picUrl, play_back_iv);
        ImageLoaderUtils.getImageByloader(picUrl, play_back_iv_01);
        titleTv.setText(title);
        play_back_tv_titlebottom.setText(titlebottom);
        nickname.setText(nickname1);
        play_back_iv.setScaleType(ImageView.ScaleType.FIT_XY);

        Log.e("fff",MusicConstant.ISPlay+"");
//        if (MusicConstant.ISPlay) {
//            play_back_play_ib.setChecked(true);
//        } else {
//            play_back_play_ib.setChecked(false);
//        }

        play_back_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayBackActivity.this,SelectPicPopupWindowActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });

      play_back_play_ib.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View view) {
              if (MusicSong.list == null) {
                  Toast.makeText(PlayBackActivity.this, "当前没有选中的音乐", Toast.LENGTH_SHORT).show();
              }else {
                  Log.e("sdsdsd","sdsdsd");
                  Intent intent = new Intent();
                  intent.setAction("haha");
                  if (MusicConstant.IS_PLAYING){
                      intent.putExtra("type", MusicConstant.PAUSE);
                      play_back_play_ib.setSelected(false);
                      MusicConstant.ISPlay = false;
                  }else{
                      intent.putExtra("type", 10);
                      handler.sendEmptyMessage(0);
                      play_back_play_ib.setSelected(true);
                      MusicConstant.ISPlay = true;
                  }
                  sendBroadcast(intent);
              }

          }
      });

        play_back_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    MusicConstant.PROGRESS = i;
                    Intent intentSwitch = new Intent();
                    intentSwitch
                            .setAction("haha");
                    intentSwitch
                            .putExtra("type", 11);
                    sendBroadcast(intentSwitch);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        play_back_timing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayBackActivity.this, TimingActivity.class));

            }
        });

    }

    public String formatTime(int time){
        //对毫秒进行时间的格式化
        if (time / 1000 % 60 < 10) {
            return time/1000/60+":0"+time/1000%60;
        }else{
            return time/1000/60+":"+time/1000%60;
        }

    }

}

package com.lanou.radiostation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.radiostation.R;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;

public class BroadCastPlayActivity extends Activity {

    private TextView tvTitle,play_back_tv_titlebottom,broadcas_play_tv_now,nickname;
    private CheckBox cb;
    private String title;
    private ImageView broadcas_play_iv_bg,broadcas_play_iv,play_back_iv_01;
    private String picUrl;
    private String programName;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast_play);

        title = getIntent().getStringExtra("title");
        picUrl = getIntent().getStringExtra("picUrl");
        programName = getIntent().getStringExtra("programName");
         id=getIntent().getIntExtra("id",0);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (id!=0){
            MusicConstant.getRadio_id_pre=id;
        }

    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.broadcas_play_tv_title);
        cb = (CheckBox) findViewById(R.id.broadcas_play_ib);
        broadcas_play_iv_bg = (ImageView) findViewById(R.id.broadcas_play_iv_bg);
        broadcas_play_iv = (ImageView) findViewById(R.id.broadcas_play_iv);
        play_back_iv_01 = (ImageView) findViewById(R.id.play_back_iv_01);
        play_back_tv_titlebottom = (TextView) findViewById(R.id.play_back_tv_titlebottom);
        nickname = (TextView) findViewById(R.id.nickname);
        broadcas_play_tv_now = (TextView) findViewById(R.id.broadcas_play_tv_now);
        ImageLoaderUtils.getImageByloader(picUrl,broadcas_play_iv_bg);
        ImageLoaderUtils.getImageByloader(picUrl,play_back_iv_01);
        broadcas_play_iv_bg.setAlpha(15);

        tvTitle.setText(title);
        play_back_tv_titlebottom.setText(title);
        broadcas_play_tv_now.setText(programName);
        nickname.setText("正在直播 : "+programName);
        if (!MusicConstant.ISPlay) {
            cb.setChecked(false);
        } else {
            cb.setChecked(true);
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent intent = new Intent();
                intent.setAction("haha");
                if (b) {
                    if (MusicSong.list == null) {
                        Toast.makeText(BroadCastPlayActivity.this, "当前没有选中的音乐", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.putExtra("type", 10);
                        MusicConstant.ISPlay = true;
                    }
                } else {
                    intent.putExtra("type", MusicConstant.PAUSE);
                    MusicConstant.ISPlay = false;
                }
                sendBroadcast(intent);
            }
        });
    }
}

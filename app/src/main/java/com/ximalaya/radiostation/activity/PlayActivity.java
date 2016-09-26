package com.lanou.radiostation.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.BroadRadio;
import com.lanou.radiostation.bean.DownLoad;
import com.lanou.radiostation.bean.PlayBroad;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends Activity {

    List<PlayBroad.Today> list=new ArrayList<>();
    String url;
    private String radioCoverSmall;
    private String rname;
    private String programName;
    private TextView play_rname;
    private ImageView play_radioCoverSmall;
    private TextView play_programName;
    private int radioPlayCount;
    private TextView play_time;
    private TextView play_tv_title,play_player_tv;
    private TextView play_tv_bigtitle,play_time_tv_start,play_time_tv_end;
    private int radioId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        radioCoverSmall =getIntent().getStringExtra("radioCoverSmall");
        rname =getIntent().getStringExtra("rname");
        programName =getIntent().getStringExtra("programName");
        radioPlayCount =getIntent().getIntExtra("radioPlayCount",0);
        radioId =getIntent().getIntExtra("radioId",91);
        url  ="http://live.ximalaya.com/live-web/v1/getProgramSchedules?device=android&radioId=" +
                radioId +
                "&statEvent=pageview/radio@93&statmodule=%E6%8E%A8%E8%8D%90%E7%94%B5%E5%8F%B0&statpage=tab@%E5%8F%91%E7%8E%B0_%E5%B9%BF%E6%92%AD&statposition=1";

        initView();
        getData();
    }

    private void initView() {
        play_player_tv = (TextView) findViewById(R.id.play_player_tv);
        play_time_tv_start= (TextView) findViewById(R.id.play_time_tv_start);
        play_time_tv_end= (TextView) findViewById(R.id.play_time_tv_end);
        play_tv_bigtitle = (TextView) findViewById(R.id.play_tv_bigtitle);
        play_tv_title = (TextView) findViewById(R.id.play_tv_title);
        play_rname = (TextView) findViewById(R.id.play_rname);
        play_programName = (TextView) findViewById(R.id.play_programName);
        play_radioCoverSmall = (ImageView) findViewById(R.id.play_radioCoverSmall);
        play_time= (TextView) findViewById(R.id.play_time);
        play_rname.setText(rname);
        play_programName.setText(programName);
        play_time.setText(radioPlayCount+"人");
        play_tv_title.setText(programName);
        play_tv_bigtitle.setText(rname);
        ImageLoaderUtils.getImageByloader(radioCoverSmall,play_radioCoverSmall);
    }


    public void getData(){
        HttpUtils utils =new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result =responseInfo.result;
               // Toast.makeText(PlayActivity.this,result, Toast.LENGTH_SHORT).show();
                Gson gson =new Gson();
          PlayBroad playbroad= gson.fromJson(result,PlayBroad.class);
                list = playbroad.result.todaySchedules;
                    if (list.get(3).announcerList != null) {
                        play_player_tv.setText("主播:"+list.get(3).announcerList.get(0).announcerName);
                        Toast.makeText(PlayActivity.this, list.get(3).announcerList.get(0).announcerName, Toast.LENGTH_SHORT).show();
                    }else{
                        play_player_tv.setText("主播:未知");
                }
                play_time_tv_start.setText(list.get(0).startTime+"-");
                play_time_tv_end.setText(list.get(0).endTime);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

}

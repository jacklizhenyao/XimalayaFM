package com.lanou.radiostation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.BroadLocaltionAdapter;
import com.lanou.radiostation.bean.BroadLocaltion;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;
import com.lanou.radiostation.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

public class BroadLocaltionAndCountryActivity extends AppCompatActivity {

    private ListView activity_broad_localtion_lv;
    private BroadLocaltionAdapter adapter;
    private List<BroadLocaltion.Datas> list = new ArrayList<>();
    private PullToRefreshView pull;
    private String url;
    private int mark;
    private TextView activity_broad_localtion_tv_title;
    private int pageNum = 1;
    private BroadLocaltion broadLocaltion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_localtion_and_country);

        mark = getIntent().getIntExtra("mark",1);
        initView();
    }

    private void initView() {
        setDataUrl(0);
        activity_broad_localtion_tv_title = (TextView) findViewById(R.id.activity_broad_localtion_tv_title);
        if (mark == 0) {
            activity_broad_localtion_tv_title.setText("本地台");
        }
        else if(mark == 1){
            activity_broad_localtion_tv_title.setText("国家台");
        }
        else if(mark == 2){
            activity_broad_localtion_tv_title.setText("网络台");
        }
        pull = (PullToRefreshView) findViewById(R.id.activity_broad_localtion_pull);
        activity_broad_localtion_lv = (ListView) findViewById(R.id.activity_broad_localtion_lv);
        adapter = new BroadLocaltionAdapter(this,list);
        activity_broad_localtion_lv.setAdapter(adapter);

        pull.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                setDataUrl(2);
                Toast.makeText(BroadLocaltionAndCountryActivity.this, "到底了", Toast.LENGTH_SHORT).show();
            }
        });

        pull.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                setDataUrl(1);
            }
        });
    }

    private void setDataUrl(final int type) {

        switch (type){
            case 0:
                pageNum = 1;
                break;
            case 1:
                pageNum = 1;
                break;
            case 2:
                pageNum ++;
                break;
        }
        if (mark == 0) {
            url = "http://live.ximalaya.com/live-web/v2/radio/province?pageNum=" +
                    pageNum +
                    "&pageSize=20&provinceCode=110000";
        }
        else if(mark == 1){
            url = "http://live.ximalaya.com/live-web/v2/radio/national?pageNum=" +
                    pageNum +
                    "&pageSize=20";
        }
        else if(mark == 2){
            url = "http://live.ximalaya.com/live-web/v2/radio/network?pageNum=" +
                    pageNum +
                    "&pageSize=20";
        }

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String result = responseInfo.result;
                Gson gson = new Gson();
                broadLocaltion = gson.fromJson(result, BroadLocaltion.class);
                switch (type){
                    case 0:
                        list = broadLocaltion.data.data;
                        break;
                    case 1:
                        pull.onHeaderRefreshFinish();
                        list = broadLocaltion.data.data;
                        break;
                    case 2:
                        if (broadLocaltion.data.data == null) {
                            Toast.makeText(BroadLocaltionAndCountryActivity.this, "到底了", Toast.LENGTH_SHORT).show();
                            pull.onFooterLoadFinish();
                            return;
                        }
                        list.addAll(broadLocaltion.data.data);
                        pull.onFooterLoadFinish();
                        break;
                }


                adapter.setList(list);

                MusicSong.list.clear();
                for (int i = 0; i < list.size(); i++) {
                    MusicSong.list.add(list.get(i).playUrl.ts24);
                }

                activity_broad_localtion_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MusicConstant.PLAYING_POSITION = i;
                        Intent intent = new Intent();
                        intent.setAction("haha");
                        intent.putExtra("type", MusicConstant.PLAY_GO_ON);
                        sendBroadcast(intent);

                        Intent intent1 = new Intent(BroadLocaltionAndCountryActivity.this, BroadCastPlayActivity.class);
                        intent1.putExtra("title",list.get(i).name);
                        intent1.putExtra("picUrl",list.get(i).coverSmall);
                        intent1.putExtra("programName",list.get(i).programName);
                        startActivity(intent1);

                        MusicSong.tag = "guangbo";
                        MusicConstant.ISPlay = true;
                    }
                });



            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}

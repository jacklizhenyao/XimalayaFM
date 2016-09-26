package com.lanou.radiostation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.ActivityBroadAdapter;
import com.lanou.radiostation.bean.ActivityBroad;
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

public class BroadActivity extends AppCompatActivity {

    private String url;
    private int id;
    private ListView activity_broad_lv;
    private List<ActivityBroad.Datas> list = new ArrayList<>();
    private ActivityBroadAdapter adapter;
    private PullToRefreshView pull;
    private int pageNum = 1;
    private ActivityBroad activityBroad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_braod);
        id = getIntent().getIntExtra("id", 5);


        initView();
    }

    private void initView() {

        setDataUrl(0);
        pull = (PullToRefreshView) findViewById(R.id.activity_broad_pull);
        activity_broad_lv = (ListView) findViewById(R.id.activity_broad_lv);
        adapter = new ActivityBroadAdapter(this,list);
        activity_broad_lv.setAdapter(adapter);

        pull.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                setDataUrl(2);
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
                pageNum++;
                break;

        }
        url = "http://live.ximalaya.com/live-web/v2/radio/category?categoryId=" +
                id +
                "&pageNum=" +
                pageNum +
                "&pageSize=20";

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                activityBroad = gson .fromJson(result, ActivityBroad.class);
                switch (type){
                    case 0:
                        list = activityBroad.data.data;
                        break;
                    case 1:
                        pull.onHeaderRefreshFinish();
                        list = activityBroad.data.data;
                        break;
                    case 2:
                        if (activityBroad.data.data == null) {
                            Toast.makeText(BroadActivity.this, "到底了", Toast.LENGTH_SHORT).show();
                            pull.onFooterLoadFinish();
                            return;
                        }

                        list.addAll(activityBroad.data.data);
                        pull.onFooterLoadFinish();
                        break;
                }
                adapter.setList(list);
                MusicSong.list.clear();
                for (int i = 0; i < list.size(); i++) {
                    MusicSong.list.add(list.get(i).playUrl.ts24);
                }

                activity_broad_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MusicConstant.PLAYING_POSITION = i;
                        Intent intent = new Intent();
                        intent.setAction("haha");
                        intent.putExtra("type", MusicConstant.PLAY_GO_ON);
                        sendBroadcast(intent);

                        Intent intent1 = new Intent(BroadActivity.this, BroadCastPlayActivity.class);
                        intent1.putExtra("title",list.get(i).name);
                        intent1.putExtra("picUrl",list.get(i).coverSmall);
                        intent1.putExtra("programName",list.get(i).programName);
                        startActivity(intent1);
                        MusicConstant.ISPlay = true;

                        MusicSong.tag = "guangbo";
                    }
                });


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}

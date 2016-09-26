package com.lanou.radiostation.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.MyBroadcastAdapter;
import com.lanou.radiostation.bean.Radio;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BroadcastActivity extends Activity {

private ListView allbroadcast_lv;
    private MyBroadcastAdapter adapter;
    private List<Radio.Result>list;
    private TextView tvTitle;
    private ImageButton include_ib_back;
    String url;
    private int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        a = getIntent().getIntExtra("a" , 0);
        if (a == 1) {
            url =  "http://live.ximalaya.com/live-web/v1/" +
                    "getRadiosListByType?pageNum=1&radioType=1&device=android&pageSize=15";
        }else{
            url = "http://live.ximalaya.com/live-web/v1/getRadiosListByType?pageNum=1&radio" +
                    "Type=2&device=android&provinceCode=110000&pageSize=15";
        }

        initView();
        setData();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.include_tv_title);
        allbroadcast_lv = (ListView) findViewById(R.id.allbroadcast_lv);

        allbroadcast_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent =new Intent(BroadcastActivity.this,PlayActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
            }
        });


        include_ib_back= (ImageButton) findViewById(R.id.include_ib_back);
        include_ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (a == 0) {
            tvTitle.setText("本地台");
        }else if(a == 1){
            tvTitle.setText("国家台");
        }

        else if(a == 3 ){
            tvTitle.setText("网络台");
        }
        adapter = new MyBroadcastAdapter(BroadcastActivity.this,list);
        list = new ArrayList<>();
        allbroadcast_lv.setAdapter(adapter);

    }
public void setData() {
    HttpUtils utils = new HttpUtils();
    utils.send(HttpMethod.GET, url, new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            String result = responseInfo.result;
            Gson gson = new Gson();
            Radio radio = gson.fromJson(result, Radio.class);
            for (int i = 0; i < radio.result.size(); i++) {
                list.add(radio.result.get(i));
            }
            adapter.setList(list);
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    });


}


}

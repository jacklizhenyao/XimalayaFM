package com.lanou.radiostation.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.MyBroadcastAdapter;
import com.lanou.radiostation.adapter.SimpleAdapter;
import com.lanou.radiostation.bean.City;
import com.lanou.radiostation.bean.Radio;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends Activity {
    private TextView include_tv_title;
    private ImageButton include_ib_back;
//下面的listview
    private List<Radio.Result> list =new ArrayList<>();
    private ListView city_all_lv;
    private  String  Url;
    private MyBroadcastAdapter adapter;
    //横向的RecyclerView
    private RecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<>();
    private SimpleAdapter mAdapter;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
    }

    private void initView() {
        Url ="http://live.ximalaya.com/live-web/v1/getRadiosListByType?pageNum=" +
                "1&radioType=2&device=android&provinceCode=110000&pageSize=15";
        url = "http://live.ximalaya.com/live-web/v1/getProvinceList";
        mRecyclerView = (RecyclerView) findViewById(R.id.city_recycle);
        city_all_lv = (ListView) findViewById(R.id.city_all_lv);
        include_ib_back = (ImageButton) findViewById(R.id.include_ib_back);
        include_ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        include_tv_title = (TextView) findViewById(R.id.include_tv_title);
        include_tv_title.setText("省市台");
        setDataUrl();

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        //list绑定
        adapter =new MyBroadcastAdapter(CityActivity.this,list);
        city_all_lv.setAdapter(adapter);

//recy的绑定
        mAdapter = new SimpleAdapter(this,mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setDataUrl() {
        HttpUtils utis = new HttpUtils();
        utis.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                   String result = responseInfo.result;
                Gson gson = new Gson();
                City city =  gson.fromJson(result, City.class);
                for (int i = 0; i < city.result.size(); i++) {
                    mDatas.add(city.result.get(i).provinceName);
                }
                mAdapter = new SimpleAdapter(CityActivity.this,mDatas);
                mRecyclerView.setAdapter(mAdapter);
              //  Toast.makeText(CityActivity.this, result, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

        HttpUtils utils  =new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, Url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
               Gson gson =new Gson();
               Radio radio = gson.fromJson(result,Radio.class);
                for (int i = 0; i <radio.result.size() ; i++) {
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

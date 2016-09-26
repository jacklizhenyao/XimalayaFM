package com.lanou.radiostation.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.MyAnchorAdapter;
import com.lanou.radiostation.bean.Album;
import com.lanou.radiostation.bean.Ancher;
import com.lanou.radiostation.view.PullToRefreshView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个主播的fragment
 * Created by user on 2016/7/22.
 */
public class AnchorFragment extends BaseFragment {

    private ListView anchor_lv;
    private List<Ancher.Data> list;
    private MyAnchorAdapter anchorAdapter;
    private List<String> listUrl= new ArrayList<>();

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.anchor_list, null);
        anchor_lv = (ListView) view.findViewById(R.id.anchor_lv);
        list = new ArrayList<>();
        anchorAdapter = new MyAnchorAdapter(list, getActivity());
        return view;
    }

    @Override
    public void setData() {
        getNetData();

        anchor_lv.setAdapter(anchorAdapter);

    }

    private void getNetData() {

        String url = "http://mobile.ximalaya.com/m/explore_user_index?device=android&page=1";
        String urlt = "http://mobile.ximalaya.com/m/explore_user_index?device=android&page=2";
        String urlth = "http://mobile.ximalaya.com/m/explore_user_index?device=android&page=3";
        //xUtils的api,网络请求工具类
        com.lidroid.xutils.HttpUtils utils = new com.lidroid.xutils.HttpUtils();

		/*
         * 发出请求并且回调
		 * 参数1：请求方式
		 * 	   2：请求地址
		 *     3：回调接口
		 */
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                Gson gson = new Gson();
                Ancher ancher = gson.fromJson(result, Ancher.class);
                    for (int i = 0; i < ancher.list.size(); i++) {
                        list.add(ancher.list.get(i));
                    }
                anchorAdapter.setList(list);
            }
            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }



}

package com.lanou.radiostation.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.AllClassifyAdapter;
import com.lanou.radiostation.bean.AllClassifyTitle;
import com.lanou.radiostation.fragment.AllclassifyFragment;
import com.lanou.radiostation.fragment.AllclassifyFragment_01;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class AllClassfiyActivity extends FragmentActivity {

    private int id;
    private String url;
    private RecyclerView allclassify_recyclerView;
    private String result;
    private ImageView ivSearch;
    private List<Integer> keywordIdList = new ArrayList<>();

    public static int count;

    private List<String> mDatas = new ArrayList<>();
    private AllClassifyAdapter mAdapter;
    private AllClassifyTitle allClassifyTitle;
    private ImageButton shareIb;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private AllclassifyFragment fragment;
    private AllclassifyFragment_01 fragment_01;
    private TextView tvTitle;
    private ImageButton include_ib_back;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(getApplicationContext());
        setContentView(R.layout.activity_all_classfiy);

        include_ib_back = (ImageButton) findViewById(R.id.include_ib_back);
        tvTitle = (TextView) findViewById(R.id.include_tv_title);
        shareIb = (ImageButton) findViewById(R.id.include_share);

        title = getIntent().getStringExtra("title");
        supportFragmentManager = getSupportFragmentManager();
        include_ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        shareIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(AllClassfiyActivity.this, "分享", Toast.LENGTH_SHORT).show();

                //一键分享功能
                OnekeyShare oneKeyShare = new OnekeyShare();
                //设置分享的标题
                oneKeyShare.setTitle(title);
                //设置信息
                oneKeyShare.setText("听喜马拉雅");

                oneKeyShare.setTitleUrl("http://www.ximalaya.com/explore/");
                //设置图标
//				oneKeyShare.setImagePath(imagePath);
//
                //显示分享列表
                oneKeyShare.show(AllClassfiyActivity.this);

            }
        });

        id = getIntent().getIntExtra("id", 0);

        tvTitle.setText(getIntent().getStringExtra("title"));
       url = "http://mobile.ximalaya.com/mobile/discovery/v3/category/recommends?categoryId=" +
               id +
               "&contentType=album&device=android&version=5.4.21";

        initView();
    }

    private void initView() {
        setDataUrl();
        allclassify_recyclerView = (RecyclerView) findViewById(R.id.allclassify_recyclerView);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL, false);
        allclassify_recyclerView.setLayoutManager(linearLayoutManager);



        allclassify_recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setDataUrl() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                result = responseInfo.result;

                Gson gson = new Gson();
                allClassifyTitle = gson.fromJson(result, AllClassifyTitle.class);

                mDatas.add("推荐");
                for (int i = 0; i < allClassifyTitle.keywords.list.size(); i++) {
                    mDatas.add(allClassifyTitle.keywords.list.get(i).keywordName);

                }

                for (int i = 0; i < allClassifyTitle.keywords.list.size(); i++) {
                    keywordIdList.add(allClassifyTitle.keywords.list.get(i).keywordId);
                }

                mAdapter = new AllClassifyAdapter(AllClassfiyActivity.this,mDatas);
                allclassify_recyclerView.setAdapter(mAdapter);

                fragment_01 = new AllclassifyFragment_01(id);
                fragmentTransaction = supportFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.allclassify_fl, fragment_01);
                fragmentTransaction.commit();

                mAdapter.setOnItemClickListener(new AllClassifyAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position) {

                        if (position == 0) {
                            fragment_01 = new AllclassifyFragment_01(id);
                            fragmentTransaction = supportFragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.allclassify_fl, fragment_01);
                            fragmentTransaction.commit();
                        }else if(position > 0){
                            fragment = new AllclassifyFragment(id,keywordIdList.get(position - 1), allClassifyTitle.keywords.list.get(position - 1).keywordName);
                            fragmentTransaction = supportFragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.allclassify_fl, fragment);
                            fragmentTransaction.commit();
                        }

                    }

                    @Override
                    public void OnItemLongClick(View view, int position) {

                    }
                });

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        count = 0;
    }
}

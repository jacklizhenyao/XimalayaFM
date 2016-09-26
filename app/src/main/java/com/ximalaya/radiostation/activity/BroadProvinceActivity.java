package com.lanou.radiostation.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.MyAdapter;
import com.lanou.radiostation.bean.TabLayoutTitle;
import com.lanou.radiostation.fragment.MyFragment;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.TitleLayout;

public class BroadProvinceActivity extends AppCompatActivity {

    /**
     * TabLayout继承与HorizontalScrollView ，结合ViewPager使用展示
     * 控件的使用无外乎几大步骤：
     * 1、在布局文件里建标签 ，并在代码中实例化控件  TabLayout和ViewPager
     * 2、设置要添加的数据，在这要展示Fragment
     * 3、ViewPage的适配器
     * 4、ViewPager绑定适配器
     * 5、TabLayout和ViewPager绑定
     */
    private TabLayout tabLayout;
    private ViewPager mViewPgeer;
    private List<String> titles;
    private List<Fragment> list;
    private MyAdapter mAdapter;
    private String[] titles1 = new String[100];
    private int [] titles2 = new int[100];
    private TabLayoutTitle tabLayoutTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_province);


        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, "http://live.ximalaya.com/live-web/v1/getProvinceList", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                tabLayoutTitle = gson.fromJson(result, TabLayoutTitle.class);
                for (int i = 0; i < tabLayoutTitle.result.size(); i++) {
//                    titles1[i] = tabLayoutTitle.result.get(0).provinceName;
                      titles1[i] =tabLayoutTitle.result.get(i).provinceName;
                      titles2[i] =tabLayoutTitle.result.get(i).provinceCode;

                }
                init();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    //功能代码区
    public void init() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPgeer = (ViewPager) findViewById(R.id.viewPager);
        // tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setData();
        mAdapter = new MyAdapter(getSupportFragmentManager(), titles, list);
        mViewPgeer.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPgeer);
        // tabLayout.setTabsFromPagerAdapter(mAdapter);

    }
    //添加数据
    public void setData() {
        titles = new ArrayList<>();
        list = new ArrayList<>();
        for (int i = 0; i < tabLayoutTitle.result.size(); i++) {
            titles.add(titles1[i]);//标题的集合
            list.add(MyFragment.newInstance(titles2[i]));//Activit向Fragment传值
        }
    }
}

package com.lanou.radiostation.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.adapter.AllClassBaseAdapter_01;
import com.lanou.radiostation.adapter.AllClassPagerAdapter;
import com.lanou.radiostation.bean.Allclassify_01;
import com.lanou.radiostation.bean.Allclassify_01_lv;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lanou.radiostation.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/29.
 */
public class AllclassifyFragment_01 extends BaseFragment{

    String url;
    private Allclassify_01 allclassify_01;
    private ViewPager allClassifyVp;
    private List<ImageView> vpList = new ArrayList<>();
    private AllClassPagerAdapter vpAdapter;


    private ListView allClassifyLv;

    private List<Allclassify_01_lv.Data> lvList = new ArrayList<>();
    private AllClassBaseAdapter_01 lvAdapter;
    private View view;
    private View viewHead;
    private PullToRefreshView pull;

    String lvUrl;


    int id;
    private int pageId;

    public AllclassifyFragment_01(int id){
        this.id = id;
    }

    @Override
    public View initView() {
        url = "http://mobile.ximalaya.com/mobile/discovery/v3/category/recommends?categoryId=" +
                id +
                "&contentType=album&device=android&version=5.4.21";

        view = View.inflate(getActivity(), R.layout.frag_allcallify_01,null);
        initViews(view);
        return view;

    }

    private void initViews(View view) {
        setDataUrl();
        setDataLv(0);
        pull = (PullToRefreshView) view.findViewById(R.id.frag_allclassify_01_pull);
        viewHead = View.inflate(getActivity(),R.layout.frag_allcallify_01_head,null);
        allClassifyLv = (ListView) view.findViewById(R.id.frag_allclassify_01_lv);
        allClassifyVp = (ViewPager) viewHead.findViewById(R.id.frag_allclassify_01_head_vp);

        allClassifyLv.addHeaderView(viewHead);
        lvAdapter = new AllClassBaseAdapter_01(lvList,getActivity());

        allClassifyLv.setAdapter(lvAdapter);


        pull.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                setDataLv(1);
            }
        });

        pull.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                setDataLv(2);
            }
        });

    }

    private void setDataUrl() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                Gson gson = new Gson();
                allclassify_01 = gson.fromJson(result, Allclassify_01.class);
                for (int i = 0; i < allclassify_01.focusImages.list.size(); i++) {
                    ImageView iv = new ImageView(getActivity());
                    ImageLoaderUtils.getImageByloader(allclassify_01.focusImages.list.get(i).pic,iv);
                    vpList.add(iv);
                }

                vpAdapter = new AllClassPagerAdapter(vpList);
                allClassifyVp.setAdapter(vpAdapter);

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }


    private void setDataLv(final int type){
        switch (type){
            case 0:
                pageId = 1;
                break;
            case 1:
                pageId = 1;
                break;
            case 2:
                pageId ++;
                break;
        }
        lvUrl = "http://mobile.ximalaya.com/mobile/discovery/v2/category/metadata/albums?calcDimension=hot&categoryId=" +
                id +
                "&device=android&pageId=" +
                pageId +
                "&pageSize=20&version=5.4.2";


        HttpUtils utils1 = new HttpUtils();
        utils1.send(HttpRequest.HttpMethod.GET, lvUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                Gson gson = new Gson();
                Allclassify_01_lv allclassify_01_lv = gson.fromJson(result, Allclassify_01_lv.class);


                switch (type){
                    case 0:
                        lvList = allclassify_01_lv.list;
                        break;
                    case 1:
                        lvList = allclassify_01_lv.list;
                        pull.onHeaderRefreshFinish();
                        break;
                    case 2:
                        lvList.addAll(allclassify_01_lv.list);
                        pull.onFooterLoadFinish();
                        break;
                }

                if(lvList.size() < 0||lvList == null){
                    Toast.makeText(getActivity(), "当前数据不完整", Toast.LENGTH_SHORT).show();
                    return;
                }

                lvAdapter.setListener(lvList);

                allClassifyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity(), AlbumActivity.class);
                        intent.putExtra("picUrl",lvList.get(i - 1).albumCoverUrl290);
                        intent.putExtra("title",lvList.get(i -1 ).title);
                        intent.putExtra("titleName",lvList.get(i -1).title);
                        intent.putExtra("albumId",lvList.get(i -1).albumId);
                        intent.putExtra("position",1);
                        intent.putExtra("playsCounts",lvList.get(i -1).playsCounts);
                        intent.putExtra("nickname",lvList.get(i -1).nickname);
                        getActivity().startActivity(intent);
                    }
                });


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });



    }

    @Override
    public void setData() {

    }
}

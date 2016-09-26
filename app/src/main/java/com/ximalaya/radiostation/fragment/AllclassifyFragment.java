package com.lanou.radiostation.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.adapter.MyClassifyAdapter;
import com.lanou.radiostation.adapter.MyFragClassifyAdapter;
import com.lanou.radiostation.bean.FragClassify;
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
public class AllclassifyFragment extends BaseFragment {


    int keywordId;
    String title;
    int id;
    String url;



    private ListView allClassfiyListView;
    private MyFragClassifyAdapter lvAdapter;
    private List<FragClassify.Data> list = new ArrayList<>();
    private FragClassify fragClassify;
    private PullToRefreshView pull;
    private int pageId;
    private int type;
    private int pageSize;


    public AllclassifyFragment(int id, int keywordId, String title) {
        this.keywordId = keywordId;
        this.title = title;
        this.id = id;
    }

    @Override
    public View initView() {


        View view = View.inflate(getActivity(), R.layout.frag_allcallify, null);
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        setDataUrl(0);
        pull = (PullToRefreshView) view.findViewById(R.id.frag_allclassify_pull);
        allClassfiyListView = (ListView) view.findViewById(R.id.frag_allclassify_lv);
        lvAdapter = new MyFragClassifyAdapter(getActivity(),list);
        allClassfiyListView.setAdapter(lvAdapter);


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
                pageId = 1;
                break;
            case 1:
                pageId = 1;
                break;
            case 2:
                pageId ++ ;
                break;
        }

        url = "http://mobile.ximalaya.com/mobile/discovery/v2/category/keyword/albums?" +
                "calcDimension=hot&categoryId=" +
                id +
                "&device=android&keywordId=" +
                keywordId +
                "&pageId=" +
                pageId +
                "&pageSize=" +
                20 +
                "&status=0&version=5.4.21";

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                fragClassify = gson.fromJson(result, FragClassify.class);
                switch (type){
                    case 0:
                        list = fragClassify.list;
                        break;
                    case 1:
                        list = fragClassify.list;
                        pull.onHeaderRefreshFinish();
                        break;
                    case 2:
                        list.addAll(fragClassify.list);

                        pull.onFooterLoadFinish();
                        break;
                }
                lvAdapter.setListener(list);

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    public void setData() {
        allClassfiyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), AlbumActivity.class);
                intent.putExtra("picUrl",list.get(i).albumCoverUrl290);
                intent.putExtra("title",list.get(i).title);
                intent.putExtra("titleName",list.get(i).title);
                intent.putExtra("albumId",list.get(i).albumId);
                intent.putExtra("position",1);
                intent.putExtra("playsCounts",list.get(i).playsCounts);
                intent.putExtra("nickname",list.get(i).nickname);
                getActivity().startActivity(intent);
            }
        });

    }
}

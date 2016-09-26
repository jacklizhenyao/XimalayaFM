package com.lanou.radiostation.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.activity.SearchActivity;
import com.lanou.radiostation.adapter.MySearchBaseAdapter;
import com.lanou.radiostation.adapter.MySearchHeadBaseAdapter;
import com.lanou.radiostation.bean.Search;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by user on 2016/7/29.
 */
public class SearchFragment extends BaseFragment implements SearchActivity.switchUrl {

    private ListView searchLv;
    private MySearchBaseAdapter adapter;
    private List<Search.QueryResultList> list = new ArrayList<>();
    private Search search;
    private LinearLayout ll01, ll02;
    ImageView iv1, iv2;
    TextView tvcontent01, tvcontent02, tvTitle01, tvTitle02;

    @Override
    public View initView() {

        View view = View.inflate(getActivity(), R.layout.frag_search, null);
        initViews(view);
        return view;
    }


    private void initViews(View view) {

        tvcontent01 = (TextView) view.findViewById(R.id.search_tv_content_01);
        tvcontent02 = (TextView) view.findViewById(R.id.search_tv_content_02);
        tvTitle01 = (TextView) view.findViewById(R.id.search_tv_title_01);
        tvTitle02 = (TextView) view.findViewById(R.id.search_tv_title_02);
        iv1 = (ImageView) view.findViewById(R.id.search_iv_01);
        iv2 = (ImageView) view.findViewById(R.id.search_iv_02);
        ll01 = (LinearLayout) view.findViewById(R.id.search_ll_01);
        ll02 = (LinearLayout) view.findViewById(R.id.search_ll_02);
        searchLv = (ListView) view.findViewById(R.id.fragment_search_lv);
        ((SearchActivity) getActivity()).setOnSwitchUrl(this);

    }


    public void setDataNet(String urlSearch) {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, urlSearch, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();

                search = gson.fromJson(result, Search.class);
                if (search.albumResultList.size() > 0 && search.albumResultList != null) {
                    if (search.albumResultList.size() == 1) {
                        ll01.setVisibility(View.VISIBLE);
                        tvTitle01.setText(search.albumResultList.get(0).category);
                        tvcontent01.setText(search.albumResultList.get(0).keyword);
                        ImageLoaderUtils.getImageByloader(search.albumResultList.get(0).imgPath, iv1);
                        ll02.setVisibility(View.GONE);

                        ll01.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), AlbumActivity.class);
                                intent.putExtra("picUrl", search.albumResultList.get(0).imgPath);
                                intent.putExtra("albumId", search.albumResultList.get(0).id);
                                intent.putExtra("title", search.albumResultList.get(0).category);
                                intent.putExtra("position", 1);
                                intent.putExtra("titleName", search.albumResultList.get(0).category);
                                intent.putExtra("nickname", search.albumResultList.get(0).highlightKeyword);
                                intent.putExtra("playsCounts", 80000);
                                getActivity().startActivity(intent);
                            }
                        });


                    } else if (search.albumResultList.size() == 2) {
                        ll01.setVisibility(View.VISIBLE);
                        tvTitle01.setText(search.albumResultList.get(0).category);
                        tvcontent01.setText(search.albumResultList.get(0).keyword);
                        ImageLoaderUtils.getImageByloader(search.albumResultList.get(0).imgPath, iv1);
                        ll02.setVisibility(View.VISIBLE);
                        tvTitle02.setText(search.albumResultList.get(1).category);
                        tvcontent02.setText(search.albumResultList.get(1).keyword);
                        ImageLoaderUtils.getImageByloader(search.albumResultList.get(1).imgPath, iv2);

                        ll01.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), AlbumActivity.class);
                                intent.putExtra("picUrl", search.albumResultList.get(0).imgPath);
                                intent.putExtra("albumId", search.albumResultList.get(0).id);
                                intent.putExtra("title", search.albumResultList.get(0).category);
                                intent.putExtra("position", 1);
                                intent.putExtra("titleName", search.albumResultList.get(0).category);
                                intent.putExtra("nickname", search.albumResultList.get(0).highlightKeyword);
                                intent.putExtra("playsCounts", 80000);
                                getActivity().startActivity(intent);
                            }
                        });
                        ll02.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), AlbumActivity.class);
                                intent.putExtra("picUrl", search.albumResultList.get(1).imgPath);
                                intent.putExtra("albumId", search.albumResultList.get(1).id);
                                intent.putExtra("title", search.albumResultList.get(1).category);
                                intent.putExtra("position", 1);
                                intent.putExtra("titleName", search.albumResultList.get(1).category);
                                intent.putExtra("nickname", search.albumResultList.get(1).highlightKeyword);
                                intent.putExtra("playsCounts", 80000);
                                getActivity().startActivity(intent);
                            }
                        });


                    }

                } else {
                    ll01.setVisibility(View.GONE);
                    ll02.setVisibility(View.GONE);
                }

                adapter = new MySearchBaseAdapter(getActivity(), search.queryResultList);
                searchLv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                searchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


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

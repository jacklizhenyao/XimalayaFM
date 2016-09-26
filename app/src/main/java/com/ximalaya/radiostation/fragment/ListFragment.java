package com.lanou.radiostation.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.ListWebActivity;
import com.lanou.radiostation.adapter.ListFragAdapter;
import com.lanou.radiostation.adapter.ListFragVpAdapter;
import com.lanou.radiostation.bean.ListFrag;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是榜单的fragment
 * Created by user on 2016/7/22.
 */
public class ListFragment extends BaseFragment{

    private ListView listLv;
    private ListFragAdapter lvAdapter;
    private List<ListFrag.News> lvList = new ArrayList<>();
    private String url;
    private ProgressDialog pd;
    private View viewHead;
    private ImageView iv,frag_list_footer_iv_01,frag_list_footer_iv_02,frag_list_footer_iv_03,frag_list_footer_iv_04;
    private View viewFooter;
    private TextView frag_list_tv_footer_title_01,frag_list_tv_footer_title_02,frag_list_tv_footer_title_03,frag_list_tv_footer_title_04,
                     frag_list_footer_tv_01_01,frag_list_footer_tv_01_02,frag_list_footer_tv_01_03,frag_list_footer_tv_01_04,
                     frag_list_footer_tv_02_01,frag_list_footer_tv_02_02,frag_list_footer_tv_02_03,frag_list_footer_tv_02_04;



    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.frag_list,null);
        viewHead = View.inflate(getActivity(),R.layout.frag_list_head,null);
        viewFooter = View.inflate(getActivity(), R.layout.frag_list_footer, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        setDataUrl();
        pd = new ProgressDialog(getActivity());
        pd.setMessage("正在加载中");
        pd.show();
        listLv = (ListView) view.findViewById(R.id.frag_list_lv);
        iv = (ImageView) viewHead.findViewById(R.id.frag_list_head_iv);
        frag_list_footer_iv_01 = (ImageView) viewFooter.findViewById(R.id.frag_list_footer_iv_01);
        frag_list_footer_iv_02 = (ImageView) viewFooter.findViewById(R.id.frag_list_footer_iv_02);
        frag_list_footer_iv_03 = (ImageView) viewFooter.findViewById(R.id.frag_list_footer_iv_03);
        frag_list_footer_iv_04 = (ImageView) viewFooter.findViewById(R.id.frag_list_footer_iv_04);

        frag_list_tv_footer_title_01 = (TextView) viewFooter.findViewById(R.id.frag_list_tv_footer_title_01);
        frag_list_tv_footer_title_02 = (TextView) viewFooter.findViewById(R.id.frag_list_tv_footer_title_02);
        frag_list_tv_footer_title_03 = (TextView) viewFooter.findViewById(R.id.frag_list_tv_footer_title_03);
        frag_list_tv_footer_title_04 = (TextView) viewFooter.findViewById(R.id.frag_list_tv_footer_title_04);

        frag_list_footer_tv_01_01 = (TextView) viewFooter.findViewById(R.id.frag_list_footer_tv_01_01);
        frag_list_footer_tv_01_02 = (TextView) viewFooter.findViewById(R.id.frag_list_footer_tv_01_02);
        frag_list_footer_tv_01_03 = (TextView) viewFooter.findViewById(R.id.frag_list_footer_tv_01_03);
        frag_list_footer_tv_01_04 = (TextView) viewFooter.findViewById(R.id.frag_list_footer_tv_01_04);

        frag_list_footer_tv_02_01 = (TextView) viewFooter.findViewById(R.id.frag_list_footer_tv_02_01);
        frag_list_footer_tv_02_02 = (TextView) viewFooter.findViewById(R.id.frag_list_footer_tv_02_02);
        frag_list_footer_tv_02_03 = (TextView) viewFooter.findViewById(R.id.frag_list_footer_tv_02_03);
        frag_list_footer_tv_02_04 = (TextView) viewFooter.findViewById(R.id.frag_list_footer_tv_02_04);

    }

    private void setDataUrl() {
        url = "http://mobile.ximalaya.com/mobile/discovery/v2/rankingList/group?channel=and-a1&device=android&includeActivity=true&includeSpecial=true&scale=2&version=5.4.21";
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                pd.dismiss();
                String result = responseInfo.result;

                Gson gson = new Gson();
                ListFrag listFrag = gson.fromJson(result, ListFrag.class);
                lvList = listFrag.datas.get(0).list;


                ImageLoaderUtils.getImageByloader(listFrag.focusImages.list.get(0).pic,iv);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                listLv.addHeaderView(viewHead);

                ImageLoaderUtils.getImageByloader(listFrag.datas.get(1).list.get(0).coverPath,frag_list_footer_iv_01);
                ImageLoaderUtils.getImageByloader(listFrag.datas.get(1).list.get(1).coverPath,frag_list_footer_iv_02);
                ImageLoaderUtils.getImageByloader(listFrag.datas.get(1).list.get(2).coverPath,frag_list_footer_iv_03);
                ImageLoaderUtils.getImageByloader(listFrag.datas.get(1).list.get(3).coverPath,frag_list_footer_iv_04);

                frag_list_tv_footer_title_01.setText(listFrag.datas.get(1).list.get(0).title);
                frag_list_tv_footer_title_02.setText(listFrag.datas.get(1).list.get(1).title);
                frag_list_tv_footer_title_03.setText(listFrag.datas.get(1).list.get(2).title);
                frag_list_tv_footer_title_04.setText(listFrag.datas.get(1).list.get(3).title);


                frag_list_footer_tv_01_01.setText(listFrag.datas.get(1).list.get(0).firstKResults.get(0).title);
                frag_list_footer_tv_01_02.setText(listFrag.datas.get(1).list.get(1).firstKResults.get(0).title);
                frag_list_footer_tv_01_03.setText(listFrag.datas.get(1).list.get(2).firstKResults.get(0).title);
                frag_list_footer_tv_01_04.setText(listFrag.datas.get(1).list.get(3).firstKResults.get(0).title);

                frag_list_footer_tv_02_01.setText(listFrag.datas.get(1).list.get(0).firstKResults.get(1).title);
                frag_list_footer_tv_02_02.setText(listFrag.datas.get(1).list.get(1).firstKResults.get(1).title);
                frag_list_footer_tv_02_03.setText(listFrag.datas.get(1).list.get(2).firstKResults.get(1).title);
                frag_list_footer_tv_02_04.setText(listFrag.datas.get(1).list.get(3).firstKResults.get(1).title);


                listLv.addFooterView(viewFooter);


                lvAdapter = new ListFragAdapter(getActivity(),lvList);
                listLv.setAdapter(lvAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    @Override
    public void setData() {

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListWebActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}

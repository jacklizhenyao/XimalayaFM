package com.lanou.radiostation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.MyParticularsAdapter;
import com.lanou.radiostation.bean.Particulars;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

public class ParticularsActivity extends AppCompatActivity {


    private ListView mListView;
    private MyParticularsAdapter mLvAdapter;
    private List<Particulars.Data> mLvList = new ArrayList<>();
    private View viewHead,viewFooter;
    private int specialId;
    private String url;
    private TextView particulars_tv_01,particulars_tv_03,particulars_footer_tv_title,
            particulars_footer_tv_content;
    ImageView particulars_iv_04,particulars_footer_iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);

        specialId = getIntent().getIntExtra("specialId",0);


            url = "http://mobile.ximalaya.com/m/subject_detail?device=android&id=" +
                    specialId +
                    "&statEvent=pageview%2Fsubject%40706&statModule=%E7%B2%BE%E5%93%81%E5%90%AC%E5%8D%95&statPage=tab%40%E5%8F%91%E7%8E%B0_%E6%8E%A8%E8%8D%90&statPosition=1";

        initView();
    }

    private void initView() {
        setDataUrl();
        mListView = (ListView) findViewById(R.id.particulars_lv);
        viewHead = View.inflate(ParticularsActivity.this,R.layout.activity_particulars_head,null);
        particulars_tv_01 = (TextView) viewHead.findViewById(R.id.particulars_tv_01);
        particulars_tv_03 = (TextView) viewHead.findViewById(R.id.particulars_tv_03);
        particulars_iv_04 = (ImageView) viewHead.findViewById(R.id.particulars_iv_04);

        viewFooter = View.inflate(this,R.layout.activity_particulars_footer,null);
        particulars_footer_iv = (ImageView) viewFooter.findViewById(R.id.particulars_footer_iv);
        particulars_footer_tv_title = (TextView) viewFooter.findViewById(R.id.particulars_footer_tv_title);
        particulars_footer_tv_content = (TextView) viewFooter.findViewById(R.id.particulars_footer_tv_content);

    }

    private void setDataUrl() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String result = responseInfo.result;
                Gson gson = new Gson();
                Particulars particulars = gson.fromJson(result, Particulars.class);
                particulars_tv_01.setText(particulars.info.title);
                particulars_tv_03.setText(particulars.info.intro);
                ImageLoaderUtils.getImageByloader(particulars.info.smallLogo,particulars_iv_04);
                ImageLoaderUtils.getImageByloader(particulars.info.smallLogo,particulars_footer_iv);
                particulars_footer_tv_title.setText(particulars.info.nickname+" : ");
                particulars_footer_tv_content.setText(particulars.info.personalSignature);

                mLvList = particulars.list;
                mLvAdapter = new MyParticularsAdapter(ParticularsActivity.this,mLvList);

                mListView.addHeaderView(viewHead);
                mListView.addFooterView(viewFooter);
                mListView.setAdapter(mLvAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}

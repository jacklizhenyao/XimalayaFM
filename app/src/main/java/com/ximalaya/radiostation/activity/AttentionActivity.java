package com.lanou.radiostation.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.MyAttentionAdapter;
import com.lanou.radiostation.bean.Attention;
import com.lanou.radiostation.bean.AttentionHead;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lanou.radiostation.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.factory.BitmapFactory;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AttentionActivity extends Activity {
    private ListView attention_lv;
    private PullToRefreshView pull;
    private List<Attention.Data> list;
    private List<AttentionHead> headlist = new ArrayList<>();
    private MyAttentionAdapter adapter;
    private String smallLogo;
    private String title;
    private String nickname;
    private int position;
    private ImageView attention_head_big_iv,attention_item_iv_left;
    private TextView attention_head_tv_titlename, attention_all_tv_context,
            attention_item_title,attention_item_context;
    private ImageView attention_head_center_iv;
    private TextView attention_tv_important,
            attention_tv_fans,attention_tv_praise,attention_tv_album,attention_tv_tracks;
    String url;
    private int uid = 0;
    private int pageId;
    private String verifyTitle;
    private int statPosition;
    private String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        smallLogo = getIntent().getStringExtra("smallLogo");
        title = getIntent().getStringExtra("title");
        nickname = getIntent().getStringExtra("nickname");
        position = getIntent().getIntExtra("position", 0);
        uid = getIntent().getIntExtra("uid", 0);
        verifyTitle = getIntent().getStringExtra("verifyTitle");

        Url = "http://mobile.ximalaya.com/mobile/others/ca/homePage?" +
                "device=android&statEvent=" +
                "pageview%2Fuser%401000241&statModule=" +
                "%E7%83%AD%E9%97%A8&statPage=tab%40%E5%8F%91%E7%8E%B0_%E4%B8%BB%E6%92%AD&" +
                "statPosition=" +
                statPosition +
                "&toUid=" +
                uid;
        initView();
        setData();
    }

    private void initView() {
        getNetData(0);
        attention_lv = (ListView) findViewById(R.id.attention_lv);
        pull = (PullToRefreshView) findViewById(R.id.attention_pull);
        adapter = new MyAttentionAdapter(AttentionActivity.this, list);
        View view = View.inflate(AttentionActivity.this, R.layout.activity_attention_item_head, null);
        attention_head_big_iv = (ImageView) view.findViewById(R.id.attention_head_big_iv);
        attention_all_tv_context = (TextView) view.findViewById(R.id.attention_all_tv_context);
        attention_head_tv_titlename = (TextView) view.findViewById(R.id.attention_head_tv_titlename);
        attention_head_center_iv = (ImageView) view.findViewById(R.id.attention_head_center_iv);
        attention_tv_important = (TextView) view.findViewById(R.id.attention_tv_important);
        attention_tv_fans= (TextView) view.findViewById(R.id.attention_tv_fans);
        attention_tv_praise = (TextView) view .findViewById(R.id.attention_tv_praise);
        attention_tv_album= (TextView) view .findViewById(R.id.attention_tv_album);
         attention_tv_tracks = (TextView) view.findViewById(R.id.attention_tv_tracks);
        attention_item_iv_left = (ImageView) view.findViewById(R.id.attention_item_iv_left);
        attention_item_title= (TextView) view.findViewById(R.id.attention_item_title);
        attention_item_context= (TextView) view .findViewById(R.id.attention_item_context);
        attention_lv.addHeaderView(view);
        attention_lv.setAdapter(adapter);
        attention_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(AttentionActivity.this, AlbumActivity.class);
//
//                startActivity(intent);
            }
        });

        ImageLoaderUtils.getImageByloader(smallLogo, attention_head_center_iv);
        attention_head_tv_titlename.setText(nickname);
        attention_all_tv_context.setText(verifyTitle);
        pull.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getNetData(1);
            }
        });

        pull.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                getNetData(2);
            }
        });

    }

    private void getNetData(final int type) {
        switch (type) {
            case 0:
                pageId = 1;
                break;
            case 1:
                pageId = 1;
                break;
            case 2:
                pageId++;
                break;
        }
        url = "http://mobile.ximalaya.com/mobile/v1/artist/tracks?device=android&pageId=" +
                pageId +
                "&toUid=" +
                uid +
                "&track_base_url=http://mobile.ximalaya.com/mobile/v1/artist/tracks";
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                Attention attention = gson.fromJson(result, Attention.class);

                switch (type) {
                    case 0:
                        list = attention.list;
                        break;
                    case 1:
                        list = attention.list;
                        pull.onHeaderRefreshFinish();
                        break;
                    case 2:
                        list.addAll(attention.list);
                        pull.onFooterLoadFinish();
                        break;
                }
                adapter.setListener(list);

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    private void setData() {

        HttpUtils httputils = new HttpUtils();
        httputils.send(HttpRequest.HttpMethod.GET, Url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                AttentionHead head = gson.fromJson(result, AttentionHead.class);
                uid++;
                ImageLoaderUtils.getImageByloader(head.backgroundLogo, attention_head_big_iv);
                attention_head_big_iv.setScaleType(ImageView.ScaleType.FIT_XY);
               attention_tv_important.setText(head.followings+"");
                //Toast.makeText(AttentionActivity.this, head.followings+"", Toast.LENGTH_SHORT).show();
                attention_tv_album.setText("发布的专辑:"+head.album+"");
                attention_tv_fans.setText(head.followers +"");
                attention_tv_praise.setText(head.favorites+"");
                attention_tv_tracks.setText("发布的声音"+head.tracks+"");
                ImageLoaderUtils.getImageByloader(head.mobileSmallLogo,attention_item_iv_left);
                attention_item_title.setText(head.nickname);
                attention_item_context.setText(head.personalSignature);

            }
            @Override
            public void onFailure(HttpException e, String s) {




            }
        });
    }

}

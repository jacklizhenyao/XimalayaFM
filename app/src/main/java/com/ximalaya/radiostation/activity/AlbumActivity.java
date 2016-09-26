package com.lanou.radiostation.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.AlbumAdapter;
import com.lanou.radiostation.adapter.MyAlbumAdapter;
import com.lanou.radiostation.bean.AlbumJup;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_history;
import com.lanou.radiostation.bean.PlayData;
import com.lanou.radiostation.util.DensityUtil;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;
import com.lanou.radiostation.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends Activity {
    private Bean_frag_dingyueting_history bean_frag_dingyueting_history;
    private ListView albumLv;
    private AlbumAdapter Lvadapter;
    List<AlbumJup> albumList = new ArrayList<>();
    private String url;
    private AlbumJup albumJup;
    public static String title;
    private int albumId;
    private int position;
    private PullToRefreshView pull;
    private int pageSize;
    private ImageView headIv, album_bottom_iv, album_bottom_play_iv;
    private String picUrl;
    private String titleName;
    private TextView tvTitleName, tvNickname, tvPlaysCounts;
    public static  String nickname;
    private ImageButton backIB;
    private double playsCounts;
    //下载点击事件
    private Button album_download_btn;
    private RotateAnimation animation;
    private PlayData playData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        picUrl = getIntent().getStringExtra("picUrl");
        title = getIntent().getStringExtra("title");
        albumId = getIntent().getIntExtra("albumId", 0);
        position = getIntent().getIntExtra("position", 0);
        titleName = getIntent().getStringExtra("titleName");
        nickname = getIntent().getStringExtra("nickname");//作者名
        playsCounts = getIntent().getIntExtra("playsCounts", 0);
        if (albumId == 3888524) {
            Toast.makeText(AlbumActivity.this, "这本书涉嫌不良信息,", Toast.LENGTH_SHORT).show();
            return;
        }
        animation = new RotateAnimation(
                0,
                360,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        initView();

        Lvadapter.setOnclick(new AlbumAdapter.Click() {
            @Override
            public void Onclick() {
                MusicConstant.ISPlay = true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        playData=getSharedPreData();
        if (playData.image.length==0){
            album_bottom_iv.setBackgroundResource(R.mipmap.ic_play_pause);
        }else {
            if (playData.image.length>0){
                Bitmap imagebitmap= BitmapFactory.decodeByteArray(playData.image,0, playData.image.length);
                album_bottom_iv.setImageBitmap(imagebitmap);
                if (MusicConstant.IS_PLAYING){
                    animation.setDuration(3000);
                    // 设置动画重复的次数
                    animation.setRepeatCount(Animation.INFINITE);
                    // 设置动画重复的模式
                    animation.setRepeatMode(Animation.INFINITE);
                    // 设置动画结束以后的状态
                    animation.setFillAfter(false);
                    LinearInterpolator lin = new LinearInterpolator();
                    animation.setInterpolator(lin);
                    album_bottom_iv.setAnimation(animation);

                    animation.start();
                }else {
                    if (animation.hasStarted()){
                        animation.cancel();
                    }
                }
            }
        }


    }

    private void initView() {


        setDataUrl(0);
        album_bottom_play_iv = (ImageView) findViewById(R.id.album_bottom_play_iv);
        album_bottom_iv = (ImageView) findViewById(R.id.album_bottom_iv);
        bean_frag_dingyueting_history=new Bean_frag_dingyueting_history();
        albumLv = (ListView) findViewById(R.id.album_lv);
        backIB = (ImageButton) findViewById(R.id.album_back);
        pull = (PullToRefreshView) findViewById(R.id.album_pull);
        Lvadapter = new AlbumAdapter(this, albumList);
        View view = View.inflate(this, R.layout.activity_album_item_head, null);
        headIv = (ImageView) view.findViewById(R.id.album_item_head_iv);
        tvTitleName = (TextView) view.findViewById(R.id.album_item_head_titlename);
        tvNickname = (TextView) view.findViewById(R.id.album_item_head_nickname);
        tvPlaysCounts = (TextView) view.findViewById(R.id.album_item_head_playscounts);
        tvPlaysCounts.setText(new DecimalFormat("##0.00").format(playsCounts / 10000) + "万次");
        tvTitleName.setText(titleName);
        tvNickname.setText(nickname);
        ImageLoaderUtils.getImageByloader(picUrl, headIv);
//        ImageLoaderUtils.getImageByloader(picUrl, album_bottom_iv);
        album_download_btn = (Button) view.findViewById(R.id.album_download_btn);
        setAlertDialog();
        albumLv.addHeaderView(view);
        albumLv.setAdapter(Lvadapter);

        pull.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                setDataUrl(1);
            }
        });

        pull.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
//                type = 2;
                setDataUrl(2);
            }
        });

        backIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        album_bottom_play_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playData=getSharedPreData();
                if (playData.image.length!=0){
                    Intent intent1 = new Intent(AlbumActivity.this,PlayBackActivity.class);
                    MusicConstant.ISPlay=true;
                    intent1.putExtra("picUrl",playData.coverLarge);
                    intent1.putExtra("title",playData.title);
                    intent1.putExtra("titlebottom",playData.title);
                    intent1.putExtra("nickname",playData.nickname);
                    intent1.putExtra("albumId",playData.albumId);
                    startActivity(intent1);
                    if (MusicConstant.IS_PLAYING){

                    }else {
                        MusicSong.list.clear();
                        MusicConstant.PLAYING_POSITION = 0;
                        MusicSong.list.add(playData.playUrl32);
                        Intent intent=new Intent();
                        intent.setAction("haha");
                        intent.putExtra("type", MusicConstant.PLAY_GO_ON);
                        sendBroadcast(intent);
                    }
                }
            }
        });


    }

    private void setDataUrl(final int type) {
        HttpUtils utils = new HttpUtils();

        switch (type) {
            case 0:
                pageSize = 20;
                break;
            case 1:
                pageSize = 20;
                break;
            case 2:
                pageSize += 20;
                break;
        }

        url = "http://mobile.ximalaya.com/mobile/v1/album?albumId=" +
                albumId +
                "&device=android&isAsc=true&pageId=1&pageSize=" +
                pageSize +
                "&pre_page=0&source=4&statEvent=pageview%2Falbum%40" +
                albumId +
                "&statModule=" +
                title +
                "&statPage=tab%40%E5%8F%91%E7%8E%B0_%E6%8E%A8%E8%8D%90&statPosition=" +
                position;

        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                Gson gson = new Gson();
                albumJup = gson.fromJson(result, AlbumJup.class);

                albumList.clear();

                if (albumJup.data.tracks.list == null ) {
                    Toast.makeText(AlbumActivity.this, "本书为付费书,请先登录,充值后再来收听", Toast.LENGTH_SHORT).show();
                    return;
                }
                MusicSong.list.clear();
                for (int i = 0; i < albumJup.data.tracks.list.size(); i++) {
                    albumJup.data.album.albumId=albumId;
                    albumJup.data.album.nickname=nickname;
                    albumList.add(albumJup);
                    MusicSong.list.add(albumJup.data.tracks.list.get(i).playUrl32);
                    MusicSong.titleList.add(albumJup.data.tracks.list.get(i).title);
                }
                MusicSong.albumID=albumJup.data.album.albumId;
                switch (type) {
                    case 0:
                        Lvadapter.setListener(albumList);
                        break;
                    case 1:
                        Lvadapter.setListener(albumList);
                        pull.onHeaderRefreshFinish();
                        break;
                    case 2:
                        Lvadapter.setListener(albumList);
                        pull.onFooterLoadFinish();
                        break;
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(AlbumActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    public void setAlertDialog() {
        album_download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// 1. 布局文件转换为View对象
                LayoutInflater inflater = LayoutInflater.from(AlbumActivity.this);
                RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.album_aler_dialog, null);

// 2. 新建对话框对象
                final Dialog dialog = new AlertDialog.Builder(AlbumActivity.this).create();
                dialog.setCancelable(false);
                dialog.show();
                dialog.getWindow().setContentView(layout);
                Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                Button btnLigon = (Button) layout.findViewById(R.id.dialog_ligon);
                btnLigon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AlbumActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }

    SharedPreferences sharedPreferences;

    protected PlayData getSharedPreData(){
        PlayData playData=new PlayData();
        sharedPreferences = getSharedPreferences("Rs_sharePre", Activity.MODE_PRIVATE);
        String string = sharedPreferences.getString("play_pic_content","");
        playData.image = Base64.decode(string.getBytes(), Base64.DEFAULT);
        playData.playUrl32=sharedPreferences.getString("playUrl32","");
        playData.title=sharedPreferences.getString("title","");
        playData.albumId=sharedPreferences.getInt("albumId",0);
        playData.coverLarge=sharedPreferences.getString("coverLarge","");
        playData.nickname=sharedPreferences.getString("nickname","");
        return playData;
    }

}

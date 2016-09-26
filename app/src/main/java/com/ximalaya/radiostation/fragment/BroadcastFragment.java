package com.lanou.radiostation.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.BroadActivity;
import com.lanou.radiostation.activity.BroadCastPlayActivity;
import com.lanou.radiostation.activity.BroadLocaltionAndCountryActivity;
import com.lanou.radiostation.activity.BroadProvinceActivity;
import com.lanou.radiostation.bean.AlbumJup;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_history;
import com.lanou.radiostation.bean.BroadFragment;
import com.lanou.radiostation.bean.DingYueTingRecommended;
import com.lanou.radiostation.db.RSDBDao;
import com.lanou.radiostation.util.GetBitMapByteArray;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 这是一个广播的fragment
 * Created by user on 2016/7/22.
 */
public class BroadcastFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout frag_broad_xia_rl, frag_broad_cang_rl_dian,frag_broad_beijing_rl_01,frag_broad_beijing_rl_02,frag_broad_beijing_rl_03,
    frag_broad_guojia_rl_01,frag_broad_guojia_rl_02,frag_broad_guojia_rl_03;

    private ImageView frag_broad_xia_rl_iv, frag_broad_beijing__iv_01, frag_broad_beijing__iv_02, frag_broad_beijing__iv_03,
            frag_broad_paihangbang_iv_01, frag_broad_paihangbang_iv_02, frag_broad_paihangbang_iv_03, frag_broad_localtion_iv, frag_broad_country_iv,
            frag_broad_province_iv,frag_broad_net_iv;

    private TextView frag_broad_xia_rl_tv, frag_broad_beijing_tv_01_name, frag_broad_beijing_tv_02_name, frag_broad_beijing_tv_03_name,
            frag_broad_beijing_tv_01_programname, frag_broad_beijing_tv_02_programname, frag_broad_beijing_tv_03_programname,
            frag_broad_beijing_tv_01_playcount, frag_broad_beijing_tv_02_playcount, frag_broad_beijing_tv_03_playcount,
            frag_broad_paihangbang_tv_01_name, frag_broad_paihangbang_tv_02_name, frag_broad_paihangbang_tv_03_name,
            frag_broad_paihangbang_tv_01_programname, frag_broad_paihangbang_tv_02_programname, frag_broad_paihangbang_tv_03_programname,
            frag_broad_paihangbang_tv_01_playcount, frag_broad_paihangbang_tv_02_playcount, frag_broad_paihangbang_tv_03_playcount,
            frag_broad_news_tv, frag_broad_music_tv, frag_broad_traffic_tv, frag_broad_economy_tv, frag_broad_sports_tv,
            frag_broad_culture_tv, frag_broad_drama_tv, frag_broad_campus_tv, frag_broad_dialect_tv, frag_broad_foreignlanguage_tv,
            frag_broad_life_tv, frag_broad_city_tv, frag_broad_travel_tv, frag_broad_else_tv;


    private LinearLayout frag_broad_cang_ll_01, frag_broad_cang_ll_02;
    private BroadFragment broadFragment;
    private RSDBDao rsdbDao ;
    private Cursor cursor;
    private String tableName="dingyueting_history_table";

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.broadcast_frag, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        setDataUrl();
        rsdbDao=new RSDBDao(getActivity());
        frag_broad_province_iv = (ImageView) view.findViewById(R.id.frag_broad_province_iv);
        frag_broad_localtion_iv = (ImageView) view.findViewById(R.id.frag_broad_localtion_iv);
        frag_broad_country_iv = (ImageView) view.findViewById(R.id.frag_broad_country_iv);

        frag_broad_news_tv = (TextView) view.findViewById(R.id.frag_broad_news_tv);
        frag_broad_music_tv = (TextView) view.findViewById(R.id.frag_broad_music_tv);
        frag_broad_traffic_tv = (TextView) view.findViewById(R.id.frag_broad_traffic_tv);
        frag_broad_economy_tv = (TextView) view.findViewById(R.id.frag_broad_economy_tv);
        frag_broad_sports_tv = (TextView) view.findViewById(R.id.frag_broad_sports_tv);

        frag_broad_culture_tv = (TextView) view.findViewById(R.id.frag_broad_culture_tv);
        frag_broad_drama_tv = (TextView) view.findViewById(R.id.frag_broad_drama_tv);
        frag_broad_campus_tv = (TextView) view.findViewById(R.id.frag_broad_campus_tv);
        frag_broad_dialect_tv = (TextView) view.findViewById(R.id.frag_broad_dialect_tv);
        frag_broad_foreignlanguage_tv = (TextView) view.findViewById(R.id.frag_broad_foreignlanguage_tv);

        frag_broad_life_tv = (TextView) view.findViewById(R.id.frag_broad_life_tv);
        frag_broad_city_tv = (TextView) view.findViewById(R.id.frag_broad_city_tv);
        frag_broad_travel_tv = (TextView) view.findViewById(R.id.frag_broad_travel_tv);
        frag_broad_else_tv = (TextView) view.findViewById(R.id.frag_broad_else_tv);

        frag_broad_news_tv.setOnClickListener(this);
        frag_broad_music_tv.setOnClickListener(this);
        frag_broad_traffic_tv.setOnClickListener(this);
        frag_broad_economy_tv.setOnClickListener(this);
        frag_broad_sports_tv.setOnClickListener(this);

        frag_broad_culture_tv.setOnClickListener(this);
        frag_broad_drama_tv.setOnClickListener(this);
        frag_broad_campus_tv.setOnClickListener(this);
        frag_broad_dialect_tv.setOnClickListener(this);
        frag_broad_foreignlanguage_tv.setOnClickListener(this);

        frag_broad_life_tv.setOnClickListener(this);
        frag_broad_city_tv.setOnClickListener(this);
        frag_broad_travel_tv.setOnClickListener(this);
        frag_broad_else_tv.setOnClickListener(this);


        frag_broad_beijing_tv_01_name = (TextView) view.findViewById(R.id.frag_broad_beijing_tv_01_name);
        frag_broad_beijing_tv_02_name = (TextView) view.findViewById(R.id.frag_broad_beijing_tv_02_name);
        frag_broad_beijing_tv_03_name = (TextView) view.findViewById(R.id.frag_broad_beijing_tv_03_name);

        frag_broad_beijing_tv_01_programname = (TextView) view.findViewById(R.id.frag_broad_beijing_tv_01_programname);
        frag_broad_beijing_tv_02_programname = (TextView) view.findViewById(R.id.frag_broad_beijing_tv_02_programname);
        frag_broad_beijing_tv_03_programname = (TextView) view.findViewById(R.id.frag_broad_beijing_tv_03_programname);

        frag_broad_beijing_tv_01_playcount = (TextView) view.findViewById(R.id.frag_broad_beijing_tv_01_playcount);
        frag_broad_beijing_tv_02_playcount = (TextView) view.findViewById(R.id.frag_broad_beijing_tv_02_playcount);
        frag_broad_beijing_tv_03_playcount = (TextView) view.findViewById(R.id.frag_broad_beijing_tv_03_playcount);

        frag_broad_paihangbang_tv_01_name = (TextView) view.findViewById(R.id.frag_broad_paihangbang_tv_01_name);
        frag_broad_paihangbang_tv_02_name = (TextView) view.findViewById(R.id.frag_broad_paihangbang_tv_02_name);
        frag_broad_paihangbang_tv_03_name = (TextView) view.findViewById(R.id.frag_broad_paihangbang_tv_03_name);

        frag_broad_paihangbang_tv_01_programname = (TextView) view.findViewById(R.id.frag_broad_paihangbang_tv_01_programname);
        frag_broad_paihangbang_tv_02_programname = (TextView) view.findViewById(R.id.frag_broad_paihangbang_tv_02_programname);
        frag_broad_paihangbang_tv_03_programname = (TextView) view.findViewById(R.id.frag_broad_paihangbang_tv_03_programname);

        frag_broad_paihangbang_tv_01_playcount = (TextView) view.findViewById(R.id.frag_broad_paihangbang_tv_01_playcount);
        frag_broad_paihangbang_tv_02_playcount = (TextView) view.findViewById(R.id.frag_broad_paihangbang_tv_02_playcount);
        frag_broad_paihangbang_tv_03_playcount = (TextView) view.findViewById(R.id.frag_broad_paihangbang_tv_03_playcount);


        frag_broad_beijing__iv_01 = (ImageView) view.findViewById(R.id.frag_broad_beijing__iv_01);
        frag_broad_beijing__iv_02 = (ImageView) view.findViewById(R.id.frag_broad_beijing__iv_02);
        frag_broad_beijing__iv_03 = (ImageView) view.findViewById(R.id.frag_broad_beijing__iv_03);

        frag_broad_paihangbang_iv_01 = (ImageView) view.findViewById(R.id.frag_broad_paihangbang_iv_01);
        frag_broad_paihangbang_iv_02 = (ImageView) view.findViewById(R.id.frag_broad_paihangbang_iv_02);
        frag_broad_paihangbang_iv_03 = (ImageView) view.findViewById(R.id.frag_broad_paihangbang_iv_03);

        frag_broad_xia_rl = (RelativeLayout) view.findViewById(R.id.frag_broad_xia_rl);
        frag_broad_cang_rl_dian = (RelativeLayout) view.findViewById(R.id.frag_broad_cang_rl_dian);
        frag_broad_xia_rl_iv = (ImageView) view.findViewById(R.id.frag_broad_xia_rl_iv);
        frag_broad_xia_rl_tv = (TextView) view.findViewById(R.id.frag_broad_xia_rl_tv);
        frag_broad_cang_ll_01 = (LinearLayout) view.findViewById(R.id.frag_broad_cang_ll_01);
        frag_broad_cang_ll_02 = (LinearLayout) view.findViewById(R.id.frag_broad_cang_ll_02);

        frag_broad_beijing_rl_01 = (RelativeLayout) view.findViewById(R.id.frag_broad_beijing_rl_01);
        frag_broad_beijing_rl_02 = (RelativeLayout) view.findViewById(R.id.frag_broad_beijing_rl_02);
        frag_broad_beijing_rl_03 = (RelativeLayout) view.findViewById(R.id.frag_broad_beijing_rl_03);

        frag_broad_guojia_rl_01 = (RelativeLayout) view.findViewById(R.id.frag_broad_guojia_rl_01);
        frag_broad_guojia_rl_02 = (RelativeLayout) view.findViewById(R.id.frag_broad_guojia_rl_02);
        frag_broad_guojia_rl_03 = (RelativeLayout) view.findViewById(R.id.frag_broad_guojia_rl_03);


        frag_broad_net_iv = (ImageView) view.findViewById(R.id.frag_broad_net_iv);

        frag_broad_xia_rl_tv.setOnClickListener(this);

        frag_broad_xia_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag_broad_xia_rl_iv.setVisibility(View.GONE);
                frag_broad_xia_rl_tv.setVisibility(View.VISIBLE);
                frag_broad_cang_ll_01.setVisibility(View.VISIBLE);
                frag_broad_cang_ll_02.setVisibility(View.VISIBLE);
            }
        });
        frag_broad_cang_rl_dian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag_broad_xia_rl_iv.setVisibility(View.VISIBLE);
                frag_broad_xia_rl_tv.setVisibility(View.GONE);
                frag_broad_cang_ll_01.setVisibility(View.GONE);
                frag_broad_cang_ll_02.setVisibility(View.GONE);
            }
        });


        frag_broad_localtion_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BroadLocaltionAndCountryActivity.class);
                intent.putExtra("mark", 0);
                getActivity().startActivity(intent);

            }
        });
        frag_broad_country_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BroadLocaltionAndCountryActivity.class);
                intent.putExtra("mark", 1);
                getActivity().startActivity(intent);

            }
        });
        frag_broad_net_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BroadLocaltionAndCountryActivity.class);
                intent.putExtra("mark", 2);
                getActivity().startActivity(intent);

            }
        });

        frag_broad_province_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), BroadProvinceActivity.class));
            }
        });


    }

    private void setDataUrl() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, "http://live.ximalaya.com/live-web/v4/homepage", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                broadFragment = gson.fromJson(result, BroadFragment.class);

                setDataResult(broadFragment);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void setDataResult(final BroadFragment broadFragment) {
        ImageLoaderUtils.getImageByloader(broadFragment.data.localRadios.get(0).coverSmall, frag_broad_beijing__iv_01);
        ImageLoaderUtils.getImageByloader(broadFragment.data.localRadios.get(1).coverSmall, frag_broad_beijing__iv_02);
        ImageLoaderUtils.getImageByloader(broadFragment.data.localRadios.get(2).coverSmall, frag_broad_beijing__iv_03);

        frag_broad_beijing_tv_01_name.setText(broadFragment.data.localRadios.get(0).name);
        frag_broad_beijing_tv_02_name.setText(broadFragment.data.localRadios.get(1).name);
        frag_broad_beijing_tv_03_name.setText(broadFragment.data.localRadios.get(2).name);

        frag_broad_beijing_tv_01_programname.setText("正在直播 " + " " + broadFragment.data.localRadios.get(0).programName);
        frag_broad_beijing_tv_02_programname.setText("正在直播 " + " " + broadFragment.data.localRadios.get(1).programName);
        frag_broad_beijing_tv_03_programname.setText("正在直播 " + " " + broadFragment.data.localRadios.get(2).programName);

        double playCount1 = broadFragment.data.localRadios.get(0).playCount;
        double playCount2 = broadFragment.data.localRadios.get(1).playCount;
        double playCount3 = broadFragment.data.localRadios.get(2).playCount;
        frag_broad_beijing_tv_01_playcount.setText(new DecimalFormat("##0.0").format(playCount1 / 10000) + "万次" + "");
        frag_broad_beijing_tv_02_playcount.setText(new DecimalFormat("##0.0").format(playCount2 / 10000) + "万次" + "");
        frag_broad_beijing_tv_03_playcount.setText(new DecimalFormat("##0.0").format(playCount3 / 10000) + "万次" + "");

        frag_broad_beijing_rl_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getActivity(), BroadCastPlayActivity.class);
                intent1.putExtra("title",broadFragment.data.localRadios.get(0).name);
                intent1.putExtra("picUrl",broadFragment.data.localRadios.get(0).coverSmall);
                intent1.putExtra("programName",broadFragment.data.localRadios.get(0).programName);
                intent1.putExtra("id",broadFragment.data.localRadios.get(0).id);
                getActivity().startActivity(intent1);
                MusicConstant.ISPlay =  true;
                MusicSong.tag = "guangbo";
                setInsertData(broadFragment.data,"top",0);
                MusicSong.list.clear();
                MusicSong.list.add(broadFragment.data.localRadios.get(0).playUrl.ts24);

            }
        });
        frag_broad_beijing_rl_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicSong.list.clear();
                MusicSong.list.add(broadFragment.data.localRadios.get(1).playUrl.ts24);

                setInsertData(broadFragment.data,"top",1);

                Intent intent1 = new Intent(getActivity(), BroadCastPlayActivity.class);
                intent1.putExtra("title",broadFragment.data.localRadios.get(1).name);
                intent1.putExtra("picUrl",broadFragment.data.localRadios.get(1).coverSmall);
                intent1.putExtra("programName",broadFragment.data.localRadios.get(1).programName);
                intent1.putExtra("id",broadFragment.data.localRadios.get(1).id);
                getActivity().startActivity(intent1);
                MusicConstant.ISPlay =  true;

                MusicSong.tag = "guangbo";

            }
        });
        frag_broad_beijing_rl_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicSong.list.clear();
                    MusicSong.list.add(broadFragment.data.localRadios.get(2).playUrl.ts24);

                setInsertData(broadFragment.data,"top",2);

                Intent intent1 = new Intent(getActivity(), BroadCastPlayActivity.class);
                intent1.putExtra("title",broadFragment.data.localRadios.get(2).name);
                intent1.putExtra("picUrl",broadFragment.data.localRadios.get(2).coverSmall);
                intent1.putExtra("programName",broadFragment.data.localRadios.get(2).programName);
                intent1.putExtra("id",broadFragment.data.localRadios.get(2).id);
                getActivity().startActivity(intent1);

                MusicConstant.ISPlay =  true;

                MusicSong.tag = "guangbo";
            }
        });


        frag_broad_guojia_rl_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicSong.list.clear();
                MusicSong.list.add(broadFragment.data.topRadios.get(0).playUrl.ts24);

                setInsertData(broadFragment.data,"topRadios",0);

                Intent intent1 = new Intent(getActivity(), BroadCastPlayActivity.class);
                intent1.putExtra("title",broadFragment.data.topRadios.get(0).name);
                intent1.putExtra("picUrl",broadFragment.data.topRadios.get(0).coverSmall);
                intent1.putExtra("programName",broadFragment.data.topRadios.get(0).programName);
                intent1.putExtra("id",broadFragment.data.topRadios.get(0).id);
                getActivity().startActivity(intent1);

                MusicConstant.ISPlay =  true;
                MusicSong.tag = "guangbo";

            }
        });
        frag_broad_guojia_rl_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicSong.list.clear();
                MusicSong.list.add(broadFragment.data.topRadios.get(1).playUrl.ts24);
                setInsertData(broadFragment.data,"topRadios",1);

                Intent intent1 = new Intent(getActivity(), BroadCastPlayActivity.class);
                intent1.putExtra("title",broadFragment.data.topRadios.get(1).name);
                intent1.putExtra("picUrl",broadFragment.data.topRadios.get(1).coverSmall);
                intent1.putExtra("programName",broadFragment.data.topRadios.get(1).programName);
                intent1.putExtra("id",broadFragment.data.topRadios.get(1).id);
                getActivity().startActivity(intent1);
                MusicConstant.ISPlay =  true;

                MusicSong.tag = "guangbo";

            }
        });
        frag_broad_guojia_rl_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicSong.list.clear();
                    MusicSong.list.add(broadFragment.data.topRadios.get(2).playUrl.ts24);

                setInsertData(broadFragment.data,"topRadios",2);

                Intent intent1 = new Intent(getActivity(), BroadCastPlayActivity.class);
                intent1.putExtra("title",broadFragment.data.topRadios.get(2).name);
                intent1.putExtra("picUrl",broadFragment.data.topRadios.get(2).coverSmall);
                intent1.putExtra("programName",broadFragment.data.topRadios.get(2).programName);
                intent1.putExtra("id",broadFragment.data.topRadios.get(2).id);
                getActivity().startActivity(intent1);

                MusicConstant.ISPlay =  true;

                MusicSong.tag = "guangbo";
            }
        });


        /**==============================================================================================================*/

        ImageLoaderUtils.getImageByloader(broadFragment.data.topRadios.get(0).coverSmall, frag_broad_paihangbang_iv_01);
        ImageLoaderUtils.getImageByloader(broadFragment.data.topRadios.get(1).coverSmall, frag_broad_paihangbang_iv_02);
        ImageLoaderUtils.getImageByloader(broadFragment.data.topRadios.get(2).coverSmall, frag_broad_paihangbang_iv_03);

        frag_broad_paihangbang_tv_01_name.setText(broadFragment.data.topRadios.get(0).name);
        frag_broad_paihangbang_tv_02_name.setText(broadFragment.data.topRadios.get(1).name);
        frag_broad_paihangbang_tv_03_name.setText(broadFragment.data.topRadios.get(2).name);

        frag_broad_paihangbang_tv_01_programname.setText("正在直播 " + " " + broadFragment.data.topRadios.get(0).programName);
        frag_broad_paihangbang_tv_02_programname.setText("正在直播 " + " " + broadFragment.data.topRadios.get(1).programName);
        frag_broad_paihangbang_tv_03_programname.setText("正在直播 " + " " + broadFragment.data.topRadios.get(2).programName);

        double playCountPai1 = broadFragment.data.topRadios.get(0).playCount;
        double playCountPai2 = broadFragment.data.topRadios.get(1).playCount;
        double playCountPai3 = broadFragment.data.topRadios.get(2).playCount;
        frag_broad_paihangbang_tv_01_playcount.setText(new DecimalFormat("##0.0").format(playCountPai1 / 10000) + "万次" + "");
        frag_broad_paihangbang_tv_02_playcount.setText(new DecimalFormat("##0.0").format(playCountPai2 / 10000) + "万次" + "");
        frag_broad_paihangbang_tv_03_playcount.setText(new DecimalFormat("##0.0").format(playCountPai3 / 10000) + "万次" + "");


    }

    GetBitMapByteArray getBitMapByteArray=new GetBitMapByteArray();
    private  void setInsertData(BroadFragment.Data data, final String tag, int i){

        final Bean_frag_dingyueting_history bean_frag_dingyueting_history=new Bean_frag_dingyueting_history();
        String picUrl=null;
        if(tag.equals("top")){
            bean_frag_dingyueting_history.playUrl32=data.localRadios.get(i).playUrl.ts24;
            bean_frag_dingyueting_history.albumId=data.localRadios.get(i).id;
            bean_frag_dingyueting_history.author=data.localRadios.get(i).name;
            bean_frag_dingyueting_history.coverSmall=data.localRadios.get(i).coverSmall;
            bean_frag_dingyueting_history.title=data.localRadios.get(i).programName;
            bean_frag_dingyueting_history.tag="guangbo";
            bean_frag_dingyueting_history.nickname=data.localRadios.get(i).programName;
            picUrl= bean_frag_dingyueting_history.coverSmall;
            MusicConstant.radio_id=broadFragment.data.localRadios.get(i).id;
        }else {
            bean_frag_dingyueting_history.playUrl32=data.topRadios.get(i).playUrl.ts24;
            bean_frag_dingyueting_history.albumId=data.topRadios.get(i).id;
            bean_frag_dingyueting_history.author=data.topRadios.get(i).programName;
            bean_frag_dingyueting_history.coverSmall=data.topRadios.get(i).coverSmall;
            bean_frag_dingyueting_history.title=data.topRadios.get(i).programName;
            bean_frag_dingyueting_history.tag="guangbo";
            bean_frag_dingyueting_history.nickname=data.topRadios.get(i).programName;
            picUrl= bean_frag_dingyueting_history.coverSmall;
            MusicConstant.radio_id=broadFragment.data.topRadios.get(i).id;
        }
        getBitMapByteArray.setData(picUrl,100, new GetBitMapByteArray.RequestCallBack() {
            @Override
            public void onSuccess(byte[] result) {
                bean_frag_dingyueting_history.imageBytes=result;
                    insertDistinct(bean_frag_dingyueting_history);
                MusicConstant.PLAYING_POSITION = 0;
                Intent intent = new Intent();
                intent.setAction("haha");
                intent.putExtra("type", MusicConstant.PLAY_GO_ON);
                getActivity().sendBroadcast(intent);
            }
            @Override
            public void onFailure() {

            }
        });
    }

    private void insertDistinct(Bean_frag_dingyueting_history bean_frag_dingyueting_history) {
        cursor= rsdbDao.select(tableName);
        int albumId= bean_frag_dingyueting_history.albumId;
        while (cursor.moveToNext()){
//            Log.e("cursor",cursor.getString(cursor.getColumnIndex("albumId"))+"");
            if (albumId==cursor.getInt(cursor.getColumnIndex("albumId"))) {
                if (getAlbumId()!=0&&getAlbumId()==albumId){
                    return;//如果当前专辑id为数据库中的最大id 就不在执行删除出入 操作
                }else {
                    rsdbDao.delete(tableName,albumId+"");
                    rsdbDao.insert(bean_frag_dingyueting_history);
                    return;
                }
            }
        }
        rsdbDao.insert(bean_frag_dingyueting_history);
    }
    public int getAlbumId(){
        int id=getMaxId();
        if (id!=0){
            int albumId=0;
            Cursor cursor=rsdbDao.select(tableName,id);
            while (cursor.moveToNext()){
                albumId=cursor.getInt(cursor.getColumnIndex("albumId"));
            }
            return albumId;
        }
      return 0;
    }
    public int getMaxId(){
       Cursor cursor=rsdbDao.getMaxId(tableName);
        int maxId=0;
        while (cursor.moveToNext()){
            maxId=cursor.getInt(cursor.getColumnIndex("maxId"));
        }
        return  maxId;
    }
    @Override
    public void setData() {
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), BroadActivity.class);
        switch (view.getId()) {
            case R.id.frag_broad_news_tv:
                intent.putExtra("id", broadFragment.data.categories.get(0).id);
                break;
            case R.id.frag_broad_music_tv:
                intent.putExtra("id", broadFragment.data.categories.get(1).id);
                break;
            case R.id.frag_broad_traffic_tv:
                intent.putExtra("id", broadFragment.data.categories.get(2).id);
                break;
            case R.id.frag_broad_economy_tv:
                intent.putExtra("id", broadFragment.data.categories.get(3).id);
                break;
            case R.id.frag_broad_sports_tv:
                intent.putExtra("id", broadFragment.data.categories.get(4).id);
                break;
            case R.id.frag_broad_culture_tv:
                intent.putExtra("id", broadFragment.data.categories.get(5).id);
                break;
            case R.id.frag_broad_drama_tv:
                intent.putExtra("id", broadFragment.data.categories.get(6).id);
                break;
            case R.id.frag_broad_campus_tv:
                intent.putExtra("id", broadFragment.data.categories.get(8).id);
                break;
            case R.id.frag_broad_dialect_tv:
                intent.putExtra("id", broadFragment.data.categories.get(9).id);
                break;
            case R.id.frag_broad_foreignlanguage_tv:
                intent.putExtra("id", broadFragment.data.categories.get(10).id);
                break;
            case R.id.frag_broad_life_tv:
                intent.putExtra("id", broadFragment.data.categories.get(11).id);
                break;
            case R.id.frag_broad_city_tv:
                intent.putExtra("id", broadFragment.data.categories.get(12).id);
                break;
            case R.id.frag_broad_travel_tv:
                intent.putExtra("id", broadFragment.data.categories.get(13).id);
                break;
            case R.id.frag_broad_else_tv:
                intent.putExtra("id", broadFragment.data.categories.get(14).id);
                break;
            case R.id.frag_broad_xia_rl_tv:
                intent.putExtra("id", broadFragment.data.categories.get(7).id);
                break;
        }
        startActivity(intent);
    }
}

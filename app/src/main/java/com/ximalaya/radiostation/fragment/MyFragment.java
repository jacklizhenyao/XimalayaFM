package com.lanou.radiostation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.BroadCastPlayActivity;
import com.lanou.radiostation.adapter.ActivityBroadAdapter;
import com.lanou.radiostation.bean.ActivityBroad;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;
import com.lanou.radiostation.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/8/3.
 */
public class MyFragment extends Fragment{

    private String url;
    private View view;
    private ListView content;//在此处我只展示一个TextView
    private ActivityBroadAdapter adapter;
    private List<ActivityBroad.Datas> list = new ArrayList<>();
    private PullToRefreshView myfragment_pull;
    private int pageNum = 1;
    private ActivityBroad activityBroad;

    /**
     * 次方法利用静态工厂的模式，相比直接使用new MyFragment()更方便，不容易出错
     * @param paramas  实例化Fragment时需要传的参数
     * @return  返回一个Fragment的对象
     */
    public static MyFragment newInstance(int paramas) {
//利用Bundle存值
        Bundle args = new Bundle();
        args.putInt("flag", paramas);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);//将存有值的Bundle利用setArguments再次存起来
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myfragment, null);
        init();
        return view;
    }

    public void init(){
        setDataUrl(0);
        myfragment_pull = (PullToRefreshView) view.findViewById(R.id.myfragment_pull);
        content = (ListView) view.findViewById(R.id.content);
        adapter = new ActivityBroadAdapter(getActivity(),list);
        content.setAdapter(adapter);

        myfragment_pull.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                setDataUrl(2);
            }
        });

        myfragment_pull.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                setDataUrl(1);
            }
        });



    }

    private void setDataUrl(final int type) {
        switch (type){
            case 0:
                pageNum = 1;
                break;
            case 1:
                pageNum = 1;
                break;
            case 2:
                pageNum++;
                break;

        }

        url = "http://live.ximalaya.com/live-web/v2/radio/province?pageNum=" +
                pageNum +
                "&pageSize=20&provinceCode=" +
                getArguments().get("flag"); //取出值，去上面存值相对应
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                activityBroad = gson.fromJson(result, ActivityBroad.class);
                switch (type){
                    case 0:
                        list = activityBroad.data.data;
                        break;
                    case 1:
                        list = activityBroad.data.data;
                        myfragment_pull.onHeaderRefreshFinish();
                        break;
                    case 2:
                        if (activityBroad.data.data == null) {
                            Toast.makeText(getActivity(), "打底了", Toast.LENGTH_SHORT).show();
                            myfragment_pull.onFooterLoadFinish();
                            return;
                        }
                        list.addAll(activityBroad.data.data);
                        myfragment_pull.onFooterLoadFinish();
                        break;
                }
                adapter.setList(list);

                MusicSong.list.clear();
                for (int i = 0; i < list.size(); i++) {
                    MusicSong.list.add(list.get(i).playUrl.ts24);
                }


                content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MusicConstant.PLAYING_POSITION = i;
                        Intent intent = new Intent();
                        intent.setAction("haha");
                        intent.putExtra("type", MusicConstant.PLAY_GO_ON);
                        getActivity().sendBroadcast(intent);

                        Intent intent1 = new Intent(getActivity(), BroadCastPlayActivity.class);
                        intent1.putExtra("title",list.get(i).name);
                        intent1.putExtra("picUrl",list.get(i).coverSmall);
//                        intent1.putExtra("programName","asd");
                        intent1.putExtra("programName",list.get(i).programName);
                        getActivity().startActivity(intent1);
                        MusicConstant.ISPlay = true;

                        MusicSong.tag = "guangbo";
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}

package com.lanou.radiostation.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.adapter.FragRecommendedAdapter;
import com.lanou.radiostation.bean.DingYueTingRecommended;
import com.lanou.radiostation.db.RSDBDao;
import com.lanou.radiostation.util.CollectionUtil;
import com.lanou.radiostation.view.PullToRefreshView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 2016/7/22.
 */
public class Frag_dingyueting_recommended extends Fragment implements FragRecommendedAdapter.SendRecomDataAdapter, FragRecommendedAdapter.SubscriptionDataListener {
    View view;
    List<DingYueTingRecommended.RecommendedData> recommendedDataList;//装载需要Adapter解析的list数据
    private ListView frag_dingyueting_recommended_listview;
    private FragRecommendedAdapter fragRecommendedAdapter;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private BitmapUtils bitmapUtils;
    private PullToRefreshView pullToRefreshView;
    private Cursor cursor;
    // 直接请求常量
    public static final int REQUEST = 0;
    // 下拉刷新请求常量
    public static final int REFRESH = 1;
    // 上拉加载请求常量
    public static final int LOAD = 2;

    private List<Integer> deleteItemList;//记录刷新时要删除的item集合


    //当前请求数据为第几页数
    int pageSize = 10;
    private Button frag_dingyueting_recommended_button_refresh;//网络刷新按钮
    private LinearLayout linearLayout;
    private RSDBDao rsdbDao;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frag_dingyueting_recommended, null);
        initView();
        return view;
    }

    //当界面从Activity到Fragment切换时 会刷数据
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
    }

    private void initView() {
        frag_dingyueting_recommended_listview =
                (ListView) view.findViewById(R.id.frag_dingyueting_recommended_listview);
        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.frag_dingyueting_tv_recommended_pulltorefreshview);
        bitmapUtils = new BitmapUtils(getActivity());
        recommendedDataList = new ArrayList<DingYueTingRecommended.RecommendedData>();
        fragRecommendedAdapter = new FragRecommendedAdapter(getActivity(), recommendedDataList);
        frag_dingyueting_recommended_listview.setAdapter(fragRecommendedAdapter);
        linearLayout = (LinearLayout) view.findViewById(R.id.frag_dingyueting_recommended_ll);
        rsdbDao=new RSDBDao(getActivity());
        frag_dingyueting_recommended_button_refresh = (Button) view.findViewById(R.id.frag_dingyueting_recommended_button_refresh);
        fragRecommendedAdapter.setOnRemoveItemListener(this);
        deleteItemList = new ArrayList<Integer>();
        frag_dingyueting_recommended_button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    refreshData();

            }
        });

        fragRecommendedAdapter.setSubscriptionDataListener(this);
        frag_dingyueting_recommended_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getActivity(), AlbumActivity.class);
                intent.putExtra("picUrl",recommendedDataList.get(i).coverLarge);
                intent.putExtra("title",recommendedDataList.get(i).title);
                intent.putExtra("albumId",recommendedDataList.get(i).albumId);
                intent.putExtra("titleName",recommendedDataList.get(i).title);
                intent.putExtra("nickname",recommendedDataList.get(i).nickname);
                intent.putExtra("playsCounts",recommendedDataList.get(i).playsCounts);
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 添加下拉和上拉的监听事件
     */

    private boolean isFooterLoading = false;//判断是否正在加载
    private boolean isHeaderLoading = false;
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    private void setPullListener() {
        //下拉刷新的监听事件
        pullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                isHeaderLoading = true;
                //下拉刷新方式请求数据
                getData(REFRESH);

            }
        });
        //上拉加载的监听事件
        pullToRefreshView.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {


            @Override
            public void onFooterLoad(PullToRefreshView view) {
                isFooterLoading = true;
                //上拉加载方式请求数据
                getData(LOAD);
            }
        });
    }

    private void setData() {
        getData(REQUEST);
        setPullListener();
    }

    public boolean isLoadFail = false;//网络数据是否加载失败

    //获取网络数据
    private void getData(final int type) {

        //判断当前的请求方式是什么
        switch (type) {
            case REQUEST:
                pageSize = 10;
                break;
            case REFRESH:
                pageSize = 10;
                break;
            case LOAD:
                pageSize += 10;
                break;
            default:
                pageSize=type;
                Log.e("====",type+"");
                break;
        }
        String url = "http://mobile.ximalaya.com/feed/v1/recommend/classic/unlogin?device=android&pageId=1&pageSize=" + pageSize;

        HttpUtils http = new HttpUtils();

        http.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {


                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        isLoadFail = false;
                        Gson gson = new Gson();
                        DingYueTingRecommended dingYueTingRecommended = gson.fromJson(responseInfo.result, DingYueTingRecommended.class);

//                             Log.e("-----------",responseInfo.result+"");
                        if (type == LOAD) {
                            recommendedDataList.addAll(dingYueTingRecommended.data.list);
                            //加载方式请求结束后，关闭加载视图
                            pullToRefreshView.onFooterLoadFinish();
                            isFooterLoading = false;
                        } else {
                            //把album对象中的data数据集合赋值给adater使用的list集合
                            recommendedDataList = dingYueTingRecommended.data.list;
                            //刷新方式请求结束后，关闭刷新视图
                            pullToRefreshView.onHeaderRefreshFinish();
                            isHeaderLoading = false;
                        }

                        pullToRefreshView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        pullToRefreshView.setPullRefreshEnable(true);

                        if (deleteItemList != null) {
                            List<Integer> dataList=new ArrayList<Integer>();//添加请求的网络数据albumId的集合
                            for (int i=0;i<recommendedDataList.size();i++){
                                dataList.add(recommendedDataList.get(i).albumId);
                            }
                           List<Integer> resultList=new ArrayList<Integer>();//装载过滤掉的数据albumId
                            resultList= (List<Integer>) CollectionUtil.getDiffent(dataList,deleteItemList);
                            List<DingYueTingRecommended.RecommendedData> resultRecommendedDataList=new ArrayList<DingYueTingRecommended.RecommendedData>();//添加要显示的信息
                            for (int i=0;i<recommendedDataList.size();i++){
                                for (int j=0;j<resultList.size();j++){
                                    if (recommendedDataList.get(i).albumId==resultList.get(j)){
                                        resultRecommendedDataList.add(recommendedDataList.get(i));
                                        break;
                                    }
                                }
                            }
                            if(resultRecommendedDataList.size()<10){
                                getData(LOAD);
                                return;
                            }
                            fragRecommendedAdapter.setFreshData(resultRecommendedDataList); //刷新adatper数据
                        }else {
                            if (recommendedDataList!=null){
                                fragRecommendedAdapter.setFreshData(recommendedDataList); //刷新adatper数据
                            }
                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getActivity(), "网络加载失败！，请稍后尝试！", Toast.LENGTH_LONG).show();
                        if (isFooterLoading) {
                            pullToRefreshView.onFooterLoadFinish();
                            isFooterLoading = false;
                        } else if (isHeaderLoading) {
                            pullToRefreshView.onHeaderRefreshFinish();
                            isHeaderLoading = false;
                        }
                        isLoadFail = true;
                        if (isLoadFail){
                            pullToRefreshView.onHeaderRefreshFinish();
                            pullToRefreshView.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
    final String tableName = "dingyueting_subscription_table";//数据库表名字

    public  List<Integer> getDateFormDB() {
        List<Integer> deleteList=new ArrayList<>();
        cursor = rsdbDao.selectColumn(tableName,"albumId");
        if (cursor!=null){
            while (cursor.moveToNext()) {
                int albumId = cursor.getInt(cursor.getColumnIndex("albumId"));
                deleteList.add(albumId);
            }
            return deleteList;
        }
        return null;
    }

    ProgressDialog dialog;

    private void refreshData() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在为您努力加载...");
        dialog.show();
        String url = "http://mobile.ximalaya.com/feed/v1/recommend/classic/unlogin?device=android&pageId=1&pageSize=10";
        HttpUtils http1 = new HttpUtils();
        http1.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Gson gson = new Gson();
                        DingYueTingRecommended dingYueTingRecommended = gson.fromJson(responseInfo.result, DingYueTingRecommended.class);

                        Log.e("refreshData", responseInfo.result + "");
                            if (isNetworkConnected(getActivity())){
                                //把album对象中的data数据集合赋值给adater使用的list集合
                                recommendedDataList = dingYueTingRecommended.data.list;
                                //刷新方式请求结束后，关闭刷新视图

//                                     pullToRefreshView.onFooterLoadFinish();
                                pullToRefreshView.setPullRefreshEnable(true);
                                linearLayout.setVisibility(View.GONE);
                                pullToRefreshView.setVisibility(View.VISIBLE);


                                fragRecommendedAdapter.setFreshData(recommendedDataList); //刷新adatper数据
                            }else {

                            }
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        pullToRefreshView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void getDeleteItem() {
        deleteItemList =getDateFormDB();
    }

//向Frag_dingyueting传递订阅数据的接口
    @Override
    public void sendSubscriptionData() {
        Log.e("sendSubscriptionData", "sendSubscriptionData");
        sendSubscriptionDataListener.sendSubscriptionData();
    }
    private SendSubscriptionDataListener sendSubscriptionDataListener;
    public interface SendSubscriptionDataListener {
        public void sendSubscriptionData();
    }
    public void setOnSendSubscriptionListener(SendSubscriptionDataListener listener){
          this.sendSubscriptionDataListener=listener;
    }
}

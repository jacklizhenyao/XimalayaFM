package com.lanou.radiostation.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.MainActivity;
import com.lanou.radiostation.adapter.RecommendLvAdapter;
import com.lanou.radiostation.adapter.RecommendVpAdapter;
import com.lanou.radiostation.bean.Recommend;
import com.lanou.radiostation.bean.RecommendFooter;
import com.lanou.radiostation.util.HttpUtils;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是推荐的fragment
 * Created by user on 2016/7/22.
 */
public class RecommendFragment extends BaseFragment {
    private ListView recommendLv;
    private List<Recommend> recommendLvList = new ArrayList<>();
    private List<Recommend.HotRecommends> hotRecommendsLvList = new ArrayList<>();
    private RecommendLvAdapter lvAdapter;

    private RelativeLayout rl,frag_recommend_footer_rl;

    private ViewPager recommendVp,footerVp;
    private List<ImageView> recommendVpList = new ArrayList<>();
    private List<ImageView> footerVpList = new ArrayList<>();
    private RecommendVpAdapter vpAdapter;
    private RecommendVpAdapter footervpAdapter;
    int currentItem = 0;

    LinearLayout llDoc;
    //存放vp的小圆点
    List<ImageView> vpDoc = new ArrayList<>();


    private String footerUrl = "http://adse.ximalaya.com/ting?device=android&name=find_banner&network=wifi&operator=0&version=5.4.21";
    private Recommend recommend;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (recommendVpList != null && recommendVpList.size() > 0) {
                currentItem++;
                recommendVp.setCurrentItem(currentItem % recommendVpList.size());
                handler.sendEmptyMessageDelayed(0, 2000);

            }
        }
    };
    private ProgressDialog dialog;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.frag_recommend, null);
        initViews(view);

        return view;
    }

    private void initViews(View view) {
        recommendLv = (ListView) view.findViewById(R.id.frag_recommend_lv);
        rl = (RelativeLayout) view.findViewById(R.id.frag_recommend_rl);
        rl.setVisibility(View.VISIBLE);
//        dialog = new ProgressDialog(getActivity());
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setMessage("正在加载中...");
//        dialog.show();
    }


    @Override
    public void setData() {

        new MyAsyncTask().execute();
        lvAdapter = new RecommendLvAdapter(getActivity(), recommendLvList, hotRecommendsLvList);
        View view = View.inflate(getActivity(), R.layout.frag_recommend_head, null);
        View viewFooter = View.inflate(getActivity(),R.layout.frag_recommend_footer,null);
        footerVp = (ViewPager) viewFooter.findViewById(R.id.frag_recommend_footer_vp);
        frag_recommend_footer_rl = (RelativeLayout) viewFooter.findViewById(R.id.frag_recommend_footer_rl);

        com.lidroid.xutils.HttpUtils utils = new com.lidroid.xutils.HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, footerUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                RecommendFooter recommendFooter = gson.fromJson(result, RecommendFooter.class);
                for (int i = 0; i < recommendFooter.data.size(); i++) {
                    ImageView imageView = new ImageView(getActivity());
                    ImageLoaderUtils.getImageByloader(recommendFooter.data.get(i).cover,imageView);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    footerVpList.add(imageView);
                }

                footervpAdapter = new RecommendVpAdapter(footerVpList);
                footerVp.setAdapter(footervpAdapter);
                
                
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

        recommendVp = (ViewPager) view.findViewById(R.id.frag_recommend_head_vp);
        llDoc = (LinearLayout) view.findViewById(R.id.frag_recommend_head_ll);



        recommendLv.addHeaderView(view);
        recommendLv.addFooterView(viewFooter);
        recommendLv.setAdapter(lvAdapter);

        vpAdapter = new RecommendVpAdapter(recommendVpList);
        recommendVp.setAdapter(vpAdapter);

        recommendVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                swichDoc(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String s = HttpUtils.requestStringByGet("http://mobile.ximalaya.com/mobile/" +
                    "discovery/v1/recommends?channel=yz-xm&device=androi" +
                    "d&includeActivity=true&includeSpecial=true&scale=2" +
                    "&version=4.3.20.14");

            publishProgress();
            return s;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (TextUtils.isEmpty(s)) {
                Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                return;

            } else {
                Gson gson = new Gson();
                hotRecommendsLvList.clear();

                recommend = gson.fromJson(s, Recommend.class);
                for (int i = 0; i < recommend.hotRecommends.list.size(); i++) {
                    hotRecommendsLvList.add(recommend.hotRecommends);
                }
                recommendLvList.add(recommend);
                lvAdapter.setListener(recommendLvList);

                recommendVpList.clear();
                for (int i = 0; i < recommend.focusImages.list.size(); i++) {
                    ImageView iv = new ImageView(getActivity());
                    ImageLoaderUtils.getImageByloader(recommend.focusImages.list.get(i).pic, iv);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    recommendVpList.add(iv);
                }
                vpAdapter.setListener(recommendVpList);

                setVPDoc();
                roll();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            rl.setVisibility(View.GONE);
//            dialog.dismiss();
        }
    }

    /**
     * 设置ViewPager小圆点
     */
    private void setVPDoc() {
        vpDoc.clear();
        for (int i = 0; i < recommendVpList.size(); i++) {
            ImageView ivDoc = new ImageView(getActivity());

            llDoc.addView(ivDoc);
            /*
			 * 1、通过控件获取到设置属性的params对象
			 * 2、给params添加属性
			 * 3、把设置好的属性对象交给我们的控件
			 */
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivDoc.getLayoutParams();
            params.width = 10;
            params.height = 10;
            params.setMargins(0, 0, 15, 0);
            ivDoc.setLayoutParams(params);
            ivDoc.setImageResource(R.drawable.shap_docll);
            vpDoc.add(ivDoc);
        }
        //给小圆点设置一个初值颜色
        if (recommendVpList != null && recommendVpList.size() > 0) {
            vpDoc.get(0).setImageResource(R.drawable.shap_docll_end);
        }
    }

    /**
     * 根据position切换圆点的颜色
     *
     * @param position
     */
    private void swichDoc(int position) {
        for (int i = 0; i < vpDoc.size(); i++) {
            //找到position图片的位置，设置红色
            if (position == i) {
                vpDoc.get(i).setImageResource(R.drawable.shap_docll_end);
            } else {
                vpDoc.get(i).setImageResource(R.drawable.shap_docll);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

    }

    public void roll() {
        if (handler.hasMessages(0)) {
            handler.removeMessages(0);
        }
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public void onStop() {
        handler.removeMessages(0);
        super.onStop();
    }
}

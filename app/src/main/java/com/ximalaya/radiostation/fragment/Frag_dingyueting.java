package com.lanou.radiostation.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.FragmentAdapter;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.view.ViewPagerIndicator;
import com.lanou.radiostation.view.ViewPagerIndicator_recomended;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class Frag_dingyueting extends Fragment implements Frag_dingyueting_recommended.SendSubscriptionDataListener {
    List<Fragment> fragmentList;//装载推荐、订阅、历史模块的fragment
    View view;
    FragmentPagerAdapter fragmentPagerAdapter;//解析推荐、订阅、历史模块的fragment
    private ViewPager frag_dingyueting_vp;
    private Frag_dingyueting_recommended frag_dingyueting_recommended;
    public Frag_dingyueting_subscription frag_dingyueting_subscription;
    public Frag_dingyueting_history frag_dingyueting_history;
    private ViewPagerIndicator_recomended viewPagerIndicator;
    private TextView frag_dingyueting_tv_recommended,frag_dingyueting_tv_subscription,frag_dingyueting_tv_history;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
//        getActivity().unregisterReceiver(updateBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }else {
           //刷新数据库
            frag_dingyueting_history.refreshData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=View.inflate(getActivity(), R.layout.frag_dingyueting,null);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setDate();
        super.onActivityCreated(savedInstanceState);
    }

    public void setDate() {
        fragmentList.add(frag_dingyueting_recommended);
        fragmentList.add(frag_dingyueting_subscription);
        fragmentList.add(frag_dingyueting_history);
        fragmentPagerAdapter=new FragmentAdapter(getChildFragmentManager(),fragmentList);
        frag_dingyueting_vp.setAdapter(fragmentPagerAdapter);
    }

    private void initView() {
        frag_dingyueting_tv_recommended=(TextView)view.findViewById(R.id.frag_dingyueting_tv_recommended);
        final int textColor= Color.rgb(230,114,87) ;//头部标题字体颜色
        frag_dingyueting_tv_recommended.setTextColor(textColor);//初始化时的字体颜色
        frag_dingyueting_tv_subscription=(TextView)view.findViewById(R.id.frag_dingyueting_tv_subscription);
        frag_dingyueting_tv_history=(TextView)view.findViewById(R.id.frag_dingyueting_tv_historys);
        frag_dingyueting_vp = (ViewPager) view.findViewById(R.id.frag_dingyueting_vp);
        fragmentList=new ArrayList<Fragment>();
        viewPagerIndicator=(ViewPagerIndicator_recomended) view.findViewById(R.id.frag_dingyueting_viewpagerindicator);
        frag_dingyueting_recommended=  new Frag_dingyueting_recommended();
        frag_dingyueting_subscription=  new Frag_dingyueting_subscription();
        frag_dingyueting_history=new Frag_dingyueting_history();
        frag_dingyueting_recommended.setOnSendSubscriptionListener(this);
        frag_dingyueting_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                viewPagerIndicator.scroll(position,positionOffset);
                //随着vp滑动时字体颜色的改变
                switch (position){
                    case 0:
                        frag_dingyueting_tv_recommended.setTextColor(textColor);
                        frag_dingyueting_tv_subscription.setTextColor(Color.BLACK);
                        frag_dingyueting_tv_history.setTextColor(Color.BLACK);
                        break;
                    case 1:
                        frag_dingyueting_tv_subscription.setTextColor(textColor);
                        frag_dingyueting_tv_recommended.setTextColor(Color.BLACK);
                        frag_dingyueting_tv_history.setTextColor(Color.BLACK);
                        break;
                    case 2:
                        frag_dingyueting_tv_history.setTextColor(textColor);
                        frag_dingyueting_tv_recommended.setTextColor(Color.BLACK);
                        frag_dingyueting_tv_subscription.setTextColor(Color.BLACK);
                        break;
                }
                frag_dingyueting_subscription.setOnupdateListenr(new Frag_dingyueting_subscription.OnupdateDBListener() {
                    @Override
                    public void updateDB() {
                        frag_dingyueting_recommended.getDeleteItem();
                    }

                    @Override
                    public void goToRecom() {
                        frag_dingyueting_vp.setCurrentItem(0);
                    }
                });


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
//接收来自Frag_dingyueting_recom的数据
    @Override
    public void sendSubscriptionData() {
        frag_dingyueting_subscription.RefreshData();
    }
}

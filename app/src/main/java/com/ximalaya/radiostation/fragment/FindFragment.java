package com.lanou.radiostation.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.SearchActivity;
import com.lanou.radiostation.adapter.FindAdapter;
import com.lanou.radiostation.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class FindFragment extends BaseFragment implements View.OnClickListener{

    private ViewPager findViewPager;
    private ViewPagerIndicator mIndicator;
    private FindAdapter mAdapter;
    private List<BaseFragment> mList = new ArrayList<BaseFragment>();
    private TextView recommendTv,classifyTv,broadcastTv,listTv,anchorTv;
    private ImageButton frag_find_searchIb;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.frag_find,null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        frag_find_searchIb = (ImageButton) view.findViewById(R.id.frag_find_search);
        findViewPager = (ViewPager) view.findViewById(R.id.frag_find_vp);
        mIndicator = (ViewPagerIndicator) view.findViewById(R.id.indicator);
        recommendTv = (TextView) view.findViewById(R.id.find_recommend_tv);
        classifyTv = (TextView) view.findViewById(R.id.find_classify_tv);
        broadcastTv = (TextView) view.findViewById(R.id.find_broadcast_tv);
        listTv = (TextView) view.findViewById(R.id.find_list_tv);
        anchorTv = (TextView) view.findViewById(R.id.find_anchor_tv);
        recommendTv.setTextColor(Color.parseColor("#E72F18"));

        recommendTv.setOnClickListener(this);
        classifyTv.setOnClickListener(this);
        broadcastTv.setOnClickListener(this);
        listTv.setOnClickListener(this);
        anchorTv.setOnClickListener(this);

        findViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position ,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                switchTv();
                switch (position){
                    case 0:
                        recommendTv.setTextColor(Color.parseColor("#E72F18"));
                        break;
                    case 1:
                        classifyTv.setTextColor(Color.parseColor("#E72F18"));
                        break;
                    case 2:
                        broadcastTv.setTextColor(Color.parseColor("#E72F18"));
                        break;
                    case 3:
                        listTv.setTextColor(Color.parseColor("#E72F18"));
                        break;
                    case 4:
                        anchorTv.setTextColor(Color.parseColor("#E72F18"));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        frag_find_searchIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });


    }

    @Override
    public void setData() {
        RecommendFragment recommendFragment = new RecommendFragment();
        mList.add(recommendFragment);

        ClassifyFragment classifyFragment = new ClassifyFragment();
        mList.add(classifyFragment);

        BroadcastFragment broadcastFragment = new BroadcastFragment();
        mList.add(broadcastFragment);

        ListFragment listFragment = new ListFragment();
        mList.add(listFragment);

        AnchorFragment anchorFragment = new AnchorFragment();
        mList.add(anchorFragment);


        mAdapter = new FindAdapter(getChildFragmentManager(),mList);
        findViewPager.setAdapter(mAdapter);
    }

    private void switchTv(){
        recommendTv.setTextColor(Color.parseColor("#000000"));
        classifyTv.setTextColor(Color.parseColor("#000000"));
        broadcastTv.setTextColor(Color.parseColor("#000000"));
        listTv.setTextColor(Color.parseColor("#000000"));
        anchorTv.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public void onClick(View view) {
        switchTv();
        switch (view.getId()){
            case R.id.find_recommend_tv:
                recommendTv.setTextColor(Color.parseColor("#E72F18"));
                findViewPager.setCurrentItem(0);
                break;
            case R.id.find_classify_tv:
                classifyTv.setTextColor(Color.parseColor("#E72F18"));
                findViewPager.setCurrentItem(1);
                break;
            case R.id.find_broadcast_tv:
                broadcastTv.setTextColor(Color.parseColor("#E72F18"));
                findViewPager.setCurrentItem(2);
                break;
            case R.id.find_list_tv:
                listTv.setTextColor(Color.parseColor("#E72F18"));
                findViewPager.setCurrentItem(3);
                break;
            case R.id.find_anchor_tv:
                anchorTv.setTextColor(Color.parseColor("#E72F18"));
                findViewPager.setCurrentItem(4);
                break;
        }
    }
}

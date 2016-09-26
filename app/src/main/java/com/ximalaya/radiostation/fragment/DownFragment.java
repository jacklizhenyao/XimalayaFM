package com.lanou.radiostation.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.MyFragPagerAdapter;
import com.lanou.radiostation.view.ViewPagerIndicator;
import com.lanou.radiostation.view.ViewPagerIndicatorthree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class DownFragment extends BaseFragment implements View.OnClickListener {
    private ViewPager load_frag_vp;
    private List<Fragment> list;
    private MyFragPagerAdapter adapter;
    private ViewPagerIndicatorthree view_indicator;
    private Fragment AlbumFragment,SoundFragment,DownloadFragment;
    private TextView myvpfrag_album_tv,myvpfrag_sound_tv,myvpfrag_download_tv;
    @Override
    public View initView() {
        View view =View.inflate(getActivity(), R.layout.myvpfragment,null);
        load_frag_vp = (ViewPager) view.findViewById(R.id.load_frag_vp);
        view_indicator = (ViewPagerIndicatorthree) view.findViewById(R.id.view_indicator);
        myvpfrag_album_tv = (TextView) view.findViewById(R.id.myvpfrag_album_tv);
        myvpfrag_sound_tv = (TextView) view.findViewById(R.id.myvpfrag_sound_tv);
        myvpfrag_download_tv = (TextView) view.findViewById(R.id.myvpfrag_download_tv);
        //第一个tv的颜色
        resetTextView();
        myvpfrag_album_tv.setTextSize(22);
        myvpfrag_album_tv.setTextColor(Color.RED);
        myvpfrag_album_tv.setOnClickListener(this);
        myvpfrag_sound_tv.setOnClickListener(this);
        myvpfrag_download_tv.setOnClickListener(this);
        list = new ArrayList<>();
        setFragment();


       return view;
    }

    private void setFragment(){
        DownloadFragment = new DownloadFragment();
        AlbumFragment = new AlbumFragment();
        SoundFragment = new SoundFragment();
        list.add(AlbumFragment);
        list.add(DownloadFragment);
        list.add(SoundFragment);
        adapter =new MyFragPagerAdapter(getChildFragmentManager(),list);
        load_frag_vp.setAdapter(adapter);
        /**
         * vp滑动切换三个fragemnt布局
         */
        load_frag_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                view_indicator.scroll(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        myvpfrag_album_tv.setTextSize(22);
                        myvpfrag_album_tv.setTextColor(Color.RED);
                        break;
                    case 1:
                        myvpfrag_sound_tv.setTextSize(22);
                        myvpfrag_sound_tv.setTextColor(Color.RED);
                        break;
                    case 2:
                        myvpfrag_download_tv.setTextSize(22);
                        myvpfrag_download_tv.setTextColor(Color.RED);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetTextView() {

        myvpfrag_album_tv.setTextSize(18);
        myvpfrag_album_tv.setTextColor(Color.BLACK);
        myvpfrag_sound_tv.setTextSize(18);
        myvpfrag_sound_tv.setTextColor(Color.BLACK);
        myvpfrag_download_tv.setTextSize(18);
        myvpfrag_download_tv.setTextColor(Color.BLACK);
    }

    /**
     * tv切换布局的方法
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myvpfrag_album_tv:
                load_frag_vp.setCurrentItem(0);
                break;
            case R.id.myvpfrag_sound_tv:
                load_frag_vp.setCurrentItem(1);
                break;
            case R.id.myvpfrag_download_tv:
                load_frag_vp.setCurrentItem(2);
                break;
        }

    }





    @Override
    public void setData() {

    }
}

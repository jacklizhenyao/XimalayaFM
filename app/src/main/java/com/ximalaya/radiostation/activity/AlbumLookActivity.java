package com.lanou.radiostation.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lanou.radiostation.R;

import com.lanou.radiostation.adapter.MyBasePageAdapter;
import com.lanou.radiostation.fragment.BaseFragment;
import com.lanou.radiostation.fragment.ClassicFragment;
import com.lanou.radiostation.fragment.FireFragment;
import com.lanou.radiostation.fragment.NewFragment;

import java.util.ArrayList;
import java.util.List;

public class AlbumLookActivity extends FragmentActivity {
    private RadioGroup albumlook_top_rg;
    private RadioButton fire_btn, newest_btn, classic_btn;
    private List<BaseFragment> list;
    private MyBasePageAdapter adapter;
    private BaseFragment fireFragment, newFragment, classicFragment;
    private FrameLayout main_fl;
    private FragmentManager manager;
    private FragmentTransaction fragmentTransaction;
private ImageButton include_ib_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_look);
        initView();

    }

    private void initView() {
        manager = getSupportFragmentManager();
        main_fl = (FrameLayout) findViewById(R.id.main_fl);
        albumlook_top_rg = (RadioGroup) findViewById(R.id.albumlook_top_rg);
        fire_btn = (RadioButton) findViewById(R.id.fire_btn);
        newest_btn = (RadioButton) findViewById(R.id.newest_btn);
        classic_btn = (RadioButton) findViewById(R.id.classic_btn);
//        fire_btn.setOnClickListener(this);
//        newest_btn.setOnClickListener(this);
//        classic_btn.setOnClickListener(this);
        include_ib_back = (ImageButton) findViewById(R.id.include_ib_back);
        include_ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fire_btn.setSelected(true);
        setClick(1);
        list = new ArrayList<>();

        adapter = new MyBasePageAdapter(getSupportFragmentManager(), list);

        albumlook_top_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                fire_btn.setSelected(false);
                newest_btn.setSelected(false);
                 classic_btn.setSelected(false);
                switch (i){
                    case R.id.fire_btn:
                        fire_btn.setSelected(true);
                        setClick(1);
                        break;
                    case R.id.newest_btn:
                        newest_btn.setSelected(true);
                        setClick(2);
                        break;
                    case R.id.classic_btn:
                        classic_btn.setSelected(true);
                        setClick(3);
                        break;
                    default:
                        break;
                }
            }
        });
    }



    private void setClick(int i) {
        fragmentTransaction = manager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (i) {
            //让会话按钮所对应的fragment显示
            case 1:
                if (fireFragment==null){
                    fireFragment= new FireFragment();
                    fragmentTransaction.add(R.id.main_fl,fireFragment);
                }else {
                    fragmentTransaction.show(fireFragment);
                }
                break;
            case 2:
                if(newFragment==null) {
                newFragment = new NewFragment();
                fragmentTransaction.add(R.id.main_fl, newFragment);
            }else {
                    fragmentTransaction.show(newFragment);
                }
                break;
            case 3:
                if(classicFragment ==null){
       classicFragment= new ClassicFragment();
        fragmentTransaction.add(R.id.main_fl,classicFragment);

                }else {
                    fragmentTransaction.show(classicFragment);
                }

                break;
  default:
    break;
        }
        fragmentTransaction.commit();

    }

    //这个方法的作用是隐藏已经存在的所有的fragment
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if ( fireFragment != null ){
            fragmentTransaction.hide(fireFragment);
        }
        if ( newFragment != null ){
            fragmentTransaction.hide(newFragment);
        }
        if ( classicFragment != null ){
            fragmentTransaction.hide(classicFragment);
        }
    }
}

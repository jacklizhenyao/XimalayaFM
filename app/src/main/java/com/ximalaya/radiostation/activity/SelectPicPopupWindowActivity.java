package com.lanou.radiostation.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lanou.radiostation.R;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class SelectPicPopupWindowActivity extends Activity {

    private Button pic_window_buttom_btn;
    private ImageView pic_window_shareiv;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(getApplicationContext());

        setContentView(R.layout.activity_select_pic_popup_window);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 1.0);   //高度设置为屏幕的1.0
        p.width = (int) (d.getWidth());    //宽度设置为屏幕的0.8
        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.0f;      //设置黑暗度
        getWindow().setAttributes(p);

        title = getIntent().getStringExtra("title");

        initView();
    }

    private void initView() {

        pic_window_shareiv = (ImageView) findViewById(R.id.pic_window_shareiv);
        pic_window_buttom_btn = (Button) findViewById(R.id.pic_window_buttom_btn);

        pic_window_buttom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pic_window_shareiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //一键分享功能
                OnekeyShare oneKeyShare = new OnekeyShare();
                //设置分享的标题
                oneKeyShare.setTitle(title);
                //设置信息
                oneKeyShare.setText("听喜马拉雅");

                oneKeyShare.setTitleUrl("http://www.ximalaya.com/explore/");


                //设置图标
//				oneKeyShare.setImagePath(imagePath);

                //显示分享列表
                oneKeyShare.show(SelectPicPopupWindowActivity.this);
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}

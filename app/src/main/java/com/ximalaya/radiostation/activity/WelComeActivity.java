package com.lanou.radiostation.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.bean.WelCome;
import com.lanou.radiostation.util.ImageLoaderUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class WelComeActivity extends AppCompatActivity {

    private String url = "http://adse.ximalaya.com/ting/loading?androidId=e025fc53f2a025d9c5013367462c809338b23c7c&appid=0&device=android&name=loading&network=wifi&operator=0&userAgent=Mozilla/5.0%20(Linux;%20Android%204.4.2;%20NoxW%20Build/KOT49H)%20AppleWebKit/537.36%20(KHTML,%20like%20Gecko)%20Version/4.0%20Chrome/30.0.0.0%20Mobile%20Safari/537.36&version=5.4.21";
    private ImageView welcome_iv;
    private Button welcome_btn;
    private int count = 3;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                startActivity(new Intent(WelComeActivity.this,MainActivity.class));
                finish();
            }else if(msg.what == 1){
                welcome_btn.setText("跳过 "+count);
                count--;
                handler.sendEmptyMessageDelayed(1,1000);
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come);
        welcome_iv = (ImageView) findViewById(R.id.welcome_iv);

        welcome_btn = (Button) findViewById(R.id.welcome_btn);

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Gson gson = new Gson();
                final WelCome welCome = gson.fromJson(responseInfo.result, WelCome.class);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ImageLoaderUtils.getImageByloader(welCome.data.get(0).cover,welcome_iv);
                welcome_iv.setScaleType(ImageView.ScaleType.FIT_XY);
                welcome_btn.setVisibility(View.VISIBLE);
                handler.sendEmptyMessage(1);

                welcome_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(WelComeActivity.this,MainActivity.class));
                        handler.removeMessages(0);
                        finish();
                    }
                });

                welcome_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(WelComeActivity.this,WelComeWebActivity.class);
                        intent.putExtra("url",welCome.data.get(0).link);
                        startActivity(intent);
                        handler.removeMessages(0);
                        finish();
                    }
                });

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });



        handler.sendEmptyMessageDelayed(0,4000);
    }
}

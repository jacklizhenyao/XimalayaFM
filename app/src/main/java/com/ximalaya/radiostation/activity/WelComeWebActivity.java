package com.lanou.radiostation.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lanou.radiostation.R;

public class WelComeWebActivity extends AppCompatActivity {

    private String url;
    private WebView viewById;
    private ProgressDialog pd;
    private TextView welcome_tv_back,welcome_tv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come_web);

        url = getIntent().getStringExtra("url");

        initView();
    }

    private void initView() {
        welcome_tv_back = (TextView) findViewById(R.id.welcome_tv_back);
        welcome_tv_close = (TextView) findViewById(R.id.welcome_tv_close);


        viewById = (WebView) findViewById(R.id.welcome_webView);
        viewById.getSettings().setJavaScriptEnabled(true);
//        viewById.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        WebSettings webSettings = viewById.getSettings();
        viewById.getSettings().setDefaultTextEncodingName("gb2312");

        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);

        viewById.setWebViewClient(new WebViewClient(){
            public void onReceivedSslError(WebView view, android.webkit.SslErrorHandler handler, android.net.http.SslError error) {

                //handler.cancel(); // Android默认的处理方式
                handler.proceed();  // 接受所有网站的证书
                //handleMessage(Message msg); // 进行其他处理


            };
        } );

        pd = new ProgressDialog(this);
        pd.setMessage("加载中....");

        viewById.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    WelComeWebActivity.this.setTitle("加载完成");
                    pd.dismiss();
                } else {
                    WelComeWebActivity.this.setTitle("加载中.......");
                    pd.show();
                }
            }
        });

        viewById.loadUrl(url);






//        viewById.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(final WebView view,
//                                                    final String url) {
//                loadurl(view, url);
//                return true;
//            }
//        });


        welcome_tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelComeWebActivity.this,MainActivity.class));
                finish();
            }
        });
        welcome_tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelComeWebActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            startActivity(new Intent(WelComeWebActivity.this,MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

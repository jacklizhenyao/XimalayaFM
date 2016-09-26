package com.lanou.radiostation.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lanou.radiostation.R;

public class ListWebActivity extends AppCompatActivity {

    private WebView viewById;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_web);
        initView();
    }


    private void initView() {
        viewById = (WebView) findViewById(R.id.listweb_wv);
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
                    ListWebActivity.this.setTitle("加载完成");
                    pd.dismiss();
                } else {
                    ListWebActivity.this.setTitle("加载中.......");
                    pd.show();
                }
            }
        });

        viewById.loadUrl("http://m.ximalaya.com/xmposter/top_list?app=iting");
    }

}

package com.lanou.radiostation.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lanou.radiostation.R;

/**
 * Created by user on 2016/7/30.
 */
public class VideoActivity extends Activity{
    private WebView viewById;
    private ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
    }

    private void initView() {
        viewById = (WebView) findViewById(R.id.webView1);
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
                    VideoActivity.this.setTitle("加载完成");
                    pd.dismiss();
                } else {
                    VideoActivity.this.setTitle("加载中.......");
                    pd.show();
                }
            }
        });

        viewById.loadUrl("http://game.ximalaya.com/games-operation/v1/games/list?ly=fxxq&app=iting&version=5.4.21.3");






//        viewById.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(final WebView view,
//                                                    final String url) {
//                loadurl(view, url);
//                return true;
//            }
//        });
    }
}

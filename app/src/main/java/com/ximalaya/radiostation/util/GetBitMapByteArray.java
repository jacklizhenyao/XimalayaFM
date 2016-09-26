package com.lanou.radiostation.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2016/7/28.
 */
public class GetBitMapByteArray {

    // bitmapQuality 网络请求的图片被压缩处理后的质量 范围：1-100
    private  int bitmapQuality;
    private String path;//图片网络地址
    private Thread td;
    private RequestCallBack requestCallBack;
    //接口 负责处理网络请求失败和成功的方法
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x1) {
                if (msg.obj != null) {
                    byte[] imageData = (byte[]) msg.obj;
                    requestCallBack.onSuccess(imageData);
                    if (td != null) {
                        td.interrupt();
                        handler.removeCallbacksAndMessages(null);
                    }
                } else {
                    requestCallBack.onFailure();
                }
            }
        }

    };


    public void setData(String url,int Quality,RequestCallBack callBack) {
        this.path = url;
        this.bitmapQuality=Quality;
        this.requestCallBack=callBack;
        td = new Thread(runnable);
        td.start();
    }


//    public void onHandleDestroy(){
//        if (td != null) {
//            td.interrupt();
//            handler.removeCallbacksAndMessages(null);
//        }
//    }
    public interface RequestCallBack {
        public void onSuccess(byte[] result);
        public void onFailure();
    }

  private   Runnable runnable = new Runnable() {
        @Override
        public void run() {
            InputStream is=null;
            ByteArrayOutputStream baos=null;
            try {
                //1. 将路径转换为URL
                URL url = new URL(path);
                Log.e("url",path+"");
                //2. 打开网络链接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //3. 设置请求方式
                conn.setRequestMethod("GET");
                //4.设置超时时间
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                //5.设置允许写入
                conn.setDoInput(true);
                //6.判断链接是否成功

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //获取输入流
                    is = conn.getInputStream();
                    //将流转换为位图
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    int size = bitmap.getWidth() * bitmap.getHeight() * 4;
                    //创建一个字节数组输出流,流的大小为size
                     baos = new ByteArrayOutputStream(size);
                    //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
                    bitmap.compress(Bitmap.CompressFormat.JPEG, bitmapQuality, baos);
                    //将字节数组输出流转化为字节数组byte[]
                    byte[] imageData = baos.toByteArray();
                    //关闭连接
                    is.close();
                    conn.disconnect();
                    //使用Handler将Bitmap发送数据
                    Message msg = handler.obtainMessage();
                    msg.what = 0x1;
                    msg.obj = imageData;
                    handler.sendMessage(msg);
                    //关闭字节数组输出流
                    baos.close();
                }else {
                    Log.e("fsdfsdfsdfsdf","fsdfsdfsdfsdfdsf");
                    requestCallBack.onFailure();
                }
            } catch (Exception e) {

            } finally {
                try {
                    if (is!=null&&baos!=null){
                        is.close();
                        baos.close();
                    }else {
                        requestCallBack.onFailure();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}

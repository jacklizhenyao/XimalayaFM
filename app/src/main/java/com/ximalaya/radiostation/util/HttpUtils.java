package com.lanou.radiostation.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpUtils {
    /**
     * 通过get请求获取字符串
     * URL
     * HttpURLConnection
     * IO输入输出流
     * @return
     */
    public static String requestStringByGet(String path){
        //网络请求需要捕获异常
        try {
            //1、把字符串类型的地址，转换成URL对象
            URL url = new URL(path);
            //2、使用URL对象打开网络连接
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            //3、设置请求的模式 和属性等
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            //4、先判断请求是否成功
            if(connection.getResponseCode() == 200){
                //5、拿到请求的输入流
                InputStream inputStream = connection.getInputStream();
                //6、把输入流读到输出流中
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
				/*
				 * 循环读取inputStream中的数据，一次读一个字节
				 * 如果读取完毕，会返回一个-1
				 * 那么我们就用这个条件来做循环判断
				 */
                while((len = inputStream.read(buffer)) != -1){
					/*
					 * 把buffer中的字节写入到输出流，
					 * 从第0位开始到第len位
					 */
                    outputStream.write(buffer, 0, len);
                }
                //7、把输出流转换成字符串
                String result = new String(outputStream.toByteArray());
                //8、关闭输入输出流
                inputStream.close();
                outputStream.close();
                //9、返回字符串
                return result;
            }else{
                //请求失败直接返回空
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过get请求获取图片
     * URL
     * HttpURLConnection
     * IO输入输出流
     * @return
     */
    public static Bitmap requestBitmapByGet(String path){
        //网络请求需要捕获异常
        try {
            //1、把字符串类型的地址，转换成URL对象
            URL url = new URL(path);
            //2、使用URL对象打开网络连接
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            //3、设置请求的模式 和属性等
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            //4、先判断请求是否成功
            if(connection.getResponseCode() == 200){
                //5、拿到请求的输入流
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //6、关闭输入输出流
                inputStream.close();
                //7、返回bitmap
                return bitmap;
            }else{
                //请求失败直接返回空
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}


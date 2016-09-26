package com.lanou.radiostation.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_recom;
import com.lanou.radiostation.bean.DingYueTingRecommended;
import com.lanou.radiostation.db.RSDBDao;
import com.lanou.radiostation.util.GetBitMapByteArray;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class FragRecommendedAdapter extends BaseAdapter {
    List<DingYueTingRecommended.RecommendedData> list;
    Context context;
    RSDBDao rsdbDao;
    Cursor cursor;
    GetBitMapByteArray getBitMapByteArray;//获取相片的字节流
   final String tableName = "dingyueting_subscription_table";//数据库表名字
    public FragRecommendedAdapter(Context context, List<DingYueTingRecommended.RecommendedData> list) {
        this.list = list;
        this.context = context;
        rsdbDao =new RSDBDao(context);
        getBitMapByteArray=new GetBitMapByteArray();
    }

    @Override
    public int getCount() {

        return list != null ? list.size() : 0;
    }

    int i;

    @Override
    public Object getItem(int i) {

        return list != null ? list.get(i) : null;

    }

    @Override
    public long getItemId(int i) {
        return list != null ? i : 0;
    }

    //  int i=0;
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        BitmapUtils bitmapUtils = new BitmapUtils(context);
        ViewHolder viewholder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_frag_dingyueting_recommended, null);
            viewholder = new ViewHolder();
            viewholder.imageView_album = (ImageView) view.findViewById(R.id.item_frag_dingyueting_recom_imageview_album_background);//专辑图片
            viewholder.textView_recom_title = (TextView)
                    view.findViewById(R.id.item_frag_dingyueting_recom_textview_recom_title);//专辑标题
            viewholder.textView_recom = (TextView)
                    view.findViewById(R.id.item_frag_dingyueting_recom_textview_recom);//专题推荐
            viewholder.textView_paly_times = (TextView)
                    view.findViewById(R.id.item_frag_dingyueting_recom_textview_playtimes_count);//播放次数
            viewholder.textView_sounds_counts = (TextView)
                    view.findViewById(R.id.item_frag_dingyueting_recom_textview_sounds_count);//播放集数
            viewholder.imageView_wifi = (ImageButton) view.findViewById(R.id.item_frag_dingyueting_recom_icon_wifi);//是否点击订阅
           viewholder.relativeLayout=(RelativeLayout)view.findViewById(R.id.item_frag_dingyueting_recom_linearlayout);
            view.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) view.getTag();
        }

        bitmapUtils.display(viewholder.imageView_album, list.get(i).coverMiddle); //设置专辑背景
        viewholder.textView_recom_title.setText(list.get(i).title);
        viewholder.textView_recom.setText(list.get(i).recReason);
        viewholder.textView_sounds_counts.setText(list.get(i).tracks + "集");
        viewholder.textView_paly_times.setText(getPlayTimes(list.get(i).playsCounts));
        if (!TextUtils.isEmpty(list.get(i).tags) && list.get(i).tags.equals("true")) {
            viewholder.imageView_wifi.setSelected(true);
        } else {
            viewholder.imageView_wifi.setSelected(false);
        }
        viewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, AlbumActivity.class);
                intent.putExtra("picUrl",list.get(i).coverLarge);
                intent.putExtra("title",list.get(i).title);
                intent.putExtra("albumId",list.get(i).albumId);
                intent.putExtra("titleName",list.get(i).title);
                intent.putExtra("nickname",list.get(i).nickname);
                intent.putExtra("playsCounts",list.get(i).playsCounts);
                context.startActivity(intent);
            }
        });
        viewholder.imageView_wifi.setTag(i);
        viewholder.imageView_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View subcriptionView = view;
                if (view.findViewById(R.id.item_frag_dingyueting_recom_icon_wifi).isSelected()) {
                    //inflate() 可以把一个布局的资源文件转换成一个View对象
                    View alertDialogView = View.inflate(context, R.layout.dialog_frag_dingyueting_recom, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //给dialog设置一个自定义的布局
                    builder.setView(alertDialogView);
                    //点击dialog以为的部分  是否可以关闭对话框
                    builder.setCancelable(true);
//				builder.show();
                    final AlertDialog alertDialog = builder.show();
                    alertDialogView.findViewById(R.id.dialog_notification_cancle).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialogView.findViewById(R.id.dialog_notification_ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            list.get((Integer) subcriptionView.getTag()).tags = "false";
                            subcriptionView.findViewById(R.id.item_frag_dingyueting_recom_icon_wifi).setSelected(false);
                            notifyDataSetChanged();
                            //取消订阅后 从数据库删除指定数据
                            deleteDataFromDB( list.get((Integer) subcriptionView.getTag()).albumId);

//                            cursor= rsdbDao.select(tableName);
//                            while (cursor.moveToNext()){
//                                Log.e("cursor",cursor.getString(cursor.getColumnIndex("updatedAt"))+"");
//                            }
                            alertDialog.dismiss();
                            Toast.makeText(context, "已取消订阅！", Toast.LENGTH_SHORT).show();


                        }
                    });
                } else {
                    list.get((Integer) view.getTag()).tags = "true";
                    int AlbumId = list.get((Integer) view.getTag()).albumId;
                    int i = (Integer) view.getTag();

                    subcriptionView.findViewById(R.id.item_frag_dingyueting_recom_icon_wifi).setSelected(true);
//                    notifyDataSetChanged();
                    getNetData(i, AlbumId);
                    //订阅成功后 添加数据到数据控
                    Toast.makeText(context, "订阅成功！", Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }
    public SubscriptionDataListener subscriptionDataListener;//发送订阅数据的接口
  public interface SubscriptionDataListener{
      public void sendSubscriptionData();
  }
    public void setSubscriptionDataListener(SubscriptionDataListener listener){
        this.subscriptionDataListener=listener;
    }
    private void deleteDataFromDB(int ablumId) {
        String ablumIdStr=String.valueOf(ablumId);
        rsdbDao.delete(tableName,ablumIdStr);
        subscriptionDataListener.sendSubscriptionData();//发送订阅数据
    }

    //监听订阅的list条目位置
    public SendRecomDataAdapter sendRecomAdapterData;

    public interface SendRecomDataAdapter {
        public void getDeleteItem();
    }

    public void setOnRemoveItemListener(SendRecomDataAdapter listener) {
        this.sendRecomAdapterData = listener;
    }


    private String getPlayTimes(int playtimes) {
        if (playtimes > 10000) {
            String palyTimes = String.valueOf(playtimes / 10000);
            return palyTimes + "万";
        } else {
            String palyTimes = String.valueOf(playtimes);
            return palyTimes;
        }
    }

    public void setFreshData(List<DingYueTingRecommended.RecommendedData> recommendedDataList) {
        this.list = recommendedDataList;
        notifyDataSetChanged();
    }


    class ViewHolder {
        ImageView imageView_album;
        TextView textView_recom_title;
        TextView textView_recom;
        TextView textView_paly_times;
        TextView textView_sounds_counts;
        ImageButton imageView_wifi;
        RelativeLayout relativeLayout;
    }


    private void getNetData(int position, final int albumId) {


        final String url = "http://mobile.ximalaya.com/mobile/v1/album?albumId=" + albumId + "&device=android&isAsc=true&pageId=1&pageSize=20&pre_page=0&source=2&statEvent=pageview%2Falbum%40" + albumId + "&statModule=%E6%8E%A8%E8%8D%90&statPage=tab%40%E8%AE%A2%E9%98%85%E5%90%AC_%E6%8E%A8%E8%8D%90&statPosition=" + position;

        HttpUtils http = new HttpUtils();

        http.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Gson gson = new Gson();
                        final Bean_frag_dingyueting_recom bean_frag_dingyueting_recom = gson.fromJson(responseInfo.result, Bean_frag_dingyueting_recom.class);

                        getBitMapByteArray.setData(bean_frag_dingyueting_recom.data.album.coverLarge, 20, new GetBitMapByteArray.RequestCallBack() {
                            @Override
                            public void onSuccess(byte[] result) {
//                                Log.e("图片请求成功！","图片请求成功");
                                bean_frag_dingyueting_recom.data.tracks.imageBytes=result;//给实体类中的byte类型数组赋值，用于显示照片
                                insertDistinct(bean_frag_dingyueting_recom);
                                Log.e(".data.album.title",bean_frag_dingyueting_recom.data.album.title+"");
                                Log.e(".data.album.title",bean_frag_dingyueting_recom.data.album.playTimes+"");
                                subscriptionDataListener.sendSubscriptionData();//发送订阅数据
                                sendRecomAdapterData.getDeleteItem();//发送订阅完成后要删除的数据
                            }

                            @Override
                            public void onFailure() {
                                Log.e("图片请求失败！","图片请求失败");
                            }
                        });
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.e("bean_frag", "失败！");
                    }
                });
    }
//判断数据库中有无相同数据
    private void insertDistinct(Bean_frag_dingyueting_recom bean_frag_dingyueting_recom) {
        cursor= rsdbDao.select(tableName);
        int albumId= bean_frag_dingyueting_recom.data.album.albumId;
        while (cursor.moveToNext()){
//            Log.e("cursor",cursor.getString(cursor.getColumnIndex("albumId"))+"");
          if (albumId==cursor.getInt(cursor.getColumnIndex("albumId"))) {
              return;
          }
        }
        rsdbDao.insert(tableName,bean_frag_dingyueting_recom);
    }

}

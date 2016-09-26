package com.lanou.radiostation.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.activity.RegisterActivity;
import com.lanou.radiostation.adapter.FragSubscriptionAdapter;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_recom;
import com.lanou.radiostation.db.RSDBDao;
import com.lanou.radiostation.util.UpdateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class Frag_dingyueting_subscription extends Fragment {
    View view;
    private ListView frag_dingyueting_subscription_listview;
    List<Bean_frag_dingyueting_recom> list;
    private FragSubscriptionAdapter fragSubscriptionAdapter;
    Cursor cursor;
    RSDBDao rsdbDao;
    final String tableName = "dingyueting_subscription_table";//数据库表名字

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("onCreate", "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.frag_dingyueting_subscription, null);
        initView();
        Log.e("onCreateView", "onCreateView");
        return view;
    }

    //当界面从Activity到Fragment切换时 会刷数据
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("onActivityCreated", "onActivityCreated");
        setData();
    }
   private Button button_goToRecom;
   private TextView textview_goToLogin;
    private void initView() {
        frag_dingyueting_subscription_listview =
                (ListView) view.findViewById(R.id.frag_dingyueting_subscription_listview);
        list = new ArrayList<Bean_frag_dingyueting_recom>();
        rsdbDao = new RSDBDao(getActivity());
        frag_dingyueting_subscription_ll= (LinearLayout) view.findViewById(R.id.frag_dingyueting_subscription_ll);
       button_goToRecom= (Button)view.findViewById(R.id.frag_dingyueting_subscription_button_refresh);
        button_goToRecom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onupdateDBListener.goToRecom();
            }
        });
        frag_dingyueting_subscription_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                showDialog(getActivity(), i);
                return false;
            }
        });

        frag_dingyueting_subscription_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getActivity(), AlbumActivity.class);
                intent.putExtra("picUrl",list.get(i).data.album.coverLarge);
                intent.putExtra("title",list.get(i).data.album.title);
                intent.putExtra("albumId",list.get(i).data.album.albumId);
                intent.putExtra("titleName",list.get(i).data.album.title);
                intent.putExtra("nickname",list.get(i).data.album.nickname);

                intent.putExtra("playsCounts",list.get(i).data.album.playTimes);
                Log.e("------------",list.get(i).data.album.playTimes+"");
                getActivity().startActivity(intent);
            }
        });
         textview_goToLogin= (TextView) view.findViewById(R.id.frag_dingyueting_subscription_textview_gotorecom);
        textview_goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), RegisterActivity.class);
                  getActivity().startActivity(intent);
//                Toast.makeText(getActivity(),"fsdfsdfsdf",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void RefreshData() {
        if (getDateFormDB() != null) {
            frag_dingyueting_subscription_ll.setVisibility(View.GONE);
            frag_dingyueting_subscription_listview.setVisibility(View.VISIBLE);
            list = getDateFormDB();
            fragSubscriptionAdapter = new FragSubscriptionAdapter(getActivity(), list);
            frag_dingyueting_subscription_listview.setAdapter(fragSubscriptionAdapter);
        }
    }

    private void setData() {

        if (getDateFormDB().size()==0) {
//           Log.e("FSDFDFF","SADSADSD");
            frag_dingyueting_subscription_listview.setVisibility(View.GONE);
            frag_dingyueting_subscription_ll.setVisibility(View.VISIBLE);
        } else {
            frag_dingyueting_subscription_ll.setVisibility(View.GONE);
            frag_dingyueting_subscription_listview.setVisibility(View.VISIBLE);
            list = getDateFormDB();
            fragSubscriptionAdapter = new FragSubscriptionAdapter(getActivity(), list);
            frag_dingyueting_subscription_listview.setAdapter(fragSubscriptionAdapter);
        }
    }

    public void showDialog(Context context, final int i) {
        View alertDialogView = View.inflate(context, R.layout.dialog_frag_dingyueting_subscription, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //给dialog设置一个自定义的布局
        builder.setView(alertDialogView);
        //点击dialog以为的部分  是否可以关闭对话框
        builder.setCancelable(true);
//				builder.show();
        final AlertDialog alertDialog = builder.show();
//        alertDialogView.findViewById(R.id.dialog_notification_cancle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
        alertDialogView.findViewById(R.id.dialog_frag_dingyueting_subscription_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                showSelectDialog(getActivity(), i);
            }
        });

    }
private LinearLayout frag_dingyueting_subscription_ll;
    public void showSelectDialog(Context context, final int i) {
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
                rsdbDao.delete(tableName, "" + list.get(i).data.album.albumId);
                list.remove(i);
                fragSubscriptionAdapter = new FragSubscriptionAdapter(getActivity(), list);
                frag_dingyueting_subscription_listview.setAdapter(fragSubscriptionAdapter);
                alertDialog.dismiss();
                if (list.size()==0){
                    frag_dingyueting_subscription_listview.setVisibility(View.GONE);
                    frag_dingyueting_subscription_ll.setVisibility(View.VISIBLE);
                }
                onupdateDBListener.updateDB();//通知推荐板块更新数据库 （更新过滤信息）
            }
        });

    }

    public OnupdateDBListener onupdateDBListener;

    public void setOnupdateListenr(OnupdateDBListener listenr) {
        this.onupdateDBListener = listenr;
    }

    public interface OnupdateDBListener {
        public void updateDB();
        public void goToRecom();
    }

    public List<Bean_frag_dingyueting_recom> getDateFormDB() {
        List<Bean_frag_dingyueting_recom> recomList = new ArrayList<>();
        cursor = rsdbDao.selectByOder(tableName, "id", true);
        UpdateTime updateTime = new UpdateTime();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Bean_frag_dingyueting_recom recomData = new Bean_frag_dingyueting_recom();
                recomData.data = recomData.new Data();
                recomData.data.tracks = recomData.new Tracks();
                recomData.data.tracks.list = new ArrayList<Bean_frag_dingyueting_recom.AlbumDetail>();
                recomData.data.album = recomData.new Album();
                /**
                 *   values.put("albumId", bean_frag_dingyueting_recom.data.album.albumId);
                 values.put("pageId", bean_frag_dingyueting_recom.data.tracks.pageId);
                 values.put("pageSize", bean_frag_dingyueting_recom.data.tracks.pageSize);
                 values.put("maxPageId",bean_frag_dingyueting_recom.data.tracks.maxPageId);
                 values.put("title", bean_frag_dingyueting_recom.data.tracks.list.get(0).title);
                 values.put("coverLarge", bean_frag_dingyueting_recom.data.album.coverLarge);
                 String time=String.valueOf(bean_frag_dingyueting_recom.data.album.updatedAt);
                 values.put("updatedAt", time);
                 values.put("image", bean_frag_dingyueting_recom.data.tracks.imageBytes);
                 */
                String timeStr = cursor.getString(cursor.getColumnIndex("updatedAt"));
                Long time = Long.parseLong(timeStr);
                recomData.data.album.updatedAtStr = updateTime.getUpdateTime(time, false);
                recomData.data.album.updatedAt = time;
                recomData.data.album.albumId = cursor.getInt(cursor.getColumnIndex("albumId"));
                recomData.data.album.coverLarge = cursor.getString(cursor.getColumnIndex("coverLarge"));
                recomData.data.album.title = cursor.getString(cursor.getColumnIndex("albumTitle"));
                Bean_frag_dingyueting_recom.AlbumDetail albumDetail=recomData.new AlbumDetail();
                recomData.data.tracks.list.add(albumDetail);
                recomData.data.tracks.list.get(0).title = cursor.getString(cursor.getColumnIndex("title"));
                recomData.data.tracks.imageBytes = cursor.getBlob(cursor.getColumnIndex("image"));
                recomData.data.tracks.maxPageId = cursor.getInt(cursor.getColumnIndex("maxPageId"));
                recomData.data.tracks.pageId = cursor.getInt(cursor.getColumnIndex("pageId"));
                recomData.data.tracks.pageSize = cursor.getInt(cursor.getColumnIndex("pageSize"));
                recomData.data.album.playTimes = cursor.getInt(cursor.getColumnIndex("playtimes"));
                recomData.data.album.nickname = cursor.getString(cursor.getColumnIndex("nickname"));
//                recomData.data.tracks.totalCount=cursor.getInt(cursor.getColumnIndex("totalCount"));
                recomList.add(recomData);
            }
            return recomList;
        }
        return null;
    }

}

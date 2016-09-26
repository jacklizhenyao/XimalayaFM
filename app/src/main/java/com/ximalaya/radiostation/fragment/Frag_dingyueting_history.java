package com.lanou.radiostation.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.MainActivity;
import com.lanou.radiostation.adapter.FragHistoryAdapter;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_history;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_recom;
import com.lanou.radiostation.db.RSDBDao;
import com.lanou.radiostation.util.GetBitMapByteArray;
import com.lanou.radiostation.util.MusicConstant;
import com.lanou.radiostation.util.MusicSong;
import com.lanou.radiostation.util.UpdateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class Frag_dingyueting_history extends Fragment {
    View view;
     View headView;

List< Bean_frag_dingyueting_history> list;
    private FragHistoryAdapter adapter_Frag_dingyueting_history;
    private ListView listview;
    private Button button_clear;
    private LinearLayout linearlayout;
    String tableName="dingyueting_history_table";
    RSDBDao rsdbDao;
    Cursor cursor;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=View.inflate(getActivity(), R.layout.frag_dingyueting_history,null);
        initView();
        Log.e("onCreateView","onCreateView");
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
        Log.e("onActivityCreated","onActivityCreated");
    }
    /**
     * 接收广播，处理对应的动作
     *
     */


    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        rsdbDao=new RSDBDao(getActivity());
        listview=(ListView)view.findViewById(R.id.frag_dingyueting_historys_listview);
        headView=View.inflate(getActivity(),R.layout.head_listview_frag_dingyueting_history,null);
        linearlayout=(LinearLayout)view.findViewById(R.id.frag_dingyueting_history_ll);
        list=new ArrayList<>();
        listview.addHeaderView(headView);
        button_clear= (Button) headView.findViewById(R.id.head_frag_dingyueting_history_button_clear);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(getActivity());

            }
        });
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//    }



    public void setData() {
       if (getDateFormDB().size()>0){
           linearlayout.setVisibility(View.GONE);
           listview.setVisibility(View.VISIBLE);
           adapter_Frag_dingyueting_history=new FragHistoryAdapter(getActivity(),getDateFormDB());
           listview.setAdapter(adapter_Frag_dingyueting_history);
       }else {
           listview.setVisibility(View.GONE);
           linearlayout.setVisibility(View.VISIBLE);
       }

    }

    public void refreshData(){
        // 当点击播放按钮时 停止时 暂停时 查询数据库跟新数据
        if (getDateFormDB().size()>0){
            linearlayout.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            adapter_Frag_dingyueting_history=new FragHistoryAdapter(getActivity(),getDateFormDB());
            listview.setAdapter(adapter_Frag_dingyueting_history);
        }else {
            try {
                listview.setVisibility(View.GONE);
                linearlayout.setVisibility(View.VISIBLE);
            }catch (Exception e){

            }
        }
    }
    private void showDialog(Context context){
        //inflate() 可以把一个布局的资源文件转换成一个View对象
        View view = View.inflate(context, R.layout.dialog_frag_dingyueting_history, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //给dialog设置一个自定义的布局
        builder.setView(view);
        //点击dialog以为的部分  是否可以关闭对话框
        builder.setCancelable(true);
//				builder.show();
        final AlertDialog alertDialog = builder.show();
        view.findViewById(R.id.dialog_history_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.dialog_history_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                //从数据库中删除所有记录 重新绑定数据源
                list.clear();
                listview.setVisibility(View.GONE);
                linearlayout.setVisibility(View.VISIBLE);
                rsdbDao.delete(tableName);
            }
        });
    }

    public List<Bean_frag_dingyueting_history> getDateFormDB() {
        List<Bean_frag_dingyueting_history> historyList = new ArrayList<>();
        int dataSize=0;
        try {
            cursor = rsdbDao.selectByOder(tableName, "id", true);
            dataSize=cursor.getCount();
        }catch (Exception e){

        }
        if (dataSize>0) {
            while (cursor.moveToNext()) {
                Bean_frag_dingyueting_history historyData = new Bean_frag_dingyueting_history();
                historyData.albumId=cursor.getInt(cursor.getColumnIndex("albumId"));
                historyData.author=cursor.getString(cursor.getColumnIndex("author"));
                historyData.coverSmall=cursor.getString(cursor.getColumnIndex("coverSmall"));
                historyData.title=cursor.getString(cursor.getColumnIndex("title"));
                historyData.playUrl32=cursor.getString(cursor.getColumnIndex("playUr"));
                historyData.imageBytes=cursor.getBlob(cursor.getColumnIndex("image"));
                historyData.play_time_last=cursor.getString(cursor.getColumnIndex("play_time_last"));
                historyData.tag=cursor.getString(cursor.getColumnIndex("tag"));
                if(!historyData.tag.equals("guangbo")&&historyData.play_time_last==null){
                    historyData.play_time_last="正在播放......";
                }else if(historyData.tag.equals("guangbo")){
                    String timeStr=historyData.play_time_last;
                    historyData.duration_play_time_last=timeStr;
                    long time=Long.parseLong(timeStr);
                    historyData.play_time_last=getCurremtTime(time);
                    historyData.title= "上次收听的节目: "+ historyData.title;
                }
                else{
                    String timeStr=historyData.play_time_last;
                    historyData.duration_play_time_last=timeStr;
                    long time=Long.parseLong(timeStr);
                    historyData.play_time_last=formatTime(time);
                }
                historyList.add(historyData);
            }
            return historyList;
        }
        return historyList;
    }
    public static String formatTime(long time){
        //对毫秒进行时间的格式化
        if (time / 1000 % 60 < 10) {
            return "上次播放至："+time/1000/60+"分"+time/1000%60+"秒";
        }else{
            return "上次播放至："+time/1000/60+"分"+time/1000%60+"秒";
        }

    }
    public String getCurremtTime(long time){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(time);
        String updateTime=dateFormat.format(date);
        return "上次收听时间："+updateTime;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

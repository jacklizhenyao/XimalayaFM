package com.lanou.radiostation.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumLookActivity;
import com.lanou.radiostation.adapter.MyLookAdapter;
import com.lanou.radiostation.bean.DownLoad;

import java.io.File;
import java.util.List;
import java.util.Vector;


/**
 * Created by user on 2016/7/22.
 */
public class AlbumFragment extends Fragment implements View.OnClickListener {
    private Vector<String> vecFile;

    private ListView album_look_lv;
    private Button album_golook_btn;
    private MyLookAdapter lookAdapter;
    private List<DownLoad> list;
    ImageView album_look_iv;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.frag_album, null);
        album_look_lv = (ListView) view.findViewById(R.id.album_look_lv);
        album_golook_btn = (Button) view.findViewById(R.id.album_golook_btn);
        album_look_iv = (ImageView) view.findViewById(R.id.album_look_iv);
        album_golook_btn.setOnClickListener(this);

        //获取sd卡路径
        String sd = Environment.
                getExternalStorageDirectory().getPath();
        //定义保存下载文件的路径
        String target = sd + "/music";
        final Vector<String> strings = GetMp3FileName(target);
        if (strings != null && strings.size() > 0) {
            album_look_iv.setVisibility(View.GONE);
            album_golook_btn.setVisibility(View.GONE);

            album_look_lv.setVisibility(View.VISIBLE);
            lookAdapter = new MyLookAdapter(strings, getActivity());
            album_look_lv.setAdapter(lookAdapter);
            lookAdapter.notifyDataSetChanged();
            album_look_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int a, long l) {

                    new AlertDialog.Builder(getActivity()).setTitle("确定删除这条吗?")
                            .setNegativeButton("取消",null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    strings.remove(a);
                                    lookAdapter.notifyDataSetChanged();
                                }
                            }).show();



                }
            });


        } else {
            album_look_iv.setVisibility(View.VISIBLE);
            album_golook_btn.setVisibility(View.VISIBLE);
            album_look_lv.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), AlbumLookActivity.class);
        getActivity().startActivity(intent);
    }

    public Vector<String> GetMp3FileName(String fileAbsolutePath) {
        vecFile = new Vector<String>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为MP4结尾
                if (filename.trim().toLowerCase().endsWith(".mp3")) {
                    vecFile.add(filename);
                }
            }
        }
        return vecFile;
    }

}

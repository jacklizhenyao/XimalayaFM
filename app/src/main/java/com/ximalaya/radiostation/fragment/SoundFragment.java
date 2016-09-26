package com.lanou.radiostation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumLookActivity;

/**
 * Created by user on 2016/7/22.
 */
public class SoundFragment extends Fragment implements View.OnClickListener {

private Button  sound_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =View.inflate(getActivity(), R.layout.frag_sound,null);
        sound_btn = (Button) view.findViewById(R.id.sound_btn);
        sound_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent =new Intent(getActivity(),AlbumLookActivity.class);
        getActivity().startActivity(intent);
    }

}

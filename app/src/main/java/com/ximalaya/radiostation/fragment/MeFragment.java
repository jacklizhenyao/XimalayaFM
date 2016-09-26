package com.lanou.radiostation.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.RegisterActivity;

/**
 * 这是我的fragment
 * Created by user on 2016/7/22.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener{

    private ImageButton frag_ib_register;
    @Override
    public View initView() {
View view = View.inflate(getActivity(), R.layout.frag_me,null);
        frag_ib_register = (ImageButton) view.findViewById(R.id.frag_ib_register);
        frag_ib_register.setOnClickListener(this);
      return view;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_ib_register:
                Intent intent =new Intent(getActivity(),RegisterActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in,R.anim.out);
                break;
        }
    }

    @Override
    public void setData() {

    }
}

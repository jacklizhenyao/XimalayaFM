package com.lanou.radiostation.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AllClassfiyActivity;
import com.lanou.radiostation.adapter.MyClassifyAdapter;
import com.lanou.radiostation.bean.Classify;
import com.lanou.radiostation.util.HttpUtils;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个分类的fragment
 * Created by user on 2016/7/22.
 */
public class ClassifyFragment extends BaseFragment  implements View.OnClickListener{

    private ListView classifyLv;
    private MyClassifyAdapter classifyLvAdapter;
    private List<Classify> classifyList = new ArrayList<>();

    private ImageView audiobookIv, musicIv, crosstalkIv, varietyIv, childrenIv;
    private View viewHead;
    private Classify classify;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.frag_classify, null);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        classifyLv = (ListView) view.findViewById(R.id.frag_classify_lv);
        viewHead = View.inflate(getActivity(), R.layout.frag_classify_head, null);
        audiobookIv = (ImageView) viewHead.findViewById(R.id.frag_classify_head_audiobook);
        musicIv = (ImageView) viewHead.findViewById(R.id.frag_classify_head_music);
        crosstalkIv = (ImageView) viewHead.findViewById(R.id.frag_classify_head_crosstalk);
        varietyIv = (ImageView) viewHead.findViewById(R.id.frag_classify_head_variety);
        childrenIv = (ImageView) viewHead.findViewById(R.id.frag_classify_head_children);


        audiobookIv.setOnClickListener(this);
        musicIv.setOnClickListener(this);
        crosstalkIv.setOnClickListener(this);
        varietyIv.setOnClickListener(this);
        childrenIv.setOnClickListener(this);
    }

    @Override
    public void setData() {
        new MyAsyncTask().execute();
        classifyLvAdapter = new MyClassifyAdapter(getActivity(), classifyList);
        classifyLv.addHeaderView(viewHead);
        classifyLv.setAdapter(classifyLvAdapter);
    }



    class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = HttpUtils.requestStringByGet("http://mobile.ximalaya.com/mobile/discovery/v1/categories?device=android&picVersion=11&scale=2");


            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (TextUtils.isEmpty(s)) {
                return;
            } else {
                Gson gson = new Gson();
                classify = gson.fromJson(s, Classify.class);
                setDataImage(classify);

                classifyList.clear();
                for (int i = 0; i < classify.list.size() - 5; i++) {
                    classifyList.add(classify);
                }

                classifyLvAdapter.setListener(classifyList);
            }
        }
    }

    private void setDataImage(Classify classify) {
        audiobookIv.setImageResource(R.mipmap.a);
        ImageLoaderUtils.getImageByloader(classify.list.get(1).coverPath, musicIv);
        ImageLoaderUtils.getImageByloader(classify.list.get(2).coverPath, crosstalkIv);
        ImageLoaderUtils.getImageByloader(classify.list.get(3).coverPath, varietyIv);
        ImageLoaderUtils.getImageByloader(classify.list.get(4).coverPath, childrenIv);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getActivity(), AllClassfiyActivity.class);
        switch (view.getId()) {
            case R.id.frag_classify_head_audiobook:
                intent.putExtra("id",classify.list.get(0).id);
                intent.putExtra("title",classify.list.get(0).title);
                break;
            case R.id.frag_classify_head_music:
                intent.putExtra("id",classify.list.get(1).id);
                intent.putExtra("title",classify.list.get(1).title);
                break;
            case R.id.frag_classify_head_crosstalk:
                intent.putExtra("id",classify.list.get(2).id);
                intent.putExtra("title",classify.list.get(2).title);
                break;
            case R.id.frag_classify_head_variety:
                intent.putExtra("id",classify.list.get(3).id);
                intent.putExtra("title",classify.list.get(3).title);
                break;
            case R.id.frag_classify_head_children:
                intent.putExtra("id",classify.list.get(4).id);
                intent.putExtra("title",classify.list.get(4).title);
                break;
        }
        getActivity().startActivity(intent);
    }


}

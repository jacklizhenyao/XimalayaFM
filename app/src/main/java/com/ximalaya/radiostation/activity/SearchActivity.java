package com.lanou.radiostation.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanou.radiostation.R;
import com.lanou.radiostation.adapter.MySearchBaseAdapter;
import com.lanou.radiostation.bean.Search;
import com.lanou.radiostation.fragment.SearchFragment;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends FragmentActivity {

    private ImageButton backIb,searchIb;
    private EditText searchEt;

    private String url;
    private String urlSearch;
    private String etText;

    switchUrl switchUrl;
    private ImageButton search_back_ib;
    private FragmentManager supportFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        supportFragmentManager = getSupportFragmentManager();



        backIb = (ImageButton) findViewById(R.id.search_back_ib);
        searchIb = (ImageButton) findViewById(R.id.search_search_ib);
        searchEt = (EditText) findViewById(R.id.search_et);
        search_back_ib = (ImageButton) findViewById(R.id.search_back_ib);
        search_back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        supportFragmentManager.beginTransaction().replace(R.id.search_fl,new SearchFragment()).commit();

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


//                url = "http://search.ximalaya.com/front/v1?core=all&kw=" +
//                        charSequence.toString() +
//                        "&live=true&page=1&paidFilter=false&rows=3&spellchecker=true";
                urlSearch = "http://search.ximalaya.com/suggest?kw=" +
                        charSequence.toString()+
                        "&paidFilter=false";


                switchUrl.setDataNet(urlSearch);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    public void setOnSwitchUrl(switchUrl switchUrl){
        this.switchUrl = switchUrl;
    }
    public interface switchUrl{
        void setDataNet(String url);
    };




}

package com.lanou.radiostation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.lanou.radiostation.R;

public class RegisterActivity extends Activity implements View.OnClickListener{
private ImageButton include_ib_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        include_ib_back= (ImageButton) findViewById(R.id.include_ib_back);
        include_ib_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.include_ib_back:
                Intent intent =new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}

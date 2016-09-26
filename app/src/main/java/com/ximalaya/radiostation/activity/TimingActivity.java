package com.lanou.radiostation.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.radiostation.R;

public class TimingActivity extends Activity implements CompoundButton.OnCheckedChangeListener{

    private CheckBox timing_cb01, timing_cb02, timing_cb03, timing_cb04, timing_cb05, timing_cb06, timing_cb07;

    private TextView timing_time_cb3, timing_time_cb4, timing_time_cb5, timing_time_cb6, timing_time_cb7;
    private Button timing_btn_close;
    SharedPreferences preferences;
    private int index = 600000;
    private int a = 1;

Handler handler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(Message message) {


        switch (message.what){
            case 0:
                timing_time_cb3.setText(formatTime(message.arg1));
//                finish();
                if(index<0){
                    finish();
                }
                break;

        }

        return false;
    }
});
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);
        preferences =getPreferences(MODE_PRIVATE);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 1.0);   //高度设置为屏幕的1.0
        p.width = (int) (d.getWidth());    //宽度设置为屏幕的0.8
        p.alpha = 1.0f;      //设置本身透明度
        p.dimAmount = 0.0f;      //设置黑暗度
        getWindow().setAttributes(p);
        initView();
    }

    private void initView() {
        timing_time_cb3 = (TextView) findViewById(R.id.timing_time_cb3);
        timing_time_cb4 = (TextView) findViewById(R.id.timing_time_cb4);
        timing_time_cb5 = (TextView) findViewById(R.id.timing_time_cb5);
        timing_time_cb6 = (TextView) findViewById(R.id.timing_time_cb6);
        timing_time_cb7= (TextView) findViewById(R.id.timing_time_cb7);
        timing_cb01 = (CheckBox) findViewById(R.id.timing_cb01);
        timing_cb02 = (CheckBox) findViewById(R.id.timing_cb02);
        timing_cb03 = (CheckBox) findViewById(R.id.timing_cb03);
        timing_cb04 = (CheckBox) findViewById(R.id.timing_cb04);
        timing_cb05 = (CheckBox) findViewById(R.id.timing_cb05);
        timing_cb06 = (CheckBox) findViewById(R.id.timing_cb06);
        timing_cb07 = (CheckBox) findViewById(R.id.timing_cb07);
        timing_cb01.setOnCheckedChangeListener(this);
        timing_cb02.setOnCheckedChangeListener(this);
        timing_cb03.setOnCheckedChangeListener(this);
        timing_cb04.setOnCheckedChangeListener(this);
        timing_cb05.setOnCheckedChangeListener(this);
        timing_cb06.setOnCheckedChangeListener(this);
        timing_cb07.setOnCheckedChangeListener(this);
        int i=preferences.getInt("tag",0);

        switch (i){

//         case 1:
//                 timing_cb01.setChecked(true);
//             break;

            case 2:
                timing_cb02.setChecked(true);
                break;
            case 3:
                timing_cb03.setChecked(true);
                break;
            case 4:
                timing_cb04.setChecked(true);
                break;
            case 5:
                timing_cb05.setChecked(true);
                break;
            case 6:
                timing_cb06.setChecked(true);
                break;
            case 7:
                timing_cb07.setChecked(true);
                break;
            default:
                break;
        }

        timing_btn_close = (Button) findViewById(R.id.timing_btn_close);
        timing_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }




    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        //获取preferences实例对象通过.edit()方法获取Editor
        SharedPreferences.Editor editor =preferences.edit();


        switch (compoundButton.getId()) {


            case R.id.timing_cb01:
                if(b){
                    timing_cb02.setChecked(false);
                    timing_cb03.setChecked(false);
                    timing_cb04.setChecked(false);
                    timing_cb05.setChecked(false);
                    timing_cb06.setChecked(false);
                    timing_cb07.setChecked(false);


                }
                editor.putInt("tag", 1);

                break;

            case R.id.timing_cb02:

                if(b){
                    timing_cb01.setChecked(false);
                    timing_cb03.setChecked(false);
                    timing_cb04.setChecked(false);
                    timing_cb05.setChecked(false);
                    timing_cb06.setChecked(false);
                    timing_cb07.setChecked(false);
                    editor.putInt("tag", 2);
                }

                break;
            case R.id.timing_cb03:


                if(b){

                    handler.removeMessages(0);
                    timing_cb02.setChecked(false);
                    timing_cb01.setChecked(false);
                    timing_cb04.setChecked(false);
                    timing_cb05.setChecked(false);
                    timing_cb06.setChecked(false);
                    timing_cb07.setChecked(false);
                    timing_time_cb3.setVisibility(View.VISIBLE);

                                       new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true){
                                try {
                                    Thread.sleep(1000);
                                    Message message = new Message();
                                    message.what = 0;
                                    index -=1000;
                                    message.arg1 = index;
                                    handler.sendMessageDelayed(message,1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }).start();

                    editor.putInt("tag", 3);



                }else{
                    handler.removeMessages(0);
                }

                break;
            case R.id.timing_cb04:
                if(b){
                    timing_cb02.setChecked(false);
                    timing_cb03.setChecked(false);
                    timing_cb01.setChecked(false);
                    timing_cb05.setChecked(false);
                    timing_cb06.setChecked(false);
                    timing_cb07.setChecked(false);
                    editor.putInt("tag", 4);
                }

                break;
            case R.id.timing_cb05:
                if(b){
                    timing_cb02.setChecked(false);
                    timing_cb03.setChecked(false);
                    timing_cb04.setChecked(false);
                    timing_cb01.setChecked(false);
                    timing_cb06.setChecked(false);
                    timing_cb07.setChecked(false);
                    editor.putInt("tag", 5);
                }

                break;
            case R.id.timing_cb06:
                if(b){
                    timing_cb02.setChecked(false);
                    timing_cb03.setChecked(false);
                    timing_cb04.setChecked(false);
                    timing_cb05.setChecked(false);
                    timing_cb01.setChecked(false);
                    timing_cb07.setChecked(false);
                    editor.putInt("tag", 6);
                }

                break;
            case R.id.timing_cb07:
                if(b){
                    timing_cb02.setChecked(false);
                    timing_cb03.setChecked(false);
                    timing_cb04.setChecked(false);
                    timing_cb05.setChecked(false);
                    timing_cb06.setChecked(false);
                    timing_cb01.setChecked(false);
                    editor.putInt("tag", 7);

                }

                break;


        }

        editor.commit();

    }

    public static String formatTime(int time){
        //对毫秒进行时间的格式化
        if (time / 1000 % 60 < 10) {
            return time/1000/60+":0"+time/1000%60;
        }else{
            return time/1000/60+":"+time/1000%60;
        }

    }

}

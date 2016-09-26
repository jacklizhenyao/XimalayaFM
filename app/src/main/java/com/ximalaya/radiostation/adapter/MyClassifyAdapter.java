package com.lanou.radiostation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AllClassfiyActivity;
import com.lanou.radiostation.bean.Classify;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by user on 2016/7/24.
 */
public class MyClassifyAdapter extends BaseAdapter{

    Context context;
    List<Classify> classifyList;

    public MyClassifyAdapter(Context context, List<Classify> classifyList) {
        this.context = context;
        this.classifyList = classifyList;
    }

    @Override
    public int getCount() {
        if (classifyList == null) {
            return 0;
        }else {
            return classifyList.size() / 4;
        }
    }

    @Override
    public Object getItem(int i) {
        return classifyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.frag_classify_item,null);
            holder.iv01 = (ImageView) view.findViewById(R.id.frag_classify_item_iv_01);
            holder.iv02 = (ImageView) view.findViewById(R.id.frag_classify_item_iv_02);
            holder.iv03 = (ImageView) view.findViewById(R.id.frag_classify_item_iv_03);
            holder.iv04 = (ImageView) view.findViewById(R.id.frag_classify_item_iv_04);
            holder.iv05 = (ImageView) view.findViewById(R.id.frag_classify_item_iv_05);
            holder.iv06 = (ImageView) view.findViewById(R.id.frag_classify_item_iv_06);

            holder.tv01 = (TextView) view.findViewById(R.id.frag_classify_item_tv_01);
            holder.tv02 = (TextView) view.findViewById(R.id.frag_classify_item_tv_02);
            holder.tv03 = (TextView) view.findViewById(R.id.frag_classify_item_tv_03);
            holder.tv04 = (TextView) view.findViewById(R.id.frag_classify_item_tv_04);
            holder.tv05 = (TextView) view.findViewById(R.id.frag_classify_item_tv_05);
            holder.tv06 = (TextView) view.findViewById(R.id.frag_classify_item_tv_06);

            holder.ll_01 = (LinearLayout) view.findViewById(R.id.frag_classify_item_ll_01);
            holder.ll_02 = (LinearLayout) view.findViewById(R.id.frag_classify_item_ll_02);
            holder.ll_03 = (LinearLayout) view.findViewById(R.id.frag_classify_item_ll_03);
            holder.ll_04 = (LinearLayout) view.findViewById(R.id.frag_classify_item_ll_04);
            holder.ll_05 = (LinearLayout) view.findViewById(R.id.frag_classify_item_ll_05);
            holder.ll_06 = (LinearLayout) view.findViewById(R.id.frag_classify_item_ll);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }


        if (i ==0) {
            holder.tv01.setText(classifyList.get(i).list.get(5).title);
            holder.tv02.setText(classifyList.get(i).list.get(6).title);
            holder.tv03.setText(classifyList.get(i).list.get(7).title);
            holder.tv04.setText(classifyList.get(i).list.get(8).title);
            holder.tv05.setText(classifyList.get(i).list.get(9).title);
            holder.tv06.setText(classifyList.get(i).list.get(10).title);

            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(5).coverPath,holder.iv01);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(6).coverPath,holder.iv02);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(7).coverPath,holder.iv03);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(8).coverPath,holder.iv04);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(9).coverPath,holder.iv05);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(10).coverPath,holder.iv06);

            holder.iv06.setVisibility(View.VISIBLE);
            holder.tv06.setVisibility(View.VISIBLE);

            holder.ll_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(5).id);
                    intent.putExtra("title",classifyList.get(i).list.get(5).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(6).id);
                    intent.putExtra("title",classifyList.get(i).list.get(6).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(7).id);
                    intent.putExtra("title",classifyList.get(i).list.get(7).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(8).id);
                    intent.putExtra("title",classifyList.get(i).list.get(8).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(9).id);
                    intent.putExtra("title",classifyList.get(i).list.get(9).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_06.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(10).id);
                    intent.putExtra("title",classifyList.get(i).list.get(10).title);
                    context.startActivity(intent);
                }
            });



        }else if(i ==1){

            holder.tv01.setText(classifyList.get(i).list.get(11).title);
            holder.tv02.setText(classifyList.get(i).list.get(12).title);
            holder.tv03.setText(classifyList.get(i).list.get(13).title);
            holder.tv04.setText(classifyList.get(i).list.get(14).title);
            holder.tv05.setText(classifyList.get(i).list.get(15).title);
            holder.tv06.setText(classifyList.get(i).list.get(16).title);

            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(11).coverPath,holder.iv01);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(12).coverPath,holder.iv02);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(13).coverPath,holder.iv03);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(14).coverPath,holder.iv04);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(15).coverPath,holder.iv05);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(16).coverPath,holder.iv06);




            holder.ll_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(11).id);
                    intent.putExtra("title",classifyList.get(i).list.get(11).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(12).id);
                    intent.putExtra("title",classifyList.get(i).list.get(12).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(13).id);
                    intent.putExtra("title",classifyList.get(i).list.get(13).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(14).id);
                    intent.putExtra("title",classifyList.get(i).list.get(14).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(15).id);
                    intent.putExtra("title",classifyList.get(i).list.get(15).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_06.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(16).id);
                    intent.putExtra("title",classifyList.get(i).list.get(16).title);
                    context.startActivity(intent);
                }
            });



        }
        else if(i == 2){

            holder.tv01.setText(classifyList.get(i).list.get(17).title);
            holder.tv02.setText(classifyList.get(i).list.get(18).title);
            holder.tv03.setText(classifyList.get(i).list.get(19).title);
            holder.tv04.setText(classifyList.get(i).list.get(20).title);
            holder.tv05.setText(classifyList.get(i).list.get(21).title);
            holder.tv06.setText(classifyList.get(i).list.get(22).title);

            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(17).coverPath,holder.iv01);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(18).coverPath,holder.iv02);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(19).coverPath,holder.iv03);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(20).coverPath,holder.iv04);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(21).coverPath,holder.iv05);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(22).coverPath,holder.iv06);


            holder.ll_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(17).id);
                    intent.putExtra("title",classifyList.get(i).list.get(17).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(18).id);
                    intent.putExtra("title",classifyList.get(i).list.get(18).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(19).id);
                    intent.putExtra("title",classifyList.get(i).list.get(19).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(20).id);
                    intent.putExtra("title",classifyList.get(i).list.get(20).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(21).id);
                    intent.putExtra("title",classifyList.get(i).list.get(21).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_06.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(22).id);
                    intent.putExtra("title",classifyList.get(i).list.get(22).title);
                    context.startActivity(intent);
                }
            });



        }
        else if(i ==3){

            holder.tv01.setText(classifyList.get(i).list.get(11).title);
            holder.tv02.setText(classifyList.get(i).list.get(12).title);
            holder.tv03.setText(classifyList.get(i).list.get(13).title);
            holder.tv04.setText(classifyList.get(i).list.get(14).title);
            holder.tv05.setText(classifyList.get(i).list.get(15).title);
            holder.tv06.setText(classifyList.get(i).list.get(16).title);

            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(11).coverPath,holder.iv01);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(12).coverPath,holder.iv02);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(13).coverPath,holder.iv03);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(14).coverPath,holder.iv04);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(15).coverPath,holder.iv05);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(16).coverPath,holder.iv06);



            holder.ll_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(11).id);
                    intent.putExtra("title",classifyList.get(i).list.get(11).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(12).id);
                    intent.putExtra("title",classifyList.get(i).list.get(12).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(13).id);
                    intent.putExtra("title",classifyList.get(i).list.get(13).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(14).id);
                    intent.putExtra("title",classifyList.get(i).list.get(14).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(15).id);
                    intent.putExtra("title",classifyList.get(i).list.get(15).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_06.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(16).id);
                    intent.putExtra("title",classifyList.get(i).list.get(16).title);
                    context.startActivity(intent);
                }
            });



        }else if( i == 4){
            holder.tv01.setText(classifyList.get(i).list.get(23).title);
            holder.tv02.setText(classifyList.get(i).list.get(24).title);
            holder.tv03.setText(classifyList.get(i).list.get(25).title);
            holder.tv04.setText(classifyList.get(i).list.get(26).title);
            holder.tv05.setText(classifyList.get(i).list.get(27).title);


            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(23).coverPath,holder.iv01);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(24).coverPath,holder.iv02);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(25).coverPath,holder.iv03);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(26).coverPath,holder.iv04);
            ImageLoaderUtils.getImageByloader(classifyList.get(i).list.get(27).coverPath,holder.iv05);

            holder.iv06.setVisibility(View.GONE);
            holder.tv06.setVisibility(View.GONE);


            holder.ll_01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(23).id);
                    intent.putExtra("title",classifyList.get(i).list.get(23).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(24).id);
                    intent.putExtra("title",classifyList.get(i).list.get(24).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(25).id);
                    intent.putExtra("title",classifyList.get(i).list.get(25).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_04.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(26).id);
                    intent.putExtra("title",classifyList.get(i).list.get(26).title);
                    context.startActivity(intent);
                }
            });
            holder.ll_05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllClassfiyActivity.class);
                    intent.putExtra("id",classifyList.get(i).list.get(27).id);
                    intent.putExtra("title",classifyList.get(i).list.get(27).title);
                    context.startActivity(intent);
                }
            });






        }



        return view;
    }
    class ViewHolder{
        ImageView iv01,iv02,iv03,iv04,iv05,iv06;
        TextView tv01,tv02,tv03,tv04,tv05,tv06;
        LinearLayout ll_01,ll_02,ll_03,ll_04,ll_05,ll_06;
    }

    public void setListener(List<Classify> classifyList){
        this.classifyList = classifyList;
        notifyDataSetChanged();

    }
}

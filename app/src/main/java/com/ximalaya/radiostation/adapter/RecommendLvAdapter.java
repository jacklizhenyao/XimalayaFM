package com.lanou.radiostation.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanou.radiostation.R;
import com.lanou.radiostation.activity.AlbumActivity;
import com.lanou.radiostation.activity.ParticularsActivity;
import com.lanou.radiostation.activity.VideoActivity;
import com.lanou.radiostation.bean.Recommend;
import com.lanou.radiostation.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class RecommendLvAdapter extends BaseAdapter {
    List<Recommend> list;
    Context context;
    private ViewHolder holder1;
    List<Recommend.HotRecommends> hotRecommendsLvList;
    private final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_COUNT = 2;
    private ViewHolder2 holder2;

    public RecommendLvAdapter(Context context, List<Recommend> list, List<Recommend.HotRecommends> hotRecommendsLvList) {
        this.context = context;
        this.list = list;
        this.hotRecommendsLvList = hotRecommendsLvList;
    }


    /**
     * 该方法返回多少个不同的布局
     */
    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return TYPE_COUNT;
    }


    @Override
    public int getCount() {
//        Log.i("getCount", "getCount: " + hotRecommendsLvList.size());
        if (hotRecommendsLvList == null) {
            return 0;
        } else {

            return hotRecommendsLvList.size();
        }
    }

    @Override
    public Object getItem(int i) {

        return list.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }


    /**
     * 根据position返回相应的Item
     */
    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        int po = position;
        if (po == 0)
            return TYPE_ONE;
        else
            return TYPE_TWO;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        int type = getItemViewType(i);
        if (view == null) {

            switch (type) {
                case 0:

                    holder1 = new ViewHolder();
                    view = View.inflate(context, R.layout.frag_recommend_item_01, null);
                    holder1.message_tv_left = (TextView) view.findViewById(R.id.fragment_recommend_item_left_tv);
                    holder1.message_iv_left = (ImageView) view.findViewById(R.id.fragment_recommend_item_left_iv);
                    holder1.message_tv_center = (TextView) view.findViewById(R.id.fragment_recommend_item_center_tv);
                    holder1.message_iv_center = (ImageView) view.findViewById(R.id.fragment_recommend_item_center_iv);
                    holder1.message_tv_right = (TextView) view.findViewById(R.id.fragment_recommend_item_right_tv);
                    holder1.message_iv_right = (ImageView) view.findViewById(R.id.fragment_recommend_item_right_iv);
                    holder1.frag_recommend_item_jingping_ll_01 = (LinearLayout) view.findViewById(R.id.frag_recommend_item_jingping_ll_01);
                    holder1.frag_recommend_item_jingping_ll_02 = (LinearLayout) view.findViewById(R.id.frag_recommend_item_jingping_ll_02);

                    holder1.boutiqueTopTitle = (TextView) view.findViewById(R.id.fragment_recommend_item_boutique_top_title_tv);
                    holder1.boutiqueTopiv = (ImageView) view.findViewById(R.id.fragment_recommend_item_boutique_top_iv);
                    holder1.boutiqueTopContent = (TextView) view.findViewById(R.id.fragment_recommend_item_boutique_top_content_tv);
                    holder1.boutiqueBottomiv = (ImageView) view.findViewById(R.id.fragment_recommend_item_boutique_bottom_iv);
                    holder1.boutiqueBottomTitle = (TextView) view.findViewById(R.id.fragment_recommend_item_boutique_bottom_title_tv);
                    holder1.boutiqueBottomContent = (TextView) view.findViewById(R.id.fragment_recommend_item_boutique_bottom_content_tv);

                    holder1.fragment_recommend_hear_shopping_ll = (LinearLayout) view.findViewById(R.id.fragment_recommend_hear_shopping_ll);
                    holder1.hearShoppingIv = (ImageView) view.findViewById(R.id.fragment_recommend_hear_shopping_iv);
                    holder1.hearShoppingTitle = (TextView) view.findViewById(R.id.fragment_recommend_hear_shopping_title);
                    holder1.hearShoppingContent = (TextView) view.findViewById(R.id.fragment_recommend_hear_shopping_content);

                    holder1.hearActivityIv = (ImageView) view.findViewById(R.id.fragment_recommend_hear_activity_iv);
                    holder1.hearActivityTitle = (TextView) view.findViewById(R.id.fragment_recommend_hear_activity_title);
                    holder1.hearActivityContent = (TextView) view.findViewById(R.id.fragment_recommend_hear_activity_content);

                    holder1.hearCircleIv = (ImageView) view.findViewById(R.id.fragment_recommend_hear_circle_iv);
                    holder1.hearCircleTitle = (TextView) view.findViewById(R.id.fragment_recommend_hear_circle_title);
                    holder1.hearCircleContent = (TextView) view.findViewById(R.id.fragment_recommend_hear_circle_content);

                    holder1.hearRingIv = (ImageView) view.findViewById(R.id.fragment_recommend_hear_ring_iv);
                    holder1.hearRingTitle = (TextView) view.findViewById(R.id.fragment_recommend_hear_ring_title);
                    holder1.hearRingContent = (TextView) view.findViewById(R.id.fragment_recommend_hear_ring_centent);

                    view.setTag(holder1);

                    break;

                case 1:

                    holder2 = new ViewHolder2();
                    view = View.inflate(context, R.layout.frag_recommend_item, null);
                    holder2.inCludeTitle = (TextView) view.findViewById(R.id.fragment_recommend_include_tv);
                    holder2.message_tv_left = (TextView) view.findViewById(R.id.fragment_recommend_item_left_tv);
                    holder2.message_iv_left = (ImageView) view.findViewById(R.id.fragment_recommend_item_left_iv);
                    holder2.message_tv_center = (TextView) view.findViewById(R.id.fragment_recommend_item_center_tv);
                    holder2.message_iv_center = (ImageView) view.findViewById(R.id.fragment_recommend_item_center_iv);
                    holder2.message_tv_right = (TextView) view.findViewById(R.id.fragment_recommend_item_right_tv);
                    holder2.message_iv_right = (ImageView) view.findViewById(R.id.fragment_recommend_item_right_iv);
                    view.setTag(holder2);

                    break;

            }


        } else {

            switch (type) {
                case 0:
                    holder1 = (ViewHolder) view.getTag();
                    break;
                case 1:
                    holder2 = (ViewHolder2) view.getTag();
                    break;
            }


        }

        switch (type) {
            case 0:
                ImageLoaderUtils.getImageByloader(list.get(i).editorRecommendAlbums.list.get(0).albumCoverUrl290, holder1.message_iv_left);
                holder1.message_tv_left.setText(list.get(i).editorRecommendAlbums.list.get(0).trackTitle);
                ImageLoaderUtils.getImageByloader(list.get(i).editorRecommendAlbums.list.get(1).albumCoverUrl290, holder1.message_iv_center);
                holder1.message_tv_center.setText(list.get(i).editorRecommendAlbums.list.get(1).trackTitle);
                ImageLoaderUtils.getImageByloader(list.get(i).editorRecommendAlbums.list.get(2).albumCoverUrl290, holder1.message_iv_right);
                holder1.message_tv_right.setText(list.get(i).editorRecommendAlbums.list.get(2).trackTitle);
                holder1.boutiqueTopTitle.setText(list.get(i).specialColumn.list.get(0).title);
                holder1.boutiqueTopContent.setText(list.get(i).specialColumn.list.get(0).subtitle);
                ImageLoaderUtils.getImageByloader(list.get(i).specialColumn.list.get(0).coverPath, holder1.boutiqueTopiv);
                holder1.boutiqueBottomTitle.setText(list.get(i).specialColumn.list.get(1).title);
                holder1.boutiqueBottomContent.setText(list.get(i).specialColumn.list.get(1).subtitle);
                ImageLoaderUtils.getImageByloader(list.get(i).specialColumn.list.get(1).coverPath, holder1.boutiqueBottomiv);

                ImageLoaderUtils.getImageByloader(list.get(i).discoveryColumns.list.get(0).coverPath, holder1.hearShoppingIv);
                holder1.hearShoppingTitle.setText(list.get(i).discoveryColumns.list.get(0).title);
                holder1.hearShoppingContent.setText(list.get(i).discoveryColumns.list.get(0).subtitle);

                ImageLoaderUtils.getImageByloader(list.get(i).discoveryColumns.list.get(1).coverPath, holder1.hearActivityIv);
                holder1.hearActivityTitle.setText(list.get(i).discoveryColumns.list.get(1).title);
                holder1.hearActivityContent.setText(list.get(i).discoveryColumns.list.get(1).subtitle);

                ImageLoaderUtils.getImageByloader(list.get(i).discoveryColumns.list.get(2).coverPath, holder1.hearCircleIv);
                holder1.hearCircleTitle.setText(list.get(i).discoveryColumns.list.get(2).title);
                holder1.hearCircleContent.setText(list.get(i).discoveryColumns.list.get(2).subtitle);

                ImageLoaderUtils.getImageByloader(list.get(i).discoveryColumns.list.get(3).coverPath, holder1.hearRingIv);
                holder1.hearRingTitle.setText(list.get(i).discoveryColumns.list.get(3).title);
                holder1.hearRingContent.setText(list.get(i).discoveryColumns.list.get(3).subtitle);



                holder1.fragment_recommend_hear_shopping_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        context.startActivity(intent);
                    }
                });


                holder1.message_iv_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, AlbumActivity.class);
                        intent.putExtra("picUrl",list.get(i).editorRecommendAlbums.list.get(0).albumCoverUrl290);
                        intent.putExtra("albumId",list.get(i).editorRecommendAlbums.list.get(0).albumId);
                        intent.putExtra("title",list.get(i).editorRecommendAlbums.list.get(0).title);
                        intent.putExtra("position",0);
                        intent.putExtra("titleName",list.get(i).editorRecommendAlbums.list.get(0).title);
                        intent.putExtra("nickname",list.get(i).editorRecommendAlbums.list.get(0).nickname);
                        intent.putExtra("playsCounts",list.get(i).editorRecommendAlbums.list.get(0).playsCounts);
                        context.startActivity(intent);
                    }
                });
                holder1.message_iv_center.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, AlbumActivity.class);
                        intent.putExtra("picUrl",list.get(i).editorRecommendAlbums.list.get(1).albumCoverUrl290);
                        intent.putExtra("albumId",list.get(i).editorRecommendAlbums.list.get(1).albumId);
                        intent.putExtra("title",list.get(i).editorRecommendAlbums.list.get(1).title);
                        intent.putExtra("position",0);
                        intent.putExtra("titleName",list.get(i).editorRecommendAlbums.list.get(1).title);
                        intent.putExtra("nickname",list.get(i).editorRecommendAlbums.list.get(1).nickname);
                        intent.putExtra("playsCounts",list.get(i).editorRecommendAlbums.list.get(1).playsCounts);
                        context.startActivity(intent);
                    }
                });
                holder1.message_iv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, AlbumActivity.class);
                        intent.putExtra("picUrl",list.get(i).editorRecommendAlbums.list.get(2).albumCoverUrl290);
                        intent.putExtra("albumId",list.get(i).editorRecommendAlbums.list.get(2).albumId);
                        intent.putExtra("title",list.get(i).editorRecommendAlbums.list.get(2).title);
                        intent.putExtra("position",0);
                        intent.putExtra("titleName",list.get(i).editorRecommendAlbums.list.get(2).title);
                        intent.putExtra("nickname",list.get(i).editorRecommendAlbums.list.get(2).nickname);
                        intent.putExtra("playsCounts",list.get(i).editorRecommendAlbums.list.get(2).playsCounts);
                        context.startActivity(intent);
                    }
                });


                holder1.frag_recommend_item_jingping_ll_01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, ParticularsActivity.class);
                        intent.putExtra("specialId",list.get(i).specialColumn.list.get(0).specialId);
                        context.startActivity(intent);
                    }
                });
                holder1.frag_recommend_item_jingping_ll_02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, ParticularsActivity.class);
                        intent.putExtra("specialId",list.get(i).specialColumn.list.get(1).specialId);
                        context.startActivity(intent);
                    }
                });



                break;
            case 1:

                holder2.inCludeTitle.setText(hotRecommendsLvList.get(i).list.get(i - 1).title);
                ImageLoaderUtils.getImageByloader(hotRecommendsLvList.get(i).list.get(i - 1).list.get(0).albumCoverUrl290, holder2.message_iv_left);
                ImageLoaderUtils.getImageByloader(hotRecommendsLvList.get(i).list.get(i - 1).list.get(1).albumCoverUrl290, holder2.message_iv_center);

                if (hotRecommendsLvList.get(i).list.get(i - 1).list.size() == 2) {
                    holder2.message_iv_right.setImageResource(R.mipmap.haha);
                }else{
                    ImageLoaderUtils.getImageByloader(hotRecommendsLvList.get(i).list.get(i - 1).list.get(2).albumCoverUrl290, holder2.message_iv_right);
                }

                holder2.message_tv_left.setText(hotRecommendsLvList.get(i).list.get(i - 1).list.get(0).trackTitle);
                holder2.message_tv_center.setText(hotRecommendsLvList.get(i).list.get(i - 1).list.get(1).trackTitle);


                if (hotRecommendsLvList.get(i).list.get(i - 1).list.size() == 2) {
                    holder2.message_tv_right.setText("中医是一门大学问,医人即医国");
                }else{
                    holder2.message_tv_right.setText(hotRecommendsLvList.get(i).list.get(i - 1).list.get(2).trackTitle);
                }


                holder2.message_iv_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, AlbumActivity.class);
                        intent.putExtra("picUrl",hotRecommendsLvList.get(i).list.get(i - 1).list.get(0).albumCoverUrl290);
                        intent.putExtra("albumId",hotRecommendsLvList.get(i).list.get(i - 1).list.get(0).albumId);
                        intent.putExtra("title",hotRecommendsLvList.get(i).list.get(i - 1).title);
                        intent.putExtra("titleName",hotRecommendsLvList.get(i).list.get( i - 1).list.get(0).title);
                        intent.putExtra("nickname",hotRecommendsLvList.get(i).list.get( i - 1).list.get(0).nickname);
                        intent.putExtra("playsCounts",hotRecommendsLvList.get(i).list.get( i - 1).list.get(0).playsCounts);
                        intent.putExtra("position",1);
                        context.startActivity(intent);
                    }
                });
                holder2.message_iv_center.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, AlbumActivity.class);
                        intent.putExtra("picUrl",hotRecommendsLvList.get(i).list.get(i - 1).list.get(1).albumCoverUrl290);
                        intent.putExtra("albumId",hotRecommendsLvList.get(i).list.get(i - 1).list.get(1).albumId);
                        intent.putExtra("title",hotRecommendsLvList.get(i).list.get(i - 1).title);
                        intent.putExtra("position",2);
                        intent.putExtra("titleName",hotRecommendsLvList.get(i).list.get( i - 1).list.get(1).title);
                        intent.putExtra("nickname",hotRecommendsLvList.get(i).list.get( i - 1).list.get(1).nickname);
                        intent.putExtra("playsCounts",hotRecommendsLvList.get(i).list.get( i - 1).list.get(1).playsCounts);
                        context.startActivity(intent);
                    }
                });
                holder2.message_iv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(context, AlbumActivity.class);
                        intent.putExtra("picUrl",hotRecommendsLvList.get(i).list.get(i - 1).list.get(2).albumCoverUrl290);
                        intent.putExtra("albumId",hotRecommendsLvList.get(i).list.get(i - 1).list.get(2).albumId);
                        intent.putExtra("title",hotRecommendsLvList.get(i).list.get(i - 1).title);
                        intent.putExtra("position",3);
                        intent.putExtra("titleName",hotRecommendsLvList.get(i).list.get( i - 1).list.get(2).title);
                        intent.putExtra("nickname",hotRecommendsLvList.get(i).list.get( i - 1).list.get(2).nickname);
                        intent.putExtra("playsCounts",hotRecommendsLvList.get(i).list.get( i - 1).list.get(2).playsCounts);
                        context.startActivity(intent);
                    }
                });





                break;

        }


        return view;


    }

    class ViewHolder {
        ImageView message_iv_left;
        TextView message_tv_left;
        ImageView message_iv_center;
        TextView message_tv_center;
        ImageView message_iv_right;
        TextView message_tv_right;

        ImageView boutiqueTopiv;
        TextView boutiqueTopTitle;
        TextView boutiqueTopContent;
        ImageView boutiqueBottomiv;
        TextView boutiqueBottomTitle;
        TextView boutiqueBottomContent;


        ImageView hearShoppingIv;
        TextView hearShoppingTitle;
        TextView hearShoppingContent;
        LinearLayout fragment_recommend_hear_shopping_ll;


        ImageView hearActivityIv;
        TextView hearActivityTitle;
        TextView hearActivityContent;

        ImageView hearCircleIv;
        TextView hearCircleTitle;
        TextView hearCircleContent;

        ImageView hearRingIv;
        TextView hearRingTitle;
        TextView hearRingContent;

        LinearLayout frag_recommend_item_jingping_ll_01,frag_recommend_item_jingping_ll_02;
    }

    class ViewHolder2 {
        ImageView message_iv_left;
        TextView message_tv_left;
        ImageView message_iv_center;
        TextView message_tv_center;
        ImageView message_iv_right;
        TextView message_tv_right;
        TextView inCludeTitle;
    }

    public void setListener(List<Recommend> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}

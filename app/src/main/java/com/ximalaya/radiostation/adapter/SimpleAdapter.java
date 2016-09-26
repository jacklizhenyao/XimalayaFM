package com.lanou.radiostation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanou.radiostation.R;

import java.util.List;

/**
 * Created by user on 2016/7/20.
 */
public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    protected List<String> datas;
    private LayoutInflater mInflater;

    public interface OnItemClickListener{
        void OnItemClick(View view, int position);
        void OnItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public SimpleAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;

        mInflater = LayoutInflater.from(context);
    }

    /*
    创建ViewHolder
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = mInflater.inflate(R.layout.item_single_textview,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    /*
    绑定ViewHolder
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv.setText(datas.get(position));
        setUpItemEvent(holder);


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(int position){
        datas.add(position,"insert One");
        notifyItemInserted(position);
    }

    public void deleteData(int position){
        datas.remove(position);
        notifyItemRemoved(position);
    }


    protected void setUpItemEvent(final MyViewHolder holder){

        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.OnItemClick(holder.itemView,layoutPosition);

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.OnItemLongClick(holder.itemView,layoutPosition);
                    return false;
                }
            });

        }

    }
}



class MyViewHolder extends RecyclerView.ViewHolder
{

    TextView tv;
    public MyViewHolder(View itemView) {
        super(itemView);

        tv = (TextView) itemView.findViewById(R.id.id_tv);
    }
}
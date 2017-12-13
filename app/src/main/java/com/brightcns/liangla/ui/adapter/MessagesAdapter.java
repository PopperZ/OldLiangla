package com.brightcns.liangla.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brightcns.liangla.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.brightcns.liangla.service.entity.MessageBean;

/**
 * Created by zhangfeng on 14/4/17.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<MessageBean.DataBean> mList;  //mList=body

    public MessagesAdapter(ArrayList<MessageBean.DataBean> list, Context context) {
        mList = list;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_list_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    /*每个条目走一次*/
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MessageBean.DataBean messageBean = mList.get(position);
        String createTime = messageBean.getCreateTime();
        holder.mMsgName.setText(messageBean.getTitle());
        holder.mContent.setText(messageBean.getContent());
//
//        String time = DateUtils.formatDateTime(mContext, Long.parseLong(createTime),  DateUtils.FORMAT_24HOUR|DateUtils.FORMAT_SHOW_TIME
//                |DateUtils.FORMAT_SHOW_YEAR|DateUtils.LENGTH_LONG|DateUtils.FORMAT_ABBREV_MONTH);
        try {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date = format.parse(createTime);
            String formatTime = format.format(date);
            holder.mMsgtime.setText(formatTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.mMsgListItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(position, messageBean.getContent());
            }
        });

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mMsgtime;
        TextView mMsgName;
        TextView mContent;
        RelativeLayout mMsgListItemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mMsgtime = (TextView) itemView.findViewById(R.id.message_time);
            mMsgName = (TextView) itemView.findViewById(R.id.message_name);
            mContent = (TextView) itemView.findViewById(R.id.message_content);
            mMsgListItemView = (RelativeLayout) itemView.findViewById(R.id.msg_list_item_view);
            mMsgListItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                    }

                    return false;
                }
            });

        }
    }

    /*点击事件回调*/
    public interface OnItemClickListener {
        void onItemClickListener(int position, String content);
    }

    ;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    /*长按时间回调*/
    public interface OnItemLongClickListener {
        void onItemLongClickListener();
    }

    ;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }


}

package com.brightcns.liangla.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brightcns.liangla.R;
import com.brightcns.liangla.service.entity.ChangeAgrBean;

import java.util.ArrayList;

/**
 * Created by 巩贺 on 2017/4/26.
 */

public class ChangeVcodeAdapter extends BaseAdapter {
    private String cityProjectName;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ChangeAgrBean> mList;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    private Itemclick mItemclick;
    private MyViewHolder mViewHolder;

    public ChangeVcodeAdapter(Context context, ArrayList<ChangeAgrBean> list, String cityProjectName) {
        super();
        this.context = context;
        this.mList = list;
        this.cityProjectName = cityProjectName;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ChangeAgrBean changeVcodeBean = mList.get(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_changecity_item, parent, false);
            mViewHolder = new MyViewHolder();
            mViewHolder.cityprojectIcon = (ImageView) view.findViewById(R.id.icon_cityprojectname);
            mViewHolder.cityprojectName = (TextView) view.findViewById(R.id.name_cityprojectname);
            mViewHolder.isChoosedicon = (ImageView) view.findViewById(R.id.changeVcode_selectediv);
            mViewHolder.clickview = (RelativeLayout) view.findViewById(R.id.balance);
            view.setTag(mViewHolder);
        } else {
            view = convertView;
            mViewHolder = (MyViewHolder) view.getTag();
        }
        if (cityProjectName.equals(changeVcodeBean.getmAreaName())) {
            mViewHolder.isChoosedicon.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.isChoosedicon.setVisibility(View.INVISIBLE);
        }

        mViewHolder.cityprojectIcon.setImageResource(changeVcodeBean.getmAreaIcon());
        mViewHolder.cityprojectName.setText(changeVcodeBean.getmAreaName());
        mViewHolder.clickview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemclick.onitemclickclick(position);
            }
        });
        return view;
    }

    public class MyViewHolder {
        ImageView cityprojectIcon;
        TextView cityprojectName;
        ImageView isChoosedicon;
        RelativeLayout clickview;
    }

    public interface Itemclick {
        void onitemclickclick(int position);
    }

    public void onItemclickclicl(Itemclick itemclick) {
        this.mItemclick = itemclick;
    }
}

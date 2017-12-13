package com.brightcns.liangla.weight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.brightcns.liangla.R;
import com.brightcns.liangla.service.entity.ChangeAgrBean;
import com.brightcns.liangla.ui.activity.RidingActivity;
import com.brightcns.liangla.ui.adapter.ChangeVcodeAdapter;
import com.brightcns.liangla.utils.ConstantUtil;

import java.util.ArrayList;

/**
 * Created by zhangfeng on 4/12/17.
 */

public class ChooseAgrPopupWindow extends PopupWindow {
    private final View mMenuView;
    private ListView mView;
    private ImageView mCancel;
    private Context mContext;
    private String []name={"上海地铁","智慧松江","松江有轨","厦门BRT"};
    private ArrayList<ChangeAgrBean> mList;
    private int [] icon={R.mipmap.ic_shanghaisubway,R.mipmap.ic_wisdomsongjiang,R.mipmap.ic_tianjinmetro,R.mipmap.ic_xiamenbrt};
    private final SharedPreferences mSp;
    private final SharedPreferences.Editor mEditor;
    private String TAG = "TAG";
    private final String mNameforVcode;

    public ChooseAgrPopupWindow(Context context) {
        super(context);
        this.mContext=context;
        mSp = context.getSharedPreferences("admin", Context.MODE_PRIVATE);
        mEditor = mSp.edit();

        mNameforVcode = mSp.getString("agr", "上海地铁");
        mList=new ArrayList<ChangeAgrBean>();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.chooseagr_layout, null);
        initView(mMenuView);
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.chooseagr).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        //设置SelectPicPopupWindow的View
        this.setContentView(this.mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);

        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);


        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        ChooseAgrPopupWindow.this.setBackgroundDrawable(dw);

        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.avatar_anim);

        initData();
    }

    private void initData() {
        for (int i=0;i<icon.length;i++){
//            ChosseAgrBean data=new ChosseAgrBean(icon[i],name[i]);
            ChangeAgrBean data = new ChangeAgrBean(icon[i],name[i]);
            mList.add(data);
        }
//             LinearLayoutMannager 是一个布局排列 ， 管理的接口,子类都都需要按照接口的规范来实现。
        LinearLayoutManager ms = new LinearLayoutManager(mContext);
//         设置 recyclerview 布局方式为横向布局
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        ChangeVcodeAdapter changeVcodeAdapter = new ChangeVcodeAdapter(mContext,mList,mNameforVcode);
        mView.setAdapter(changeVcodeAdapter);
        changeVcodeAdapter.onItemclickclicl(new ChangeVcodeAdapter.Itemclick() {
            @Override
            public void onitemclickclick(int position) {
                mEditor.putString("agr",name[position]);
                mEditor.commit();
                ChooseAgrPopupWindow.this.dismiss();
                ((RidingActivity)mContext).mBroadcastManager.sendBroadcast(new Intent(ConstantUtil.ACTION_QR_NEW));
            }
        });
    }

    private void initView(View mMenuView) {
        mCancel= (ImageView) mMenuView.findViewById(R.id.cancel_chooseAgr);
        //取消按钮
        mCancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        mView= (ListView) mMenuView.findViewById(R.id.chooseagr_view);
    }
}

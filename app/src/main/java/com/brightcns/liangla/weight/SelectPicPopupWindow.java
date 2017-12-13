package com.brightcns.liangla.weight;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.brightcns.liangla.R;


/**
 * Created by 巩贺 on 2017/2/22.
 */
public class SelectPicPopupWindow extends PopupWindow {

    private final View mMenuView;
    private final RelativeLayout mPoppaizhao;
    private final RelativeLayout mPopxuanzezhaopian;
    private final RelativeLayout mPopquxiao;

    public SelectPicPopupWindow(Context context, View.OnClickListener itemsOnClick) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.alert_dialog, null);

        mPoppaizhao = (RelativeLayout) mMenuView.findViewById(R.id.poppaizhao);
        mPopxuanzezhaopian = (RelativeLayout) mMenuView.findViewById(R.id.popxuanzezhaopian);
        mPopquxiao = (RelativeLayout) mMenuView.findViewById(R.id.popquxiao);

        //取消按钮
        mPopquxiao.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });

        //设置条目监听
        mPoppaizhao.setOnClickListener(itemsOnClick);
        mPopxuanzezhaopian.setOnClickListener(itemsOnClick);

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.selectpic_pop_layout).getTop();

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
        SelectPicPopupWindow.this.setBackgroundDrawable(dw);

        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.avatar_anim);

    }
}

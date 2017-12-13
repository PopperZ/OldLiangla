package com.brightcns.liangla.service.presenter.RidingPresenter;

import android.content.Context;
import android.content.Intent;

import com.brightcns.liangla.greenDao.dao.RidingWayDao;
import com.brightcns.liangla.service.presenter.Presenter;
import com.brightcns.liangla.service.view.BaseView;
import com.brightcns.liangla.service.view.RidingView.RidingWayView;
import com.brightcns.liangla.utils.AppUtil;
import com.brightcns.liangla.utils.LogUtil;

/**
 * Created by zhangfeng on 1/12/17.
 */

public class RidingWayPresenter implements Presenter {
    private Context mContext;
    private RidingWayView mRidingWayView;
    private String TAG="RidingWayPresenter";

    public RidingWayPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreat() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void AttachView(BaseView baseView) {
        mRidingWayView= (RidingWayView) baseView;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    //根据城市代码和业务类型选择乘车方式
    public  void selectRidingWay(String areaCode,String businessType){
        //建表进行映射关系
        String bleType= RidingWayDao.queryBleType(areaCode,businessType);
        if (bleType.equals("pos")){
            LogUtil.e("ridingWay","POS");
            mRidingWayView.go2Pos(bleType);
        }else if (bleType.equals("metro")){
            LogUtil.e("ridingWay","MRTEO");
            mRidingWayView.go2Metro(bleType);
        }
    }

}

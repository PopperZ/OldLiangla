package com.brightcns.liangla.service.presenter.RidingPresenter;

import android.content.Context;
import android.content.Intent;

import com.brightcns.liangla.Ble.MetroQRData;
import com.brightcns.liangla.service.manager.DataManager;
import com.brightcns.liangla.service.presenter.Presenter;
import com.brightcns.liangla.service.view.BaseView;
import com.brightcns.liangla.utils.BleUtils;
import com.brightcns.liangla.utils.DESMacUtils;
import com.brightcns.liangla.utils.LogUtil;

import java.util.Map;

import com.brightcns.liangla.service.entity.QRBean;
import com.brightcns.liangla.service.view.RidingView.QRView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangfeng on 30/11/17.
 */
//
        /*
        * 二维码生成流程
        * 1.判断进出站
        * 2.判断网络
        * 3.判断订单
        * 4.判断蓝牙状态
        * 5.获取或本地生成二维码信息
        * 6.显示二维码
        * */
public class QRPresenter implements Presenter {
    private Context mContext;
    private QRView mQRView;
    private CompositeSubscription mCompositeSubscription;
    private DataManager mDataManager;
    private QRBean mQRBean;


    public QRPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreat() {
        mDataManager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
        mDataManager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
    }
    @Override
    public void AttachView(BaseView baseView) {
        mQRView= (QRView) baseView;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    public void getQRMsg(Map<String, String> headers){
        LogUtil.e("QRPresenter",headers.toString());
        mCompositeSubscription.add(mDataManager.getQRMsg(headers,"xiamen")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QRBean>() {
                    @Override
                    public void onCompleted() {
                        if (mQRBean!=null) {
                            mQRView.onSuccess(setQRMsg(mQRBean.getData(), BleUtils.getBleMac(mContext)));
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        mQRView.onFail("請求錯誤");
                    }

                    @Override
                    public void onNext(QRBean qrBean) {
                        mQRBean=qrBean;
                    }
                }));
    }

    public String setQRMsg(QRBean.DataBean dataBean,String blemac){
        MetroQRData qrData=new MetroQRData(mContext);
        qrData.Init();
        qrData.initBleContent(dataBean,blemac);
        try {
            qrData.setMac(DESMacUtils.calculatePBOC3DesMAC(qrData.getMACContent(),"02F614743F53B76DE5D88EA056379241").substring(0,8));
        } catch (Exception e) {
            LogUtil.e("QRPresenter","des加密失败"+e.toString());
            e.printStackTrace();
        }

        try {
            qrData.setAppMac(DESMacUtils.calculatePBOC3DesMAC(qrData.getAppMACContent(), "02F614743F53B76DE5D88EA056379241").substring(0, 8));
        } catch (Exception e) {
            LogUtil.e("QRPresenter", "des加密异常"+e.toString());
            e.printStackTrace();
        }
        return qrData.getQrConetent();
    }
}

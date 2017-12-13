package com.brightcns.liangla.service.downloadapk;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brightcns.liangla.R;
import com.brightcns.liangla.app.MyApplication;
import com.brightcns.liangla.ui.activity.WelcomeActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;


/**
 * Created by gh on 2016/7/27.
 */
public class NewUpdateNormalDialog extends Dialog implements View.OnClickListener {

    private String mApkUri;
    private TextView mTvContent;
    private Button mBtnOk;
    private Button mBtnCancel;

    private Context mContext;
//    private CheckUpdata mData;

    private String mUrl;
    private boolean mApkExist;
    private String mPath;
    private boolean isForce;

    public NewUpdateNormalDialog(Context context, String apkUri) {
        super(context);
        this.mContext =context;
        this.mApkUri = apkUri;
        init();
    }

    public NewUpdateNormalDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected NewUpdateNormalDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

/*    public NewUpdateNormalDialog(Context context, CheckUpdata data) {
        super(context, R.style.transparent_bg_dialog);//noneblackbg_dialog
        getWindow().setWindowAnimations(R.style.normal_updata_dialogAnim);
        this.mContext = context;
        this.mData = data;
        init();
    }*/

    private void init() {

        initView(mContext);

        initData(mContext);

        initListener(mContext);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.new_update_dialog, null);
        setContentView(view);

        mTvContent = (TextView) findViewById(R.id.umeng_update_content);

        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
    }

    private void initData(Context context) {
//        PreferencesUtil.getInstance().putInt(GuideManager.APP_VERSION, Integer.parseInt(mData.version));

/*        mUrl = mData.link;
        mPath = Environment.getExternalStorageDirectory() + "/" + FrameApplication.PROJECT_NAME + "/" + "QMLive" + Utils.version + ".apk";

        mApkExist = isApkExist(mPath);

        //处理转义字符
        String doc = mData.tips.replace("\\n", "\n");
        doc = doc + "";
        mTvContent.setText(doc);*/
    }

    private boolean isApkExist(String path) {
        File file = new File(path);
        file.isFile();
        return file.exists();
    }

    private void initListener(Context context) {
        mBtnOk.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:

//                if (getOwnerActivity() == null) return;
                new RxPermissions((WelcomeActivity) mContext)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe((aBoolean) -> {
                            if (!aBoolean) {
                                Toast.makeText(mContext, "拒绝权限将导致部分功能不好用！", Toast.LENGTH_SHORT).show();

                            } else {

/*                                if (mUrl.startsWith("http")) {
                                    ToastUtil.showToast(mContext.getApplicationContext(), mContext.getString(R.string.start_download));
                                    FrameApplication.getApp().getDownBinder().updataQM(mUrl);
                                } else {
                                    ToastUtil.showToast(getContext().getApplicationContext(), mContext.getString(R.string.server_exception));
                                    return;
                                }*/
//                                    FrameApplication.getApp().getDownBinder().updataQM(mUrl);
                                //测试 用URL    "http://www.liangla.mobi/dl/app-release-u1023.apk"
                                MyApplication.getDownBinder().updataQM(mApkUri);
                                dismiss();

                            }
                        }, Throwable::printStackTrace);

                break;
            case R.id.btn_cancel:

                dismiss();

                break;

        }
    }

    @Override
    public void show() {
        if (isForce) {
            mBtnCancel.setVisibility(View.GONE);
            mBtnOk.setBackgroundDrawable(null);
            mBtnOk.setTextColor(mContext.getResources().getColor(R.color.color_FA514E));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mBtnOk.setLayoutParams(layoutParams);
        }
        mContext.startService(new Intent(mContext, DownloadGameService.class));
        super.show();
    }

    private void installApk(String path) {

        try {
            /*********下载完成，点击安装***********/
            File apkfile = new File(path);
            if (!apkfile.exists()) {
                return;
            }
            Intent intentinstall = new Intent(Intent.ACTION_VIEW);
            intentinstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentinstall.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                    "application/vnd.android.package-archive");
            mContext.startActivity(intentinstall);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public void setIsForceUpdata(boolean isForce) {

        this.isForce = isForce;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isForce) {
                Toast.makeText(mContext, "您的版本过低，更新才可以使用哦~", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        } else
            return super.onKeyDown(keyCode, event);
    }

}

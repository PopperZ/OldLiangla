package com.brightcns.liangla.service.downloadapk;


import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

import com.brightcns.liangla.R;

import java.io.File;

import com.brightcns.liangla.app.MyApplication;

/**
 * 创建一个在后台下载的服务
 */

public class DownloadGameService extends Service implements IDownLoadGame {


    private String mUrl = "";
    private DownloadManager mDownloadManager;
    private DownloadReceiver mDownloadReceiver;
    private String sdRootPath;
    private String sdPath;
    private String dataPath;
    private String DOWNLOADPATH = "/apk/";//下载路径，如果不定义自己的路径，6.0的手机不自动安装

    @Override
    public void onCreate() {
        super.onCreate();

        MyApplication.setDownBinder(new DownloadBinder(this));
        mDownloadReceiver = new DownloadReceiver();
        registerReceiver(mDownloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new DownloadBinder(this);
    }

    /***
     * down file
     *
     * @return
     */
    public void downloadFile(String name, String down_url) {
        if (TextUtils.isEmpty(down_url)) {
            return;
        }

        if ((mUrl + "").equals(down_url))
            return;

        if (!down_url.startsWith("http")) {
            down_url = "http" + down_url;
        }

        String saveurl = getSaveUrl(down_url);
        String serviceString = Context.DOWNLOAD_SERVICE;

        //创建一个下载请求，down_url为下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(down_url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);//设置哪种网络类型可下载
        request.setAllowedOverRoaming(false);//漫游网络是否可以下载

        //在通知栏中显示，默认为显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        request.setTitle(name);
        request.setDescription(name);
        request.setDestinationUri(Uri.parse(saveurl));
        request.setDestinationInExternalPublicDir(DOWNLOADPATH, name + ".apk");

        //需要将下载请求加入下载队列
        mDownloadManager = (DownloadManager) getSystemService(serviceString);
        //加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务等等
        mDownloadManager.enqueue(request);
    }

    @Override
    public void updataQM(String down_url) {

        if (!isDownloadManagerAvailable(getApplicationContext())) {

            Toast.makeText(this, "下载管理器被禁用，请在设置中开启", Toast.LENGTH_SHORT).show();

            try {
                //Open the specific App Info page:
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + "com.android.providers.downloads"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();

                //Open the generic Apps page:
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            return;
        }

        String saveurl = getSaveUrl(down_url);

        String serviceString = Context.DOWNLOAD_SERVICE;

        //创建一个下载请求，down_url为下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(down_url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);//设置哪种网络类型可下载
        request.setAllowedOverRoaming(false);//漫游网络是否可以下载

        //在通知栏中显示，默认为显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        request.setTitle(getString(R.string.app_name));
        request.setDescription(getString(R.string.app_name));
        request.setDestinationUri(Uri.parse(saveurl));
        request.setDestinationInExternalPublicDir(DOWNLOADPATH, "666.apk");

        //需要将下载请求加入下载队列
        mDownloadManager = (DownloadManager) getSystemService(serviceString);
        //加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务等等
        mDownloadManager.enqueue(request);
    }

    private boolean isDownloadManagerAvailable(Context context) {

        if (!isPkgInstalled("com.android.providers.downloads")) {
            return true;
        }

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED_USER
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {

                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isPkgInstalled(String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = this.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    public String getSaveUrl(String down_url) {

        return getdatapath() + FileUtil.getFileName(down_url);
    }

    /*获取文件保存路径*/
    public String getdatapath() {
        String fileSeparator = System.getProperty("file.separator");
        sdPath = getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
        sdRootPath = sdPath + fileSeparator + "liangla" + fileSeparator;
        dataPath = sdRootPath + "data" + fileSeparator;
        return dataPath;
    }

    private void onError(String url) {

        try {
            File file = new File(url + "");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mDownloadReceiver);
    }

    public class DownloadBinder extends Binder {

        IDownLoadGame inteface;

        public DownloadBinder(IDownLoadGame it) {

            inteface = it;
        }

        public void downloadFile(String name, String down_url) {
            inteface.downloadFile(name, down_url);
        }

        public void updataQM(final String down_url) {
            inteface.updataQM(down_url);
        }
    }
}

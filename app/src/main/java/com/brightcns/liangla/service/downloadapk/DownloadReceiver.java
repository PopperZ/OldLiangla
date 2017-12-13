package com.brightcns.liangla.service.downloadapk;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import java.io.File;

/**
 * 广播————————>如果下载完成就安装apk
 */

public class DownloadReceiver extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            DownloadManager.Query query = new DownloadManager.Query();
            //在广播中取出下载任务的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            query.setFilterById(id);
            Cursor c = manager.query(query);
            if (c != null && c.moveToFirst()) {
                //获取文件下载路径
                String filename = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                Log.e("TAG", "获取文件下载路径"+filename);
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                //如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
                if (filename != null) {
                    switch (status) {
                        case DownloadManager.STATUS_PAUSED:

                            break;
                        case DownloadManager.STATUS_PENDING:

                            break;
                        case DownloadManager.STATUS_RUNNING:

                            break;
                        case DownloadManager.STATUS_SUCCESSFUL:

                            Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                            //下载完成安装APK
                            String path = filename;
                            installApk(path);//避免下载成功之后文件自动重命名得问题

                            break;
                        case DownloadManager.STATUS_FAILED:
                            Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
                long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
                //点击通知栏取消下载
                manager.remove(ids);
                Toast.makeText(context, "已经取消下载", Toast.LENGTH_SHORT).show();
            }
        }

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

}
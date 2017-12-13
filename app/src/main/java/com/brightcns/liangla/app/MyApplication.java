package com.brightcns.liangla.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.brightcns.liangla.greenDao.entity.DaoMaster;
import com.brightcns.liangla.greenDao.entity.DaoSession;
import com.brightcns.liangla.service.downloadapk.DownloadGameService;

/**
 * Created by 巩贺 on 2017/11/7.
 */

public class MyApplication extends Application{
    private static Context mContext;
    private static DownloadGameService.DownloadBinder binder;
    private static DaoSession mDaoSession;

    public static DownloadGameService.DownloadBinder getDownBinder() {
        return binder;
    }
    public static void setDownBinder(DownloadGameService.DownloadBinder bind) {
        binder = bind;
    }
    private static MyApplication myApplication = null;

    public static MyApplication getApplication() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mContext = getApplicationContext();
        //配置数据库
        setupDatabase();
    }


    //配置数据库
    private void setupDatabase() {
        //创建数据库库liangla.db
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"liangla.db",null);
        //获取可写数据库
        SQLiteDatabase database=helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster=new DaoMaster(database);
        //获取Dao对象管理者
        mDaoSession=daoMaster.newSession();
    }

    public static DaoSession getDaoInstant(){
        return mDaoSession;
    }

    /**
     * get
     * @return
     */
    public static Context getGlobalContext() {
        return mContext;
    }


}

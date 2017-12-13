package com.brightcns.liangla.service;


import android.content.Context;

import com.brightcns.liangla.app.MyApplication;
import com.brightcns.liangla.utils.HttpsFactroy;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 巩贺 on 2017/10/28.
 */

public class RetrofitHelper {
    private Context mCntext;
    OkHttpClient mOkHttpClient = new OkHttpClient
            .Builder().build();
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit;

    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitHelper(context);
        }
        return instance;
    }

    /*constructure*/
    private RetrofitHelper(Context mContext) {
        mCntext = mContext;
        init();
    }

    private void init() {
        resetApp();
    }
    ///api/v2/apps/versions
    //http://192.168.10.116/api/v2/apps/versions
    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.brightcns.com/api/v2/")
                .client(HttpsFactroy.getClient(MyApplication.getGlobalContext()))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public RetrofitService getServer() {

        return mRetrofit.create(RetrofitService.class);
    }

}

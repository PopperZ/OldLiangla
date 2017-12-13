package com.brightcns.liangla.utils;

import android.content.Context;
import android.util.Log;


import com.brightcns.liangla.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zhangfeng on 21/3/17.
 */

public class HttpsFactroy {
    /*
    *
    *https请求的方法
    */
    //hostname
    private static String hostUrls="api.brightcns.com";

    //自签名的数字认证
    public static SSLSocketFactory getSSLSocketFactory(Context context, String keyStoreType, int keystoreResId)
    throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException
    {

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        InputStream caInput = context.getResources().openRawResource(keystoreResId);

        Certificate ca = cf.generateCertificate(caInput);

        caInput.close();

        if(keyStoreType ==null|| keyStoreType.length() ==0) {

            keyStoreType = KeyStore.getDefaultType();

        }

        KeyStore keyStore = KeyStore.getInstance(keyStoreType);

        keyStore.load(null,null);

        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);

        tmf.init(keyStore);

        TrustManager[] wrappedTrustManagers =getWrappedTrustManagers(tmf.getTrustManagers());

        SSLContext sslContext = SSLContext.getInstance("TLS");

        sslContext.init(null, wrappedTrustManagers,null);

        return sslContext.getSocketFactory();
    }

    //hostname的认证
    public static HostnameVerifier getHostnameVerifier() {

        HostnameVerifier TRUSTED_VERIFIER = new HostnameVerifier() {

            public boolean verify(String hostname, SSLSession session) {
                boolean ret = false;
                    if (hostUrls.equalsIgnoreCase(hostname)) {
                        ret = true;
                    }
                return ret;
            }
        };

        return TRUSTED_VERIFIER;
    }

    //得到请求client
    public static OkHttpClient getClient(final Context context){
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("TAG","OkHttp====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        OkHttpClient client = null;
        try {
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(getSSLSocketFactory(context, "BKS", R.raw.lldata))
                    .hostnameVerifier(getHostnameVerifier())
                    .addInterceptor(loggingInterceptor)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    /**
     * Enables https connections
     */
        private static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
            final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
            return new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return originalTrustManager.getAcceptedIssuers();
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            try {
                                originalTrustManager.checkClientTrusted(certs, authType);
                            } catch (CertificateException e) {
                                e.printStackTrace();
                            }
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            try {
                                originalTrustManager.checkServerTrusted(certs, authType);
                            } catch (CertificateException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            };
        }

}

package com.brightcns.liangla.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brightcns.liangla.R;


public class WebActivity extends BaseActivity{
    private LinearLayout mWBView;
    private WebView mView;
    private String url,mMenuName;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initData();
    }

    private void initData() {
        url=getIntent().getStringExtra("skipUrl");
        mMenuName=getIntent().getStringExtra("menuName");
        mToolbarTitle.setText(mMenuName);
        Log.e("WEBVIEW",url);
        mView=new WebView(WebActivity.this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mView.setLayoutParams(params);
        mView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
        mView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mView.getSettings().setJavaScriptEnabled(true);

        // 设置可以支持缩放
        mView.getSettings().setSupportZoom(true);
        mView.getSettings().setBuiltInZoomControls(true);
        mView.getSettings().setUseWideViewPort(true);
        //不显示webview缩放按钮
        mView.getSettings().setDisplayZoomControls(false);
        mView.loadUrl(url);
        mWBView.addView(mView);
    }

    private void initView() {
        initToolBar();
        mWBView= (LinearLayout) findViewById(R.id.webview);
    }

    private void initToolBar() {
    /*init toolbar*/
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");

        setSupportActionBar(mToolbar);

        ImageView mBack = (ImageView) findViewById(R.id.iv_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.this.finish();
            }
        });
        /*设置标题栏*/
        mToolbarTitle = (TextView) findViewById(R.id.tv_toolbartitle);
    }

    //销毁webview防止内存泄露
    public void destroy() {
        if (mView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mView);
            }
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mView.getSettings().setJavaScriptEnabled(false);
            mView.clearHistory();
            mView.clearView();
            mView.removeAllViews();
            try {
                mView.destroy();
            } catch (Throwable ex) {

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

}

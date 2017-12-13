package com.brightcns.liangla.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.brightcns.liangla.R;

import com.brightcns.liangla.weight.CircleImageView;

public class MessageDetailsActivity extends AppCompatActivity {
    private CircleImageView mIcon;
    private TextView mContent,mName;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
            init();
    }
    private  void init(){
        initToolBar();
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");


        mContent       = (TextView) findViewById(R.id.msgdetailtv);
        mContent.setText(content);


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
                MessageDetailsActivity.this.finish();
            }
        });
        /*设置标题栏*/
        TextView toolbarTitle = (TextView) findViewById(R.id.tv_toolbartitle);
        toolbarTitle.setText("我的消息");
    }

}

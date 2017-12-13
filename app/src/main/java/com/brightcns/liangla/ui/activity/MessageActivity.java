package com.brightcns.liangla.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brightcns.liangla.R;

import java.util.ArrayList;
import java.util.List;

import com.brightcns.liangla.service.entity.MessageBean;
import com.brightcns.liangla.service.presenter.MyMessagePresenter;
import com.brightcns.liangla.service.view.MyMessageBaseView;
import com.brightcns.liangla.ui.adapter.MessagesAdapter;
import com.brightcns.liangla.utils.CheckErrorCode;
import com.brightcns.liangla.utils.MD5;
import com.brightcns.liangla.utils.NetRequestHelper;
import com.brightcns.liangla.utils.NetUtil;
import com.brightcns.liangla.utils.ToastUtils;

public class MessageActivity extends AppCompatActivity {
    private MessagesAdapter messagesAdapter;
    private ArrayList<MessageBean.DataBean> mList;
    private Toolbar mToolbar;
    private LinearLayout mNomsg;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyMessagePresenter mMyMessagePresenter = new MyMessagePresenter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initView();
        initData();
        if (getSharedPreferences("admin", MODE_PRIVATE).getBoolean("isLogin", false)) {
            if (NetUtil.getNetWorkState(MessageActivity.this) != -1) {
                getMsg();
            } else {
                ToastUtils.showShort(MessageActivity.this, "网络已断开！");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMyMessagePresenter.onStop();
    }

    MyMessageBaseView mMyMessageView = new MyMessageBaseView() {
        @Override
        public void onSuccess(MessageBean messageBean) {
            if (messageBean.getCode() == 0) {
                List<MessageBean.DataBean> list = messageBean.getData();
                if (list.size()==0){
                    mNomsg.setVisibility(View.VISIBLE);
                }else {
                    mNomsg.setVisibility(View.GONE);
                        /*直接将body传给适配器*/
                    mList.addAll(list);
                    messagesAdapter.notifyDataSetChanged();
                }
            } else {
                CheckErrorCode.checkErrorCodeAndToast(MessageActivity.this,messageBean.getCode());
            }
        }

        @Override
        public void onError(String result) {
            Toast.makeText(MessageActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };

    private void initData() {
        mList = new ArrayList<MessageBean.DataBean>();
        messagesAdapter = new MessagesAdapter(mList, MessageActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(messagesAdapter);
        /*条目点击事件*/
        messagesAdapter.setOnItemClickListener(new MessagesAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, String content) {
                //跳转到此详情界面
                Intent intent = new Intent(MessageActivity.this, MessageDetailsActivity.class);
                intent.putExtra("content", content);
                startActivity(intent);
            }
        });

        /*初始化下拉刷新*/

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void initView() {
        initToolBar();
        /*recyclerview*/
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        /*下拉刷新控件*/
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        mNomsg = (LinearLayout) findViewById(R.id.nomsg);
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
                MessageActivity.this.finish();
            }
        });
        /*设置标题栏*/
        TextView toolbarTitle = (TextView) findViewById(R.id.tv_toolbartitle);
        toolbarTitle.setText("我的消息");
    }

    /*获取信息*/
    private void getMsg() {
        String userId = NetRequestHelper.getUserId(MessageActivity.this);
        String userToken = NetRequestHelper.getUserToken(MessageActivity.this);
        String timeStamp = NetRequestHelper.gettimestamp();
        String md5 = "/api/v1/i/users/" + userId + "/messages" + "?token=" + userToken + "&timestamp=" + timeStamp;
        final String sign = MD5.stringToMD5(md5);//签名
        mMyMessagePresenter.onCreat();
        mMyMessagePresenter.getMyMessage(userToken, Long.parseLong(timeStamp), sign, userId);
        mMyMessagePresenter.AttachView(mMyMessageView);
    }

}

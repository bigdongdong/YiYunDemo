package com.chen.firstdemo.huanXinSDK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chen.firstdemo.R;
import com.chen.firstdemo.base.BaseActivity;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HuanXinSDKActivity extends BaseActivity {

    private final String username = "aaa";
    private final String password = "aaa";
    @BindView(R.id.registerBtn)
    Button registerBtn;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.logoutBtn)
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huan_xin_sdk);
        ButterKnife.bind(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                ChatClient.getInstance().register(username, password, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "register success");
                    }

                    @Override
                    public void onError(int code, String error) {
                        Log.i(TAG, "register error code=" + code + "  error=" + error);
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*判断登录状态*/
                //登录
                ChatClient.getInstance().login(username, password, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "login success");
                        //进入会话
                        Intent intent = new IntentBuilder(context)
                                //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                                .setServiceIMNumber("kefuchannelimid_280322")
                                .build();
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int code, String error) {
                        Log.i(TAG, "login error code=" + code + "  error=" + error);
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
//                boolean isLoginHuanXin = ChatClient.getInstance().isLoggedInBefore();
//                if (!isLoginHuanXin) {
//                    //登录
//                    ChatClient.getInstance().login(username, password, new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            Log.i(TAG, "login success");
//                            //进入会话
//                            Intent intent = new IntentBuilder(context)
//                                    //获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
//                                    .setServiceIMNumber("kefuchannelimid_280322")
//                                    .build();
//                            startActivity(intent);
//                        }
//
//                        @Override
//                        public void onError(int code, String error) {
//                            Log.i(TAG, "login error code=" + code + "  error=" + error);
//                        }
//
//                        @Override
//                        public void onProgress(int progress, String status) {
//
//                        }
//                    });
//                }else{
//                    Log.i(TAG, "已经登录");
//                    ChatClient.getInstance().logout(true, new Callback(){
//                        @Override
//                        public void onSuccess() {
//                            v.callOnClick();
//                        }
//
//                        @Override
//                        public void onError(int code, String error) {
//                        }
//
//                        @Override
//                        public void onProgress(int progress, String status) {
//
//                        }
//                    });
//                }
            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*退出*/
                //第一个参数为是否解绑推送的devicetoken
                ChatClient.getInstance().logout(true, new Callback(){
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "logout success");
                    }

                    @Override
                    public void onError(int code, String error) {
                        Log.i(TAG, "logout error code=" + code + "  error=" + error);
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
            }
        });

    }
}
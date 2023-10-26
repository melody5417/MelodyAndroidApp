package com.example.melody.application.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.example.melody.application.R;
import com.example.melody.application.activity.BaseActivity;

public class BindingServiceActivity extends BaseActivity {

    private static final String TAG = BindingServiceActivity.class.getSimpleName();
    private Button connectButton;
    private Button disconnectButton;
    private Button doSomethingButton;

    private MyBinderService myBinderService;
    private boolean isBound = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinderService.MyBinder binder = (MyBinderService.MyBinder) service;
            myBinderService = binder.getService();
            isBound = true;
            Log.d(TAG, "onServiceConnected name:" + name);
        }

        /**
         * 当与服务的连接意外中断时，例如服务崩溃或被终止时，Android 系统会调用该方法。
         * 当客户端取消绑定时，系统不会调用该方法。
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            Log.d(TAG, "onServiceDisconnected name:" + name);
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_binding_service;
    }

    @Override
    protected void initView() {
         connectButton = findViewById(R.id.connect_button);
         disconnectButton = findViewById(R.id.disconnect_button);
         doSomethingButton = findViewById(R.id.do_something_button);
    }

    @Override
    protected void initData() {
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BindingServiceActivity.this, MyBinderService.class);
                /**
                 * 如果该方法返回 false，说明您的客户端与服务之间并无有效连接。
                 * 不过，您的客户端仍应调用 unbindService()；否则，您的客户端会使服务无法在空闲时关闭。
                 */
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBound) {
                    unbindService(serviceConnection);
                    isBound = false;
                }
            }
        });
        doSomethingButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBound) {
                    myBinderService.doSomething();
                } else {
                    Log.d(TAG, "service is disconnected");
                }
            }
        });
    }
}
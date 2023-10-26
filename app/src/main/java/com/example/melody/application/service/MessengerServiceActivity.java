package com.example.melody.application.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.melody.application.R;
import com.example.melody.application.activity.BaseActivity;

public class MessengerServiceActivity extends BaseActivity {

    private static final String TAG = BindingServiceActivity.class.getSimpleName();
    private Button connectButton;
    private Button disconnectButton;
    private Button doSomethingButton;

    /** Messenger for communicating with the service. */
    Messenger mService = null;

    /** Flag indicating whether we have called bind on the service. */
    boolean bound;

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            mService = new Messenger(service);
            bound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            bound = false;
        }
    };

    public void sayHello() {
        if (!bound) return;
        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, 1, 0, 0);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_messenger_service;
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
                // Bind to the service
                Intent intent = new Intent();
                intent.setAction("com.melody.service.messenger.service");
                intent.setPackage("com.example.melody.application");
                /**
                 * 如果该方法返回 false，说明您的客户端与服务之间并无有效连接。
                 * 不过，您的客户端仍应调用 unbindService()；否则，您的客户端会使服务无法在空闲时关闭。
                 */
                boolean result = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                Log.d(TAG, "bindService result:" + result);
            }
        });
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Unbind from the service
                if (bound) {
                    unbindService(mConnection);
                    bound = false;
                }
            }
        });
        doSomethingButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bound) {
                    sayHello();
                } else {
                    Log.d(TAG, "service is disconnected");
                }
            }
        });
    }
}
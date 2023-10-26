package com.example.melody.application.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

/**
 * 为接口使用 Messenger 比使用 AIDL 更简单，因为 Messenger 会将所有服务调用加入队列。
 * 纯 AIDL 接口会同时向服务发送多个请求，那么服务就必须执行多线程处理。
 *
 * 以下是对 Messenger 使用方式的摘要：
 *
 * 服务实现一个 Handler，由其接收来自客户端的每个调用的回调。
 * 服务使用 Handler 来创建 Messenger 对象（该对象是对 Handler 的引用）。
 * Messenger 创建一个 IBinder，服务通过 onBind() 将其返回给客户端。
 * 客户端使用 IBinder 将 Messenger（它引用服务的 Handler）实例化，然后再用其将 Message 对象发送给服务。
 * 服务在其 Handler 中（具体而言，是在 handleMessage() 方法中）接收每个 Message。
 */
public class MessengerService extends Service {
    /**
     * Command to the service to display a message
     */
    static final int MSG_SAY_HELLO = 1;

    /**
     * Handler of incoming messages from clients.
     */
    static class IncomingHandler extends Handler {
        private Context applicationContext;

        IncomingHandler(Context context) {
            applicationContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(applicationContext, "hello!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    Messenger mMessenger;

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        mMessenger = new Messenger(new IncomingHandler(this));
        return mMessenger.getBinder();
    }
}

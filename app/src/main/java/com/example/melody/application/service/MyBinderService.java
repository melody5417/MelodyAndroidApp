package com.example.melody.application.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * https://developer.android.com/guide/components/bound-services?hl=zh-cn
 * 绑定服务通常只在为其他应用组件提供服务时处于活动状态，不会无限期在后台运行。
 *
 * 可以创建同时具有已启动和已绑定两种状态的服务。
 * 换言之，可以通过调用 startService() 来启动服务，让服务无限期运行，也可以通过调用 bindService() 让客户端绑定到该服务。
 *
 * 绑定服务在所有绑定客户端取消绑定后会自动销毁服务，但是启动服务不会，必须通过调用 stopSelf() 或 stopService() 显式停止服务。
 *
 * 其他说明：
 * 您应该始终捕获 DeadObjectException 异常，系统会在连接中断时抛出此异常。这是远程方法抛出的唯一异常。
 * 对象是跨进程计数的引用。
 */
public class MyBinderService extends Service {

    private final IBinder binder = new MyBinder();

    /**
     * 系统会缓存 IBinder 服务通信通道。
     * 只有在第一个客户端绑定服务时，系统才会调用服务的 onBind() 方法来生成 IBinder。
     * 然后，系统会将该 IBinder 传递至绑定到同一服务的所有其他客户端，无需再次调用 onBind()。
     * 当最后一个客户端取消与服务的绑定时，系统会销毁该服务（除非还通过 startService() 启动了该服务）。
     *
     * 创建binder方式：
     * 方式1 扩展 Binder 类
     * 如果服务只是您自有应用的后台工作器，应优先采用这种方式。
     * 您不使用这种方式创建接口的唯一一种情况是：其他应用或不同进程占用了您的服务。
     * 方式2 使用 Messenger
     * 底层是AIDL，这是执行进程间通信 (IPC) 最为简单的方式，因为 Messenger 会在单个线程中创建包含所有请求的队列，这样您就不必对服务进行线程安全设计。
     * 方式3 使用 AIDL
     * 在此情况下，您的服务必须达到线程安全的要求，并且能够进行多线程处理。
     *
     */
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {
        public MyBinderService getService() {
            return MyBinderService.this;
        }
    }

    public void doSomething() {
        // 这里可以写一些具体的业务逻辑
        Log.d("MyBinderService", "doSomething");
    }
}

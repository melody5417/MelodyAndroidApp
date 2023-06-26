package com.example.melody.application.Common;

import android.app.Application;
import android.content.Context;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ContextHolder {

    public static final String ANDROID_APP_ACTIVITY_THREAD = "android.app.ActivityThread";
    public static final String ANDROID_APP_APP_GLOBALS = "android.app.AppGlobals";
    private static Context sApplicationContext = null;
    private static Context sCustomizeContext = null;

    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext() {
        if (sCustomizeContext != null) {
            return sCustomizeContext;
        } else if (sApplicationContext == null) {
            try {
                Application application = (Application) Class.forName(ANDROID_APP_ACTIVITY_THREAD)
                        .getMethod("currentApplication").invoke(null, (Object[]) null);
                if (application != null) {
                    sApplicationContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Class<?> activityThreadclz = Class.forName(ANDROID_APP_ACTIVITY_THREAD);
                Field field = activityThreadclz.getDeclaredField("sCurrentActivityThread");
                field.setAccessible(true);
                //得到ActivityThread的对象，虽然是隐藏的，但已经指向了内存的堆地址
                Object currentActivity = field.get(null);
                Method getApplicationMethod = activityThreadclz.getDeclaredMethod("getApplication");
                getApplicationMethod.setAccessible(true);
                Application application = (Application) getApplicationMethod.invoke(currentActivity);
                if (application != null) {
                    sApplicationContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Application application = (Application) Class.forName(ANDROID_APP_APP_GLOBALS)
                        .getMethod("getInitialApplication").invoke(null, (Object[]) null);
                if (application != null) {
                    sApplicationContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new IllegalStateException(
                    "ContextHolder is not initialed, it is recommend to init with application context.");
        }
        return sApplicationContext;
    }
}
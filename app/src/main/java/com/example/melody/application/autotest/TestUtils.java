package com.example.melody.application.autotest;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestUtils {

    private static final String TAG = "TestUtils";

    /**
     * 列出指定类中所有的自定义public方法名
     *
     * @param className 类名
     * @return 方法名数组
     * @throws ClassNotFoundException 如果找不到指定类，则抛出ClassNotFoundException异常
     */
    public static String[] listPublicMethods(String className) throws ClassNotFoundException {
        // 获取指定类的Class对象
        Class<?> clazz = Class.forName(className);
        // 获取该类声明的所有public方法
        Method[] methods = clazz.getDeclaredMethods();
        // 定义一个字符串数组，用于存储所有自定义public方法名
        String[] methodNames = new String[methods.length];
        int i = 0;
        for (Method method : methods) {
            // 如果方法不是public修饰的，则跳过
            if (method.getModifiers() != 2) {
                continue;
            }
            // 如果方法是继承自Object类的方法，则跳过
            if (method.getDeclaringClass().getName().equals(Object.class.getName())) {
                continue;
            }
            // 如果方法是编译器自动生成的方法，则跳过
            if (method.isSynthetic()) {
                continue;
            }
            // 将方法名加入到数组中
            methodNames[i++] = method.getName();
        }
        // 返回所有自定义public方法名的数组
        return methodNames;
    }

    public static void listPublicMethods(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                System.out.println(method.getName());
            }
        }
    }

    /**
     * 执行obj所属类中定义的所有public方法，若为boolean返回值会打印执行结果。
     * 只能获取到本类定义的public方法，无法获取到继承的方法
     * @param obj
     */
    public static void executePublicMethods(Object obj) {
        Class<?> clazz = obj.getClass();
        try {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (Modifier.isPublic(method.getModifiers())) {
                    try {
                        Object result = method.invoke(obj);
                        if (result instanceof Boolean) {
                            Log.d(TAG, "exec class:" + clazz.getName()
                                            + ", method:" + method.getName()
                                            + ", result:" + ((Boolean)result ? "succeed" : "fail"));
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        Log.e(TAG, "exec class:" + clazz.getName()
                                + ", method:" + method.getName()
                                + ", error:" + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "exec executePublicMethods, obj:" + clazz.getName() + ", error:" + e.getMessage());
        }
    }

}

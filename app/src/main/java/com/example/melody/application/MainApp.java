package com.example.melody.application;

import android.app.Application;
import com.example.melody.application.common.ContextHolder;
import java.io.File;

import com.bytedance.raphael.Raphael;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String space = getSpace();

        // Using MemoryLeakDetector to monitor current process
        Raphael.start(
                Raphael.MAP64_MODE|Raphael.ALLOC_MODE|0x0F0000|1024,
                space, // need sdcard permission
                null
        );

//        // Using MemoryLeakDetector to monitor specified so
//        Raphael.start(
//                Raphael.MAP64_MODE|Raphael.ALLOC_MODE|0x0F0000|1024,
//                space, // need sdcard permission
//                ".*libxxx\\.so$"
//        );
    }

    private String getSpace() {
        File space = ContextHolder.getContext().getExternalFilesDir("raphael");
        if (space == null) {
            return null;
        }
        if (!space.exists()) {
            space.mkdir();
        }
        return space.getAbsolutePath();
    }
}

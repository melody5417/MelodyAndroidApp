package com.example.melody.application.raphael;

import android.Manifest;
import android.Manifest.permission;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.view.View;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bytedance.raphael.Raphael;
import com.example.melody.application.R;

public class RaphaelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raphael);

        findViewById(R.id.printRaphael).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    //拥有权限，做你想做的事情
                    Raphael.print();

                    // https://github.com/bytedance/memory-leak-detector
                    // print >>> /storage/emulated/0/Android/data/com.example.melody.application/files/raphael
                    // 1. 手动 pull 出来，放在 raphael 目录下了
                    // adb pull /storage/emulated/0/Android/data/com.example.melody.application/files/raphael
                    // ./doc/RaphaelOutput/raphael/
                    // 2. Analysis
                    // python3 ./doc/Raphael/library/src/main/python/raphael.py -r ./doc/RaphaelOutput/raphael/report
                    // -o ./doc/RaphaelOutput/leak-doubts.txt
                    // python3 ./doc/Raphael/library/src/main/python/mmap.py -m ./doc/RaphaelOutput/raphael/maps
                }else{
                    //没有开启权限，向系统申请权限
                    ActivityCompat.requestPermissions(RaphaelActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0 /*MyApplication.CODE_FOR_WRITE_PERMISSION*/);
                }

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0/*MyApplication.CODE_FOR_WRITE_PERMISSION*/) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意开启权限，执行操作
                Raphael.print();
            } else {
                //用户不同意，向用户展示该权限作用和必要性，用户点击OK，继续向系统申请权限。
                if (ActivityCompat.shouldShowRequestPermissionRationale(RaphaelActivity.this,
                        permission.WRITE_EXTERNAL_STORAGE)) {
                    new Builder(RaphaelActivity.this)
                            .setMessage("这个权限用于缓存首页的图片到你的本地，下次再使用图片时无需网络请求了")
                            .setPositiveButton("OK", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(RaphaelActivity.this,
                                            new String[] {permission.WRITE_EXTERNAL_STORAGE},
                                            0/*MyApplication.CODE_FOR_WRITE_PERMISSION*/);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                } else {
                    //这种情况是，shouldShowRequestPermissionRationale的情况1和3，默认不做处理，根据开发需求的需要吧
                }
            }
        }
    }
}
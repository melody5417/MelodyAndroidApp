package com.example.melody.application.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.melody.application.R;
import java.util.HashMap;

public class RegisterActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnResiter;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnResiter = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        btnResiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                register(account, pwd);
            }
        });
    }

    private void register(String account, String pwd) {
        if (TextUtils.isEmpty(account)) {
            showToast("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", account);
        params.put("password", pwd);
//        Api.config(ApiConfig.REGISTER, params).postRequest(this,new TtitCallback() {
//            @Override
//            public void onSuccess(final String res) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(res);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Log.e("onFailure", e.toString());
//            }
//        });
    }
}
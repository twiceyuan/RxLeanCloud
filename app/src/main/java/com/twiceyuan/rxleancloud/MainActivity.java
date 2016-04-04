package com.twiceyuan.rxleancloud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRole;
import com.avos.avoscloud.SaveCallback;

import cn.leancloud.rx.LeanCallbacks;
import cn.leancloud.rx.LeanWrap;
import rx.Observable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simple();
        rx();
    }

    private void simple() {
        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words", "Hello World!");
        // 保存对象
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    AVACL acl = new AVACL();
                    acl.setPublicReadAccess(true);
                    // 创建角色
                    AVRole role = new AVRole(testObject.getObjectId(), acl);
                    role.saveInBackground(new SaveCallback() {
                        @Override public void done(AVException e) {
                            // 设置角色权限
                            AVACL avacl = new AVACL();
                            avacl.setRoleReadAccess(role, true);
                            testObject.saveInBackground(new SaveCallback() {
                                @Override public void done(AVException e) {
                                    if (e == null) {
                                        // 保存成功
                                        Log.i("saved", "save success!");
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void rx() {
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words", "Hello World!");
        Observable.create(subscriber -> {
            // 保存对象
            testObject.setFetchWhenSave(true);
            testObject.saveInBackground(LeanCallbacks.saveRx(subscriber));
        }).<AVRole>flatMap(object -> Observable.create(subscriber -> {
            AVACL acl = new AVACL();
            acl.setPublicReadAccess(true);
            // 创建角色
            AVRole role = new AVRole(testObject.getObjectId(), acl);
            role.setFetchWhenSave(true);
            role.saveInBackground(LeanCallbacks.save((o, e) -> LeanWrap.wrap(subscriber, role, e)));
        })).flatMap(role -> Observable.create(subscriber -> {
            // 设置角色权限
            testObject.setACL(new AVACL());
            testObject.getACL().setRoleReadAccess(role, true);
            testObject.saveInBackground(LeanCallbacks.saveRx(subscriber));
        })).subscribe(success -> {
            // 保存成功
            Log.d("saved", "rx save success");
        });
    }
}

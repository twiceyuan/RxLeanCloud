package com.twiceyuan.rxleancloud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import cn.leancloud.rx.LeanWrap;
import rx.Observable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words", "Hello World!");

        // 标准回调式保存
        simpleSave(testObject);

        // Rx 封装为 Observable 保存
        simpleRxSave(testObject);

        // RxLeanCloud 保存
        withRxLeanCloud(testObject);
    }

    private void simpleSave(AVObject object) {
        // 保存对象
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    Log.i("saved", "save success!");
                }
            }
        });
    }

    private void simpleRxSave(AVObject object) {
        Observable.create(subscriber -> {
            // 保存对象
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        subscriber.onNext(null);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(e);
                    }
                }
            });
        }).subscribe(o -> {
            // 保存成功
            Log.i("saved", "save success!");
        });
    }

    private void withRxLeanCloud(AVObject object) {
        LeanWrap.save(object::saveInBackground).subscribe(aVoid -> Log.i("saved", "save success!"));
    }
}

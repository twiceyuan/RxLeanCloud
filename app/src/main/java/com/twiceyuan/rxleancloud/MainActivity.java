package com.twiceyuan.rxleancloud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import cn.leancloud.rx.LeanCallbacks;
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
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","simple success!");
                }
            }
        });
    }

    private void rx() {
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        Observable.create(subscriber -> testObject.saveInBackground(LeanCallbacks.saveRx(subscriber)))
            .subscribe(o -> Log.d("saved","rx success!"));
    }
}

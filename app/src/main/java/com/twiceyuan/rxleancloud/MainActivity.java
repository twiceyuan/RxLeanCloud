package com.twiceyuan.rxleancloud;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import cn.leancloud.rx.RxLeanCloud;
import rx.Observable;

public class MainActivity extends Activity {

    Button simpleSaveButton;
    Button simpleRxSaveButton;
    Button withRxLeanCloudButton;

    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words", "Hello World!");

        simpleSaveButton = (Button) findViewById(R.id.simpleSave);
        simpleRxSaveButton = (Button) findViewById(R.id.simpleRxSave);
        withRxLeanCloudButton = (Button) findViewById(R.id.withRxLeanCloud);
        textResult = (TextView) findViewById(R.id.textResult);

        // 标准回调式保存
        simpleSaveButton.setOnClickListener(v -> simpleSave(testObject));

        // Rx 封装为 Observable 保存
        simpleRxSaveButton.setOnClickListener(v -> simpleRxSave(testObject));

        // RxLeanCloud 保存
        withRxLeanCloudButton.setOnClickListener(v -> withRxLeanCloud(testObject));
    }

    private void simpleSave(AVObject object) {
        // 保存对象
        object.saveInBackground(new SaveCallback() {
            @SuppressLint("SetTextI18n") @Override
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    textResult.setText("simpleSave() success!");
                } else {
                    errorHandler(e);
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
            textResult.setText("simpleRxSave() success!");
        }, this::errorHandler);
    }

    private void withRxLeanCloud(AVObject object) {
        RxLeanCloud.save(object::saveInBackground)
                .subscribe(aVoid -> textResult.setText("withRxLeanCloud() success!"), this::errorHandler);
    }

    private void errorHandler(Throwable throwable) {
        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

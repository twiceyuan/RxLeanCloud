package com.twiceyuan.rxleancloud;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by twiceYuan on 4/4/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 */
public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"dkLlT93QmHonvsMWPFlE8urh-gzGzoHsz","UtMcJsdkYXaM59cFOJcl4xYU");
    }
}

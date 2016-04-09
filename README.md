# RxLeanCloud

[![](https://jitpack.io/v/twiceyuan/RxLeanCloud.svg)](https://jitpack.io/#twiceyuan/RxLeanCloud)
[![](https://travis-ci.org/twiceyuan/RxLeanCloud.svg?branch=master)](https://travis-ci.org/twiceyuan/RxLeanCloud)

RxJava + LeanCloud Android SDK （with Retrolambda）

LeanCloud Android SDK 有很多的异步方法，它们大多数情况下需要嵌套执行，所以代码很可能是这种形状的：

    ---
        ----
            ----
                ----
                    ---- 
                        ----
                    ----
                ----
            ----
        ----
    ----
               
所以使用 RxJava 来封装这些方法能让代码逻辑看起来更清楚一些，例如原来保存一个对象是这样的：

```Java
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
```

### 普通 Rx 封装

如果用 RxJava 直接封装的话，需要这样做：

```Java
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
```

好吧，看起来更复杂了。不过这样却有这样做的好处：使用 RxJava 能让嵌套层级增加的同时保持代码的线性逻辑，使之更方便的组合串联。

### RxLeanCloud

使用 RxLeanCloud 的话可以进一步简化封装的过程：

```Java
RxLeanCloud.save(object::saveInBackground).subscribe(aVoid -> Log.i("saved", "save success!"));
```

### 引入方法：

```gradle
allprojects {
    repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    compile 'com.github.twiceyuan:RxLeanCloud:[版本号]' // 上访 JitPack 图标后的版本号一般为最新版
}
```

### 许可

WTFPL

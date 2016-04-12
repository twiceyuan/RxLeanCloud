# RxLeanCloud

[![](https://jitpack.io/v/twiceyuan/RxLeanCloud.svg)](https://jitpack.io/#twiceyuan/RxLeanCloud)
[![](https://travis-ci.org/twiceyuan/RxLeanCloud.svg?branch=master)](https://travis-ci.org/twiceyuan/RxLeanCloud)
[![Awesome LeanCloud](https://img.shields.io/badge/Awesome-LeanCloud-2c97e8.svg)](http://leancloud.sexy)

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

### 如何找到对应的方法？

因为 `RxLeanCloud` 是对所有 Callback 做的封装，因此命名也和 `AVCallback` 的所有子类一一对应，具体的规则就是：

    // 去掉后面的 Callback，首字母变为小写
    callbackName.replace("Callback", "")
        .replaceFirst(
            String.valueOf(callbackName.chatAt(0), 
            String.valueOf(callbackName.chatAt(0).toLowCase()
        );
    
例如如果想找 `FoorBarCallback` 的包装方法，那么该方法就是 `RxLeanCloud.foorBar()`

### 原理

LeanCloud 中所有异步方法都会要求传入一个集成 AVCallback 的对象，在其回调方法里对结果做处理。RxLeanCloud 的目的是将这些 Callback 都封装为 Observable，但是逐一使用 HardCode 的方式来封装既麻烦又缺乏灵活性。在调用 RxLeanCloud 的静态方法时（例如 `RxLeanCloud.save()` 方法，其对应的 SDK 方法一般为 `AVObject.saveInBackground()`），会回调一个 AVCallback 的 callback，同时返回一个该 AVCallback 的 Observable，然后在 RxLeanCloud 的静态方法回调中，直接使用 SDK 方法直接传入这个 callback 就能得到 Observable 对象了。

同时给出一个不是用 Lambda 折叠的 saveInBackground 代码：

```java
final AVObject object = new AVObject("Hello");
RxLeanCloud.save(new Action1<SaveCallback>() {
    @Override public void call(SaveCallback saveCallback) {
        object.saveInBackground(saveCallback);
    }
})
```

### 加入到项目中

```gradle
allprojects {
    repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    compile 'com.github.twiceyuan:RxLeanCloud:[版本号]' // 上方 JitPack 图标后的版本号一般为最新版
}
```

### 许可

WTFPL

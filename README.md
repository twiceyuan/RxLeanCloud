# RxLeanCloud

[![](https://jitpack.io/v/twiceyuan/RxLeanCloud.svg)](https://jitpack.io/#twiceyuan/RxLeanCloud)

RxJava + LeanCloud Android SDK（WIP）

LeanCloud Android SDK 有很多的异步方法，它们大多数情况下需要嵌套执行，所以你的代码很可能是这种形状的：

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

使用本项目的话可以进一步简化封装的过程：

```Java
LeanWrap.save(object::saveInBackground).subscribe(aVoid -> Log.i("saved", "save success!"));
```

目前项目还在进行，更多例子会尽快添加到这个文档中。

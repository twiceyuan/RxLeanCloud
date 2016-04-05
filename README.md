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
               
所以使用 RxJava 来封装这些方法能让代码逻辑看起来更清楚一些，例如原来创建一个对象并保存是这样的：

```Java
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
```

现在只需要：

```Java
AVObject testObject = new AVObject("TestObject");
testObject.put("words", "Hello World!");
LeanWrap.save(testObject::saveInBackground).flatMap(aVoid -> {
    AVACL acl = new AVACL();
    acl.setPublicReadAccess(true);
    AVRole role = new AVRole(testObject.getObjectId(), acl);
    role.setFetchWhenSave(true);
    return LeanWrap.save(role::saveInBackground).map(v -> role);
}).flatMap(role -> {
    testObject.setACL(new AVACL());
    testObject.getACL().setRoleReadAccess(role, true);
    return LeanWrap.save(testObject::saveInBackground);
}).subscribe(success -> {
    // 保存成功
    Log.d("saved", "rx save success");
});
```

并且将 Observable 返回，就可以利用 RxJava 的特性组合根据你的业务逻辑轻松组合串联这些接口了：

```Java
private void rx() {
    AVObject testObject = new AVObject("TestObject");
    testObject.put("words", "Hello World!");
    saveInBackground(testObject)
            .flatMap(object -> createRole(testObject.getObjectId()))
            .flatMap(role -> setAclAndSave(testObject, role))
            .subscribe(success -> {
                // 保存成功
                Log.d("saved", "rx save success");
            });
}

// 保存对象
public Observable<Void> saveInBackground(AVObject object) {
    object.setFetchWhenSave(true);
    return LeanWrap.save(object::saveInBackground);
}

// 创建角色
public Observable<AVRole> createRole(String roleName) {
    AVACL acl = new AVACL();
    acl.setPublicReadAccess(true);
    AVRole role = new AVRole(roleName, acl);
    role.setFetchWhenSave(true);
    return LeanWrap.save(role::saveInBackground).map(aVoid -> role);
}

// 设置角色权限
public Observable<Void> setAclAndSave(AVObject object, AVRole role) {
    object.setACL(new AVACL());
    object.getACL().setRoleReadAccess(role, true);
    return LeanWrap.save(object::saveInBackground);
}
```

目前项目还在进行，更多例子会尽快添加到这个文档中。

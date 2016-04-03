package cn.leancloud.rx;

import com.avos.avoscloud.AVException;

/**
 * Created by twiceYuan on 4/3/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 */
public interface Callback<T> {
    void call(T t, AVException e);
}

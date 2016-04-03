package cn.leancloud.rx;

import rx.Subscriber;

/**
 * Created by twiceYuan on 4/3/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 * <p>
 * LeanCloud 回调包装
 */
public class LeanWrap {

    public static <T> void wrap(Subscriber<T> subscriber, T t, Throwable e) {
        if (e == null) {
            subscriber.onNext(t);
            subscriber.onCompleted();
        } else {
            subscriber.onError(e);
        }
    }
}

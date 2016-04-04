package cn.leancloud.rx;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFriendship;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.callback.AVFriendshipCallback;
import com.avos.avoscloud.callback.AVServerDateCallback;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by twiceYuan on 4/3/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 *
 * 用 RxJava 包装 LeanCloud 中的各种回调
 */
@SuppressWarnings("unused")
public class LeanCallbacks {

    /**
     * {@link com.avos.avoscloud.AVQuery#findInBackground(FindCallback)} 时使用
     */
    public static <T extends AVObject> FindCallback<T> find(Callback<List<T>> callback) {
        return new FindCallback<T>() {
            @Override public void done(List<T> list, AVException e) {
                callback.call(list, e);
            }
        };
    }

    /**
     * 使用 {@link rx.Observable#create(Observable.OnSubscribe)} 创建 Observable 时使用
     *
     * {@link com.avos.avoscloud.AVQuery#findInBackground(FindCallback)} 时使用
     */
    public static <T extends AVObject> FindCallback<T> findRx(Subscriber<? super List<T>> s) {
        return find((list, e) -> LeanWrap.wrap(s, list, e));
    }

    /**
     * {@link AVObject#saveInBackground(SaveCallback)} 时使用
     */
    public static <T> SaveCallback save(Callback<T> callback) {
        return new SaveCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    /**
     * 使用 {@link rx.Observable#create(Observable.OnSubscribe)} 创建 Observable 时使用
     *
     * {@link AVObject#saveInBackground(SaveCallback)} 时使用
     */
    public static SaveCallback saveRx(Subscriber<? super Void> subscriber) {
        return save((o, e) -> LeanWrap.wrap(subscriber, null, e));
    }

    /**
     * {@link GetCallback} 时使用
     */
    public static <T extends AVObject> GetCallback<T> get(Callback<T> callback) {
        return new GetCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    /**
     * 使用 {@link rx.Observable#create(Observable.OnSubscribe)} 创建 Observable 时使用
     */
    public static <T extends AVObject> GetCallback<T> getRx(Subscriber<? super T> subscriber) {
        return get((avObject, e) -> LeanWrap.wrap(subscriber, avObject, e));
    }

    public static AVFriendshipCallback friendship(Callback<AVFriendship> callback) {
        return new AVFriendshipCallback() {
            @Override public void done(AVFriendship friendship, AVException e) {
                callback.call(friendship, e);
            }
        };
    }

    public static AVFriendshipCallback friendshipRx(Subscriber<? super AVFriendship> subscriber) {
        return friendship((friendship, e) -> LeanWrap.wrap(subscriber, friendship, e));
    }

    public static <T> AVMobilePhoneVerifyCallback phoneVerify(Callback<T> callback) {
        return new AVMobilePhoneVerifyCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static <T> AVMobilePhoneVerifyCallback phoneVerifyRx(Subscriber<? super T> subscriber) {
        return phoneVerify((o, e) -> LeanWrap.wrap(subscriber, null, e));
    }

    public static AVServerDateCallback serveDate(Callback<Date> callback) {
        return new AVServerDateCallback() {
            @Override public void done(Date date, AVException e) {
                callback.call(date, e);
            }
        };
    }

    public static AVServerDateCallback serveDateRx(Subscriber<? super Date> subscriber) {
        return serveDate((date, e) -> LeanWrap.wrap(subscriber, date, e));
    }

    // TODO: 4/4/16 实现所有回调抽象方法到接口的转换
}

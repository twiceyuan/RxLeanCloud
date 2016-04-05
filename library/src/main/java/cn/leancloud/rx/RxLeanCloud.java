package cn.leancloud.rx;

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
import rx.functions.Action1;

/**
 * Created by twiceYuan on 4/3/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 * <p>
 * LeanCloud 回调包装
 */
@SuppressWarnings("unused")
public class RxLeanCloud {

    public static <T> void wrap(Subscriber<T> subscriber, T t, Throwable e) {
        if (e == null) {
            subscriber.onNext(t);
            subscriber.onCompleted();
        } else {
            subscriber.onError(e);
        }
    }

    public static Observable<Void> save(Action1<SaveCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.saveRx(subscriber)));
    }

    public static <T extends AVObject> Observable<List<T>> find(Action1<FindCallback<T>> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.findRx(subscriber)));
    }

    public static <T extends AVObject> Observable<T> get(Action1<GetCallback<T>> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.getRx(subscriber)));
    }

    public static Observable<AVFriendship> friendship(Action1<AVFriendshipCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.friendshipRx(subscriber)));
    }

    public static Observable<Void> phoneVerify(Action1<AVMobilePhoneVerifyCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.phoneVerifyRx(subscriber)));
    }

    public static Observable<Date> serveDate(Action1<AVServerDateCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.serveDateRx(subscriber)));
    }

//    public static CloudQueryCallback<AVCloudQueryResult> cloudQueryRx(Subscriber<? super AVCloudQueryResult> subscriber) {
//        return cloudQuery((result, e) -> RxLeanCloud.wrap(subscriber, result, e));
//    }
//
//    public static CountCallback countRx(Subscriber<? super Integer> subscriber) {
//        return count((integer, e) -> RxLeanCloud.wrap(subscriber, integer, e));
//    }
//
//    public static DeleteCallback deleteRx(Subscriber<? super Void> subscriber) {
//        return delete((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
//    }
//
//    public static <T extends AVObject> FollowCallback<T> followRx(Subscriber<? super T> subscriber) {
//        return follow((t, e) -> RxLeanCloud.wrap(subscriber, t, e));
//    }
//
//    public static <T extends AVObject> FollowersAndFolloweesCallback<T> followersAndFolloweesRx(Subscriber<? super Map<String, ? super T>> subscriber) {
//        return followersAndFollowees((stringTMap, e) -> RxLeanCloud.wrap(subscriber, stringTMap, e));
//    }
//
//    public static <T> FunctionCallback<T> functionRx(Subscriber<? super T> subscriber) {
//        return function((t, e) -> RxLeanCloud.wrap(subscriber, t, e));
//    }
//
//    public static GetDataCallback getDataRx(Subscriber<? super byte[]> subscriber) {
//        return getData((bytes, e) -> RxLeanCloud.wrap(subscriber, bytes, e));
//    }
//
//    public static <T extends AVFile> GetFileCallback<T> getFileRx(Subscriber<? super T> subscriber) {
//        return getFile((t, e) -> RxLeanCloud.wrap(subscriber, t, e));
//    }
//
//    public static InboxStatusFindCallback inboxStatusFindRx(Subscriber<? super List<AVStatus>> subscriber) {
//        return inboxStatusFind((avStatuses, e) -> RxLeanCloud.wrap(subscriber, avStatuses, e));
//    }
//
//    public static <T extends AVUser> LogInCallback<T> loginRx(Subscriber<? super T> subscriber) {
//        return login((t, e) -> RxLeanCloud.wrap(subscriber, t, e));
//    }
//
//    public static ProgressCallback progressRx(Subscriber<? super Integer> subscriber) {
//        return progress((integer, e) -> RxLeanCloud.wrap(subscriber, integer, null));
//    }
//
//    public static <T extends AVObject> RefreshCallback<T> refreshRx(Subscriber<? super T> subscriber) {
//        return refresh((t, e) -> RxLeanCloud.wrap(subscriber, t, e));
//    }
//
//    public static RequestEmailVerifyCallback requestEmailVerifyRx(Subscriber<? super Void> subscriber) {
//        return requestEmailVerify((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
//    }
//
//    public static RequestMobileCodeCallback requestMobileCodeRx(Subscriber<? super Void> subscriber) {
//        return requestMobileCode((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
//    }
//
//    public static RequestPasswordResetCallback requestPasswordResetRx(Subscriber<? super Void> subscriber) {
//        return requestPasswordReset((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
//    }
//
//    public static SendCallback sendRx(Subscriber<? super Void> subscriber) {
//        return send((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
//    }
//
//    public static SignUpCallback signUpRx(Subscriber<? super Void> subscriber) {
//        return signUp((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
//    }
//
//    public static StatusCallback statusRx(Subscriber<? super AVStatus> subscriber) {
//        return status((avStatus, e) -> RxLeanCloud.wrap(subscriber, avStatus, e));
//    }
//
//    public static StatusListCallback statusListRx(Subscriber<? super List<AVStatus>> subscriber) {
//        return statusList((avStatuses, e) -> RxLeanCloud.wrap(subscriber, avStatuses, e));
//    }
//
//    public static UpdatePasswordCallback updatePasswordRx(Subscriber<? super Void> subscriber) {
//        return updatePassword((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
//    }
}

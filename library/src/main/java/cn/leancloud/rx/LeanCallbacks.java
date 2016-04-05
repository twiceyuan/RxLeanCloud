package cn.leancloud.rx;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVFriendship;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.FollowersAndFolloweesCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.GetFileCallback;
import com.avos.avoscloud.InboxStatusFindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.RefreshCallback;
import com.avos.avoscloud.RequestEmailVerifyCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SendCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.StatusCallback;
import com.avos.avoscloud.StatusListCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.avos.avoscloud.callback.AVFriendshipCallback;
import com.avos.avoscloud.callback.AVServerDateCallback;

import java.util.Date;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by twiceYuan on 4/3/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 * <p>
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
     * <p>
     * {@link com.avos.avoscloud.AVQuery#findInBackground(FindCallback)} 时使用
     */
    public static <T extends AVObject> FindCallback<T> findRx(Subscriber<? super List<T>> s) {
        return find((list, e) -> RxLeanCloud.wrap(s, list, e));
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
     * <p>
     * {@link AVObject#saveInBackground(SaveCallback)} 时使用
     */
    public static SaveCallback saveRx(Subscriber<? super Void> subscriber) {
        return save((o, e) -> RxLeanCloud.wrap(subscriber, null, e));
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
        return get((avObject, e) -> RxLeanCloud.wrap(subscriber, avObject, e));
    }

    public static AVFriendshipCallback friendship(Callback<AVFriendship> callback) {
        return new AVFriendshipCallback() {
            @Override public void done(AVFriendship friendship, AVException e) {
                callback.call(friendship, e);
            }
        };
    }

    public static AVFriendshipCallback friendshipRx(Subscriber<? super AVFriendship> subscriber) {
        return friendship((friendship, e) -> RxLeanCloud.wrap(subscriber, friendship, e));
    }

    public static <T> AVMobilePhoneVerifyCallback phoneVerify(Callback<T> callback) {
        return new AVMobilePhoneVerifyCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static <T> AVMobilePhoneVerifyCallback phoneVerifyRx(Subscriber<? super T> subscriber) {
        return phoneVerify((o, e) -> RxLeanCloud.wrap(subscriber, null, e));
    }

    public static AVServerDateCallback serveDate(Callback<Date> callback) {
        return new AVServerDateCallback() {
            @Override public void done(Date date, AVException e) {
                callback.call(date, e);
            }
        };
    }

    public static AVServerDateCallback serveDateRx(Subscriber<? super Date> subscriber) {
        return serveDate((date, e) -> RxLeanCloud.wrap(subscriber, date, e));
    }

    public static CloudQueryCallback<AVCloudQueryResult> cloudQuery(Callback<AVCloudQueryResult> callback) {
        return new CloudQueryCallback<AVCloudQueryResult>() {
            @Override public void done(AVCloudQueryResult result, AVException e) {
                callback.call(result, e);
            }
        };
    }

    public static CloudQueryCallback<AVCloudQueryResult> cloudQueryRx(Subscriber<? super AVCloudQueryResult> subscriber) {
        return cloudQuery((result, e) -> RxLeanCloud.wrap(subscriber, result, e));
    }

    public static CountCallback count(Callback<Integer> callback) {
        return new CountCallback() {
            @Override public void done(int i, AVException e) {
                callback.call(i, e);
            }
        };
    }

    public static CountCallback countRx(Subscriber<? super Integer> subscriber) {
        return count((integer, e) -> RxLeanCloud.wrap(subscriber, integer, e));
    }

    public static DeleteCallback delete(Callback<Void> callback) {
        return new DeleteCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static DeleteCallback deleteRx(Subscriber<? super Void> subscriber) {
        return delete((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
    }

    public static <T extends AVObject> FollowCallback<T> follow(Callback<T> callback) {
        return new FollowCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T extends AVObject> FollowCallback<T> followRx(Subscriber<? super T> subscriber) {
        return follow((t, e) -> RxLeanCloud.wrap(subscriber, t, e));
    }

    public static <T extends AVObject> FollowersAndFolloweesCallback<T> followersAndFollowees(Callback<Map<String, T>> callback) {
        return new FollowersAndFolloweesCallback<T>() {
            @Override public void done(Map<String, T> map, AVException e) {
                callback.call(map, e);
            }
        };
    }

    public static <T extends AVObject> FollowersAndFolloweesCallback<T> followersAndFolloweesRx(Subscriber<? super Map<String, ? super T>> subscriber) {
        return followersAndFollowees((stringTMap, e) -> RxLeanCloud.wrap(subscriber, stringTMap, e));
    }

    public static <T> FunctionCallback<T> function(Callback<T> callback) {
        return new FunctionCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T> FunctionCallback<T> functionRx(Subscriber<? super T> subscriber) {
        return function((t, e) -> RxLeanCloud.wrap(subscriber, t, e));
    }

    public static GetDataCallback getData(Callback<byte[]> callback) {
        return new GetDataCallback() {
            @Override public void done(byte[] bytes, AVException e) {
                callback.call(bytes, e);
            }
        };
    }

    public static GetDataCallback getDataRx(Subscriber<? super byte[]> subscriber) {
        return getData((bytes, e) -> RxLeanCloud.wrap(subscriber, bytes, e));
    }

    public static <T extends AVFile> GetFileCallback<T> getFile(Callback<T> callback) {
        return new GetFileCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T extends AVFile> GetFileCallback<T> getFileRx(Subscriber<? super T> subscriber) {
        return getFile((t, e) -> RxLeanCloud.wrap(subscriber, t, e));
    }

    public static InboxStatusFindCallback inboxStatusFind(Callback<List<AVStatus>> callback) {
        return new InboxStatusFindCallback() {
            @Override public void done(List<AVStatus> list, AVException e) {
                callback.call(list, e);
            }
        };
    }

    public static InboxStatusFindCallback inboxStatusFindRx(Subscriber<? super List<AVStatus>> subscriber) {
        return inboxStatusFind((avStatuses, e) -> RxLeanCloud.wrap(subscriber, avStatuses, e));
    }

    public static <T extends AVUser> LogInCallback<T> login(Callback<T> callback) {
        return new LogInCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T extends AVUser> LogInCallback<T> loginRx(Subscriber<? super T> subscriber) {
        return login((t, e) -> RxLeanCloud.wrap(subscriber, t, e));
    }

    public static ProgressCallback progress(Callback<Integer> callback) {
        return new ProgressCallback() {
            @Override public void done(Integer integer) {
                callback.call(integer, null);
            }
        };
    }

    public static ProgressCallback progressRx(Subscriber<? super Integer> subscriber) {
        return progress((integer, e) -> RxLeanCloud.wrap(subscriber, integer, null));
    }

    public static <T extends AVObject> RefreshCallback<T> refresh(Callback<T> callback) {
        return new RefreshCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T extends AVObject> RefreshCallback<T> refreshRx(Subscriber<? super T> subscriber) {
        return refresh((t, e) -> RxLeanCloud.wrap(subscriber, t, e));
    }

    public static RequestEmailVerifyCallback requestEmailVerify(Callback<Void> callback) {
        return new RequestEmailVerifyCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static RequestEmailVerifyCallback requestEmailVerifyRx(Subscriber<? super Void> subscriber) {
        return requestEmailVerify((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
    }

    public static RequestMobileCodeCallback requestMobileCode(Callback<Void> callback) {
        return new RequestMobileCodeCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static RequestMobileCodeCallback requestMobileCodeRx(Subscriber<? super Void> subscriber) {
        return requestMobileCode((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
    }

    public static RequestPasswordResetCallback requestPasswordReset(Callback<Void> callback) {
        return new RequestPasswordResetCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static RequestPasswordResetCallback requestPasswordResetRx(Subscriber<? super Void> subscriber) {
        return requestPasswordReset((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
    }

    public static SendCallback send(Callback<Void> callback) {
        return new SendCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static SendCallback sendRx(Subscriber<? super Void> subscriber) {
        return send((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
    }

    public static SignUpCallback signUp(Callback<Void> callback) {
        return new SignUpCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static SignUpCallback signUpRx(Subscriber<? super Void> subscriber) {
        return signUp((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
    }

    public static StatusCallback status(Callback<AVStatus> callback) {
        return new StatusCallback() {
            @Override public void done(AVStatus avStatus, AVException e) {
                callback.call(avStatus, e);
            }
        };
    }

    public static StatusCallback statusRx(Subscriber<? super AVStatus> subscriber) {
        return status((avStatus, e) -> RxLeanCloud.wrap(subscriber, avStatus, e));
    }

    public static StatusListCallback statusList(Callback<List<AVStatus>> callback) {
        return new StatusListCallback() {
            @Override public void done(List<AVStatus> list, AVException e) {
                callback.call(list, e);
            }
        };
    }

    public static StatusListCallback statusListRx(Subscriber<? super List<AVStatus>> subscriber) {
        return statusList((avStatuses, e) -> RxLeanCloud.wrap(subscriber, avStatuses, e));
    }

    public static UpdatePasswordCallback updatePassword(Callback<Void> callback) {
        return new UpdatePasswordCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static UpdatePasswordCallback updatePasswordRx(Subscriber<? super Void> subscriber) {
        return updatePassword((aVoid, e) -> RxLeanCloud.wrap(subscriber, aVoid, e));
    }
}

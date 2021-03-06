package cn.leancloud.rx;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVFriendship;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
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
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMClientStatusCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationMemberCountCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMOnlineClientsCallback;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public static Observable<Void> save(final Action1<SaveCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                callback.call(LeanCallbacks.saveRx(subscriber));
            }
        });
    }

    public static Observable<Void> save(final AVObject object) {
        return save(new Action1<SaveCallback>() {
            @Override public void call(SaveCallback callback) {
                object.saveInBackground(callback);
            }
        });
    }

    public static <T extends AVObject> Observable<List<T>> find(final Action1<FindCallback<T>> callback) {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override public void call(Subscriber<? super List<T>> subscriber) {
                callback.call(LeanCallbacks.findRx(subscriber));
            }
        });
    }

    public static <T extends AVObject> Observable<List<T>> find(final AVQuery<T> query) {
        return find(new Action1<FindCallback<T>>() {
            @Override public void call(FindCallback<T> tFindCallback) {
                query.findInBackground(tFindCallback);
            }
        });
    }

    public static <T extends AVObject> Observable<T> get(final Action1<GetCallback<T>> callback) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(Subscriber<? super T> subscriber) {
                callback.call(LeanCallbacks.getRx(subscriber));
            }
        });
    }

    public static <T extends AVObject> Observable<T> get(final String objectId, final Class<T> clazz) {
        return get(new Action1<GetCallback<T>>() {
            @Override public void call(GetCallback<T> tGetCallback) {
                AVObject.getQuery(clazz).getInBackground(objectId, tGetCallback);
            }
        });
    }

    public static Observable<AVFriendship> friendship(final Action1<AVFriendshipCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<AVFriendship>() {
            @Override public void call(Subscriber<? super AVFriendship> subscriber) {
                callback.call(LeanCallbacks.friendshipRx(subscriber));
            }
        });
    }

    public static Observable<Void> phoneVerify(final Action1<AVMobilePhoneVerifyCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                callback.call(LeanCallbacks.phoneVerifyRx(subscriber));
            }
        });
    }

    public static Observable<Date> serveDate(final Action1<AVServerDateCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Date>() {
            @Override public void call(Subscriber<? super Date> subscriber) {
                callback.call(LeanCallbacks.serveDateRx(subscriber));
            }
        });
    }

    public static <T extends AVCloudQueryResult> Observable<AVCloudQueryResult> cloudQuery(final Action1<CloudQueryCallback<T>> callback) {
        return Observable.create(new Observable.OnSubscribe<AVCloudQueryResult>() {
            @Override public void call(Subscriber<? super AVCloudQueryResult> subscriber) {
                callback.call(LeanCallbacks.<T>cloudQueryRx(subscriber));
            }
        });
    }

    public static Observable<Integer> count(final Action1<CountCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override public void call(Subscriber<? super Integer> subscriber) {
                callback.call(LeanCallbacks.countRx(subscriber));
            }
        });
    }

    public static <T extends AVObject> Observable<Integer> count(final AVQuery<T> query) {
        return count(new Action1<CountCallback>() {
            @Override public void call(CountCallback countCallback) {
                query.countInBackground(countCallback);
            }
        });
    }

    public static Observable<Void> delete(final Action1<DeleteCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                callback.call(LeanCallbacks.deleteRx(subscriber));
            }
        });
    }

    public static <T extends AVObject> Observable<Void> delete(final AVQuery<T> query) {
        return delete(new Action1<DeleteCallback>() {
            @Override public void call(DeleteCallback deleteCallback) {
                query.deleteAllInBackground(deleteCallback);
            }
        });
    }

    public static <T extends AVObject> Observable<T> follow(final Action1<FollowCallback<T>> callback) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(Subscriber<? super T> subscriber) {
                callback.call(LeanCallbacks.followRx(subscriber));
            }
        });
    }

    public static <T extends AVObject> Observable<Map<String, T>> followerAndFollowees(final Action1<FollowersAndFolloweesCallback<T>> callback) {
        return Observable.create(new Observable.OnSubscribe<Map<String, T>>() {
            @Override public void call(Subscriber<? super Map<String, T>> subscriber) {
                callback.call(LeanCallbacks.followersAndFolloweesRx(subscriber));
            }
        });
    }

    public static <T> Observable<T> function(final Action1<FunctionCallback<T>> callback) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(Subscriber<? super T> subscriber) {
                callback.call(LeanCallbacks.functionRx(subscriber));
            }
        });
    }

    public static Observable<byte[]> getData(final Action1<GetDataCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override public void call(Subscriber<? super byte[]> subscriber) {
                callback.call(LeanCallbacks.getDataRx(subscriber));
            }
        });
    }

    public static <T extends AVFile> Observable<T> getFile(final Action1<GetFileCallback<T>> callback) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(Subscriber<? super T> subscriber) {
                callback.call(LeanCallbacks.getFileRx(subscriber));
            }
        });
    }

    public static Observable<List<AVStatus>> inboxStatusFind(final Action1<InboxStatusFindCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<List<AVStatus>>() {
            @Override public void call(Subscriber<? super List<AVStatus>> subscriber) {
                callback.call(LeanCallbacks.inboxStatusFindRx(subscriber));
            }
        });
    }

    public static <T extends AVUser> Observable<T> login(final Action1<LogInCallback<T>> callback) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(Subscriber<? super T> subscriber) {
                callback.call(LeanCallbacks.loginRx(subscriber));
            }
        });
    }

    public static Observable<Integer> progress(final Action1<ProgressCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override public void call(Subscriber<? super Integer> subscriber) {
                callback.call(LeanCallbacks.progressRx(subscriber));
            }
        });
    }

    public static <T extends AVObject> Observable<T> refresh(final Action1<RefreshCallback<T>> callback) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(Subscriber<? super T> subscriber) {
                callback.call(LeanCallbacks.refreshRx(subscriber));
            }
        });
    }

    public static Observable<Void> requestEmailVerify(final Action1<RequestEmailVerifyCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                callback.call(LeanCallbacks.requestEmailVerifyRx(subscriber));
            }
        });
    }

    public static Observable<Void> requestMobileCode(final Action1<RequestMobileCodeCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                callback.call(LeanCallbacks.requestMobileCodeRx(subscriber));
            }
        });
    }

    public static Observable<Void> requestPasswordReset(final Action1<RequestPasswordResetCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                callback.call(LeanCallbacks.requestPasswordResetRx(subscriber));
            }
        });
    }

    public static Observable<Void> send(final Action1<SendCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                callback.call(LeanCallbacks.sendRx(subscriber));
            }
        });
    }

    public static Observable<Void> signUp(final Action1<SignUpCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                callback.call(LeanCallbacks.signUpRx(subscriber));
            }
        });
    }

    public static Observable<AVStatus> status(final Action1<StatusCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<AVStatus>() {
            @Override public void call(Subscriber<? super AVStatus> subscriber) {
                callback.call(LeanCallbacks.statusRx(subscriber));
            }
        });
    }

    public static Observable<List<AVStatus>> statusList(final Action1<StatusListCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<List<AVStatus>>() {
            @Override public void call(Subscriber<? super List<AVStatus>> subscriber) {
                callback.call(LeanCallbacks.statusListRx(subscriber));
            }
        });
    }

    public static Observable<Void> updatePassword(final Action1<UpdatePasswordCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                callback.call(LeanCallbacks.updatePasswordRx(subscriber));
            }
        });
    }

    /*******************************************************************************************
     * ************************************ 即时通讯相关回调 *************************************
     *******************************************************************************************/

    public static Observable<AVIMClient> client(final Action1<AVIMClientCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<AVIMClient>() {
            @Override public void call(Subscriber<? super AVIMClient> subscriber) {
                callback.call(LeanCallbacks.clientRx(subscriber));
            }
        });
    }

    public static Observable<AVIMClient.AVIMClientStatus> clientStatus(final Action1<AVIMClientStatusCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<AVIMClient.AVIMClientStatus>() {
            @Override public void call(Subscriber<? super AVIMClient.AVIMClientStatus> subscriber) {
                callback.call(LeanCallbacks.clientStatusRx(subscriber));
            }
        });
    }

    public static Observable<Void> conversation(final Action1<AVIMConversationCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                callback.call(LeanCallbacks.conversationRx(subscriber));
            }
        });
    }

    public static Observable<AVIMConversation> conversationCreated(final Action1<AVIMConversationCreatedCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<AVIMConversation>() {
            @Override public void call(Subscriber<? super AVIMConversation> subscriber) {
                callback.call(LeanCallbacks.conversationCreatedRx(subscriber));
            }
        });
    }

    public static Observable<Integer> conversationMemberCount(final Action1<AVIMConversationMemberCountCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override public void call(Subscriber<? super Integer> subscriber) {
                callback.call(LeanCallbacks.conversationMemberCountRx(subscriber));
            }
        });
    }

    public static Observable<List<AVIMConversation>> conversationQuery(final Action1<AVIMConversationQueryCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<List<AVIMConversation>>() {
            @Override public void call(Subscriber<? super List<AVIMConversation>> subscriber) {
                callback.call(LeanCallbacks.conversationQueryRx(subscriber));
            }
        });
    }

    public static Observable<List<AVIMMessage>> messagesQuery(final Action1<AVIMMessagesQueryCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<List<AVIMMessage>>() {
            @Override public void call(Subscriber<? super List<AVIMMessage>> subscriber) {
                callback.call(LeanCallbacks.messagesQueryRx(subscriber));
            }
        });
    }

    public static Observable<List<String>> onlineClients(final Action1<AVIMOnlineClientsCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override public void call(Subscriber<? super List<String>> subscriber) {
                callback.call(LeanCallbacks.onlineClientsRx(subscriber));
            }
        });
    }

    public static Observable<AVIMMessage> singleMessageQuery(final Action1<AVIMSingleMessageQueryCallback> callback) {
        return Observable.create(new Observable.OnSubscribe<AVIMMessage>() {
            @Override public void call(Subscriber<? super AVIMMessage> subscriber) {
                callback.call(LeanCallbacks.singleMessageQueryRx(subscriber));
            }
        });
    }
}

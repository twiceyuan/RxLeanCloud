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
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
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
    public static <T extends AVObject> FindCallback<T> find(final Callback<List<T>> callback) {
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
    public static <T extends AVObject> FindCallback<T> findRx(final Subscriber<? super List<T>> s) {
        return find(new Callback<List<T>>() {
            @Override public void call(List<T> list, AVException e) {
                RxLeanCloud.wrap(s, list, e);
            }
        });
    }

    /**
     * {@link AVObject#saveInBackground(SaveCallback)} 时使用
     */
    public static <T> SaveCallback save(final Callback<T> callback) {
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
    public static SaveCallback saveRx(final Subscriber<? super Void> subscriber) {
        return save(new Callback<Object>() {
            @Override public void call(Object o, AVException e) {
                RxLeanCloud.wrap(subscriber, null, e);
            }
        });
    }

    /**
     * {@link GetCallback} 时使用
     */
    public static <T extends AVObject> GetCallback<T> get(final Callback<T> callback) {
        return new GetCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    /**
     * 使用 {@link rx.Observable#create(Observable.OnSubscribe)} 创建 Observable 时使用
     */
    public static <T extends AVObject> GetCallback<T> getRx(final Subscriber<? super T> subscriber) {
        return get(new Callback<T>() {
            @Override public void call(T avObject, AVException e) {
                RxLeanCloud.wrap(subscriber, avObject, e);
            }
        });
    }

    public static AVFriendshipCallback friendship(final Callback<AVFriendship> callback) {
        return new AVFriendshipCallback() {
            @Override public void done(AVFriendship friendship, AVException e) {
                callback.call(friendship, e);
            }
        };
    }

    public static AVFriendshipCallback friendshipRx(final Subscriber<? super AVFriendship> subscriber) {
        return friendship(new Callback<AVFriendship>() {
            @Override public void call(AVFriendship friendship, AVException e) {
                RxLeanCloud.wrap(subscriber, friendship, e);
            }
        });
    }

    public static <T> AVMobilePhoneVerifyCallback phoneVerify(final Callback<T> callback) {
        return new AVMobilePhoneVerifyCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static <T> AVMobilePhoneVerifyCallback phoneVerifyRx(final Subscriber<? super T> subscriber) {
        return phoneVerify(new Callback<Object>() {
            @Override public void call(Object o, AVException e) {
                RxLeanCloud.wrap(subscriber, null, e);
            }
        });
    }

    public static AVServerDateCallback serveDate(final Callback<Date> callback) {
        return new AVServerDateCallback() {
            @Override public void done(Date date, AVException e) {
                callback.call(date, e);
            }
        };
    }

    public static AVServerDateCallback serveDateRx(final Subscriber<? super Date> subscriber) {
        return serveDate(new Callback<Date>() {
            @Override public void call(Date date, AVException e) {
                RxLeanCloud.wrap(subscriber, date, e);
            }
        });
    }

    public static <T extends AVCloudQueryResult> CloudQueryCallback<T> cloudQuery(final Callback<AVCloudQueryResult> callback) {
        return new CloudQueryCallback<T>() {
            @Override public void done(AVCloudQueryResult t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T extends AVCloudQueryResult> CloudQueryCallback<T> cloudQueryRx(final Subscriber<? super AVCloudQueryResult> subscriber) {
        return cloudQuery(new Callback<AVCloudQueryResult>() {
            @Override public void call(AVCloudQueryResult result, AVException e) {
                RxLeanCloud.wrap(subscriber, result, e);
            }
        });
    }

    public static CountCallback count(final Callback<Integer> callback) {
        return new CountCallback() {
            @Override public void done(int i, AVException e) {
                callback.call(i, e);
            }
        };
    }

    public static CountCallback countRx(final Subscriber<? super Integer> subscriber) {
        return count(new Callback<Integer>() {
            @Override public void call(Integer integer, AVException e) {
                RxLeanCloud.wrap(subscriber, integer, e);
            }
        });
    }

    public static DeleteCallback delete(final Callback<Void> callback) {
        return new DeleteCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static DeleteCallback deleteRx(final Subscriber<? super Void> subscriber) {
        return delete(new Callback<Void>() {
            @Override public void call(Void aVoid, AVException e) {
                RxLeanCloud.wrap(subscriber, aVoid, e);
            }
        });
    }

    public static <T extends AVObject> FollowCallback<T> follow(final Callback<T> callback) {
        return new FollowCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T extends AVObject> FollowCallback<T> followRx(final Subscriber<? super T> subscriber) {
        return follow(new Callback<T>() {
            @Override public void call(T t, AVException e) {
                RxLeanCloud.wrap(subscriber, t, e);
            }
        });
    }

    public static <T extends AVObject> FollowersAndFolloweesCallback<T> followersAndFollowees(final Callback<Map<String, T>> callback) {
        return new FollowersAndFolloweesCallback<T>() {
            @Override public void done(Map<String, T> map, AVException e) {
                callback.call(map, e);
            }
        };
    }

    public static <T extends AVObject> FollowersAndFolloweesCallback<T> followersAndFolloweesRx(final Subscriber<? super Map<String, T>> subscriber) {
        return followersAndFollowees(new Callback<Map<String, T>>() {
            @Override public void call(Map<String, T> stringTMap, AVException e) {
                RxLeanCloud.wrap(subscriber, stringTMap, e);
            }
        });
    }

    public static <T> FunctionCallback<T> function(final Callback<T> callback) {
        return new FunctionCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T> FunctionCallback<T> functionRx(final Subscriber<? super T> subscriber) {
        return function(new Callback<T>() {
            @Override public void call(T t, AVException e) {
                RxLeanCloud.wrap(subscriber, t, e);
            }
        });
    }

    public static GetDataCallback getData(final Callback<byte[]> callback) {
        return new GetDataCallback() {
            @Override public void done(byte[] bytes, AVException e) {
                callback.call(bytes, e);
            }
        };
    }

    public static GetDataCallback getDataRx(final Subscriber<? super byte[]> subscriber) {
        return getData(new Callback<byte[]>() {
            @Override public void call(byte[] bytes, AVException e) {
                RxLeanCloud.wrap(subscriber, bytes, e);
            }
        });
    }

    public static <T extends AVFile> GetFileCallback<T> getFile(final Callback<T> callback) {
        return new GetFileCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T extends AVFile> GetFileCallback<T> getFileRx(final Subscriber<? super T> subscriber) {
        return getFile(new Callback<T>() {
            @Override public void call(T t, AVException e) {
                RxLeanCloud.wrap(subscriber, t, e);
            }
        });
    }

    public static InboxStatusFindCallback inboxStatusFind(final Callback<List<AVStatus>> callback) {
        return new InboxStatusFindCallback() {
            @Override public void done(List<AVStatus> list, AVException e) {
                callback.call(list, e);
            }
        };
    }

    public static InboxStatusFindCallback inboxStatusFindRx(final Subscriber<? super List<AVStatus>> subscriber) {
        return inboxStatusFind(new Callback<List<AVStatus>>() {
            @Override public void call(List<AVStatus> avStatuses, AVException e) {
                RxLeanCloud.wrap(subscriber, avStatuses, e);
            }
        });
    }

    public static <T extends AVUser> LogInCallback<T> login(final Callback<T> callback) {
        return new LogInCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T extends AVUser> LogInCallback<T> loginRx(final Subscriber<? super T> subscriber) {
        return login(new Callback<T>() {
            @Override public void call(T t, AVException e) {
                RxLeanCloud.wrap(subscriber, t, e);
            }
        });
    }

    public static ProgressCallback progress(final Callback<Integer> callback) {
        return new ProgressCallback() {
            @Override public void done(Integer integer) {
                callback.call(integer, null);
            }
        };
    }

    public static ProgressCallback progressRx(final Subscriber<? super Integer> subscriber) {
        return progress(new Callback<Integer>() {
            @Override public void call(Integer integer, AVException e) {
                RxLeanCloud.wrap(subscriber, integer, e);
            }
        });
    }

    public static <T extends AVObject> RefreshCallback<T> refresh(final Callback<T> callback) {
        return new RefreshCallback<T>() {
            @Override public void done(T t, AVException e) {
                callback.call(t, e);
            }
        };
    }

    public static <T extends AVObject> RefreshCallback<T> refreshRx(final Subscriber<? super T> subscriber) {
        return refresh(new Callback<T>() {
            @Override public void call(T t, AVException e) {
                RxLeanCloud.wrap(subscriber, t, e);
            }
        });
    }

    public static RequestEmailVerifyCallback requestEmailVerify(final Callback<Void> callback) {
        return new RequestEmailVerifyCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static RequestEmailVerifyCallback requestEmailVerifyRx(final Subscriber<? super Void> subscriber) {
        return requestEmailVerify(new Callback<Void>() {
            @Override public void call(Void aVoid, AVException e) {
                RxLeanCloud.wrap(subscriber, aVoid, e);
            }
        });
    }

    public static RequestMobileCodeCallback requestMobileCode(final Callback<Void> callback) {
        return new RequestMobileCodeCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static RequestMobileCodeCallback requestMobileCodeRx(final Subscriber<? super Void> subscriber) {
        return requestMobileCode(new Callback<Void>() {
            @Override public void call(Void aVoid, AVException e) {
                RxLeanCloud.wrap(subscriber, aVoid, e);
            }
        });
    }

    public static RequestPasswordResetCallback requestPasswordReset(final Callback<Void> callback) {
        return new RequestPasswordResetCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static RequestPasswordResetCallback requestPasswordResetRx(final Subscriber<? super Void> subscriber) {
        return requestPasswordReset(new Callback<Void>() {
            @Override public void call(Void aVoid, AVException e) {
                RxLeanCloud.wrap(subscriber, aVoid, e);
            }
        });
    }

    public static SendCallback send(final Callback<Void> callback) {
        return new SendCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static SendCallback sendRx(final Subscriber<? super Void> subscriber) {
        return send(new Callback<Void>() {
            @Override public void call(Void aVoid, AVException e) {
                RxLeanCloud.wrap(subscriber, aVoid, e);
            }
        });
    }

    public static SignUpCallback signUp(final Callback<Void> callback) {
        return new SignUpCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static SignUpCallback signUpRx(final Subscriber<? super Void> subscriber) {
        return signUp(new Callback<Void>() {
            @Override public void call(Void aVoid, AVException e) {
                RxLeanCloud.wrap(subscriber, aVoid, e);
            }
        });
    }

    public static StatusCallback status(final Callback<AVStatus> callback) {
        return new StatusCallback() {
            @Override public void done(AVStatus avStatus, AVException e) {
                callback.call(avStatus, e);
            }
        };
    }

    public static StatusCallback statusRx(final Subscriber<? super AVStatus> subscriber) {
        return status(new Callback<AVStatus>() {
            @Override public void call(AVStatus avStatus, AVException e) {
                RxLeanCloud.wrap(subscriber, avStatus, e);
            }
        });
    }

    public static StatusListCallback statusList(final Callback<List<AVStatus>> callback) {
        return new StatusListCallback() {
            @Override public void done(List<AVStatus> list, AVException e) {
                callback.call(list, e);
            }
        };
    }

    public static StatusListCallback statusListRx(final Subscriber<? super List<AVStatus>> subscriber) {
        return statusList(new Callback<List<AVStatus>>() {
            @Override public void call(List<AVStatus> avStatuses, AVException e) {
                RxLeanCloud.wrap(subscriber, avStatuses, e);
            }
        });
    }

    public static UpdatePasswordCallback updatePassword(final Callback<Void> callback) {
        return new UpdatePasswordCallback() {
            @Override public void done(AVException e) {
                callback.call(null, e);
            }
        };
    }

    public static UpdatePasswordCallback updatePasswordRx(final Subscriber<? super Void> subscriber) {
        return updatePassword(new Callback<Void>() {
            @Override public void call(Void aVoid, AVException e) {
                RxLeanCloud.wrap(subscriber, aVoid, e);
            }
        });
    }

    /*******************************************************************************************
     * ************************************ 即时通讯相关回调 *************************************
     *******************************************************************************************/

    public static AVIMClientCallback client(final Callback<AVIMClient> callback) {
        return new AVIMClientCallback() {
            @Override public void done(AVIMClient avimClient, AVIMException e) {
                callback.call(avimClient, e);
            }
        };
    }

    public static AVIMClientCallback clientRx(final Subscriber<? super AVIMClient> subscriber) {
        return client(new Callback<AVIMClient>() {
            @Override public void call(AVIMClient client, AVException e) {
                RxLeanCloud.wrap(subscriber, client, e);
            }
        });
    }

    public static AVIMClientStatusCallback clientStatus(final Callback<AVIMClient.AVIMClientStatus> callback) {
        return new AVIMClientStatusCallback() {
            @Override public void done(AVIMClient.AVIMClientStatus status) {
                callback.call(status, null);
            }
        };
    }

    public static AVIMClientStatusCallback clientStatusRx(final Subscriber<? super AVIMClient.AVIMClientStatus> subscriber) {
        return clientStatus(new Callback<AVIMClient.AVIMClientStatus>() {
            @Override public void call(AVIMClient.AVIMClientStatus status, AVException e) {
                RxLeanCloud.wrap(subscriber, status, e);
            }
        });
    }

    public static AVIMConversationCallback conversation(final Callback<Void> callback) {
        return new AVIMConversationCallback() {
            @Override public void done(AVIMException e) {
                callback.call(null, e);
            }
        };
    }

    public static AVIMConversationCallback conversationRx(final Subscriber<? super Void> subscriber) {
        return conversation(new Callback<Void>() {
            @Override public void call(Void aVoid, AVException e) {
                RxLeanCloud.wrap(subscriber, aVoid, e);
            }
        });
    }

    public static AVIMConversationCreatedCallback conversationCreated(final Callback<AVIMConversation> callback) {
        return new AVIMConversationCreatedCallback() {
            @Override public void done(AVIMConversation conversation, AVIMException e) {
                callback.call(conversation, e);
            }
        };
    }

    public static AVIMConversationCreatedCallback conversationCreatedRx(final Subscriber<? super AVIMConversation> subscriber) {
        return conversationCreated(new Callback<AVIMConversation>() {
            @Override public void call(AVIMConversation conversation, AVException e) {
                RxLeanCloud.wrap(subscriber, conversation, e);
            }
        });
    }

    public static AVIMConversationMemberCountCallback conversationMemberCount(final Callback<Integer> callback) {
        return new AVIMConversationMemberCountCallback() {
            @Override public void done(Integer integer, AVIMException e) {
                callback.call(integer, e);
            }
        };
    }

    public static AVIMConversationMemberCountCallback conversationMemberCountRx(final Subscriber<? super Integer> subscriber) {
        return conversationMemberCount(new Callback<Integer>() {
            @Override public void call(Integer integer, AVException e) {
                RxLeanCloud.wrap(subscriber, integer, e);
            }
        });
    }

    public static AVIMConversationQueryCallback conversationQuery(final Callback<List<AVIMConversation>> callback) {
        return new AVIMConversationQueryCallback() {
            @Override public void done(List<AVIMConversation> list, AVIMException e) {
                callback.call(list, e);
            }
        };
    }

    public static AVIMConversationQueryCallback conversationQueryRx(final Subscriber<? super List<AVIMConversation>> subscriber) {
        return conversationQuery(new Callback<List<AVIMConversation>>() {
            @Override public void call(List<AVIMConversation> conversations, AVException e) {
                RxLeanCloud.wrap(subscriber, conversations, e);
            }
        });
    }

    public static AVIMMessagesQueryCallback messagesQuery(final Callback<List<AVIMMessage>> callback) {
        return new AVIMMessagesQueryCallback() {
            @Override public void done(List<AVIMMessage> list, AVIMException e) {
                callback.call(list, e);
            }
        };
    }

    public static AVIMMessagesQueryCallback messagesQueryRx(final Subscriber<? super List<AVIMMessage>> subscriber) {
        return messagesQuery(new Callback<List<AVIMMessage>>() {
            @Override public void call(List<AVIMMessage> messages, AVException e) {
                RxLeanCloud.wrap(subscriber, messages, e);
            }
        });
    }

    public static AVIMOnlineClientsCallback onlineClients(final Callback<List<String>> callback) {
        return new AVIMOnlineClientsCallback() {
            @Override public void done(List<String> list, AVIMException e) {
                callback.call(list, e);
            }
        };
    }

    public static AVIMOnlineClientsCallback onlineClientsRx(final Subscriber<? super List<String>> subscriber) {
        return onlineClients(new Callback<List<String>>() {
            @Override public void call(List<String> strings, AVException e) {
                RxLeanCloud.wrap(subscriber, strings, e);
            }
        });
    }

    public static AVIMSingleMessageQueryCallback singleMessageQuery(final Callback<AVIMMessage> callback) {
        return new AVIMSingleMessageQueryCallback() {
            @Override public void done(AVIMMessage avimMessage, AVIMException e) {
                callback.call(avimMessage, e);
            }
        };
    }

    public static AVIMSingleMessageQueryCallback singleMessageQueryRx(final Subscriber<? super AVIMMessage> subscriber) {
        return singleMessageQuery(new Callback<AVIMMessage>() {
            @Override public void call(AVIMMessage message, AVException e) {
                RxLeanCloud.wrap(subscriber, message, e);
            }
        });
    }
}

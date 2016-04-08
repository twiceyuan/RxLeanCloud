package cn.leancloud.rx;

import com.avos.avoscloud.AVCloudQueryResult;
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
import com.avos.avoscloud.im.v2.AVIMMessage;
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

    public static <T extends AVCloudQueryResult> Observable<AVCloudQueryResult> cloudQuery(Action1<CloudQueryCallback<T>> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.cloudQueryRx(subscriber)));
    }

    public static Observable<Integer> count(Action1<CountCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.countRx(subscriber)));
    }

    public static Observable<Void> delete(Action1<DeleteCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.deleteRx(subscriber)));
    }

    public static <T extends AVObject> Observable<T> follow(Action1<FollowCallback<T>> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.followRx(subscriber)));
    }

    public static <T extends AVObject> Observable<Map<String, T>> followerAndFollowees(Action1<FollowersAndFolloweesCallback<T>> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.followersAndFolloweesRx(subscriber)));
    }

    public static <T> Observable<T> function(Action1<FunctionCallback<T>> callback) {
        return Observable.create((Observable.OnSubscribe<T>) (subscriber) -> callback.call(LeanCallbacks.functionRx(subscriber)));
    }

    public static Observable<byte[]> getData(Action1<GetDataCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.getDataRx(subscriber)));
    }

    public static <T extends AVFile> Observable<T> getFile(Action1<GetFileCallback<T>> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.getFileRx(subscriber)));
    }

    public static Observable<List<AVStatus>> inboxStatusFind(Action1<InboxStatusFindCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.inboxStatusFindRx(subscriber)));
    }

    public static <T extends AVUser> Observable<T> login(Action1<LogInCallback<T>> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.loginRx(subscriber)));
    }

    public static Observable<Integer> progress(Action1<ProgressCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.progressRx(subscriber)));
    }

    public static <T extends AVObject> Observable<T> refresh(Action1<RefreshCallback<T>> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.refreshRx(subscriber)));
    }

    public static Observable<Void> requestEmailVerify(Action1<RequestEmailVerifyCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.requestEmailVerifyRx(subscriber)));
    }

    public static Observable<Void> requestMobileCode(Action1<RequestMobileCodeCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.requestMobileCodeRx(subscriber)));
    }

    public static Observable<Void> requestPasswordReset(Action1<RequestPasswordResetCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.requestPasswordResetRx(subscriber)));
    }

    public static Observable<Void> send(Action1<SendCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.sendRx(subscriber)));
    }

    public static Observable<Void> signUp(Action1<SignUpCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.signUpRx(subscriber)));
    }

    public static Observable<AVStatus> status(Action1<StatusCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.statusRx(subscriber)));
    }

    public static Observable<List<AVStatus>> statusList(Action1<StatusListCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.statusListRx(subscriber)));
    }

    public static Observable<Void> updatePassword(Action1<UpdatePasswordCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.updatePasswordRx(subscriber)));
    }

    /*******************************************************************************************
     * ************************************ 即时通讯相关回调 *************************************
     *******************************************************************************************/

    public static Observable<AVIMClient.AVIMClientStatus> clientStatus(Action1<AVIMClientStatusCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.clientStatusRx(subscriber)));
    }

    public static Observable<Void> conversation(Action1<AVIMConversationCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.conversationRx(subscriber)));
    }

    public static Observable<AVIMConversation> conversationCreated(Action1<AVIMConversationCreatedCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.conversationCreatedRx(subscriber)));
    }

    public static Observable<Integer> conversationMemberCount(Action1<AVIMConversationMemberCountCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.conversationMemberCountRx(subscriber)));
    }

    public static Observable<List<AVIMConversation>> conversationQuery(Action1<AVIMConversationQueryCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.conversationQueryRx(subscriber)));
    }

    public static Observable<List<AVIMMessage>> messagesQuery(Action1<AVIMMessagesQueryCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.messagesQueryRx(subscriber)));
    }

    public static Observable<List<String>> onlineClients(Action1<AVIMOnlineClientsCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.onlineClientsRx(subscriber)));
    }

    public static Observable<AVIMMessage> singleMessageQuery(Action1<AVIMSingleMessageQueryCallback> callback) {
        return Observable.create(subscriber -> callback.call(LeanCallbacks.singleMessageQueryRx(subscriber)));
    }
}

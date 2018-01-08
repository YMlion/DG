package com.duoyi.drawguess.api;

import android.support.annotation.Nullable;
import com.duoyi.drawguess.util.AppObserver;
import com.duoyi.drawguess.util.DLog;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * web socket
 * Created by YMlion on 2018/1/2.
 */

public class AppSocket {

    private static AppSocket INSTANCE = new AppSocket();
    private String mUrl;
    private WebSocket mWebSocket;
    //private Observable<SocketResult> mObservable;

    private AppSocket() {
    }

    public static AppSocket get() {
        if (INSTANCE == null) {
            INSTANCE = new AppSocket();
        }

        return INSTANCE;
    }

    public void init() {
        // Observable.fromCallable(() -> new SocketServer().getUrl()).subscribe();
        Observable.defer(() -> Observable.just(new SocketServer().getUrl()))
                .subscribeOn(Schedulers.io())
                .subscribe(new AppObserver<String>() {
                    @Override public void onNext(String s) {
                        //mUrl = new SocketServer().getUrl();
                        mUrl = "ws://10.32.8.172:8002/";
                        DLog.d(mUrl);
                        OkHttpClient client = new OkHttpClient.Builder().build();
                        Request request = new Request.Builder().url(mUrl).build();
                        AppSocketListener socketListener = new AppSocketListener();
                        client.newWebSocket(request, socketListener);
                        //mObservable = Observable.create(socketListener::setObservableEmitter);
                    }
                });
    }

    public void close() {
        if (mWebSocket != null) {
            mWebSocket.close(3000, "exit");
            //mObservable = null;
            //Completable.fromAction(() -> mWebSocket.close(3000, "exit")).subscribe();
        }
    }

    //public Observable<SocketResult> getObservable() {
    //    return mObservable;
    //}

    /**
     * 开始你画我猜游戏
     */
    public void startDG() {
        if (mWebSocket != null) {
            mWebSocket.send("token");
            mWebSocket.send("start");
        }
    }

    class AppSocketListener extends WebSocketListener {
        //private ObservableEmitter mEmitter;
        @Override public void onOpen(WebSocket webSocket, Response response) {
            mWebSocket = webSocket;
            DLog.d("client onOpen.");
        }

        @Override public void onMessage(WebSocket webSocket, String text) {
            DLog.d("client onMessage text : " + text);
            //if (mEmitter != null) {
            //    mEmitter.onNext(new SocketResult<>(1, "", text));
            //}
        }

        @Override public void onMessage(WebSocket webSocket, ByteString bytes) {
            DLog.d("client onMessage bytes : " + bytes);
        }

        @Override public void onClosing(WebSocket webSocket, int code, String reason) {
            DLog.d("client onClosing");
        }

        @Override public void onClosed(WebSocket webSocket, int code, String reason) {
            DLog.d("client onClosed");
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
            DLog.d("client onFailure");
            //if (mEmitter != null) {
            //    mEmitter.onError(t);
            //}
        }

        //public void setObservableEmitter(ObservableEmitter<SocketResult> emitter) {
        //    mEmitter = emitter;
        //}
    }
}

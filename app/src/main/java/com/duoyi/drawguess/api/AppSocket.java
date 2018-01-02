package com.duoyi.drawguess.api;

import android.support.annotation.Nullable;
import com.duoyi.drawguess.util.AppObserver;
import com.duoyi.drawguess.util.DLog;
import io.reactivex.Completable;
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
                        mUrl = new SocketServer().getUrl();
                        DLog.d(mUrl);
                        OkHttpClient client = new OkHttpClient.Builder().build();
                        Request request = new Request.Builder().url(mUrl).build();
                        client.newWebSocket(request, new AppSocketListener());
                    }
                });
    }

    public void close() {
        if (mWebSocket != null) {
            Completable.fromAction(() -> mWebSocket.close(3000, "exit")).subscribe();
        }
    }

    /**
     * 开始你画我猜游戏
     */
    public void startDG() {
        if (mWebSocket != null) {
            Completable.fromAction(() -> {
                mWebSocket.send("token");
                mWebSocket.send("start");
            }).subscribe();
        }
    }

    class AppSocketListener extends WebSocketListener {
        @Override public void onOpen(WebSocket webSocket, Response response) {
            mWebSocket = webSocket;
            DLog.d("client onOpen.");
        }

        @Override public void onMessage(WebSocket webSocket, String text) {
            DLog.d("client onMessage : " + text);
        }

        @Override public void onMessage(WebSocket webSocket, ByteString bytes) {
            DLog.d("client onMessage : " + bytes);
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
        }
    }
}

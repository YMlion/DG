package com.duoyi.drawguess.api;

import android.support.annotation.Nullable;
import com.duoyi.drawguess.util.DLog;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.ByteString;

/**
 * mock server
 * Created by YMlion on 2018/1/2.
 */

public class SocketServer {

    private MockWebServer server;
    private WebSocket mSocket;

    public SocketServer() {
        init();
    }

    public MockWebServer getServer() {
        return server;
    }

    private void init() {
        server = new MockWebServer();
        server.enqueue(new MockResponse().withWebSocketUpgrade(new WebSocketListener() {
            @Override public void onOpen(WebSocket webSocket, Response response) {
                mSocket = webSocket;
                DLog.d("server onOpen.");
            }

            @Override public void onMessage(WebSocket webSocket, String text) {
                DLog.d("server onMessage : " + text);
                switch (text) {
                    case "token":
                        DLog.d("check token");
                        break;
                    case "start":
                        DLog.d("ready to start game, return room");
                        webSocket.send("12121");
                        break;
                }
            }

            @Override public void onMessage(WebSocket webSocket, ByteString bytes) {
                DLog.d("server onMessage : " + bytes);
            }

            @Override public void onClosing(WebSocket webSocket, int code, String reason) {
                DLog.d("server onClosing");
            }

            @Override public void onClosed(WebSocket webSocket, int code, String reason) {
                DLog.d("server onClosed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
                DLog.d("server onFailure");
            }
        }));
    }

    public String getUrl() {
        String hostName = server.getHostName();
        int port = server.getPort();
        String url = "ws://" + hostName + ":" + port + "/";
        return url;
    }
}

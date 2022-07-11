package com.robot.robotpic;

import android.util.Log;

import org.java_websocket.framing.CloseFrame;

import java.net.URI;

public class WebSocketManager {
    private static WebSocketManager webSocketManager;
    private JWebSocketClient socketClient;

    private WebSocketManager(){

    }

    public static WebSocketManager getInstance(){
        if (webSocketManager == null) {
            webSocketManager = new WebSocketManager();
        }
        return webSocketManager;
    }


    public void openSocket(String ip){
        if (socketClient != null) {
            socketClient.close();
        }

        URI uri = URI.create("ws://");
        socketClient = new JWebSocketClient(uri){
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                Log.i("HHH","message : "+message);
                LiveDataBus.getInstance().getChannel(LiveDataBus.CHANNEL_RMSG,String.class).postValue(message);
            }
        };

        // 设置超时
        socketClient.setConnectionLostTimeout(60 * 1000);

        try {
            socketClient.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送websocket
     * @param msg
     */
    public void sendMsg(String msg) {
        if (socketClient != null && socketClient.isOpen()) {
            socketClient.send(msg);
        }
    }

    /**
     * 关闭websocket
     */
    public void closeConnect() {
        socketClient.close();
        socketClient = null;
    }
}

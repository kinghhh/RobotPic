package com.robot.robotpic;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

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


    /**
     * 建立websocket连接
     * @param ip
     */
    public void openSocket(String ip){
        if (socketClient != null) {
            socketClient.close();
        }

        URI uri = URI.create("ws://"+ip);
        socketClient = new JWebSocketClient(uri){
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                // websocket会在这里收到服务端发过来的消息
                LogUtils.i("HHH","message : "+message);
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

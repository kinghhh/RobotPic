package com.robot.robotpic;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

public class LiveDataBus {
    public static final String CHANNEL_RMSG = "receive_message ";
    public static final String CHANNEL_SMSG = "send_message ";
    private static LiveDataBus instance;
    private static Object object = new Object();
    private Map<String, MutableLiveData<Object>> mBus;

    private LiveDataBus(){
        mBus = new HashMap<>();
    }

    public static LiveDataBus getInstance(){
        if (instance == null){
            synchronized (object){
                if (instance == null){
                    instance = new LiveDataBus();
                }
            }
        }
        return instance;
    }

    public <T> MutableLiveData<T> getChannel(String target, Class<T> type) {
        if (!mBus.containsKey(target)) {
            mBus.put(target, new MutableLiveData<>());
        }
        return (MutableLiveData<T>) mBus.get(target);
    }

    public MutableLiveData<Object> getChannel(String target) {
        return getChannel(target, Object.class);
    }
}

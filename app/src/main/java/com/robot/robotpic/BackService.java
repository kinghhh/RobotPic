package com.robot.robotpic;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;

public class BackService extends Service implements LifecycleOwner {
    /**
     * handler处理消息的CODE
     */
    private static final int CODE_OPEN_ACTIVITY = 0x1001;
    /**
     * 定时唤醒程序
     */
    private int startActivityTime = 10*1000;
    /**
     * 生命周期持有者 不用关心这个是啥
     */
    private LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case CODE_OPEN_ACTIVITY:
                    // 唤醒程序 先判断当前这个程序是否在前台运行 如果已经运行了 就不再唤醒了
                    if (!AppUtils.isAppForeground(getPackageName())){
                        ActivityUtils.startActivity("com.robot.robotpic","com.robot.robotpic.MainActivity");
                    }
                    // 发送定时唤醒消息
                    mHandler.sendEmptyMessageDelayed(CODE_OPEN_ACTIVITY,startActivityTime);
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler.sendEmptyMessageDelayed(CODE_OPEN_ACTIVITY,startActivityTime);
        //
        LiveDataBus.getInstance().getChannel(LiveDataBus.CHANNEL_RMSG,String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }
}

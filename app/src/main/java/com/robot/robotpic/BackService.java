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
    private static final int CODE_OPEN_ACTIVITY = 0x1001;

    private int startActivityTime = 10*1000;

    private LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case CODE_OPEN_ACTIVITY:
                    if (!AppUtils.isAppForeground(getPackageName())){
                        ActivityUtils.startActivity("com.robot.robotpic","com.robot.robotpic.MainActivity");
                    }
                    mHandler.sendEmptyMessageDelayed(CODE_OPEN_ACTIVITY,startActivityTime);
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler.sendEmptyMessageDelayed(CODE_OPEN_ACTIVITY,startActivityTime);
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

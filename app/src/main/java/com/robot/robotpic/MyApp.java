package com.robot.robotpic;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;

import com.blankj.utilcode.util.LogUtils;

public class MyApp extends Application {

    private String logSavePath = Environment.getExternalStorageDirectory().getPath()+"/logger/";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.Config config = LogUtils.getConfig();
        config.setLogSwitch(true)
        .setLog2FileSwitch(true)
        .setDir(logSavePath);

        LogUtils.iTag("robot","app on create");
        startService(new Intent(this,BackService.class));
    }
}

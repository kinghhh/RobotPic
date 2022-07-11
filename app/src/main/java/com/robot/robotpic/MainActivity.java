package com.robot.robotpic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.UtilsTransActivity;
import com.robot.robotpic.adapter.PicAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_CHANGE_IMG = 0x01;
    private static final int CODE_CLOSE = 0x02;

    public static int count = 0;

    private long scrollerTime = 1000 * 2;
    private long backTime = 10 * 1000;

    private ArrayList<String> picUrls = new ArrayList<>();

    private ViewPager vp_pic;

    private PicAdapter picAdapter;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case CODE_CHANGE_IMG:
                    count = (count+1) % picUrls.size();
                    try {
                        vp_pic.setCurrentItem(count);
                        mHandler.sendEmptyMessageDelayed(CODE_CHANGE_IMG,scrollerTime);
                    } catch (Exception e){

                    }
                    break;
                case CODE_CLOSE:
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        checkPermission();

        mHandler.sendEmptyMessage(CODE_CHANGE_IMG);

        // 删除之前发送的 CODE_CLOSE
        mHandler.removeMessages(CODE_CLOSE);
        mHandler.sendEmptyMessageDelayed(CODE_CLOSE, backTime);
    }

    private void checkPermission(){
//        if (!PermissionUtils.isGranted(Manifest.permission.SYSTEM_ALERT_WINDOW)){
//            PermissionUtils.permission(Manifest.permission.SYSTEM_ALERT_WINDOW).callback(new PermissionUtils.FullCallback() {
//                @Override
//                public void onGranted(@NonNull List<String> granted) {
//                    Log.i("HHH","granted :: "+granted);
//                }
//
//                @Override
//                public void onDenied(@NonNull List<String> deniedForever, @NonNull List<String> denied) {
//                    Log.i("HHH","denied :: "+deniedForever+" "+denied);
//                }
//            }).explain(new PermissionUtils.OnExplainListener() {
//                @Override
//                public void explain(@NonNull UtilsTransActivity activity, @NonNull List<String> denied, @NonNull ShouldRequest shouldRequest) {
//                    RequestPermissionDialog dialog = new RequestPermissionDialog(MainActivity.this);
//                    dialog.show();
//                }
//            }).request();
//        }
    }

    private void findView(){
        vp_pic = findViewById(R.id.vp_pic);

        picAdapter = new PicAdapter();
        vp_pic.setAdapter(picAdapter);

        picUrls.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ftu1.whhost.net%2Fuploads%2F20180424%2F12%2F1524543440-GVyjOYJMzH.jpg&refer=http%3A%2F%2Ftu1.whhost.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660039289&t=86009f684997149f4d8810a3ed52d642");
        picUrls.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.mms591.com%2Fwww.mms591.com-photo%2F20200812%2F1-200Q2125123_480x800.jpg&refer=http%3A%2F%2Fwww.mms591.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660039289&t=ecb808d86681cac100cd1568711ffa5d");
        picUrls.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.mms591.com%2Fwww.mms591.com-photo%2F20200725%2F1-200H5211336-50_480x800.jpg&refer=http%3A%2F%2Fwww.mms591.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660039289&t=66105fe2ad42741933b8e5142b46793e");

        picAdapter.setData(picUrls);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(CODE_CHANGE_IMG);
        super.onDestroy();
    }
}
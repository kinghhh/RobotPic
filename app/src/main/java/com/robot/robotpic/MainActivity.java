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
    /**
     * handler 消息CODE
     */
    private static final int CODE_CHANGE_IMG = 0x01;
    /**
     * handler 消息CODE
     */
    private static final int CODE_CLOSE = 0x02;
    /**
     * 用于计数 图片自动播放使用
     */
    public static int count = 0;
    /**
     * 图片轮播的切换时间间隔
     */
    private long scrollerTime = 1000 * 2;
    /**
     * 程序切换到后台的时间间隔
     */
    private long backTime = 10 * 1000;
    /**
     * 轮播图片的列表
     */
    private ArrayList<String> picUrls = new ArrayList<>();
    /**
     * 轮播图片的控件
     */
    private ViewPager vp_pic;
    /**
     * 适配器
     */
    private PicAdapter picAdapter;
    /**
     * Handler消息处理 定时发送消息 然后做相应的处理
     */
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case CODE_CHANGE_IMG:
                    // 轮播图片
                    count = (count+1) % picUrls.size();
                    try {
                        // 设置当前显示那一张图片 图片的下标从0~(picUrls.size()-1)
                        vp_pic.setCurrentItem(count);
                        // 发送下一个定时消息
                        mHandler.sendEmptyMessageDelayed(CODE_CHANGE_IMG,scrollerTime);
                    } catch (Exception e){
                        // 为了避免程序进入后台后，控件被回收，而消息刚好处理到这里会抛出异常
                    }
                    break;
                case CODE_CLOSE:
                    // 关闭程序 等待下一次唤醒
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定控件
        findView();

        // 检查权限 有待完善
        checkPermission();

        // 发送图片轮播消息
        mHandler.sendEmptyMessage(CODE_CHANGE_IMG);

        // 删除之前发送的 CODE_CLOSE 避免出现程序刚起来 就退出的情况
        mHandler.removeMessages(CODE_CLOSE);
        // 发送定时消息 到时间 关闭程序
        mHandler.sendEmptyMessageDelayed(CODE_CLOSE, backTime);
    }

    /**
     * 权限检查 有待完善
     */
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

    /**
     * 绑定控件
     */
    private void findView(){
        vp_pic = findViewById(R.id.vp_pic);

        picAdapter = new PicAdapter();
        vp_pic.setAdapter(picAdapter);

        // 添加图片
        picUrls.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ftu1.whhost.net%2Fuploads%2F20180424%2F12%2F1524543440-GVyjOYJMzH.jpg&refer=http%3A%2F%2Ftu1.whhost.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660039289&t=86009f684997149f4d8810a3ed52d642");
        picUrls.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.mms591.com%2Fwww.mms591.com-photo%2F20200812%2F1-200Q2125123_480x800.jpg&refer=http%3A%2F%2Fwww.mms591.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660039289&t=ecb808d86681cac100cd1568711ffa5d");
        picUrls.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.mms591.com%2Fwww.mms591.com-photo%2F20200725%2F1-200H5211336-50_480x800.jpg&refer=http%3A%2F%2Fwww.mms591.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660039289&t=66105fe2ad42741933b8e5142b46793e");

        // 将图片显示在界面上 下次需要更新图片的话 也可以直接用picAdapter.setData更新
        picAdapter.setData(picUrls);
    }

    @Override
    protected void onDestroy() {
        // 删除图片轮播处理消息
        mHandler.removeMessages(CODE_CHANGE_IMG);
        super.onDestroy();
    }
}
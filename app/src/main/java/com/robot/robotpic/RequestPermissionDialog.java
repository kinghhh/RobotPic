package com.robot.robotpic;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.PermissionUtils;

public class RequestPermissionDialog extends Dialog {

    private TextView tv_confirm,tv_cancel;

    public RequestPermissionDialog(@NonNull Context context) {
        super(context);
        View view = View.inflate(context,R.layout.dialog_request_permission,null);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_confirm = view.findViewById(R.id.tv_confirm);

        tv_confirm.setOnClickListener(v -> {
            PermissionUtils.launchAppDetailsSettings();
            dismiss();
        });

        tv_cancel.setOnClickListener(v -> {
            dismiss();
        });
        setContentView(view);
    }
}

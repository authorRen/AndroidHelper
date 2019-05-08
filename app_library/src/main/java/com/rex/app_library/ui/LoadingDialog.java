package com.rex.app_library.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.rex.app_library.R;

/**
 * Created by renzeqiang
 * on 2019/5/5
 */
public class LoadingDialog extends Dialog {
    public Context context;

    public LoadingDialog(Context context) {
        super(context, R.style.loading_alert_dialog);
        this.context = context;
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        assert window != null;
        window.setWindowAnimations(R.style.DialogWindowStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

    }
}
package com.rex.app_library.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by renzeqiang
 * on 2019/5/7
 */
public class BaseApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 退出app
     */
    public void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}

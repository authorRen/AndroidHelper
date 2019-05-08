package com.rex.app_library.manager.NetManager;

import android.text.TextUtils;

import com.rex.app_library.base.BaseApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * okHttp提供者
 *
 * Created by renzeqiang
 * on 2019/5/8
 */
public class OkHttpProvider {

    public static final long DEFAULT_CONNECT_TIMEOUT = 15;
    public static final long DEFAULT_WRITE_TIMEOUT = 20;
    public static final long DEFAULT_READ_TIMEOUT = 20;

    private OkHttpClient mOkHttpClient;

    private OkHttpProvider() {
        initOkHttp();
    }

    private static class SingletonHolder {
        private static final OkHttpProvider INSTANCE = new OkHttpProvider();
    }

    public static OkHttpProvider getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(createDir(BaseApplication.getContext().getCacheDir() + "/okhttp"), 10 * 1024 * 1024L));

//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(new LoggerInterceptor());
//        }

//        HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(MyApplication.getAppContext().getResources().openRawResource(R.raw.andgjj));
//        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        mOkHttpClient = builder.build();
    }

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

    public static File createDir(String dir) {
        if (TextUtils.isEmpty(dir)) {
            return null;
        }
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                return null;
            }
        }
        return dirFile;
    }
}

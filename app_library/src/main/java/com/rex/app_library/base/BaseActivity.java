package com.rex.app_library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rex.app_library.ui.LoadingDialog;

/**
 * Created by renzeqiang
 * on 2019/5/5
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    protected Context mContext;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            getBundleExtras(bundle);
        }
        initView(savedInstanceState);
        initData();
    }

    protected abstract @LayoutRes int getLayoutId();

    /**
     * 传递数据
     *
     * @param bundle bundle
     */
    protected void getBundleExtras(Bundle bundle) {}

    /**
     * 初始化视图
     *
     * @param savedInstanceState aty销毁时保存的临时参数
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化数据源
     */
    protected abstract void initData();



    /**
     * show Loading
     */
    protected void showLoadDialog() {
        if (!isFinishing()) {
            dismissLoadDialog();
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.show();
        }
    }

    /**
     * dismiss Loading
     */
    protected void dismissLoadDialog() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.rex.app_library.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.rex.app_library.R;

/**
 * 自定义Loading旋转样式
 *
 * Created by renzeqiang
 * on 2019/5/5
 */
public class LoadingSpinView extends android.support.v7.widget.AppCompatImageView implements LoadIndeterminate {

    private float mRotateDegrees;
    private int mFrameTime;
    private boolean mNeedToUpdateView;
    private Runnable mUpdateViewRunnable;

    public LoadingSpinView(Context context) {
        super(context);
        init();
    }

    public LoadingSpinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setImageResource(R.drawable.kprogresshud_spinner);
        mFrameTime = 1000 / 12;
        mUpdateViewRunnable = new Runnable() {
            @Override
            public void run() {
                mRotateDegrees += 30;
                mRotateDegrees = mRotateDegrees < 360 ? mRotateDegrees : mRotateDegrees - 360;
                invalidate();
                if (mNeedToUpdateView) {
                    postDelayed(this, mFrameTime);
                }
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(mRotateDegrees, getWidth() >> 1, getHeight() >> 1);
        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeedToUpdateView = true;
        post(mUpdateViewRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        mNeedToUpdateView = false;
        super.onDetachedFromWindow();

    }

    @Override
    public void setAnimationSpeed(float scale) {
        mFrameTime = (int) (1000 / 12 / scale);
    }
}

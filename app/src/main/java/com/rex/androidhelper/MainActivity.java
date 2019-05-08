package com.rex.androidhelper;



import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rex.app_library.base.BaseActivity;
import com.rex.app_library.util.Countdown;


public class MainActivity extends BaseActivity {


    private TextView mTest;
    private Countdown countdown;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTest = findViewById(R.id.tv_test);
        countdown = new Countdown(60,
                getResources().getString(R.string.countdown_text),
                mTest);
        countdown.setCountdownListener(new Countdown.CountdownListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
                mTest.setText("重新获取");
            }

            @Override
            public void onUpdate(int currentRemainingSeconds) {
            }
        });
    }

    @Override
    protected void initData() {
        mTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countdown.start();
            }
        });
    }
}

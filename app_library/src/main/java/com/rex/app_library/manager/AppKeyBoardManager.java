package com.rex.app_library.manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 软键盘管理
 *
 * Created by renzeqiang
 * on 2019/5/5
 */
public class AppKeyBoardManager {

    /**
     * 打开软键盘
     *
     * @param editText 输入框
     * @param context  上下文
     */
    public static void openKeyBoard(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param editText 输入框
     * @param context  上下文
     */
    public static void closeKeyBoard(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 通过定时器强制隐藏虚拟键盘
     *
     * @param view view
     */
    public static void TimerHideKeyBoard(final View view) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
                }
            }
        }, 10);
    }

    /**
     * 键盘是否显示
     */
    public static boolean keyBoard(EditText editText) {
        boolean bool = false;
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            bool = true;
        }
        return bool;
    }

    /**
     * 切换键盘的状态
     * 如当前为收起变为弹出，若当前为弹出变为收起
     */
    public static void toggleKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void hideKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    /**
     * 强制显示输入法键盘
     */
    public static void showKeyBoard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 隐藏输入法键盘
     * @param activity 当前的activity
     */
    public static void hideInputMethod(Activity activity) {
        try {
            View view = activity.getWindow().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示输入法键盘
     * @param activity 当前的activity
     */
    public static void showInputMethod(Activity activity) {
        View v = activity.getCurrentFocus();
        if (null == v) {
            return;
        }
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(v, 0);
    }
}

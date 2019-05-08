package com.rex.app_library.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity管理类
 *
 * Created by renzeqiang
 * on 2019/5/5
 */
public class ActivityStackManager {

    private static Stack<Activity> activityStack;
    private static ActivityStackManager instance;

    private ActivityStackManager() {
    }

    public static synchronized ActivityStackManager getInstance() {
        if (instance == null) {
            instance = new ActivityStackManager();
        }
        return instance;
    }

    /**
     * 关闭activity
     * finish the activity and remove it from stack
     *
     * @param activity activity
     */
    public void popActivity(Activity activity) {
        if (activityStack == null) return;
        if (activity != null) {
            activity.finish();
            activity.overridePendingTransition(0, 0);
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获取当前的Activity
     * get the current activity
     *
     * @return activity
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) return null;
        return activityStack.lastElement();
    }

    /**
     * 获取第一个入栈的Activity
     * get the first activity in the stack
     *
     * @return activity
     */
    public Activity firstActiviy() {
        if (activityStack == null || activityStack.isEmpty()) return null;
        return activityStack.firstElement();
    }

    /**
     * 添加activity到stack
     * add the activity to the stack
     *
     * @param activity activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.push(activity);
    }

    /**
     * 移除所有的activity
     * remove all activity
     */
    public void popAllActivity() {
        if (activityStack == null) return;
        while (true) {
            if (activityStack.isEmpty()) {
                break;
            }
            Activity activity = currentActivity();
            popActivity(activity);
        }
    }

    /**
     * 移除所有Activity但保持目前的Activity
     * remove all activity but keep the current activity
     */
    public void popAllActivityWithOutCurrent() {
        Activity activity = currentActivity();
        while (true) {
            if (activityStack.size() == 1) {
                break;
            }
            if (firstActiviy() == activity) {
                break;
            } else {
                popActivity(activity);
            }
        }
    }
}

package com.example.testfinddatabase;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;
/*
* 实现结束 Activity
*
* */


public class ActivityManager extends Application {
    //用list保存 Activity
    private List<Activity> mList = new LinkedList<Activity>();
    //创建静态对象
    private static com.example.testfinddatabase.ActivityManager instance;
    //构造方法
    private ActivityManager() {}
    //实例化一次
    public synchronized static com.example.testfinddatabase.ActivityManager getInstance() {
        if (null==instance) {
            instance = new com.example.testfinddatabase.ActivityManager();
        }
        return instance;
    }

    //add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    //close list中的 activity
    //exit
    public void exit() {
        try {
            for (Activity activity:mList) {
                if (activity!=null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }

    }
    //exit end

    //干掉进程
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}

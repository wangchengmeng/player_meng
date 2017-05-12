/**
 * @Project: PMH_Main
 * @Title: ActivityManager.java
 * @Package com.pdw.pmh.library
 * @Description: activity的管理
 * @author huang.b
 * @date 2013-10-8 下午1:44:14
 * @Copyright: 2013 www.paidui.cn Inc. All rights reserved.
 * @version V1.0
 */
package com.maogu.htclibrary.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * PMH的Activity管理类
 *
 * @author huang.b
 */
public class ActivityStackUtil {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}

package com.suyou.media.util;

import android.content.Context;

import com.maogu.htclibrary.app.HtcApplicationBase;
import com.maogu.htclibrary.orm.DataManager;
import com.maogu.htclibrary.orm.DatabaseBuilder;
import com.maogu.htclibrary.util.EvtLog;
import com.maogu.htclibrary.util.PackageUtil;

/**
 * 数据库初始化类
 *
 * @author zou.sq
 * @since 2013-03-28 zou.sq 添加SpecialDishModel表初始化操作
 */
public class DBUtil {
    private static final String TAG = "DBUtil";

    private static DatabaseBuilder DATABASE_BUILDER;
    private static PMRDataManager  INSTANCE;

    // 获取数据库实例
    static {
        EvtLog.d(TAG, "DBUtil static, pmr");
        if (DATABASE_BUILDER == null) {
            DATABASE_BUILDER = new DatabaseBuilder(PackageUtil.getConfigString("db_name"));
        }
    }

    /**
     * 清除所有的数据表
     */
    public static void clearAllTables() {
        if (null != DATABASE_BUILDER) {
            String[] tables = DATABASE_BUILDER.getTables();
            for (String tableName : tables) {
            }
        }
    }

    /**
     * 清除用户相关的数据表
     */
    public static void clearUserTables() {
    }

    public static DataManager getDataManager() {
        if (INSTANCE == null) {
            INSTANCE = new PMRDataManager(HtcApplicationBase.getInstance(), DATABASE_BUILDER);
        }
        return INSTANCE;
    }

    static class PMRDataManager extends DataManager {
        PMRDataManager(Context context, DatabaseBuilder databaseBuilder) {
            super(context, PackageUtil.getConfigString("db_name"), PackageUtil.getConfigInt("db_version"), databaseBuilder);
        }
    }
}

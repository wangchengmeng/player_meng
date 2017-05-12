package com.maogu.htclibrary.listener;

import com.maogu.htclibrary.auth.ActionResult;

/**
 * 动作回调接口
 *
 * @author zeng.ww
 * @version 1.1.0
 */
public interface RequestListener {
    /**
     * 执行动作
     *
     * @return 对象
     */
    ActionResult onAsyncRun();

    /**
     * 成功回调
     *
     * @param result 返回结果
     */
    void onSuccess(ActionResult result);

    /**
     * 失败回调
     *
     * @param result 返回结果
     */
    void onError(ActionResult result);
}

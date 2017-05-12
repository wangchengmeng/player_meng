package com.maogu.htclibrary.auth;

/**
 * 动作执行结果封装基类
 *
 * @author zou.sq
 */

public class ActionResult<T> {
    /**
     * 接口正常
     */
    public static final String RESULT_CODE_SUCCESS   = "200";
    /**
     * 网络异常
     */
    public static final String RESULT_CODE_NET_ERROR = "111";
    /**
     * 未授权
     */
    public static final String RESULT_CODE_NO_AUTH   = "401";
    /**
     * token过期状态
     */
    public static final String RESULT_CODE_NO_LOGIN  = "403";
    /**
     * 页面未发现
     */
    public static final String RESULT_CODE_NO_FOUND  = "404";
    /**
     * 结果状态
     */
    public              String ResultCode            = "0";
    /**
     * 结果对象
     */
    public T ResultObject;
}

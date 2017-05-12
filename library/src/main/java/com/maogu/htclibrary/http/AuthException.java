package com.maogu.htclibrary.http;

import com.maogu.htclibrary.R;
import com.maogu.htclibrary.app.HtcApplicationBase;

/**
 * 网络异常
 *
 * @author wang.xy
 */
public class AuthException extends Exception {

    private static String MESSAGE = HtcApplicationBase.getInstance().getString(R.string.no_auth);
    private int exceptionCode;

    /**
     * 构造函数
     */
    public AuthException() {
        super(MESSAGE);
    }

    /**
     * 构造函数
     *
     * @param message 网络异常的内容
     */
    public AuthException(String message) {
        super(message);
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int mExceptionCode) {
        this.exceptionCode = mExceptionCode;
    }
}

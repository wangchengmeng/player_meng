package com.maogu.htclibrary.http;

import com.maogu.htclibrary.app.JsonResult;
import com.maogu.htclibrary.util.MessageException;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.params.HttpParams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 网络访问辅助类
 *
 * @author zou.sq
 */

/**
 * @author alina
 * @version 2012-08-02，zeng.ww，增加httpGet,getString,parseUrl,decodeUrl,encodeUrl等方法
 *          <br>
 *          2012-10-31，tan.xx，get， post新增带参数requestType处理等方法<br>
 */
public class HttpClientUtil {
    /**
     * 这个变量需要重构<br>
     * 这个属性不能用于判断网络是否可用，判断网络是否可用请用 NetUtil.isNetworkAvailable() 方法；<br>
     * 这个方法仅用于用于判断返回的json是否有异常，如果有异常，表示有可能是使用了错误的网络，如CMCC等；
     */
    public static boolean LAST_REQUEST_IS_OK = true;
    private static IPDWHttpClient PDW_HTTP_CLIENT = new DefaultPDWHttpClient();

    /**
     * @param pdwHttpClient IPDWHttpClient对象
     */
    public static void setPDWHttpClient(IPDWHttpClient pdwHttpClient) {
        if (pdwHttpClient == null) {
            throw new NullPointerException("http client 不能为空");
        }

        PDW_HTTP_CLIENT = pdwHttpClient;
    }

    /**
     * @param cookieStore 存储coockie
     */
    public static void setCookieStore(CookieStore cookieStore) {
        PDW_HTTP_CLIENT.setCookieStore(cookieStore);
    }

    /**
     * 通过post方式，跟服务器进行数据交互。该方法已经进行了网络检查
     *
     * @param url        url地址
     * @param httpParams http参数
     * @param postParams 参数
     * @return json数据 json数据异常
     * @throws NetworkException 网络异常
     * @throws MessageException 业务异常
     */
    public static JsonResult post(String url, HttpParams httpParams, List<NameValuePair> postParams)
            throws NetworkException, MessageException {
        return PDW_HTTP_CLIENT.post(url, httpParams, postParams);
    }

    /**
     * 通过put方式，跟服务器进行数据交互。该方法已经进行了网络检查
     *
     * @param url        url地址
     * @param httpParams http参数
     * @param postParams 参数
     * @return json数据 json数据异常
     * @throws NetworkException 网络异常
     * @throws MessageException 业务异常
     */
    public static JsonResult put(String url, HttpParams httpParams, List<NameValuePair> postParams)
            throws NetworkException, MessageException {
        return PDW_HTTP_CLIENT.put(url, httpParams, postParams);
    }

    /**
     * 通过delete方式，跟服务器进行数据交互。该方法已经进行了网络检查
     *
     * @param url        url地址
     * @param httpParams http参数
     * @param postParams 参数
     * @return json数据 json数据异常
     * @throws NetworkException 网络异常
     * @throws MessageException 业务异常
     */
    public static JsonResult delete(String url, HttpParams httpParams, List<NameValuePair> postParams)
            throws NetworkException, MessageException {
        return PDW_HTTP_CLIENT.delete(url, httpParams, postParams);
    }

    /**
     * 通过post方式，跟服务器进行数据交互。该方法已经进行了网络检查
     *
     * @param url        url地址
     * @param httpParams http参数
     * @param postParams 参数
     * @param fileList   文件集合
     * @return json数据 json数据异常
     * @throws NetworkException             网络异常
     * @throws MessageException             业务异常
     * @throws UnsupportedEncodingException
     */
    public static JsonResult post(String url, HttpParams httpParams, List<NameValuePair> postParams, List<File> fileList)
            throws NetworkException, MessageException, UnsupportedEncodingException {
        return PDW_HTTP_CLIENT.post(url, httpParams, postParams, fileList);
    }

    /**
     * 通过get方式，跟服务器进行数据交互。该方法已经进行了网络检查
     *
     * @param url       url地址
     * @param getParams 附加在Url后面的参数
     * @return json数据
     * @throws MessageException
     * @throws NetworkException 异常信息
     */
    public static JsonResult get(String url, List<NameValuePair> getParams) throws NetworkException, MessageException {
        return PDW_HTTP_CLIENT.get(url, getParams);
    }

    /**
     * 通过get方式，跟服务器进行数据交互。该方法已经进行了网络检查
     *
     * @param url        url地址
     * @param httpParams http参数
     * @param getParams  附加在Url后面的参数
     * @return json数据
     * @throws NetworkException 异常信息
     * @throws MessageException 消息异常
     */
    public static JsonResult get(String url, HttpParams httpParams, List<NameValuePair> getParams)
            throws NetworkException, MessageException {
        return PDW_HTTP_CLIENT.get(url, getParams);
    }

    /**
     * 设置CookieStore
     *
     * @param domain 域名
     * @param name   Cookie名称
     * @param values 值参数
     */
    public static void setCookieStores(String domain, String name, String values) {
        PDW_HTTP_CLIENT.setCookieStores(domain, name, values);
    }

    /**
     * 获取当前请求的Cookies
     *
     * @return 返回cookie信息
     */
    public static String getCookies() {
        return PDW_HTTP_CLIENT.getCookies();
    }
}

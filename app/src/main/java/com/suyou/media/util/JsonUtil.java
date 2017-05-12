package com.suyou.media.util;

import com.alibaba.fastjson.JSON;
import com.maogu.htclibrary.util.EvtLog;
import com.maogu.htclibrary.util.IOUtil;
import com.suyou.media.app.HtcApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonUtil {

    public static <T> List<T> getJson(String fileName, Class<T> type) {
        List<T> list = new ArrayList<>();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = HtcApplication.getInstance().getAssets().open(fileName);
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            list = JSON.parseArray(builder.toString(), type);
        } catch (IOException e) {
            EvtLog.w(TAG, e);
        } finally {
            IOUtil.closeStream(inputStream);
            IOUtil.closeStream(reader);
        }
        return list;
    }
}

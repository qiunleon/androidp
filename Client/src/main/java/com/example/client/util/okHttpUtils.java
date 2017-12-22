package com.example.client.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by alienware on 2017/6/3.
 */
public class okHttpUtils {

    public static OkHttpClient client = new OkHttpClient();

    public static String get(String url) {
        try {
            client.newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS);
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.example.client.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressWarnings("unused")
public class HttpUtil {

    private static class HttpUtilHolder {
        private static HttpUtil sInstance = new HttpUtil();
    }

    public HttpUtil getInstance() {
        return HttpUtilHolder.sInstance;
    }

    public String get(String path) {
        String result = "";
        URL url;
        HttpURLConnection conn;
        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(10000);

            OutputStream out = conn.getOutputStream();
            out.write(path.getBytes());
            out.flush();
            out.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                InputStream inputStream = conn.getInputStream();
                int len;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                result = os.toString();
                inputStream.close();
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    public String post(String request, String content) {
        String result = "";
        URL url;
        HttpURLConnection conn;
        try {
            url = new URL(request);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                InputStream in = conn.getInputStream();
                int len;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                result = os.toString();
                os.flush();
                os.close();
                in.close();
            }
        } catch (IOException e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }
}

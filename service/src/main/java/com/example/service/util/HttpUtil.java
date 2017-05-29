package com.example.service.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    private static final String TAG = HttpUtil.class.getName();

    private static class HttpUtilHolder {
        private static final HttpUtil sInstance = new HttpUtil();
    }

    public static HttpUtil getInstance() {
        return HttpUtilHolder.sInstance;
    }

    public String post(String request, String content) {
        String result = "";
        HttpURLConnection conn = null;
        try {
            URL url = new URL(request);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            out.write(content.getBytes());
            out.flush();
            out.close();

            int responCode = conn.getResponseCode();
            if (responCode == 200) {
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = in.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                in.close();
                result = os.toString();
                os.close();
                return request;
            }
        } catch (Exception e) {
            return result;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    public File get(String request) {
        Bitmap result = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(request);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            out.write(request.getBytes());
            out.flush();
            out.close();

            int responCode = conn.getResponseCode();
            if (responCode == 200) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                InputStream in = conn.getInputStream();
                byte[] buffer = new byte[1204];
                int len = -1;
                while ((len = in.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                in.close();
                os.close();

                String path = Environment.getExternalStorageDirectory().getPath() + "/images";

                File file = new File(path);
                file.mkdir();

                String imagePath = path + "1.png";
                File imageFile = new File(imagePath);

                FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                fileOutputStream.write(os.toByteArray());
                fileOutputStream.close();
                return imageFile;
            }
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
}

package com.example.client.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Qiu on 2018/1/26.
 */

public class CmdAsyncTask extends AsyncTask<Void, Void,Void> {

    private Process mStartRecordProcess;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            mStartRecordProcess = Runtime.getRuntime().exec(
                    "mkdir -p /sdcard/nas");
            InputStream stdin = mStartRecordProcess.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<output></output>");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            Log.d("QIU", "mkdir: " + mStartRecordProcess.toString());
            mStartRecordProcess = Runtime.getRuntime().exec(
                    "busybox mount -t nfs -o nolock 192.168.199.232:/volume1/kmbox /sdcard/nas");
            stdin = mStartRecordProcess.getInputStream();
            isr = new InputStreamReader(stdin);
            br = new BufferedReader(isr);
            line = null;
            System.out.println("<output></output>");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            Log.d("QIU", "mount: " + mStartRecordProcess.toString());
        } catch (IOException e) {
            Log.e("QIU", "Start record with IOException: " + e.getMessage());
        }
        return null;
    }
}

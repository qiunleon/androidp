package com.evideo.kmbox.demo;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.core.common.CoreClient;
import com.example.core.common.IComputeProxy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                CoreClient.getInstance().init(getApplicationContext());
            }
        }).start();

        CoreClient.getInstance().registerServiceFetcher(
                CoreClient.ServiceName.COMPUTE_SERVICE,
                new CoreClient.IServiceFetcher() {
                    @Override
                    public Object createService(IBinder binder) {
                        return IComputeProxy.Stub.asInterface(binder);
                    }
                });
        IComputeProxy proxy = (IComputeProxy) CoreClient.getInstance().getService(CoreClient.ServiceName.COMPUTE_SERVICE);
        try {
            Log.i("", "ComputeProxy: " + proxy.compute());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

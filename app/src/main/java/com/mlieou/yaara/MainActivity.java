package com.mlieou.yaara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mlieou.yaara.aria2RPC.Aria2RpcClient;

public class MainActivity extends AppCompatActivity {

    Aria2RpcClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClient = new Aria2RpcClient();
        new Thread(() -> {
            while (true) {
                mClient.getRawJson();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

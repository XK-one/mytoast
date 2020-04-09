package com.wyk.mytoast;


import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ProxyHandle1 extends Handler {
    private static final String TAG = "ProxyHandle1";

    private Handler mHandler;
    public ProxyHandle1(Handler handler){
        this.mHandler = handler;
    }

    @Override
    public void handleMessage(Message msg) {
        Log.e(TAG,"build VERSION == " + Build.VERSION.SDK_INT);
        try{
            mHandler.handleMessage(msg);
        }catch(WindowManager.BadTokenException e){
            Log.e(TAG, "error: " + e.toString());
        }
        super.handleMessage(msg);
    }

}

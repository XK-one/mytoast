package com.wyk.mytoast;


import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ProxyHandle extends Handler {
    private static final String TAG = "ProxyHandle";
    private Object mTN;
    private static final int SHOW = 0;
    private static final int HIDE = 1;
    private static final int CANCEL = 2;

    public ProxyHandle(Object tn) {
        this.mTN = tn;
    }
    @Override
    public void handleMessage(Message msg) {
        Log.e(TAG,"build VERSION == " + Build.VERSION.SDK_INT);
        switch(msg.what){
            case SHOW:
                try {
                    Method handleShow = mTN.getClass().getDeclaredMethod("handleShow", IBinder.class);
                    if(handleShow == null){
                        Log.w(TAG, "Method " + handleShow + "== null");
                    return;
                }
                try {
                    handleShow.invoke(mTN, msg.obj);
                } catch(IllegalAccessException e) {
                    e.printStackTrace();
                }catch(WindowManager.BadTokenException e){
                    //e.printStackTrace();
                }
        } catch(InvocationTargetException e) {
            e.printStackTrace();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
                }
                break;
            case HIDE:
                cancelOrHideToast();
                break;
            case CANCEL:
                cancelOrHideToast();
                break;
        }
        super.handleMessage(msg);
    }

    private void cancelOrHideToast(){
        try {
            Method handleHide = mTN.getClass().getDeclaredMethod("handleHide");
            if(handleHide == null){
                Log.w(TAG, "Method " + handleHide + "== null");
                return;
            }
            try {
                handleHide.invoke(mTN);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}

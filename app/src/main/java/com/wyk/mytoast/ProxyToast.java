package com.wyk.mytoast;

import android.os.Handler;
import android.widget.Toast;

import java.lang.reflect.Field;

public class ProxyToast {

    public static Toast toast(Toast toast){
        try {
            Field mTNField = toast.getClass().getDeclaredField("mTN");
            mTNField.setAccessible(true);
            Object tn = mTNField.get(toast);
            Field mHandlerField = tn.getClass().getDeclaredField("mHandler");
            mHandlerField.setAccessible(true);
            Handler toastHandle = (Handler)mHandlerField.get(tn);
            mHandlerField.set(tn, new ProxyHandle1(toastHandle));
            //mHandlerField.set(tn, new ProxyHandle(tn));
            return toast;
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        } catch(IllegalAccessException e2){
            e2.printStackTrace();
        }
        return null;
    }
}

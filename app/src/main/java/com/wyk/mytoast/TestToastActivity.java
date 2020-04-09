package com.wyk.mytoast;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 参考 https://tech.meituan.com/2018/03/29/toast-snackbar-replace.html
 */
public class TestToastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.show_toast).setOnClickListener(listener ->{
            //异常：android.view.WindowManager$BadTokenException:
            // Unable to add window -- token android.os.BinderProxy@19e4f6b is not valid; is your activity running?
            /*Toast.makeText(this,"test Toast on Android 7.x versions",Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            int sdkInt = Build.VERSION.SDK_INT;
            boolean isReflectedHandler = false;
            Toast toast = Toast.makeText(this, "test Toast on Android 7.x versions", Toast.LENGTH_LONG);
            if (sdkInt >= Build.VERSION_CODES.N && sdkInt < Build.VERSION_CODES.O && !isReflectedHandler) {
                ProxyToast.toast(toast);
                isReflectedHandler = true;
            }
            toast.show();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

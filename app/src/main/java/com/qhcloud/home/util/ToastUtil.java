package com.qhcloud.home.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil {

    private static Toast mToast;

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }


    public static void show(Context context, CharSequence text, int duration) {
        if (context != null && !TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, duration);
            } else {
                mToast.setDuration(duration);
                mToast.setText(text);
            }
            mToast.show();
        }
    }




}

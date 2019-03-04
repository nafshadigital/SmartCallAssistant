package com.nafshadigital.smartcallassistant.helpers;

/**
 * Created by Malaris on 9/8/2017.
 */

import android.content.Context;
import android.widget.Toast;

public class MyToast extends Toast {

    public MyToast(Context context) {
        super(context);

    }
    // TODO Auto-generated constructor stub
    public static void show(Context context, String message) {
        makeText(context, message, Toast.LENGTH_LONG).show();
    }

}

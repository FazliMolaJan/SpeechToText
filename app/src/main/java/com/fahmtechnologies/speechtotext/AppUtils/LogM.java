package com.fahmtechnologies.speechtotext.AppUtils;

import android.util.Log;

/**
 * Created by Android on 20-Aug-16.
 */
public class LogM {

    public static void Logv(String Message) {
        Log.v("LogV : ", Message);
    }

    public static void Logi(String Message) {
        Log.i("LogI : ", Message);
    }

    public static void Loge(String messege){
        Log.e("LogE",messege);
    }


}

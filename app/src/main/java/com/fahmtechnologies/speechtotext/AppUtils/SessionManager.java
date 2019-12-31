package com.fahmtechnologies.speechtotext.AppUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_FOR_APP = "speech_to_text";


    public SessionManager() {
    }
    public static void clearCredential(Context context) {
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FOR_APP, 0).edit();
            editor.clear();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

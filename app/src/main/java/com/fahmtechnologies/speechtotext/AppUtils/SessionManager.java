package com.fahmtechnologies.speechtotext.AppUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_FOR_APP = "speech_to_text";
    private static final String IS_EMAIL_SAVED = "is_email_saved";


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


    public static void saveEmail(Context context, boolean isSaved) {
        try {
            SharedPreferences prefSignupData = context.getApplicationContext().getSharedPreferences(PREF_FOR_APP, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor;
            editor = prefSignupData.edit();
            editor.putBoolean(IS_EMAIL_SAVED, isSaved);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmailSaved(Context context){
            boolean isLoginViaSocialMedia;
            SharedPreferences sp = context.getApplicationContext().getSharedPreferences(PREF_FOR_APP, Context.MODE_PRIVATE);
            isLoginViaSocialMedia = sp.getBoolean(IS_EMAIL_SAVED, false);
            return isLoginViaSocialMedia;
    }

}

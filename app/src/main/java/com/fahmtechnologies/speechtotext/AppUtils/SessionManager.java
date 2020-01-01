package com.fahmtechnologies.speechtotext.AppUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_FOR_APP = "speech_to_text";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LOCATION_ADDRESS = "location_address";



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

    public static void setLatitute(Context context, String latitute){
        SharedPreferences prefSignupData = context.getApplicationContext().getSharedPreferences(PREF_FOR_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = prefSignupData.edit();
        editor.putString(KEY_LATITUDE , latitute);
        editor.commit();
    }

    public static void setLongitude(Context context, String latitute){
        SharedPreferences prefSignupData = context.getApplicationContext().getSharedPreferences(PREF_FOR_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = prefSignupData.edit();
        editor.putString(KEY_LONGITUDE , latitute);
        editor.commit();
    }

    public static void setLocationAddress(Context context, String latitute){
        SharedPreferences prefSignupData = context.getApplicationContext().getSharedPreferences(PREF_FOR_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = prefSignupData.edit();
        editor.putString(KEY_LOCATION_ADDRESS , latitute);
        editor.commit();
    }


    public static String getLatitude(Context context) {
        String strLatidude= "";
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FOR_APP, Context.MODE_PRIVATE);
            strLatidude = pref.getString(KEY_LATITUDE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strLatidude;
    }

    public static String getLongitude(Context context) {
        String strLongitude = "";
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FOR_APP, Context.MODE_PRIVATE);
            strLongitude  = pref.getString(KEY_LONGITUDE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strLongitude ;
    }

    public static String getLocationAddress(Context context) {
        String strLocationAddress = "";
        try {
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_FOR_APP, Context.MODE_PRIVATE);
            strLocationAddress = pref.getString(KEY_LOCATION_ADDRESS, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strLocationAddress;
    }

}

package com.fahmtechnologies.speechtotext.AppUtils;

public class GlobalData {
    public static final int intFlagShow = 1;
    public static final int intFlagHide = 0;
    public static final String STR_INTERNET_ALERT_TITLE = "Network Error!";
    public static final String APP_NAME = "HOWDY_CROWDY";
    public static final String STR_INETRNET_ALERT_MESSAGE = "Please check your Internet connection.";
    public static final int INT_SPLASH_TIMEOUT = 3000;
    public static final String USER_NOT_EXIST = "User does not exist. Please contact to administrator for more info!";
    public static Boolean isUserBlockedByAdmin = false; // If user block by admin then condition will be true.
    public static Boolean isUserNotExist = false; // if user delete by admin then condition will be true.
    public static final String facebook = "com.facebook.katana",
            twitter = "com.twitter.android",
            googleplus = "com.google.android.apps.plus",
            linkedin = "com.linkedin.android",
            email = "com.google.android.gm";

    public static boolean IS_CALL_FIRE_CHECK_REQUIRED = false;  //todo make call fire disable on PUT_ME_ON_QUEUE button
    public static final String TWITTER_PACKAGE_NAME = "com.twitter.android";
    public static final String CURRENT_PACKAGE_NAME = "com.gohealthnowuser";
    public static boolean isShowAllProvider = false; // TODO: 27-09-2018 In SMD if user comming from Upcomming appoinnet for schedule appointment then show all provider..........by Sakib.
    public static boolean SHOW_ADD_TO_CARD = false; // TODO: 02-10-2018 After payment in Paypal screen, show Add To Card screen in case of user does not schedule service by Sakib START
    public static boolean IS_RECONNECTED_CALL = false; //// TODO: 20-06-2019 If video call is reconnect then we don't need to call patient left API.

    public static final int MIN_HEIGH_IN_FEET = 1;
    public static int MAX_HEIGH_IN_FEET  = 8;
    public static int MIN_HEIGHT_IN_INCHES = 1;
    public static int MAX_HEIGHT_IN_INCHES = 11;
    public static boolean IS_FROM_APPOINTMENT;

    public static String SELECTED_LANG_ID = "selected_lan_id";
    public static final String SELECTED_TEXT = "selected_text";
}

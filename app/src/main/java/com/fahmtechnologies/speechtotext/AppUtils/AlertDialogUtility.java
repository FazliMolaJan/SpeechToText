package com.fahmtechnologies.speechtotext.AppUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.widget.Toast;
import com.fahmtechnologies.speechtotext.R;


public class AlertDialogUtility {

    public static void showToast(Context context, String message) {
        SpannableStringBuilder biggerText = new SpannableStringBuilder(message);
        biggerText.setSpan(new RelativeSizeSpan(1.35f), 0, message.length(), 0);
        Toast.makeText(context, biggerText, Toast.LENGTH_LONG).show();
    }

    public static void showInstallAppAlert(Context context, String message) {
        new AlertDialog.Builder(context)
                .setIcon(0)
                .setTitle(context.getResources().getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(true).setNeutralButton("OK", null).show();
    }


    public static void showInternetAlert(Context context) {
        new AlertDialog.Builder(context).setIcon(0).setTitle(GlobalData.STR_INTERNET_ALERT_TITLE).setMessage(GlobalData.STR_INETRNET_ALERT_MESSAGE)
                .setCancelable(true).setNeutralButton("OK", null).show();
    }

    public static void CustomAlert(Context context, String title, String message, String Positive_text,
                                   String Negative_text,
                                   DialogInterface.OnClickListener PositiveListener,
                                   DialogInterface.OnClickListener NegativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(Positive_text, PositiveListener).setNegativeButton(Negative_text, NegativeListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showConfirmAlert(Context context, String msg, DialogInterface.OnClickListener onYesClick) {
        new AlertDialog.Builder(context).setIcon(0).setTitle(context.getString(R.string.app_name)).setMessage(msg).setCancelable(true).setNegativeButton("NO", null)
                .setPositiveButton("YES", onYesClick).show();
    }

    public static void showLogoutAlert(Context context, String msg, DialogInterface.OnClickListener onYesClick) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name)).setMessage(msg)
                .setCancelable(true).setNegativeButton("Cancel", null)
                .setPositiveButton("Logout", onYesClick).show();
    }

//    public static void showSingleAlert(Context context, String strMessege, DialogInterface.OnClickListener onYesClick) {
//        new AlertDialog.Builder(context).setIcon(0).setMessage(strMessege)
//                .setCancelable(true).setPositiveButton("OK", onYesClick)
//                .show()
//                .getButton(DialogInterface.BUTTON_POSITIVE)
//                .setTextColor(context.getResources().getColor(R.color.people));
//
//    }

//    public static void showUserNotExist(Context context) {
//        new AlertDialog.Builder(context).setIcon(0).setMessage(GlobalData.USER_NOT_EXIST)
//                .setCancelable(true).setPositiveButton("OK", null).show();
//    }

}

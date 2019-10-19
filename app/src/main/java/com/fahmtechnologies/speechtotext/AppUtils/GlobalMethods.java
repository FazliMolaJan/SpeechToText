package com.fahmtechnologies.speechtotext.AppUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.fahmtechnologies.speechtotext.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GlobalMethods {
    public static void CustomAlert(Context context,
                                   String title,
                                   String message,
                                   String Positive_text,
                                   String Negative_text,
                                   DialogInterface.OnClickListener PositiveListener,
                                   DialogInterface.OnClickListener NegativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(Positive_text, PositiveListener).setNegativeButton(Negative_text, NegativeListener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showConfirmAlert(Context context,
                                        String msg,
                                        DialogInterface.OnClickListener onYesClick) {
        new AlertDialog.Builder(context).setIcon(0).setTitle(context.getString(R.string.app_name)).setMessage(msg).setCancelable(true).setNegativeButton("NO", null)
                .setPositiveButton("YES", onYesClick).show();
    }

    public static void writeFiles(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Speech To Text");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved File:-" + "Phone Storege/SpeechToText/" + sFileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showKeyboard(Context context, EditText etEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyBoard(Context context, View view) {
        if(view!= null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}

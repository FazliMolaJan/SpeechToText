package com.fahmtechnologies.speechtotext.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fahmtechnologies.speechtotext.AppUtils.AlertDialogUtility;
import com.fahmtechnologies.speechtotext.AppUtils.ConnectivityDetector;
import com.fahmtechnologies.speechtotext.AppUtils.LogM;

import org.json.JSONObject;

public class PostAPIJsonObject  extends AsyncTask<String, JSONObject, JSONObject> {
    private OnUpdateListener onUpdateListener;
    private Context context;
    private JSONObject jsonObject;
    private int intDialogShow = 0;
    private ProgressDialog progressDialog;
    private String url;

    public PostAPIJsonObject(Context context, JSONObject jsonObject, String url, int intDialogShow, OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
        this.jsonObject = jsonObject;
        this.intDialogShow = intDialogShow;
        this.url = url;
        this.context = context;
        LogM.Logi("request ==>" + jsonObject);
    }

    @Override
    protected void onPreExecute() {
        if (!ConnectivityDetector.isConnectingToInternet(context)) {
            AlertDialogUtility.showInternetAlert(context);
            return;
        }

        progressDialog = new ProgressDialog(context);

        if (intDialogShow == 1) {
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setTitle("");
            if (!((Activity) context).isFinishing()) {
                //show dialog
                progressDialog.show();
            }
        }
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... param) {
        try {
            AndroidNetworking.post(url)
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                LogM.Logi("response ==> " + response);
                                if (response.getBoolean(WebFields.RESPONSE_STATUS)) {
                                    onUpdateListener.onUpdateComplete(response, true);
                                } else {
                                    onUpdateListener.onUpdateComplete(response, false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (intDialogShow == 1) {
                                    progressDialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
//                            AlertDialogUtility.showToast(context, anError.getMessage());
                            progressDialog.dismiss();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonResult) {
        super.onPostExecute(jsonResult);
    }
}
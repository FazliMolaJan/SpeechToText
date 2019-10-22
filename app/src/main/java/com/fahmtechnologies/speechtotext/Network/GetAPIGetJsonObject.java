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

public class GetAPIGetJsonObject extends AsyncTask<String, JSONObject, JSONObject> {
    private OnUpdateListener onUpdateListener;
    private Context context;
    private int intDialogShow = 0;
    private ProgressDialog progressDialog;
    private String url;

    public GetAPIGetJsonObject(Context context, int intDialogShow, String url, OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
        this.context = context;
        this.intDialogShow = intDialogShow;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        if (!ConnectivityDetector.isConnectingToInternet(context)) {
            AlertDialogUtility.showInternetAlert(context);
            return;
        }

        LogM.Logi("GET API URL ==>" + url);

        progressDialog = new ProgressDialog(context);

        if (intDialogShow == 1) {
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setTitle("");
            // TODO: 24-06-2019 isFinishing is used to check if app is killed or not by Sakib
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
            AndroidNetworking.get(url)
                    .setTag("test")
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getInt("code") == 200){
                                    onUpdateListener.onUpdateComplete(response, true);
                                }else {
                                    onUpdateListener.onUpdateComplete(response, false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (intDialogShow == 1) {
                                    // TODO: 24-06-2019 isFinishing is used to check if app is killed or not by Sakib
                                    if (!((Activity) context).isFinishing() && progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            AlertDialogUtility.showToast(context, anError.getMessage());
                            // TODO: 24-06-2019 isFinishing is used to check if app is killed or not by Sakib
                            if (!((Activity) context).isFinishing() && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
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


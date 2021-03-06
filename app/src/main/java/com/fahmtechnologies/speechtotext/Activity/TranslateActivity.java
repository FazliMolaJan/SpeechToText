package com.fahmtechnologies.speechtotext.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fahmtechnologies.speechtotext.Adepter.SpinnerAdapter;
import com.fahmtechnologies.speechtotext.AppUtils.AlertDialogUtility;
import com.fahmtechnologies.speechtotext.AppUtils.GlobalData;
import com.fahmtechnologies.speechtotext.AppUtils.HeaderForActivity;
import com.fahmtechnologies.speechtotext.AppUtils.LogM;
import com.fahmtechnologies.speechtotext.Dao.MainActivityDao;
import com.fahmtechnologies.speechtotext.Model.Languages;
import com.fahmtechnologies.speechtotext.Network.WebFields;
import com.fahmtechnologies.speechtotext.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TranslateActivity extends AppCompatActivity {

    private String mLanguageCodeFrom = "en";
    private String mLanguageCodeTo = "gu";
    private Button btnTranslante;
    private Spinner spinner_language_to;
    private SpinnerAdapter adapterFromLang;
    private ArrayList<Languages> alLanguage;
    private MainActivityDao mainActivityDao;
    private EditText edtToLanguage, edtFromLanguage;
    private HeaderForActivity headerTranslateScreen;
    private int intSelectedLanguage = 0;
    private TextView tvSelectedLang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(TranslateActivity.this);
        setContentView(R.layout.activity_translate);

        getIds();
        setListner();
        setData();
    }

    private void setData() {
        headerTranslateScreen.tvActivityName.setText("Transalor Screen");

        setAllDropDown();

        // TODO: 22-10-2019 Set default dropdown
        intSelectedLanguage = getIntent().getIntExtra(GlobalData.SELECTED_LANG_ID, 0);
        tvSelectedLang.setText(alLanguage.get(intSelectedLanguage).getStrLaguages());
        mLanguageCodeFrom = alLanguage.get(intSelectedLanguage).getStrLangId();

        edtFromLanguage.setText(getIntent().getStringExtra(GlobalData.SELECTED_TEXT));
        setSpinnerSelection();
    }

    private void setAllDropDown() {
        alLanguage = new ArrayList<>();
        mainActivityDao = new MainActivityDao();
        alLanguage.addAll(mainActivityDao.getLanguesForTranslate(TranslateActivity.this));
        adapterFromLang = new SpinnerAdapter(TranslateActivity.this, alLanguage);

        spinner_language_to.setAdapter(adapterFromLang);
    }

    private void setSpinnerSelection() {
        spinner_language_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mLanguageCodeTo = alLanguage.get(position).getStrLangId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setListner() {
        try {
            btnTranslante.setOnClickListener(onClickListener);
//            headerTranslateScreen.tvBack.setOnClickListener(onClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener onClickListener = (v) -> {
        try {
            switch (v.getId()) {
                case R.id.btnTranslante:
                    String textForTranslate = edtFromLanguage.getText().toString().trim();
                    if (textForTranslate.length() > 0) {
                        translate(textForTranslate);
                    } else {
                        AlertDialogUtility.showToast(TranslateActivity.this, "Please enter data");
                    }
                    break;
//                case R.id.headerTranslateScreen:
//                    finish();
//                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private void getIds() {
        try {
            btnTranslante = findViewById(R.id.btnTranslante);
            spinner_language_to = findViewById(R.id.spinner_language_to);
            edtFromLanguage = findViewById(R.id.edtFromLanguage);
            edtToLanguage = findViewById(R.id.edtToLanguage);
            headerTranslateScreen = findViewById(R.id.headerTranslateScreen);
            tvSelectedLang = findViewById(R.id.tvSelectedLang);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void translate(String textForTranslate) {
        Uri baseUri = Uri.parse(WebFields.BASE_REQ_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath("translate")
                .appendQueryParameter("key", WebFields.API_KEY)
                .appendQueryParameter("lang", mLanguageCodeFrom + "-" + mLanguageCodeTo)
                .appendQueryParameter("text", textForTranslate);

        String fullURL = uriBuilder.toString();
        callAPI(fullURL);
    }


    private void callAPI(String url) {
        LogM.Loge("=> Full URL " + url);
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("code") == 200) {
                                JSONArray array = response.getJSONArray("text");
                                String data = array.toString();
                                data = data.replaceAll("[\\p{Ps}\\p{Pe}]", "");
                                edtToLanguage.setText(data);
                            }else {
                                AlertDialogUtility.showToast(TranslateActivity.this,response.getString("message"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        try {
                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            AlertDialogUtility.showToast(TranslateActivity.this, jsonObject.getString("message"));
                            edtToLanguage.setText("");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}

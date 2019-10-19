package com.fahmtechnologies.speechtotext.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fahmtechnologies.speechtotext.Adepter.Adepter;
import com.fahmtechnologies.speechtotext.AppUtils.GlobalMethods;
import com.fahmtechnologies.speechtotext.AppUtils.HeaderForActivity;
import com.fahmtechnologies.speechtotext.AppUtils.SessionManager;
import com.fahmtechnologies.speechtotext.Dao.MainActivityDao;
import com.fahmtechnologies.speechtotext.Model.Languages;
import com.fahmtechnologies.speechtotext.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public static final int REQ_CODE_SPEECH_INPUT = 1;
    private Spinner sprLang;
    private EditText editEmail, edtSpeakData;
    private Button btnLogin;
    private HeaderForActivity tvHeaderForActivity;
    private ImageView image_Share,image_save,ivStartSpeak;
    private ArrayList<Languages> alLang;
    private Adapter langAdepter;
    private TextView tvCloseEmail, tvQuestMark, tvDoubleQuote, tvSingleQuote, tvFullStop, tvComma;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", Filename, inputText, strEmail;
    private int intCursonPosition = 0, intSpinnerPosition = 0;
    private AlertDialog builder;
    private static final int RC_WRING_STORAGE = 123;
    private String[] allPermissionArray = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    private boolean isSingleQuoteStart, isDoubleQuoteStart;
    private String WHITE_SPACE = " ";
    private MainActivityDao mainActivityDao;

    private RelativeLayout rlCopyToClipBoard,rlBackspace,rlEnter,rlClearText,rlMoveBack,rlMoveForword;


    // TODO: 04-10-2019 Delete character related stuff by Sakib
    private Handler handler;
    private Runnable runnable;
    private int DELETE_CHARACTER_INTERVAL = 100; //miliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callAllPermission();
        getId();
        setClickListerner();
        setData();
    }

    @AfterPermissionGranted(RC_WRING_STORAGE)
    private void callAllPermission() {
        if (!hasWringStoragePermission()) {
            EasyPermissions.requestPermissions(
                    this, "This is required permission...",
                    RC_WRING_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO);
        }
    }

    private void setData() {
        mainActivityDao = new MainActivityDao();
        setInputtypeAdepter();
        setEmailDialog();
        if (!SessionManager.isEmailSaved(MainActivity.this)) {
            builder.show();
        }
    }

    private View.OnClickListener clickListener = (v) -> {
        try {
            GlobalMethods.hideKeyBoard(MainActivity.this,v);
            switch (v.getId()) {
                case R.id.rlCopyToClipBoard:
                    inputText = edtSpeakData.getText().toString();
                    if (!inputText.equals("")) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("text", inputText);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(MainActivity.this, "Copied to clipboard.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.rlClearText:
                    GlobalMethods.CustomAlert(MainActivity.this, "Speech To Text Demo", "Are you sure you want to clear all data?",
                            "yes", "No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    edtSpeakData.getText().clear();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    break;
                case R.id.image_save:
                    if (edtSpeakData.getText().toString().length() <= 0) {
                        Toast.makeText(MainActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
                    } else {
                        Calendar cal = Calendar.getInstance();
                        Date date = cal.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                        Filename = dateFormat.format(date) + ".txt";
                        inputText = edtSpeakData.getText().toString();
                        GlobalMethods.writeFiles(MainActivity.this, Filename, inputText);
                    }
                    break;
                case R.id.image_Share:
                    if (edtSpeakData.getText().toString().length() <= 0) {
                        Toast.makeText(MainActivity.this, " Please Enter text", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent shareText = new Intent();
                        shareText.setAction(Intent.ACTION_SEND);
                        shareText.putExtra(Intent.EXTRA_TEXT, edtSpeakData.getText().toString());
                        shareText.setType("text/plain");
                        startActivity(shareText);
                    }
                    break;
                case R.id.ivStartSpeak:
                    getCursorPosition();
                    mainActivityDao.startSpeak(MainActivity.this, intSpinnerPosition);
                    break;
                case R.id.btnLogin:
                    strEmail = editEmail.getText().toString().trim();
                    if (strEmail.matches(emailPattern)) {
                        builder.dismiss();
                        SessionManager.saveEmail(MainActivity.this, true);
                    } else {
                        Toast.makeText(MainActivity.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.tvCloseEmail:
                    builder.dismiss();
                    finish();
                    break;
                case R.id.rlEnter:
                    edtSpeakData.append("\n");
                    break;
                case R.id.tvQuestMark:
                    getCursorPosition();
                    edtSpeakData.getText().insert(intCursonPosition, getResources().getString(R.string.question_mark) + WHITE_SPACE);
                    break;
                case R.id.tvDoubleQuote:
                    setDoubleQuote();
                    break;
                case R.id.tvSingleQuote:
                    setSingleQuote();
                    break;
                case R.id.tvFullStop:
                    setFullStop();
                    break;
                case R.id.tvComma:
                    setComma();
                    break;
                case R.id.rlMoveBack:
                    getCursorPosition();
                    if(intCursonPosition > 0){
                        edtSpeakData.setSelection(intCursonPosition - 1);
                    }
                    break;
                case R.id.rlMoveForword:
                    getCursorPosition();
                    int edttextIndex = edtSpeakData.getText().toString().length();
                    if(intCursonPosition < edttextIndex ){
                        edtSpeakData.setSelection(intCursonPosition + 1);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private void setBackSpace() {
        getCursorPosition();
        if (intCursonPosition > 0) {
            edtSpeakData.getText().delete(intCursonPosition - 1, intCursonPosition);
        }
    }

    private void setDoubleQuote() {
        getCursorPosition();
        if (isDoubleQuoteStart) {
            isDoubleQuoteStart = false;
        } else {
            isDoubleQuoteStart = true;
        }
        if (isDoubleQuoteStart) {
            String startWord = edtSpeakData.getText().toString().substring(intCursonPosition - 1, intCursonPosition);
            boolean isStartWtihSpace = startWord.matches("^\\s*$");
            if (isStartWtihSpace) {
                edtSpeakData.getText().insert(intCursonPosition, getResources().getString(R.string.double_quote));
            } else {
                edtSpeakData.getText().insert(intCursonPosition, WHITE_SPACE + getResources().getString(R.string.double_quote));
            }
        } else {
            if (intCursonPosition < edtSpeakData.getText().toString().length()) {
                String endWord = edtSpeakData.getText().toString().substring(intCursonPosition, intCursonPosition + 1);
                boolean isEndWtihSpace = endWord.matches("^\\s*$");
                if (isEndWtihSpace) {
                    edtSpeakData.getText().insert(intCursonPosition, getResources().getString(R.string.double_quote));
                }
            } else {
                edtSpeakData.getText().insert(intCursonPosition, getResources().getString(R.string.double_quote) + WHITE_SPACE);
            }
        }
    }

    private void setSingleQuote() {
        getCursorPosition();
        if (isSingleQuoteStart) {
            isSingleQuoteStart = false;
        } else {
            isSingleQuoteStart = true;
        }
        if (isSingleQuoteStart) {
            String startWord = edtSpeakData.getText().toString().substring(intCursonPosition - 1, intCursonPosition);
            boolean isStartWtihSpace = startWord.matches("^\\s*$");
            if (isStartWtihSpace) {
                edtSpeakData.getText().insert(intCursonPosition, getResources().getString(R.string.single_quote));
            } else {
                edtSpeakData.getText().insert(intCursonPosition, WHITE_SPACE + getResources().getString(R.string.single_quote));
            }
        } else {
            if (intCursonPosition < edtSpeakData.getText().toString().length()) {
                String endWord = edtSpeakData.getText().toString().substring(intCursonPosition, intCursonPosition + 1);
                boolean isEndWtihSpace = endWord.matches("^\\s*$");
                if (isEndWtihSpace) {
                    edtSpeakData.getText().insert(intCursonPosition, getResources().getString(R.string.single_quote));
                }
            } else {
                edtSpeakData.getText().insert(intCursonPosition, getResources().getString(R.string.single_quote) + WHITE_SPACE);
            }
        }
    }

    private void setFullStop() {
        getCursorPosition();
        String setFullStop = edtSpeakData.getText().toString().substring(intCursonPosition - 1, intCursonPosition);
        boolean isWhitespace = setFullStop.matches("^\\s*$");
        if (isWhitespace) {
            edtSpeakData.getText().insert(intCursonPosition - 1, getResources().getString(R.string.full_stop));
        } else {
            edtSpeakData.getText().insert(intCursonPosition, getResources().getString(R.string.full_stop));
        }
    }

    private void setComma() {
        getCursorPosition();
        String setComma = edtSpeakData.getText().toString().substring(intCursonPosition - 1, intCursonPosition);
        boolean isWhitespace = setComma.matches("^\\s*$");
        if (isWhitespace) {
            edtSpeakData.getText().insert(intCursonPosition - 1, getResources().getString(R.string.comma) + WHITE_SPACE);
        } else {
            edtSpeakData.getText().insert(intCursonPosition, getResources().getString(R.string.comma) + WHITE_SPACE);
        }
    }

    private void setClickListerner() {
        try {
            rlCopyToClipBoard.setOnClickListener(clickListener);
            rlClearText.setOnClickListener(clickListener);
            image_save.setOnClickListener(clickListener);
            image_Share.setOnClickListener(clickListener);
            ivStartSpeak.setOnClickListener(clickListener);
            rlEnter.setOnClickListener(clickListener);
            tvQuestMark.setOnClickListener(clickListener);
            tvDoubleQuote.setOnClickListener(clickListener);
            tvSingleQuote.setOnClickListener(clickListener);
            tvFullStop.setOnClickListener(clickListener);
            tvComma.setOnClickListener(clickListener);
            rlBackspace.setOnTouchListener(onTouchListener);
            rlMoveBack.setOnClickListener(clickListener);
            rlMoveForword.setOnClickListener(clickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener onTouchListener = (v, e) -> {
        try {
            switch (v.getId()) {
                case R.id.rlBackspace:
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
                        handler = new Handler(getMainLooper());
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                handler.postDelayed(runnable, DELETE_CHARACTER_INTERVAL);
                                setBackSpace();
                            }
                        };
                        handler.postDelayed(runnable, DELETE_CHARACTER_INTERVAL);
                    } else if (e.getAction() == MotionEvent.ACTION_UP) {
                        handler.removeCallbacks(runnable);
                    }
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void setEmailDialog() {
        builder = new AlertDialog.Builder(MainActivity.this).create();
        builder.setCancelable(false);
        builder.setCanceledOnTouchOutside(false);
        View view = getLayoutInflater().inflate(R.layout.login_dialog, null);
        //Get Ids
        editEmail = view.findViewById(R.id.edtEmail);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvCloseEmail = view.findViewById(R.id.tvCloseEmail);
        //Set Listner
        btnLogin.setOnClickListener(clickListener);
        tvCloseEmail.setOnClickListener(clickListener);
        builder.setView(view);
    }

    private void getId() {
        try {
            image_save = findViewById(R.id.image_save);
            rlClearText = findViewById(R.id.rlClearText);
            rlCopyToClipBoard = findViewById(R.id.rlCopyToClipBoard);
            tvHeaderForActivity = findViewById(R.id.tvHeaderForActivity);
            ivStartSpeak = findViewById(R.id.ivStartSpeak);
            edtSpeakData = findViewById(R.id.edtSpeakData);
            sprLang = findViewById(R.id.sprLang);
            image_Share = findViewById(R.id.image_Share);
            rlEnter = findViewById(R.id.rlEnter);
            tvQuestMark = findViewById(R.id.tvQuestMark);
            tvDoubleQuote = findViewById(R.id.tvDoubleQuote);
            tvSingleQuote = findViewById(R.id.tvSingleQuote);
            tvFullStop = findViewById(R.id.tvFullStop);
            tvComma = findViewById(R.id.tvComma);
            rlBackspace = findViewById(R.id.rlBackspace);
            rlMoveBack = findViewById(R.id.rlMoveBack);
            rlMoveForword = findViewById(R.id.rlMoveForword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInputtypeAdepter() {
        alLang = new ArrayList<>();
        alLang.addAll(mainActivityDao.setLanguageArray(MainActivity.this));

        langAdepter = new Adepter(MainActivity.this, alLang);
        sprLang.setAdapter((SpinnerAdapter) langAdepter);

        sprLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    intSpinnerPosition = i;
                    if (alLang.get(i).getStrLaguages().equalsIgnoreCase("Gujarati")) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.app_name));
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase("Hindi")) {
                        tvHeaderForActivity.tvActivityName.setText("Speech to Text (बोल से लिखाई)");
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase("Bengali")) {
                        tvHeaderForActivity.tvActivityName.setText("Speech to Text (বক্তৃতা থেকে লিখিত)");
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase("English")) {
                        tvHeaderForActivity.tvActivityName.setText("Speech to Text");
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase("Arabic")) {
                        tvHeaderForActivity.tvActivityName.setText("Speech to Text (تحويل الكلام إلى نص)");
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase("Persian")) {
                        tvHeaderForActivity.tvActivityName.setText("Speech to Text (نوشته شده از سخنرانی)");
                    } else {
                        tvHeaderForActivity.tvActivityName.setText("Speech to Text (تقرر سے لکھائی )");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCursorPosition() {
        intCursonPosition = edtSpeakData.getSelectionStart();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT:
                if (resultCode == RESULT_OK && null != data) {
                    List<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (intCursonPosition != 0) {
                        String fullString = edtSpeakData.getText().toString();
                        int intFullStringLength = fullString.length();
//                        Log.e("=> ", " intFullStringLength  " + intFullStringLength + " intCursonPosition " + intCursonPosition);
                        String startString = edtSpeakData.getText().toString().substring(intCursonPosition - 1, intCursonPosition);
                        boolean isWhitespace = startString.matches("^\\s*$");
                        if (isWhitespace) {
                            String strNewString = result.get(0) + WHITE_SPACE; // Adding white space after text
                            edtSpeakData.getText().insert(intCursonPosition, strNewString);
                        } else {
                            if (intCursonPosition < intFullStringLength) {
                                String strNewString = WHITE_SPACE + result.get(0);
                                edtSpeakData.getText().insert(intCursonPosition, strNewString);
                            } else {
                                String strNewString = WHITE_SPACE + result.get(0) + WHITE_SPACE;
                                edtSpeakData.getText().insert(intCursonPosition, strNewString);
                            }
                        }
                    } else {
                        edtSpeakData.getText().insert(intCursonPosition, result.get(0));
                    }
                }
                break;
        }
    }

    public void onBackPressed() {
        GlobalMethods.CustomAlert(MainActivity.this, "Speech To Text Demo", "Are you sure you want to exit?",
                "yes", "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    private boolean hasWringStoragePermission() {
        return EasyPermissions.hasPermissions(this, allPermissionArray);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.e("=>", "Permission granted " + requestCode);

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.e("=>", "Permission denide " + requestCode);
    }
}
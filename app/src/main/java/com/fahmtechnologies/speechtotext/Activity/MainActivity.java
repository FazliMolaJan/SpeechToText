package com.fahmtechnologies.speechtotext.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fahmtechnologies.speechtotext.Adepter.SpinnerAdapter;
import com.fahmtechnologies.speechtotext.AppUtils.GlobalData;
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
import java.util.Locale;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public static final int REQ_CODE_SPEECH_INPUT = 1;
    private Spinner sprLang;
    private EditText editEmail, edtSpeakData;
    private Button btnLogin, btnTextTranslate;
    private HeaderForActivity tvHeaderForActivity;
    private ImageView image_Share, image_save, ivStartSpeak;
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
    private RelativeLayout rlCopyToClipBoard, rlBackspace, rlEnter, rlClearText,rlTextToSpeech,rlWhatsAppShare;
    private TextToSpeech textToSpeech;

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
        setTextToSpeech();
        mainActivityDao = new MainActivityDao();
        setInputtypeAdepter();
        setEmailDialog();
        if (!SessionManager.isEmailSaved(MainActivity.this)) {
            builder.show();
        }
    }

    private void setTextToSpeech() {
        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    switch (alLang.get(intSpinnerPosition).getStrLaguages()) {
                        case "Urdu":
                            textToSpeech.setLanguage(new Locale("urd"));
                            break;
                        case "Gujarati":
                            textToSpeech.setLanguage(new Locale("gu"));
                            break;
                        case "Hindi":
                            textToSpeech.setLanguage(new Locale("hi"));
                            break;
                        case "Bengali":
                            textToSpeech.setLanguage(new Locale("bn"));
                            break;
                        case "English":
                            textToSpeech.setLanguage(Locale.US);
                            break;
                        case "French":
                            textToSpeech.setLanguage(Locale.FRENCH);
                            break;
                        case "Arabic":
                            textToSpeech.setLanguage(new Locale("ar"));
                            break;
                        case "Persian":
                            textToSpeech.setLanguage(new Locale("fa"));
                            break;
                        default:
                            textToSpeech.setLanguage(Locale.US);
                            break;
                    }
                }
            }
        });
    }

    private View.OnClickListener clickListener = (v) -> {
        try {
            GlobalMethods.hideKeyBoard(MainActivity.this, v);
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
                    GlobalMethods.CustomAlert(MainActivity.this, "Speech To Text", "Are you sure you want to clear all data?",
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
                        Toast.makeText(MainActivity.this, "Please enter text", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this, " Please enter text", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
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
//                case R.id.rlMoveBack:
//                    getCursorPosition();
//                    if (intCursonPosition > 0) {
//                        edtSpeakData.setSelection(intCursonPosition - 1);
//                    }
//                    break;
//                case R.id.rlMoveForword:
//                    getCursorPosition();
//                    int edttextIndex = edtSpeakData.getText().toString().length();
//                    if (intCursonPosition < edttextIndex) {
//                        edtSpeakData.setSelection(intCursonPosition + 1);
//                    }
//                    break;
                case R.id.btnTextTranslate:
                    if (edtSpeakData.getText().toString().trim().equalsIgnoreCase("")) {
                        GlobalMethods.showToast(MainActivity.this, getResources().getString(R.string.please_enter_value));
                    } else {
                        Intent intent = new Intent(MainActivity.this, TranslateActivity.class);
                        intent.putExtra(GlobalData.SELECTED_LANG_ID, intSpinnerPosition);
                        intent.putExtra(GlobalData.SELECTED_TEXT, edtSpeakData.getText().toString().trim());
                        startActivity(intent);
                    }
                    break;
                case R.id.rlTextToSpeech:
                    GlobalMethods.showToast(MainActivity.this,"Coming soon");
                    textToSpeech.speak(edtSpeakData.getText().toString().trim(), TextToSpeech.QUEUE_FLUSH, null);
                    setTextToSpeech();
                    break;
                case R.id.rlWhatsAppShare:
                    shareViaWhatsApp();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void shareViaWhatsApp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, edtSpeakData.getText().toString().trim());
        try {
            Objects.requireNonNull(MainActivity.this).startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
        }
    }

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
            btnTextTranslate.setOnClickListener(clickListener);
            rlTextToSpeech.setOnClickListener(clickListener);
            rlWhatsAppShare.setOnClickListener(clickListener);
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
            btnTextTranslate = findViewById(R.id.btnTextTranslate);
            rlTextToSpeech = findViewById(R.id.rlTextToSpeech);
            rlWhatsAppShare = findViewById(R.id.rlWhatsAppShare);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInputtypeAdepter() {
        alLang = new ArrayList<>();
        alLang.addAll(mainActivityDao.setLanguageArray(MainActivity.this));

        langAdepter = new SpinnerAdapter(MainActivity.this, alLang);
        sprLang.setAdapter((android.widget.SpinnerAdapter) langAdepter);

        sprLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    intSpinnerPosition = i;
                    if (alLang.get(i).getStrLaguages().equalsIgnoreCase(getResources().getString(R.string.gujarati))) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.gujarati));
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase(getResources().getString(R.string.hindi))) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.speed_text_to_hindi));
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase(getResources().getString(R.string.bengali))) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.speed_text_to_bengali));
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase(getResources().getString(R.string.marathi))) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.speed_text_to_bengali));
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase(getResources().getString(R.string.kannada))) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.speed_text_to_kannda));
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase(getResources().getString(R.string.english))) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.speed_text_to_english));
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase(getResources().getString(R.string.french))) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.speed_text_to_french));
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase(getResources().getString(R.string.arabic))) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.speed_text_to_arebic));
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase(getResources().getString(R.string.persian))) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.speed_text_to_persion));
                    } else if (alLang.get(i).getStrLaguages().equalsIgnoreCase(getResources().getString(R.string.urdu))) {
                        tvHeaderForActivity.tvActivityName.setText(getResources().getString(R.string.speed_text_to_urdu));
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

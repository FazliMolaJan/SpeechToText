package com.fahmtechnologies.speechtotext.Dao;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.fahmtechnologies.speechtotext.Activity.MainActivity;
import com.fahmtechnologies.speechtotext.Model.Languages;
import com.fahmtechnologies.speechtotext.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivityDao {

    private ArrayList<Languages> alLang;
    private Languages langModel;

    public List<Languages> setLanguageArray(Context context){
        alLang = new ArrayList<>();

        langModel = new Languages();
        langModel.setStrLaguages(context.getString(R.string.gujarati));
        alLang.add(langModel);

        langModel = new Languages();
        langModel.setStrLaguages(context.getString(R.string.hindi));
        alLang.add(langModel);

        langModel = new Languages();
        langModel.setStrLaguages(context.getString(R.string.bengali));
        alLang.add(langModel);

        langModel = new Languages();
        langModel.setStrLaguages(context.getString(R.string.english));
        alLang.add(langModel);

        langModel = new Languages();
        langModel.setStrLaguages(context.getString(R.string.arabic));
        alLang.add(langModel);

        langModel = new Languages();
        langModel.setStrLaguages(context.getString(R.string.persian));
        alLang.add(langModel);

        langModel = new Languages();
        langModel.setStrLaguages(context.getString(R.string.urdu));
        alLang.add(langModel);

        return alLang;
    }


    public void startSpeak(final Context context, int intSpinnerPosition){
        if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase("Gujarati")) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "gu_IN");
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"gu_IN"});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase("Hindi")) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi_IN");
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"hi_IN"});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase("Bengali")) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "bn_IN");
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"bn_IN"});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase("English")) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase("Arabic")) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar");
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"ar"});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase("Persian")) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa_IR");
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"fa_IR"});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ur_IN");
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"ur_IN"});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

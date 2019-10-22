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

    public List<Languages> setLanguageArray(Context context){
        alLang = new ArrayList<>();

        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_gujarati),
                context.getResources().getString(R.string.gujarati)
                ));

        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_hind),
                context.getResources().getString(R.string.hindi)
        ));

        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_bengali),
                context.getResources().getString(R.string.bengali)
        ));

        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_english),
                context.getResources().getString(R.string.english)
        ));

        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_arebic),
                context.getResources().getString(R.string.arabic)
        ));

        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_persian),
                context.getResources().getString(R.string.persian)
        ));

        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_urdu),
                context.getResources().getString(R.string.urdu)
        ));

        return alLang;
    }


    public void startSpeak(final Context context, int intSpinnerPosition){
        if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.gujarati))) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "gu_IN");
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{context.getResources().getString(R.string.lang_id_gujarati)});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.hindi))) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, context.getResources().getString(R.string.lang_id_hind));
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{context.getResources().getString(R.string.lang_id_hind)});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.bengali))) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, context.getResources().getString(R.string.lang_id_bengali));
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{context.getResources().getString(R.string.lang_id_bengali)});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.english))) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, context.getResources().getString(R.string.lang_id_english));
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{context.getResources().getString(R.string.lang_id_english)});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.arabic))) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, context.getResources().getString(R.string.lang_id_arebic));
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{context.getResources().getString(R.string.lang_id_arebic)});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.persian))) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, context.getResources().getString(R.string.lang_id_persian));
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{context.getResources().getString(R.string.lang_id_persian)});
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000000);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getString(R.string.speech_prompt));
            try {
                ((Activity) context).startActivityForResult(intent, MainActivity.REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(context, context.getString(R.string.language_not_supported), Toast.LENGTH_SHORT).show();
            }
        } else if(alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.urdu))){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, context.getResources().getString(R.string.lang_id_urdu));
            intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{context.getResources().getString(R.string.lang_id_urdu)});
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

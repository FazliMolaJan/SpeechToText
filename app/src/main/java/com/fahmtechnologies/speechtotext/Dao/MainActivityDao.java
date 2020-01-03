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

    public List<Languages> getLanguesForSpeech(Context context){
        alLang = new ArrayList<>();
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_gujarati_india),context.getResources().getString(R.string.gujarati) ));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_hind), context.getResources().getString(R.string.hindi) ));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_english), context.getResources().getString(R.string.english)));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_bengali),context.getResources().getString(R.string.bengali) ));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_french), context.getResources().getString(R.string.french)));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_Swahili), context.getResources().getString(R.string.swahili)));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_arebic), context.getResources().getString(R.string.arabic)));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_persian), context.getResources().getString(R.string.persian)));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_urdu_india), context.getResources().getString(R.string.urdu)));
        return alLang;
    }

    public List<Languages> getLanguesForTranslate(Context context){
        alLang = new ArrayList<>();
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_gujarati),context.getResources().getString(R.string.gujarati) ));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_hind), context.getResources().getString(R.string.hindi) ));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_english), context.getResources().getString(R.string.english)));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_bengali),context.getResources().getString(R.string.bengali) ));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_french), context.getResources().getString(R.string.french)));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_Swahili), context.getResources().getString(R.string.swahili)));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_arebic), context.getResources().getString(R.string.arabic)));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_persian), context.getResources().getString(R.string.persian)));
        alLang.add(new Languages(context.getResources().getString(R.string.lang_id_urdu_), context.getResources().getString(R.string.urdu)));
        return alLang;
    }


    public void startSpeak(final Context context, int intSpinnerPosition){
        if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.gujarati))) {
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_gujarati_india),context);
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.hindi))) {
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_hind),context);
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.bengali))) {
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_bengali),context);
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.marathi))) {
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_marathi),context);
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.kannada))) {
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_kannad),context);
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.english))) {
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_english),context);
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.french))) {
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_french),context);
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.arabic))) {
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_arebic),context);
        } else if (alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.persian))) {
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_persian),context);
        } else if(alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.urdu))){
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_urdu_india),context);
        } else if(alLang.get(intSpinnerPosition).getStrLaguages().equalsIgnoreCase(context.getResources().getString(R.string.swahili))){
            setSpeechToTextIntent(context.getResources().getString(R.string.lang_id_Swahili),context);
        }
    }

    private void setSpeechToTextIntent(String languageId, Context context) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageId);
        intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{context.getResources().getString(R.string.lang_id_gujarati)});
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

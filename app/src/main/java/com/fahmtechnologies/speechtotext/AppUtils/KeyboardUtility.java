package com.fahmtechnologies.speechtotext.AppUtils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtility {

	public static void ShowKeyboard(Context context, EditText etEditText) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(etEditText, InputMethodManager.SHOW_IMPLICIT);
	}

	public static void HideKeyboard(Context context, View view) {
		if(view!= null) {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
}

package com.fahmtechnologies.speechtotext.Network;

import org.json.JSONObject;

public interface OnUpdateListener {

	void onUpdateComplete(JSONObject jsonObject, boolean isSuccess);
}

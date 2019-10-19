package com.fahmtechnologies.speechtotext.AppUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fahmtechnologies.speechtotext.R;

/**
 * Created by Dileep on 29-Mar-18.
 */

public class HeaderForActivity extends RelativeLayout {

    public TextView tvBack, tvActivityName;
    private Context context;

    public HeaderForActivity(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HeaderForActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.header_for_activity, this, true);


            tvActivityName = (TextView) findViewById(R.id.tvActivityName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

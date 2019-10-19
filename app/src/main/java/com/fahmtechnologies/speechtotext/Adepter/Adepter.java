package com.fahmtechnologies.speechtotext.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fahmtechnologies.speechtotext.R;
import com.fahmtechnologies.speechtotext.Activity.MainActivity;
import com.fahmtechnologies.speechtotext.Model.Languages;


import java.util.ArrayList;

public class Adepter extends BaseAdapter {
    private ArrayList<Languages> alLang;
    private MainActivity context;
    private LayoutInflater inflater;

    public Adepter(MainActivity mainActivity, ArrayList<Languages> alLang) {
        context = mainActivity;
        this.alLang = alLang;
    }


    @Override
    public int getCount() {
        return alLang.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder {
        private TextView tvlang;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView = null;
        try {
            Holder holder;
            myView = convertView;

            if (myView == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.spinner_layout, null);
                holder = new Holder();
                holder.tvlang = (TextView) myView.findViewById(R.id.tvlang);
                myView.setTag(holder);
            } else {
                holder = (Holder) myView.getTag();
            }

            holder.tvlang.setText(alLang.get(position).getStrLaguages());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return myView;
    }
}



package com.example.sasha.finalsoftware.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.sasha.finalsoftware.R;
import com.example.sasha.finalsoftware.data.Name;
import com.google.api.Distribution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class MyNamesActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private List<String> retrieved;

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_names);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        retrieved = new ArrayList<>(Objects.requireNonNull(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getStringSet("names", new HashSet<String>())));
        Log.w("IN ON CREATE", retrieved.toString());
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Button graphButton = findViewById(R.id.graphButton);
        graphButton.setOnClickListener(e -> {
            Intent myIntent = new Intent(getApplicationContext(), GraphActivity.class);
            startActivity(myIntent);
        });

        final LinearLayout nameLayout = findViewById(R.id.nameLayout);
        refresh();
        ArrayList<String> removeList = new ArrayList<>();
//        String remove;
        Button unsaveButton = findViewById(R.id.unsaveButton);
        unsaveButton.setOnClickListener(v -> {
            if (nameLayout.getChildCount() > 0 && nameLayout.getChildAt(0).getClass() != Button.class) {
                for (int i = 0; i < nameLayout.getChildCount(); i++) {
                    CheckBox temp = ((CheckBox) (nameLayout.getChildAt(i)));
                    if (temp.isChecked()) {
                        String remove = temp.getText().toString();
                        removeList.add(remove);
                    }
                }
                String remove2;
                for (int i = 0; i < retrieved.size(); i++) {
                    remove2 = retrieved.get(i);
                    if (removeList.contains(remove2)) {
                        retrieved.remove(i);
                        i--;
                    }
                }
                SharedPreferences.Editor edit = mPrefs.edit();
                edit.putStringSet("names", new HashSet<String>(retrieved));
                edit.commit();
                while (nameLayout.getChildCount() - retrieved.size() > 1) {
                    refresh();
                }
            }
        });
    }

    private void refresh() {
        final LinearLayout nameLayout = findViewById(R.id.nameLayout);
        nameLayout.removeAllViews();
        if (retrieved.size() > 0) {
            for (int i = 0; i < retrieved.size(); i++) {
                final CheckBox rowCheckBox = new CheckBox(this);
                rowCheckBox.setText(retrieved.get(i));
                rowCheckBox.setTextSize(36);
                nameLayout.addView(rowCheckBox);
            }
        } else {
            Button getButton = new Button(getApplicationContext());
            getButton.setText("Find Names");
            getButton.setOnClickListener(e -> {
                Intent myIntent = new Intent(getApplicationContext(), NamesListedActivity.class);
                startActivity(myIntent);
            });
            nameLayout.addView(getButton);
        }
    }
}
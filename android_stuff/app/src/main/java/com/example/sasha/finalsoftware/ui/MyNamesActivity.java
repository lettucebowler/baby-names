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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class MyNamesActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private List<Name> retrieved;

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_names);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        retrieved = getNames();
        Log.w("IN ON CREATE", retrieved.toString());
        Button graphButton = findViewById(R.id.graphButton);
        graphButton.setOnClickListener(e -> {
            Intent myIntent = new Intent(getApplicationContext(), GraphActivity.class);
            startActivity(myIntent);
        });

        final LinearLayout nameLayout = findViewById(R.id.nameLayout);
        refresh();
//        ArrayList<Name> removeList = new ArrayList<>();
//        String remove;
        Button unsaveButton = findViewById(R.id.unsaveButton);
        unsaveButton.setOnClickListener(v -> {
            if (nameLayout.getChildCount() > 0 && nameLayout.getChildAt(0).getClass() != Button.class) {
                for (int i = 0; i < nameLayout.getChildCount(); i++) {
                    CheckBox temp = ((CheckBox) (nameLayout.getChildAt(i)));
                    if (temp.isChecked()) {
                        String remove = temp.getText().toString();
                        for(int h = 0; h < retrieved.size(); h++) {
                            Name tempName = retrieved.get(h);
                            if(tempName.getName().equals(remove)) {
                                retrieved.remove(h);
                            }
                        }
                    }
                }
                SharedPreferences.Editor editor = mPrefs.edit();
                Set<String> set = new HashSet<>();
                for (int i = 0; i < retrieved.size(); i++) {
                    set.add(retrieved.get(i).getJSONObject().toString());
                }
                editor.putStringSet("saveNames", set);
                editor.commit();

            }
            refresh();
        });
    }

    private void refresh() {
        final LinearLayout nameLayout = findViewById(R.id.nameLayout);
        nameLayout.removeAllViews();
        if (retrieved.size() > 0) {
            for (int i = 0; i < retrieved.size(); i++) {
                final CheckBox rowCheckBox = new CheckBox(this);
                rowCheckBox.setText(retrieved.get(i).getName());
                rowCheckBox.setTextSize(36);
                nameLayout.addView(rowCheckBox);
            }
        } else {
            Button getButton = new Button(getApplicationContext());
            getButton.setText("Find Names");
            getButton.setOnClickListener(e -> {
                ArrayList<Name> sendList = new ArrayList<>();
                Intent myIntent = new Intent(getApplicationContext(), NamesListedActivity.class);
                startActivity(myIntent);
            });
            nameLayout.addView(getButton);
        }
    }

    public ArrayList<Name> getNames() {
//        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        ArrayList<Name> items = new ArrayList<>();
        Set<String> set = mPrefs.getStringSet("saveNames", new HashSet<>());
//        if (set != null) {
//            System.out.println("not null");
            for (String s : set) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    System.out.println("json " + jsonObject.toString());
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String sex = jsonObject.getString("sex");
                    ArrayList<Double> popularity = new ArrayList<>();

                    JSONArray jArray = jsonObject.getJSONArray("popularity");
                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            popularity.add(jArray.getDouble(i));

                        }
                    }
                    Name tName = new Name(name, sex, id, popularity);
                    System.out.println(tName.getName());
                    items.add(tName);
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
//        }
        return items;
    }
}
package com.example.sasha.finalsoftware.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.sasha.finalsoftware.R;
import com.example.sasha.finalsoftware.data.Name;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyNamesActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private List<Name> retrieved;
    private String gender;
    private LinearLayout nameLayout;

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
        nameLayout = findViewById(R.id.nameLayout);
        gender = "boy";

        Button graphButton = findViewById(R.id.graphButton);
        graphButton.setOnClickListener(e -> {
            Intent myIntent = new Intent(getApplicationContext(), GraphActivity.class);
            myIntent.putExtra("names", getSelected());
            startActivity(myIntent);
        });
        refresh();

        Button unsaveButton = findViewById(R.id.unsaveButton);
        unsaveButton.setOnClickListener(v -> unsave());

        Button boyButton = findViewById(R.id.boyButton);
        boyButton.setOnClickListener(boy -> {
            gender = "boy";
            refresh();
        });

        Button girlButton = findViewById(R.id.girlButton);
        girlButton.setOnClickListener(girl -> {
            gender = "girl";
            refresh();
        });
    }

    private void refresh() {
        nameLayout.removeAllViews();
        if (retrieved.size() > 0) {
            for (int i = 0; i < retrieved.size(); i++) {
                final CheckBox rowCheckBox = new CheckBox(this);
                final Name curName = retrieved.get(i);
                if (curName.getSex().equals(gender)) {
                    rowCheckBox.setText(curName.getName());
                    rowCheckBox.setTextSize(36);
                    nameLayout.addView(rowCheckBox);
                }
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

    public ArrayList<Name> getNames() {
        ArrayList<Name> items = new ArrayList<>();
        Set<String> set = mPrefs.getStringSet("saveNames", new HashSet<>());
        if (set != null) {
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
        }
        return items;
    }

    private void unsave() {
        if (nameLayout.getChildCount() > 0 && nameLayout.getChildAt(0).getClass() != Button.class) {
            for (int i = 0; i < nameLayout.getChildCount(); i++) {
                CheckBox temp = ((CheckBox) (nameLayout.getChildAt(i)));
                if (temp.isChecked()) {
                    String remove = temp.getText().toString();
                    for (int h = 0; h < retrieved.size(); h++) {
                        Name tempName = retrieved.get(h);
                        if (tempName.getName().equals(remove)) {
                            retrieved.remove(h);
                            h--;
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
            editor.apply();
        }
        refresh();
    }

    private ArrayList<Name> getSelected() {
        ArrayList<Name> selected = new ArrayList<>();
        if (nameLayout.getChildCount() > 0 && nameLayout.getChildAt(0).getClass().equals(CheckBox.class)) {
            for (int i = 0; i < nameLayout.getChildCount(); i++) {
                CheckBox tempBox = (CheckBox) nameLayout.getChildAt(i);
                if (tempBox.isChecked()) {
                    String tempNameString = tempBox.getText().toString();
                    selected.add(fetch(tempNameString));
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Names", Toast.LENGTH_SHORT).show();
        }
        return selected;
    }

    private Name fetch(String name) {
        Name n;
        ArrayList<Name> fetchNames = getNames();
        if (getNames().size() > 0) {
            for (int i = 0; i < fetchNames.size(); i++) {
                n = fetchNames.get(i);
                if (n.getName().equals(name) && n.getSex().equals(gender)) {
                    return n;
                }
            }
        }
        return null;
    }
}

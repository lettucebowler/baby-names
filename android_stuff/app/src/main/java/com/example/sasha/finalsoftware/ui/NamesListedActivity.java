package com.example.sasha.finalsoftware.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.sasha.finalsoftware.R;
import com.example.sasha.finalsoftware.data.Name;
import com.google.firebase.database.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NamesListedActivity extends AppCompatActivity {
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabase = db.getReference();
    private static LinearLayout searchLayout;
    private static Spinner sexSpinner;
    private List<Name> nameList;
    private String gender;
    private String temp;
    private SharedPreferences mPrefs;
    private CheckBox tempCheck;
    private ArrayList<Name> retrieved;
    private ArrayList<Name> saveNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.names_listed);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        retrieved = getNames();
        Log.w("IN ON CREATE", retrieved.toString());
        nameList = new ArrayList<>();
        SearchView searchBar = findViewById(R.id.searchBar);
        searchBar.setQueryHint("Baby Name");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.sex_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner = findViewById(R.id.sexSpinner);
        sexSpinner.setAdapter(adapter);
        Button searchButton = findViewById(R.id.searchButton);
        searchLayout = findViewById(R.id.searchLinear);
        searchButton.setEnabled(false);
        mDatabase.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Name tempName = dataSnapshot.getValue(Name.class);
                tempName.setId(dataSnapshot.getKey());
                nameList.add(tempName);
                //System.out.println("enabled");
                searchButton.setEnabled(true);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        searchButton.setOnClickListener(e -> {
            searchLayout.setEnabled(true);
            searchLayout.removeAllViews();
            gender = sexSpinner.getSelectedItem().toString();
            String search;
            try {
                search = searchBar.getQuery().toString();
            } catch (StringIndexOutOfBoundsException ef) {
                search = "*";
            }
            temp = search;
            nameList.forEach(name -> {
                if (name.getName().toLowerCase().matches(temp.replace("?", ".?").replace("*",
                        ".*?").toLowerCase()) && name.getSex().equals(gender)) {
                    System.out.println("True");
                    tempCheck = new CheckBox(getApplicationContext());
                    tempCheck.setText(name.getName());
                    tempCheck.setTextSize(36);
                    searchLayout.addView(tempCheck);
                }
            });
        });
    }

    public void saveNames(View view) {
        saveNameList = getNames();
        ArrayList<Name> selected = getSelected();
        selected.forEach(name -> {
            if(!saveNameList.contains(name)) {
                saveNameList.add(name);
            }
        });
        if (saveNameList.size() > 0) {
            SharedPreferences.Editor editor = mPrefs.edit();
            Set<String> set = new HashSet<>();
            for (int i = 0; i < saveNameList.size(); i++) {
                System.out.println(saveNameList.get(i).getJSONObject().toString());
                set.add(saveNameList.get(i).getJSONObject().toString());
            }
            editor.putStringSet("saveNames", set);
            editor.apply();
        } else {
            Toast.makeText(getApplicationContext(), "No names to save.", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Name> getNames() {
        ArrayList<Name> items = new ArrayList<Name>();
        Set<String> set = mPrefs.getStringSet("saveNames", new HashSet<>());
        if (set != null) {
            for (String s : set) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
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
                    items.add(tName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return items;
    }

    private Name fetch(String name) {
        Name n;
        if (nameList.size() > 0) {
            for (int i = 0; i < nameList.size(); i++) {
                n = nameList.get(i);
                if (n.getName().equals(name) && n.getSex().equals(gender)) {
                    return n;
                }
            }
        }
        return null;
    }

    private ArrayList<Name> getSelected() {
        ArrayList<Name> selected = new ArrayList<>();
        if (searchLayout.getChildCount() > 0) {
            for (int i = 0; i < searchLayout.getChildCount(); i++) {
                CheckBox tempBox = (CheckBox) searchLayout.getChildAt(i);
                if (tempBox.isChecked()) {
                    String tempNameString = tempBox.getText().toString();
                    saveNameList.add(fetch(tempNameString));
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Names", Toast.LENGTH_SHORT).show();
        }
        return selected;
    }
}

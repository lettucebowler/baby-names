package com.example.sasha.finalsoftware.ui;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> retrieved = new ArrayList<String>(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getStringSet("names", new HashSet<String>()));
        Log.w("IN ON CREATE", retrieved.toString());
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.names_listed);
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
        searchLayout.setEnabled(false);
        searchButton.setEnabled(false);
        mDatabase.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Name tempName = dataSnapshot.getValue(Name.class);
                tempName.setId(dataSnapshot.getKey());
                nameList.add(tempName);
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
            gender = sexSpinner.getSelectedItem().toString();
            String search;
            try {
                search = searchBar.getQuery().toString();
                System.out.println(search);
            } catch (StringIndexOutOfBoundsException ef) {
                search = "*";
            }
            searchLayout.removeAllViews();
            temp = search;
            System.out.println("True");
            System.out.println(temp);
            nameList.forEach(name -> {
                if (name.getName().toLowerCase().matches(temp.replace("?", ".?").replace("*", ".*?").toLowerCase()) && name.getSex().equals(gender)) {
                    tempCheck = new CheckBox(getApplicationContext());
                    tempCheck.setText(name.getName());
                    tempCheck.setTextSize(36);
                    searchLayout.addView(tempCheck);
                }
            });
        });

    }

    public void saveNames(View view) {
        ArrayList<String> saveNameList = new ArrayList<>();
        for (int i = 0; i < searchLayout.getChildCount(); i++) {
            view = searchLayout.getChildAt(i);
            if (view instanceof CheckBox) {
                if (((CheckBox) view).isChecked()) {
                    if (!saveNameList.contains(((CheckBox) view).getText().toString())) {
                        saveNameList.add(((CheckBox) view).getText().toString());
                    }
                }
            }
        }
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putStringSet("names", new HashSet<String>(saveNameList));
        edit.commit();
        for (int i = 0; i < saveNameList.size(); i++) {
            Log.w("NAME IN INDEX " + i, saveNameList.get(i));
        }
        ArrayList<String> retrieved = new ArrayList<String>(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getStringSet("names", new HashSet<String>()));
        Log.w("TEST", "SIZE IS " + retrieved.size());

    }

}

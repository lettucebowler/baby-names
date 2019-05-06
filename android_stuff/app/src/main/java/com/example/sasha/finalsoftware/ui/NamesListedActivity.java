package com.example.sasha.finalsoftware.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.sasha.finalsoftware.R;
import com.example.sasha.finalsoftware.data.Name;
import com.example.sasha.finalsoftware.data.NameSearch;
import com.example.sasha.finalsoftware.data.NameSearchPrefix;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class NamesListedActivity extends AppCompatActivity {
    private List<Name> nameList;
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabase = db.getReference();
    private static LinearLayout searchLayout;
    private static Spinner sexSpinner;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        mDatabase.orderByChild("name")
                .addChildEventListener(new ChildEventListener() {
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
            }
            catch(StringIndexOutOfBoundsException ef) {
                search = "";
            }
            String temp1 = search.substring(1);
            temp1 = temp1.toLowerCase();
            Character temp2 = search.charAt(0);
            String temp3 = temp2.toString().toUpperCase();
            search = temp3 + temp1;
            searchLayout.removeAllViews();
            String temp = search;
            nameList.forEach(name -> {
                if (name.getName().equals(temp) && name.getSex().equals(gender)) {
                    CheckBox tempCheck = new CheckBox(getApplicationContext());
                    tempCheck.setText(name.getName());
                    tempCheck.setTextSize(36);
                    searchLayout.addView(tempCheck);
                }
            });
        });
    }

}

package com.example.sasha.finalsoftware.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.names_listed);

        nameList = new ArrayList<>();
        TextInputEditText searchBar = findViewById(R.id.searchBar);
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(e -> {
            System.out.println("buttontest");
            searchButton.setEnabled(false);
            String search = searchBar.getText().toString();
//            System.out.println(search + "\n");
            String temp1 = search.substring(1);
            temp1 = temp1.toLowerCase();
            Character temp2 = search.charAt(0);
            String temp3 = temp2.toString().toUpperCase();
            search = temp3 + temp1;
            String searchStart = search;
            String searchEnd = search;
            String temp4 = search.substring(search.length() - 2);
            System.out.println(temp4);
            if(temp4.equals("-P") || temp4.equals("-p")) {
                final String substring = search.substring(0, search.length() - 3);

                searchStart = substring;
                searchEnd = substring + "z";
            }
//            System.out.println(search + "\n");


            nameList.clear();
            mDatabase.orderByChild("name").startAt(searchStart).endAt(searchEnd)
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                            Name tempName = dataSnapshot.getValue(Name.class);
                            System.out.println(tempName.getName() + "\n");
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
                // ...
            });
    }

}

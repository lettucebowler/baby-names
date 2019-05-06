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
//            System.out.println(search + "\n");
            nameList.clear();
            nameList = NameSearchPrefix.prefixSearch(search);

                // ...
            });

        });
    }

}

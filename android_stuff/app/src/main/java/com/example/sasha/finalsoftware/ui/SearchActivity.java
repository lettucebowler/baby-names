package com.example.sasha.finalsoftware.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import com.example.sasha.finalsoftware.R;
import com.example.sasha.finalsoftware.data.NameSearch;

public class SearchActivity extends AppCompatActivity {

    private NameSearch nameSearcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        nameSearcher = new NameSearch(this);
        Button searchButton = findViewById(R.id.searchBtn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    protected void search() {
        EditText nameBox = findViewById(R.id.searchName);
        EditText minYearBox = findViewById(R.id.searchDate);
        EditText maxYearBox = findViewById(R.id.searchDate2);
        String name = nameBox.getText().toString();
        String minYear = minYearBox.getText().toString();
        String maxYear = maxYearBox.getText().toString();
        nameSearcher.getFromFirebase(name, minYear, maxYear);
    }
}
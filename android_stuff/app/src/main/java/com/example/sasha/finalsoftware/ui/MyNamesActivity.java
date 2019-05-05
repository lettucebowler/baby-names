package com.example.sasha.finalsoftware.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import com.example.sasha.finalsoftware.R;
import com.google.api.Distribution;

public class MyNamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int N = 12; // total number of textviews to add
        final LinearLayout nameLayout = findViewById(R.id.nameLayout);
        final CheckBox[] checkArray = new CheckBox[N]; // create an empty array;
        final ScrollView scroller = findViewById(R.id.nameScroll);
        for (int i = 0; i < N; i++) {
            // create a new textview
            final CheckBox rowCheckBox = new CheckBox(this);

            // set some properties of rowTextView or something
            rowCheckBox.setText("This is row #" + i);
            rowCheckBox.setTextSize(36);

            // add the textview to the linearlayout
            nameLayout.addView(rowCheckBox);


            // save a reference to the textview for later
            checkArray[i] = rowCheckBox;
        }

    }
}
package com.example.sasha.finalsoftware.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.sasha.finalsoftware.R;
import com.example.sasha.finalsoftware.data.NameSearch;

public class NamesListedActivity  extends AppCompatActivity {
    private NameSearch nameSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.names_listed);
    }

}

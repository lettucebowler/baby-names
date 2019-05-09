package com.example.sasha.finalsoftware.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.sasha.finalsoftware.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button myNamesButton = findViewById(R.id.savedNamesButton);
        myNamesButton.setOnClickListener(e -> switchToMyNames());
}
    public void switchToNamesListed(View myView) {
        Intent myIntent = new Intent(MainActivity.this, NamesListedActivity.class);
        startActivity(myIntent);
    }

    public void switchToMyNames() {
        Intent myIntent = new Intent(MainActivity.this, MyNamesActivity.class);
        startActivity(myIntent);
    }

    public void switchToDiscoverNames(View myView) {
        Intent myIntent = new Intent(MainActivity.this, DiscoverNamesActivity.class);
        startActivity(myIntent);
    }

}

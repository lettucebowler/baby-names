package com.example.sasha.finalsoftware.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.sasha.finalsoftware.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button myNamesButton = findViewById(R.id.LikeListButton);
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

    public void swtichToSearchNames(View myView) {
        Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(myIntent);
    }
}

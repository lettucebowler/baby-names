package com.example.sasha.finalsoftware.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.*;
import com.example.sasha.finalsoftware.R;
import com.example.sasha.finalsoftware.data.Name;
import com.google.firebase.database.*;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiscoverNamesActivity extends AppCompatActivity {

    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference mDatabase = db.getReference();
    private ArrayList<Name> nameList;
    private Name graphData;
    private LineChartView chart;
    private TextView maxView;
    private int x;
    private float max;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        nameList = new ArrayList<>();
        Toast.makeText(getApplicationContext(), "Getting Names", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<Line> createLines(int start, int end) {
        ArrayList<Line> lines = new ArrayList<>();
        ArrayList<PointValue> pointValues1 = new ArrayList<>();
        x = 0;
        List<Double> tempPopularity = graphData.getPopularity();
        max = tempPopularity.get(start - 1880).floatValue();
        for (int i = start; i < end + 1; i++) {
            Double tempPop = tempPopularity.get(i - 1880);
            if (tempPop.floatValue() > max) {
                max = tempPop.floatValue();
            }
            pointValues1.add(new PointValue(x, tempPop.floatValue()));
            x++;
        }
        lines.add(new Line(pointValues1).setColor(Color.BLUE).setStrokeWidth(2).setHasLabels(false).setHasPoints(false).setCubic(true));

        maxView = findViewById(R.id.maxView);
        maxView.setText("" + max);
        return lines;
    }
}


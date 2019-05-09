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
        setContentView(R.layout.names_listed);
        nameList = new ArrayList<>();
        SearchView searchBar = findViewById(R.id.searchBar);
        searchBar.setQueryHint("Baby Name");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.sex_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDatabase.orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Name tempName = dataSnapshot.getValue(Name.class);
                tempName.setId(dataSnapshot.getKey());
                nameList.add(tempName);
                System.out.println("enabled");
                //Log.w("INSIDE ONCHILD", "OKAY");
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
        Toast.makeText(this, "Please wait while we populate name list", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                String search;
                try {
                    search = searchBar.getQuery().toString();
                } catch (StringIndexOutOfBoundsException ef) {
                    search = "*";
                }
                String temp = search;
                Log.w("TESTING VALUE OF I", "i is " + nameList.size());

                setContentView(R.layout.activity_graph);
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                graphData = (nameList.get(new Random().nextInt(nameList.size())));

                ConstraintLayout layout = findViewById(R.id.constraintLayout);

                if (graphData == null) {
                    Button backButton = new Button(getApplicationContext());
                    backButton.setText("Add names to Graph");
                    backButton.setOnClickListener(back -> {
                        Intent myIntent = new Intent(getApplicationContext(), MyNamesActivity.class);
                        startActivity(myIntent);
                    });
                    layout.addView(backButton);
                    backButton.getLayoutParams().width = ActionBar.LayoutParams.MATCH_PARENT;
                    backButton.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
                    backButton.setTextSize(36);
                } else {
                    chart = findViewById(R.id.chart);
                    LineChartData chartData;

                    TextView nameView1 = findViewById(R.id.name1);
                    nameView1.setText(graphData.getName());
                    nameView1.setTextColor(Color.BLUE);

                    TextView nameView2 = findViewById(R.id.name2);
                    nameView2.setText("");

                    chartData = new LineChartData(createLines(1880, 2008));

                    maxView = findViewById(R.id.maxView);
                    maxView.setText("" + max);

                    chart.setLineChartData(chartData);
//            Viewport v = new Viewport(chart.getMaximumViewport());
////            v.top = max * 1.1f; //example max value
////            v.bottom = 0 - max * .1f;  //example min value
////            chart.setCurrentViewport(v);
                    chart.setZoomEnabled(false);
//            chart.setViewportCalculationEnabled(false);

                    Spinner startSpinner = findViewById(R.id.startSpinner);
                    Spinner endSpinner = findViewById(R.id.endSpinner);
                    ArrayList<String> spinnerList = new ArrayList<>();
                    for (int i = 1880; i < 2009; i++) {
                        spinnerList.add("" + i);
                    }
                    ArrayAdapter spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinnerList);
                    startSpinner.setAdapter(spinnerArrayAdapter);
                    endSpinner.setAdapter(spinnerArrayAdapter);

                    Button calcButton = findViewById(R.id.calcButton);
                    calcButton.setOnClickListener(b -> {
                        Integer newLeft = Integer.parseInt(startSpinner.getSelectedItem().toString());
                        Integer newRight = Integer.parseInt(endSpinner.getSelectedItem().toString());
                        if (newRight > newLeft) {
                            chartData.setLines(createLines(newLeft, newRight));
                            chart.setLineChartData(chartData);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid range. End must be greater than start.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }, 5100);

    }

    private ArrayList<Line> createLines(int start, int end) {
        ArrayList<Line> lines = new ArrayList<>();
        ArrayList<PointValue> pointValues1 = new ArrayList<>();
        ArrayList<PointValue> pointValues2 = new ArrayList<>();
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


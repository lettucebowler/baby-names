package com.example.sasha.finalsoftware.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import com.example.sasha.finalsoftware.R;
import com.example.sasha.finalsoftware.data.Name;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.*;
import lecho.lib.hellocharts.view.LineChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphActivity extends AppCompatActivity {

    private LineChartView chart;
    private ArrayList<Name> graphData;
    private ArrayList<Integer> colors;
    private int x;
    private float max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        graphData = ((ArrayList<Name>) getIntent().getExtras().getSerializable("names"));
        colors = new ArrayList<>();

        ConstraintLayout layout = findViewById(R.id.constraintLayout);

        if (graphData.isEmpty()) {
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
//            chart.setInteractive(true);
//            chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
            ArrayList<PointValue> pointValues1 = new ArrayList<>();
            ArrayList<PointValue> pointValues2 = new ArrayList<>();
            ArrayList<Line> lines = new ArrayList<>();
            LineChartData chartData;
            System.out.println("graphData1");
            x = 0;
            max = graphData.get(0).getPopularity().get(0).floatValue();
            graphData.get(0).getPopularity().forEach(prop -> {
                if(prop.floatValue() > max) {
                    max = prop.floatValue();
                }
                pointValues1.add(new PointValue(x, prop.floatValue()));
                x++;
            });
            Random rnd1 = new Random();
            int color = Color.argb(255, rnd1.nextInt(256), rnd1.nextInt(256), rnd1.nextInt(256));
            colors.add(color);
            lines.add(new Line(pointValues1).setColor(color).setStrokeWidth(2).setHasLabels(false).setHasPoints(false).setCubic(true));
            System.out.println(lines.size());

            if(graphData.size() > 1) {
                System.out.println("graphData2");
                x = 0;
                graphData.get(1).getPopularity().forEach(prop -> {
                    if(prop.floatValue() > max) {
                        max = prop.floatValue();
                    }
                    pointValues2.add(new PointValue(x, prop.floatValue()));
                    x++;
                });
                Random rnd2 = new Random();
                int color2 = Color.argb(255, rnd2.nextInt(256), rnd2.nextInt(256), rnd2.nextInt(256));
                colors.add(color);
                lines.add(new Line(pointValues2).setColor(color2).setStrokeWidth(2).setHasLabels(false).setHasPoints(false).setCubic(true));
                System.out.println(lines.size());
            }

            chartData = new LineChartData(lines);

//            List<AxisValue> axisValuesForX = new ArrayList<>();
//            List<AxisValue> axisValuesForY = new ArrayList<>();
//            AxisValue tempAxisValue;
//            for (float i = 1880; i < 2009; i++) {
//                tempAxisValue = new AxisValue(i);
//                tempAxisValue.setLabel("" + i);
//                axisValuesForX.add(tempAxisValue);
//            }
//
//            for (float i = 0.0000f; i <= 1; i += 0.001) {
//                tempAxisValue = new AxisValue(i);
//                tempAxisValue.setLabel("" + i);
//                axisValuesForY.add(tempAxisValue);
//            }
//
//            Axis xAxis = new Axis(axisValuesForX);
//            chartData.setAxisXBottom(xAxis);
//            Axis yAxis = new Axis(axisValuesForY);
//            chartData.setAxisYLeft(yAxis);

            chart.setLineChartData(chartData);
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.top = max + .000005f; //example max value
            v.bottom = 0;  //example min value
            chart.setMaximumViewport(v);
            chart.setCurrentViewport(v);
//Optional step: disable viewport recalculations, thanks to this animations will not change viewport automatically.
            chart.setViewportCalculationEnabled(false);
            chart.setZoomEnabled(false);
        }
    }

}

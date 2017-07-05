package com.tianma.chartsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.tianma.chart.SimpleLineChart;
import com.tianma.chart.formatter.LineChartLabelFormatter;
import com.tianma.chart.model.Line;
import com.tianma.chart.model.LineChartData;
import com.tianma.chart.model.Point;
import com.tianma.chartsample.utils.ResolutionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SampleActivity extends AppCompatActivity {

    private SimpleLineChart mSimpleLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        initViews();
        initData();
    }

    private void initViews() {
        mSimpleLineChart = (SimpleLineChart) findViewById(R.id.simple_line_chart);
        Switch cubicSwitch = (Switch) findViewById(R.id.cubic_switch);
        cubicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                refreshLineChart(checked);
                Toast.makeText(SampleActivity.this, checked ? R.string.bezier_curve : R.string.polyline, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        refreshLineChart(true);
    }

    private void refreshLineChart(boolean cubic) {
        float[][] pointsData = generateFakeData();

        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < pointsData.length; i++) {
            List<Point> pointValues = new ArrayList<>();
            for (int j = 0; j < pointsData[i].length; j++) {
                pointValues.add(new Point(j + 10, pointsData[i][j]));
            }
            Line line = new Line(pointValues);
            line.setLabelFormatter(new LineChartLabelFormatter() {
                @Override
                public String labelFormat(Point point) {
                    return String.format(Locale.getDefault(), "%.1fº", point.getY());
                }
            });
            line.setLineColor(Color.BLACK);
            line.setLabelColor(ContextCompat.getColor(this, R.color.colorAccent));
            line.setLabelTextSize(ResolutionUtils.sp2px(this, 12));
            if (i == 0) {
                line.setLabelAbovePoint(true);
            } else {
                line.setLabelAbovePoint(false);
            }
            line.setCubic(cubic);
            lines.add(line);
        }

        LineChartData lineChartData = new LineChartData(lines);
        mSimpleLineChart.setLineChartData(lineChartData);
    }

    private float[][] generateFakeData() {
        float[][] data = new float[][]{
                new float[]{28, 27, 29.5f, 29, 29.2f},
                new float[]{23.5f, 23.7f, 23.3f, 24.4f, 23.5f}
        };
        return data;
    }
}

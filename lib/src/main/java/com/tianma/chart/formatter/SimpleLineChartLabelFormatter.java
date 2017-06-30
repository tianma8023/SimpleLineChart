package com.tianma.chart.formatter;


import com.tianma.chart.model.Point;

import java.util.Locale;


public class SimpleLineChartLabelFormatter implements LineChartLabelFormatter {

    @Override
    public String labelFormat(Point point) {
        return String.format(Locale.getDefault(), "%.1f", point.getY());
    }
}

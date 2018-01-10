package com.tianma.chart.formatter;


import com.tianma.chart.model.Point;

import java.util.Locale;

/**
 * 简单的标签格式器，对于指定的Point的y值，保留1位小数并返回。
 * @author Tianma
 */
public class SimpleLineChartLabelFormatter implements LineChartLabelFormatter {

    @Override
    public String labelFormat(Point point) {
        return String.format(Locale.getDefault(), "%.1f", point.getY());
    }
}

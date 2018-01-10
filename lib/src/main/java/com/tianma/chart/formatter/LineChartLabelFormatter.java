package com.tianma.chart.formatter;


import com.tianma.chart.model.Point;

/**
 * Point标签格式器
 * @author Tianma
 */
public interface LineChartLabelFormatter {

    /**
     * 格式化标签
     * @param point 需要被标签标识的Point
     * @return 当前Point需要显示的标签字符串
     */
    String labelFormat(Point point);

}

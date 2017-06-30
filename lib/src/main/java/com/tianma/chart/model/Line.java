package com.tianma.chart.model;

import android.graphics.Color;
import android.graphics.PathEffect;

import com.tianma.chart.formatter.LineChartLabelFormatter;
import com.tianma.chart.formatter.SimpleLineChartLabelFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Single line for line chart
 */
public class Line {

    private static final int DEFAULT_LINE_STROKE_WITH = 3;
    private static final int DEFAULT_LINE_COLOR = Color.WHITE;
    private static final int DEFAULT_LABEL_COLOR = Color.WHITE;
    private static final float DEFAULT_LABEL_TEXT_SIZE_PX = 30;
    private static final float DEFAULT_LABEL_POINT_GAP_PX = 20;

    /**
     * 线条颜色
     */
    private int lineColor = DEFAULT_LINE_COLOR;

    /**
     * 标签颜色
     */
    private int labelColor = DEFAULT_LABEL_COLOR;
    /**
     * 标签字体大小
     */
    private float labelTextSize = DEFAULT_LABEL_TEXT_SIZE_PX;
    /**
     * 线条粗细
     */
    private int strokeWidth = DEFAULT_LINE_STROKE_WITH;

    /**
     * 标签与Point之间的间距
     */
    private float labelPointGap = DEFAULT_LABEL_POINT_GAP_PX;

    /**
     * 是否是曲线
     */
    private boolean isCubic = true;
    /**
     * 是否显示标签
     */
    private boolean showLabels = true;

    /**
     * 标签是否在Point的上方
     */
    private boolean isLabelAbovePoint = false;

    private PathEffect pathEffect;

    private LineChartLabelFormatter labelFormatter = new SimpleLineChartLabelFormatter();

    private List<Point> points;

    public Line() {

    }

    public Line(List<Point> points) {
        setPoints(points);
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public boolean isCubic() {
        return isCubic;
    }

    public void setCubic(boolean cubic) {
        isCubic = cubic;
    }

    public boolean isShowLabels() {
        return showLabels;
    }

    public void setShowLabels(boolean showLabels) {
        this.showLabels = showLabels;
    }

    public PathEffect getPathEffect() {
        return pathEffect;
    }

    public void setPathEffect(PathEffect pathEffect) {
        this.pathEffect = pathEffect;
    }

    public LineChartLabelFormatter getLabelFormatter() {
        return labelFormatter;
    }

    public void setLabelFormatter(LineChartLabelFormatter labelFormatter) {
        this.labelFormatter = labelFormatter;
    }

    public int getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
    }

    public float getLabelTextSize() {
        return labelTextSize;
    }

    public void setLabelTextSize(float labelTextSize) {
        this.labelTextSize = labelTextSize;
    }

    public boolean isLabelAbovePoint() {
        return isLabelAbovePoint;
    }

    public void setLabelAbovePoint(boolean labelAbovePoint) {
        isLabelAbovePoint = labelAbovePoint;
    }

    public float getLabelPointGap() {
        return labelPointGap;
    }

    public void setLabelPointGap(float labelPointGap) {
        this.labelPointGap = labelPointGap;
    }

    public void setPoints(List<Point> points) {
        if (points == null) {
            this.points = new ArrayList<>();
        } else {
            this.points = points;
        }
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        if (points == null) {
            points = new ArrayList<>();
        }
        points.add(point);
    }

}

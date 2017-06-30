package com.tianma.chart.model;

/**
 * Created by Tianma on 2017/5/22.
 */
public class Point {

    private float x;
    private float y;
    private String label;

    public Point() {
    }

    public Point(float x, float y) {
        this(x, y, null);
    }

    public Point(float x, float y, String label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", label='" + label + '\'' +
                '}';
    }
}

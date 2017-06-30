package com.tianma.chart.model;

import java.util.ArrayList;
import java.util.List;

public class LineChartData {

    private List<Line> lines = new ArrayList<>();

    public LineChartData(List<Line> lines) {
        setLines(lines);
    }

    public LineChartData() {

    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void removeLine(Line line) {
        lines.remove(line);
    }

}

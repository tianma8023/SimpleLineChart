package com.tianma.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.tianma.chart.model.Line;
import com.tianma.chart.model.LineChartData;
import com.tianma.chart.model.Point;

/**
 * Simple line chart
 */
public class SimpleLineChart extends View {

    private static final int DEFAULT_SPARED_SPACE_PX = 30;
    private static final float LINE_SMOOTHNESS = 0.16f;

    private LineChartData mLineChartData = new LineChartData();

    private int mWidth;
    private int mHeight;

    private int mVerticalSparedSpace = DEFAULT_SPARED_SPACE_PX;
    private int mHorizontalSparedSpace = DEFAULT_SPARED_SPACE_PX;

    private RectF pointRectF = new RectF();
    private RectF chartRectF = new RectF();

    private Paint mLinePaint;
    private TextPaint mLabelPaint;

    private Paint.FontMetricsInt mFontMetrics = new Paint.FontMetricsInt();

    private static final int MIN_CONTENT_WIDTH_PX = 600;
    private static final int MIN_CONTENT_HEIGHT_PX = 400;

    public SimpleLineChart(Context context) {
        super(context);
        init();
    }

    public SimpleLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);

        mLabelPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mLabelPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setLineChartData(LineChartData lineChartData) {
        mLineChartData = lineChartData;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 处理 wrap_content 情况
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST) {
            widthSpecSize = MIN_CONTENT_WIDTH_PX;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            heightSpecSize = MIN_CONTENT_HEIGHT_PX;
        }
        mWidth = widthSpecSize;
        mHeight = heightSpecSize;

        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateRectF();
        drawLines(canvas);
    }

    // 分别计算 Chart对应的RectF 和 Point对应的RectF
    private void calculateRectF() {
        chartRectF.set(getPaddingLeft() + mHorizontalSparedSpace, getPaddingTop() + mVerticalSparedSpace, mWidth - mHorizontalSparedSpace - getPaddingRight(), mHeight - mVerticalSparedSpace - getPaddingBottom());

        float minPointX = Float.NaN;
        float minPointY = Float.NaN;
        float maxPointX = Float.NaN;
        float maxPointY = Float.NaN;

        // 计算Point在x,y方向上的最大最小值
        for (Line line : mLineChartData.getLines()) {
            for (Point point : line.getPoints()) {
                if (Float.isNaN(minPointX)) {
                    minPointX = point.getX();
                    minPointY = point.getY();
                    maxPointX = point.getX();
                    maxPointY = point.getY();
                } else {
                    float currentX = point.getX();
                    if (currentX < minPointX) {
                        minPointX = currentX;
                    } else if (currentX > maxPointX) {
                        maxPointX = currentX;
                    }

                    float currentY = point.getY();
                    if (currentY < minPointY) {
                        minPointY = currentY;
                    } else if (currentY > maxPointY) {
                        maxPointY = currentY;
                    }
                }
            }
        }
        pointRectF.set(minPointX, minPointY, maxPointX, maxPointY);
    }

    /**
     * 根据平移伸缩变换，将Point的x坐标转换成rawX值
     *
     * @param pointX Point的x坐标
     * @return 转换后的rawX值
     */
    private float calculateRawX(float pointX) {
        return (pointX - pointRectF.left) * chartRectF.width() / pointRectF.width() + chartRectF.left;
    }

    /**
     * 根据平移伸缩变换，将Point的y坐标转换成rawY值
     *
     * @param pointY Point的y坐标
     * @return 转换后的rawY值
     */
    private float calculateRawY(float pointY) {
        return chartRectF.height() - (pointY - pointRectF.top) * chartRectF.height() / pointRectF.height() + chartRectF.top;
    }

    // 绘制线条的Paint的前期准备操作
    private void prepareLinePaint(Line line) {
        mLinePaint.setStrokeWidth(line.getStrokeWidth());
        mLinePaint.setColor(line.getLineColor());
        mLinePaint.setPathEffect(line.getPathEffect());
    }

    // 绘制标签的Paint的前期准备操作
    private void prepareLabelPaint(Line line) {
        mLabelPaint.setTextSize(line.getLabelTextSize());
        mLabelPaint.setColor(line.getLabelColor());
        // 因为字体大小改变,需要重新度量FontMetrics
        mLabelPaint.getFontMetricsInt(mFontMetrics);
    }

    // 绘制线条
    private void drawLines(Canvas canvas) {
        for (Line line : mLineChartData.getLines()) {
            if (line.isCubic()) {
                drawSmoothLine(canvas, line);
            } else {
                drawDefaultLine(canvas, line);
            }
        }
    }

    // 绘制平滑曲线
    private void drawSmoothLine(Canvas canvas, Line line) {
        prepareLinePaint(line);
        if (line.isShowLabels()) {
            prepareLabelPaint(line);
        }
        // 计算控制点(使用3阶贝塞尔曲线完成平滑曲线的绘制)
        final int pointCounts = line.getPoints().size();
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;
        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX;
        float nextPointY;

        Path path = new Path();
        for (int pointIndex = 0; pointIndex < pointCounts; pointIndex++) {
            if (Float.isNaN(currentPointX)) {
                // 当前点
                Point currentPoint = line.getPoints().get(pointIndex);
                currentPointX = calculateRawX(currentPoint.getX());
                currentPointY = calculateRawY(currentPoint.getY());
            }
            if (Float.isNaN(previousPointX)) {
                if (pointIndex > 0) {
                    // 上一个点
                    Point previousPoint = line.getPoints().get(pointIndex - 1);
                    previousPointX = calculateRawX(previousPoint.getX());
                    previousPointY = calculateRawY(previousPoint.getY());
                } else {
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }
            if (Float.isNaN(prePreviousPointX)) {
                if (pointIndex > 1) {
                    // 上上个点
                    Point prePreviousPoint = line.getPoints().get(pointIndex - 2);
                    prePreviousPointX = calculateRawX(prePreviousPoint.getX());
                    prePreviousPointY = calculateRawY(prePreviousPoint.getY());
                } else {
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }

            // nextPoint 总是 currentPoint 的下一个Point, 或者等于 currentPoint
            if (pointIndex < pointCounts - 1) {
                Point nextPoint = line.getPoints().get(pointIndex + 1);
                nextPointX = calculateRawX(nextPoint.getX());
                nextPointY = calculateRawY(nextPoint.getY());
            } else {
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (pointIndex == 0) {
                // 移动到起点位置
                path.moveTo(currentPointX, currentPointY);
            } else {
                // 计算3阶贝塞尔曲线的2个控制点
                final float firstDiffX = currentPointX - prePreviousPointX;
                final float firstDiffY = currentPointY - prePreviousPointY;
                final float secondDiffX = nextPointX - previousPointX;
                final float secondDiffY = nextPointY - previousPointY;

                final float firstControlPointX = previousPointX + firstDiffX * LINE_SMOOTHNESS;
                final float firstControlPointY = previousPointY + firstDiffY * LINE_SMOOTHNESS;
                final float secondControlPointX = currentPointX - secondDiffX * LINE_SMOOTHNESS;
                final float secondControlPointY = currentPointY - secondDiffY * LINE_SMOOTHNESS;

                // 画3阶贝塞尔曲线
                path.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY, currentPointX, currentPointY);
            }

            if (line.isShowLabels()) {
                drawLabel(canvas, line, line.getPoints().get(pointIndex), currentPointX, currentPointY, line.getLabelPointGap());
            }

            // 移动赋值，避免重复计算
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }
        canvas.drawPath(path, mLinePaint);
    }

    // 绘制折线
    private void drawDefaultLine(Canvas canvas, Line line) {
        prepareLinePaint(line);
        if (line.isShowLabels()) {
            prepareLabelPaint(line);
        }
        int pointIndex = 0;
        Path path = new Path();
        for (Point point : line.getPoints()) {
            float rawX = calculateRawX(point.getX());
            float rawY = calculateRawY(point.getY());
            if (pointIndex == 0) {
                path.moveTo(rawX, rawY);
            } else {
                path.lineTo(rawX, rawY);
            }
            if (line.isShowLabels()) {
                drawLabel(canvas, line, point, rawX, rawY, line.getLabelPointGap());
            }
            pointIndex++;
        }
        canvas.drawPath(path, mLinePaint);
    }

    // 绘制标签
    private void drawLabel(Canvas canvas, Line line, Point point, float rawX, float rawY, float offset) {
        String label = line.getLabelFormatter().labelFormat(point);
        if (TextUtils.isEmpty(label)) {
            return;
        }
        // 因为mLabelPaint字体的对齐方式为CENTER(居中对齐),所以baseLineX = rawX;
        float baseLineX = rawX;
        float baseLineY;
        if (line.isLabelAbovePoint()) {
            // label在point上面, descent = rawY - offset, descent = baseLineY + mFontMetrics.descent;
            baseLineY = rawY - offset - mFontMetrics.descent;
        } else {
            // label在point下面, ascent = rawY + offset, ascent = baseLineY + mFontMetrics.ascent;
            baseLineY = rawY + offset + Math.abs(mFontMetrics.ascent);
        }
        canvas.drawText(label, baseLineX, baseLineY, mLabelPaint);
    }

}

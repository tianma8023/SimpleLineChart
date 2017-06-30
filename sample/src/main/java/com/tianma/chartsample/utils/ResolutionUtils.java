package com.tianma.chartsample.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ResolutionUtils {

    private ResolutionUtils() {

    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static float dp2px(Context context, float dp) {
        return dp * getDisplayMetrics(context).density;
    }

    public static float px2dp(Context context, float px) {
        return px / getDisplayMetrics(context).density;
    }

    public static float sp2px(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getDisplayMetrics(context));
    }
}

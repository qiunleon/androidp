package com.example.client.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.view.View;

/**
 * Created by Qiu on 2017/6/7.
 */
public class RectView extends View {

    private static final int PADDING_TOP = 1;
    private static final int PADDING_LEFT = 50;

    public RectView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = 580;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize + PADDING_TOP;
        } else {
            height = 113 + PADDING_TOP;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int radio = 9;
        float[] radiusArray = {radio, radio, radio, radio, radio, radio, radio, radio};
        int width = this.getWidth();
        int height = this.getHeight();
        int halfHeight = (height - PADDING_TOP) / 2 + PADDING_TOP;
        Path path = new Path();
        path.addRoundRect(PADDING_LEFT, PADDING_TOP, width, height, radiusArray, Path.Direction.CW);
        canvas.clipPath(path, Region.Op.REPLACE);
        Paint p = new Paint();
        int strokeWidth = 5;
        p.setStrokeWidth(strokeWidth);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.parseColor("#99353848"));
        canvas.drawRect(PADDING_LEFT, PADDING_TOP, width, halfHeight + PADDING_TOP, p);
        p.setColor(Color.parseColor("#99060A1D"));
        canvas.drawRect(PADDING_LEFT, halfHeight + PADDING_TOP, width, height, p);
        p.setColor(Color.parseColor("#77FFFFFF"));
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(PADDING_LEFT, PADDING_TOP, width, height, radio, radio, p);
        super.onDraw(canvas);
    }
}

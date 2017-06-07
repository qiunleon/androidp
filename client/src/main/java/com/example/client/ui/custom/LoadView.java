package com.example.client.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Qiu on 2017/6/7.
 */
public class LoadView extends View {


    private int VALUE_MAX_COLOR_ALPHA = 255;
    private int VALUE_MAX_POINT_NUMBER = 12;
    private int VALUE_COLOR_ALPHA_DEGRESSION = 20;

    private int mIndex;

    public LoadView(Context context, int index) {
        super(context);
        mIndex = index;
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
            width = 150;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 150;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        int strokeWidth = 1;
        p.setStrokeWidth(strokeWidth);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        int width = this.getWidth() / 2;
        int height = this.getHeight() / 2;
        int radio = 60;
        int r = 8;
        p.setColor(getResources().getColor(android.R.color.white));
        for (int i = 0; i < VALUE_MAX_POINT_NUMBER; i++) {
            if (i < VALUE_MAX_POINT_NUMBER - mIndex) {
                p.setAlpha(VALUE_MAX_COLOR_ALPHA - VALUE_COLOR_ALPHA_DEGRESSION * mIndex - VALUE_COLOR_ALPHA_DEGRESSION * i);
            }else {
                p.setAlpha(VALUE_MAX_COLOR_ALPHA - VALUE_COLOR_ALPHA_DEGRESSION * mIndex);
            }
            canvas.drawCircle(
                    (float) (width - radio * Math.sin(30.0 * i * Math.PI / 180.0)),
                    (float) (height - radio * Math.cos(30.0 * i * Math.PI / 180.0)),
                    r,
                    p
            );
        }
    }
}

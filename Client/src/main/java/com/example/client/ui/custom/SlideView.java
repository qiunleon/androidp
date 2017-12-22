package com.example.client.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by Qiu on 2017/6/7.
 */
public class SlideView extends View {

    public SlideView(Context context) {
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
            width = 200;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 3;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();
        int halfWidth = width / 2;
        Paint p = new Paint();
        LinearGradient linearGradient = new LinearGradient(0, 0, halfWidth, height, Color.parseColor("#00FFFFFF"), Color.parseColor("#FFFFFFFF"), Shader.TileMode.CLAMP);
        p.setShader(linearGradient);
        canvas.drawRect(0, 0, halfWidth, height, p);
        linearGradient = new LinearGradient(halfWidth, 0, width, height, Color.parseColor("#FFFFFFFF"), Color.parseColor("#00FFFFFF"), Shader.TileMode.CLAMP);
        p.setShader(linearGradient);
        canvas.drawRect(halfWidth, 0, width, height, p);
    }
}

package com.harvic.BlogBerzMovePath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/3/19.
 */
public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 二\Android中贝赛尔曲线之quadTo  画波浪线
         */
        drawWave(canvas);

    }

    /**
     * 二\Android中贝赛尔曲线之quadTo  画波浪线
     * @param canvas Canvas
     */
    private void drawWave(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);

        Path path = new Path();
        path.moveTo(100,300);
        path.quadTo(200,200,300,300);
        path.quadTo(400,400,500,300);

        canvas.drawPath(path,paint);
    }
}

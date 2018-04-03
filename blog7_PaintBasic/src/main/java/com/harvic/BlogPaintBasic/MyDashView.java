package com.harvic.BlogPaintBasic;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * DashPathEffect动画效果view
 * 注意在main.xml中打开对应的xml
 * Created by qijian on 16/3/22.
 */
public class MyDashView extends View {
    private Integer dashDx=0;

    public MyDashView(Context context) {
        super(context);
    }

    public MyDashView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDashPathEffect(canvas);
    }

    private Paint getPaint(){
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        return paint;
    }

    private void drawDashPathEffect(Canvas canvas){
        Paint paint = getPaint();
        Path path = new Path();
        path.moveTo(100,600);
        path.lineTo(400,100);
        path.lineTo(700,900);

        canvas.drawPath(path,paint);
        paint.setColor(Color.RED);

        //使用DashPathEffect画线段
        paint.setPathEffect(new DashPathEffect(new float[]{20,10,100,100},0));
        canvas.translate(0,100);
        canvas.drawPath(path,paint);

        //画同一条线段,偏移值为15
        paint.setPathEffect(new DashPathEffect(new float[]{20,10,100,100},dashDx));
        paint.setColor(Color.YELLOW);
        canvas.translate(0,100);
        canvas.drawPath(path,paint);
    }


    public void startAnim(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,230);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dashDx = (Integer)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }
}

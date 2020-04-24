package com.harvic.BlogPorterDuff_2;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by qijian on 16/4/18.
 */
public class HeartMap extends View {

    private Paint mPaint;
    private int mItemWaveLength = 0;
    private int dx = 0;

    private Bitmap BmpSRC, BmpDST;

    public HeartMap(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);

        BmpDST = BitmapFactory.decodeResource(getResources(), R.drawable.heartmap, null);
        BmpSRC =
                Bitmap.createBitmap(BmpDST.getWidth(), BmpDST.getHeight(), Bitmap.Config.ARGB_8888);

        mItemWaveLength = BmpDST.getWidth();
        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 利用Mode.DST_IN模式，由于在这个模式中，相交区域优先显示目标图像，所以我们这里需要显示心电图，所以心电图就是目标图像
        //其实这里要注意的是dx，还有这里绘制的BmpSRC是不断变化的，BmpDST.getWidth() - dx表示从右边到左边，可见区域变大
        Canvas c = new Canvas(BmpSRC);
        //清空bitmap
        c.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
        //画上矩形
        c.drawRect(BmpDST.getWidth() - dx, 0, BmpDST.getWidth(), BmpDST.getHeight(), mPaint);

        //模式合成
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(BmpDST, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(BmpSRC, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
        canvas.drawBitmap(BmpSRC, 0, 0, mPaint);
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(6000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (Integer) animation.getAnimatedValue();
                dx = 100;
                postInvalidate();
            }
        });
        animator.start();
    }
}

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
public class IrregularWaveView extends View {

    private Paint mPaint;
    private int mItemWaveLength = 0;
    private int dx=0;

    private Bitmap BmpSRC,BmpDST;

    public IrregularWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        BmpDST = BitmapFactory.decodeResource(getResources(),R.drawable.wave_bg,null);
        BmpSRC = BitmapFactory.decodeResource(getResources(),R.drawable.circle_shape,null);
        mItemWaveLength = BmpDST.getWidth();

        startAnim();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //先画上圆形
        canvas.drawBitmap(BmpSRC,0,0,mPaint);

        //再画上结果
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(BmpDST,new Rect(dx,0,dx+BmpSRC.getWidth(),BmpSRC.getHeight()),new Rect(0,0,BmpSRC.getWidth(),BmpSRC.getHeight()),mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(BmpSRC,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }


    public void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(4000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (Integer) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}

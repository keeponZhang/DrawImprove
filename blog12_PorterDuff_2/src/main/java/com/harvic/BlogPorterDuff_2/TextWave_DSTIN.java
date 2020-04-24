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
public class TextWave_DSTIN extends View {
    private Paint mPaint;
    private Path mPath;
    private int mItemWaveLength = 1000;
    private int dx;

    private Bitmap BmpSRC,BmpDST;
    public TextWave_DSTIN(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        BmpSRC = BitmapFactory.decodeResource(getResources(),R.drawable.text_shade,null);
        BmpDST = Bitmap.createBitmap(BmpSRC.getWidth(), BmpSRC.getHeight(), Bitmap.Config.ARGB_8888);

        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        generageWavePath();
        // 所以我们要最终显示的被裁剪后的波纹图，所以DST目标图像就应该是波纹图(然后透过src的透明度，只展示文字波纹)


        //先清空bitmap上的图像,然后再画上Path
        Canvas c = new Canvas(BmpDST);
        c.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
        c.drawPath(mPath,mPaint);
        // 有些同学可能会问，为什么在saveLayer前，先要画一遍BmpSRC呢，这是因为我们在使用Mode.DST_IN时，
        // 除了相交区域以外，其它区域都会因为有空白像素而消失不见了
        // canvas.drawBitmap(BmpSRC,0,0,mPaint);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(BmpDST,0,0,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(BmpSRC,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    /**
     * 生成此时的Path
     */
    private void generageWavePath(){
        mPath.reset();
        int originY = BmpSRC.getHeight()/2;
        int halfWaveLen = mItemWaveLength/2;
        mPath.moveTo(-mItemWaveLength+dx,originY);
        for (int i = -mItemWaveLength;i<=getWidth()+mItemWaveLength;i+=mItemWaveLength){
            mPath.rQuadTo(halfWaveLen/2,-50,halfWaveLen,0);
            mPath.rQuadTo(halfWaveLen/2,50,halfWaveLen,0);
        }
        mPath.lineTo(BmpSRC.getWidth(),BmpSRC.getHeight());
        mPath.lineTo(0,BmpSRC.getHeight());
        mPath.close();
    }

    public void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(2000);
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

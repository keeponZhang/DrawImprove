package com.harvic.BlogBlurMaskFilter;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/7/2.
 */
public class BitmapShadowView extends View {
    private Paint mPaint;
    private Bitmap mBitmap,mAlphaBmp;

    public BitmapShadowView(Context context) {
        super(context);
        init();
    }

    public BitmapShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BitmapShadowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.blog12);
        mPaint.setColor(Color.RED);
        mAlphaBmp = mBitmap.extractAlpha(mPaint,null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = 500;
        int height = width * mAlphaBmp.getWidth()/mAlphaBmp.getHeight();
        mPaint.setColor(Color.GREEN);
        canvas.drawBitmap(mAlphaBmp,null,new Rect(0,0,width,height),mPaint);


//        canvas.drawBitmap(mBitmap.extractAlpha(), 0, 0, mPaint);
//        canvas.drawBitmap(mAlphaBmp, 0, 0, mPaint);

//        canvas.drawBitmap(bitmap,null,new Rect(100,100,100+width,100+height),mPaint);


    }
}

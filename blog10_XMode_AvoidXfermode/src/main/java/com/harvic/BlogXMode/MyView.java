package com.harvic.BlogXMode;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/12.
 */
public class MyView extends View {
    private Paint mPaint;
    private Bitmap mBmp;
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mBmp = BitmapFactory.decodeResource(getResources(),R.drawable.dog);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawTargetMode(canvas);

//        drawFlowerBmp(canvas);

//        drawTransparencyBmp(canvas);

//        drawAvoidMode(canvas);
    }

    /**
     * 使用Mode.TARGET
     * @param canvas
     */
    private void drawTargetMode(Canvas canvas){
        int width  = 500;
        int height = width * mBmp.getHeight()/mBmp.getWidth();
        mPaint.setColor(Color.RED);

        int layerID = canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
        mPaint.setXfermode(new AvoidXfermode(Color.WHITE,100, AvoidXfermode.Mode.TARGET));
        canvas.drawRect(0,0,width,height,mPaint);

        canvas.restoreToCount(layerID);
    }

    /**
     * 利用第二个图片(小花图片)来替换选区
     * @param canvas
     */
    private void drawFlowerBmp(Canvas canvas){
        int width  = 500;
        int height = width * mBmp.getHeight()/mBmp.getWidth();

        int layerID = canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
        mPaint.setXfermode(new AvoidXfermode(Color.WHITE,100, AvoidXfermode.Mode.TARGET));
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.flower),null,new Rect(0,0,width,height),mPaint);

        canvas.restoreToCount(layerID);
    }


    /**
     * 用透明图片替换选区
     * @param canvas
     */
    private void drawTransparencyBmp(Canvas canvas){
        int width  = 500;
        int height = width * mBmp.getHeight()/mBmp.getWidth();
        int layerID = canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
        mPaint.setXfermode(new AvoidXfermode(Color.WHITE,100, AvoidXfermode.Mode.TARGET));
        mPaint.setARGB(0x00,0xff,0xff,0xff);
        canvas.drawRect(0,0,width,height,mPaint);

        canvas.restoreToCount(layerID);
    }

    /**
     * 使用Mode.AVOID
     * @param canvas
     */
    private void drawAvoidMode(Canvas canvas) {
        int width = 500;
        int height = width * mBmp.getHeight() / mBmp.getWidth();
        mPaint.setColor(Color.RED);

        int layerID = canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBmp, null, new Rect(0, 0, width, height), mPaint);
        mPaint.setXfermode(new AvoidXfermode(Color.WHITE, 100, AvoidXfermode.Mode.AVOID));
        canvas.drawRect(0, 0, width, height, mPaint);

        canvas.restoreToCount(layerID);
    }
}

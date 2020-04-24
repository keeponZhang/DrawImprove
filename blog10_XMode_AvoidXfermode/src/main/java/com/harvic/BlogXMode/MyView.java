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
    //在小米8无效果，可能是这个api在高本版被废弃了
    //     drawTargetMode(canvas);

       // drawFlowerBmp(canvas);

       // drawTransparencyBmp(canvas);

       drawAvoidMode(canvas);
    }

    /**
     * 使用Mode.TARGET
     * @param canvas
     */
    private void drawTargetMode(Canvas canvas){
        int width  = 300;
        int height = width * mBmp.getHeight()/mBmp.getWidth();
        mPaint.setXfermode(null);
        mPaint.setColor(Color.RED);
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);

        int layerID = canvas.saveLayer(1.1f*width,0,width*2.1f,height,mPaint,Canvas.ALL_SAVE_FLAG);
        //这个图片要绘制到哪里，以原点为准，所以需要520
        canvas.drawBitmap(mBmp,null,new Rect((int) (1.1f*width),0, (int) (width*2.1f),height),mPaint);
        // 当容差为100时，就表示与目标色值的颜色差异在100范围内的都是可以的，而由于最大的颜色差异是255，所以当我们的容差是255时，所有的颜色都将被选中
        mPaint.setXfermode(new AvoidXfermode(Color.WHITE,255, AvoidXfermode.Mode.TARGET));
        //这个还是以dest为准
        //将画笔设置为纯透明
        canvas.drawRect(1.1f*width,0,width*2.1f,height,mPaint);
        canvas.restoreToCount(layerID);
    }

    /**
     * 利用第二个图片(小花图片)来替换选区
     * @param canvas
     */
    private void drawFlowerBmp(Canvas canvas){
        int width  = 500;
        int height = width * mBmp.getHeight()/mBmp.getWidth();
        mPaint.setXfermode(null);
        int layerID = canvas.saveLayer(0,500,width,height+500,mPaint,Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBmp,null,new Rect(0,500,width,height+500),mPaint);
        mPaint.setXfermode(new AvoidXfermode(Color.WHITE,100, AvoidXfermode.Mode.AVOID));
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.flower),null,
                new Rect(0,500,width,height+500),mPaint);
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
        //这里把选取变成全透明了，所以会露出蓝色
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
        mPaint.setXfermode(null);
        mPaint.setColor(Color.RED);

        int layerID = canvas.saveLayer(0, 0, width, height+0, mPaint, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBmp, null, new Rect(0, 0+0, width, height+0), mPaint);
        mPaint.setXfermode(new AvoidXfermode(Color.WHITE, 100, AvoidXfermode.Mode.AVOID));
        canvas.drawRect(0, 0, width, height, mPaint);

        canvas.restoreToCount(layerID);
    }
}

package com.harvic.blogCanvas;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/21.
 */
public class SaveLayerUseExample_3_1 extends View {
    private Paint mPaint;
    private Bitmap mBitmap;

    public SaveLayerUseExample_3_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);


        bmpSkew(canvas);

//        bmpRect(canvas);
    }

    /**
     * 三.1.1saveLayer后的所有动作都只对新建画布有效 DEMO
     * @param canvas
     */
    private void bmpSkew(Canvas canvas){
        int layerID = canvas.saveLayer(0,0,getWidth(),getHeight(),mPaint,Canvas.CLIP_SAVE_FLAG);
        canvas.skew(1.732f,0);
        canvas.drawRect(0,0,150,160,mPaint);
        canvas.restoreToCount(layerID);
    }

    /**
     * 三.1.2 通过Rect指定矩形大小就是新建的画布大小 DEMO
     */
    private void bmpRect(Canvas canvas){
        int layerID = canvas.saveLayer(0, 0, 100, 100, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0, 0, 500, 600, mPaint);
        canvas.restoreToCount(layerID);
    }
}

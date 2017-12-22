package com.harvic.blogCanvas;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/21.
 */
public class MATRIX_SAVE_FLAG_View extends View {
    private Paint mPaint;
    public MATRIX_SAVE_FLAG_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();

        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        saveMatrixRotate(canvas);
//        saveMatrixClip(canvas);
//        saveLayerMatrixRotate(canvas);
//        saveLayerMatrixClip(canvas);

    }

    /**
     * 三.2.1 旋转demo_save
     * @param canvas
     */
    private void saveMatrixRotate(Canvas canvas){
        canvas.drawColor(Color.RED);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate(40);
        canvas.drawRect(100,0,200,100,mPaint);
        canvas.restore();

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(100,0,200,100,mPaint);
    }

    /**
     * 三.2.1 裁剪demo_save
     * @param canvas
     */
    private void saveMatrixClip(Canvas canvas){
        canvas.drawColor(Color.RED);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.clipRect(100,0,200,100);
        canvas.drawColor(Color.GREEN);
        canvas.restore();

        canvas.drawColor(Color.YELLOW);
    }

    /**
     * 三.2.2 旋转demo_saveLayer
     * @param canvas
     */
    private void saveLayerMatrixRotate(Canvas canvas){
        canvas.drawColor(Color.RED);
        canvas.saveLayer(0,0,getWidth(),getHeight(),mPaint,Canvas.MATRIX_SAVE_FLAG|Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.rotate(40);
        canvas.drawRect(100,0,200,100,mPaint);
        canvas.restore();

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(100,0,200,100,mPaint);
    }
    /**
     * 三.2.2 裁剪demo_saveLayer
     * @param canvas
     */
    private void saveLayerMatrixClip(Canvas canvas){
        canvas.drawColor(Color.GREEN);
        canvas.saveLayer(0,0,getWidth(),getHeight(),mPaint,Canvas.MATRIX_SAVE_FLAG|Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.clipRect(100,0,200,100);
        canvas.restore();

        canvas.drawColor(Color.YELLOW);
    }
}

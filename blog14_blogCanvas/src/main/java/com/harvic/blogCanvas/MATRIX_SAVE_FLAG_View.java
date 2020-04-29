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

        // saveMatrixRotate(canvas);
       // saveMatrixClip(canvas);
       // saveLayerMatrixRotate(canvas);
       saveLayerMatrixClip(canvas);

    }

    /**
     * 三.2.1 旋转demo_save
     * @param canvas
     */
    private void saveMatrixRotate(Canvas canvas){

        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate(40);
        canvas.drawRect(100,0,200,100,mPaint);
        canvas.restore();
        // 很明显，在canvas.restore()后，画布的旋转给恢复到了原来了状态。YELLOW画出来的是正的
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(100,0,200,100,mPaint);
    }

    /**
     * 三.2.1 裁剪demo_save
     * @param canvas
     */
    private void saveMatrixClip(Canvas canvas){
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.clipRect(100,0,200,100);
        canvas.drawColor(Color.GREEN);
        canvas.restore();

        //最后显示的是一小块Yellow，说明MATRIX_SAVE_FLAG不能恢复裁剪后的画布
        canvas.drawColor(Color.YELLOW);
    }

    /**
     * 三.2.2 旋转demo_saveLayer
     * @param canvas
     */
    private void saveLayerMatrixRotate(Canvas canvas){
        canvas.drawColor(Color.RED);
        // 这里在保存Flag时，多了一个Canvas.HAS_ALPHA_LAYER_SAVE_FLAG，
        // 表示在新建的画布在合成到上一个画布上时，直接覆盖，不清空所在区域原图像
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
        //最后是根据flag去恢复，restore前的画布根据保存的信息去恢复
        canvas.saveLayer(0,0,getWidth(),getHeight(),mPaint,Canvas.MATRIX_SAVE_FLAG|Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.clipRect(100,0,200,100);
        canvas.restore();
        // clipRect这些连累也只表现在画布以后的绘图上，之前画过的图像不会受到影响。
        canvas.drawColor(Color.YELLOW);
    }
}

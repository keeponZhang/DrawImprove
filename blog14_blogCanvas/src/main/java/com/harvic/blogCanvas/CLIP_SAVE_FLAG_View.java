package com.harvic.blogCanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/21.
 */
public class CLIP_SAVE_FLAG_View extends View {
    private Paint mPaint;
    public CLIP_SAVE_FLAG_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // saveClipClip(canvas);
//        saveClipRotate(canvas);

       // saveLayerClipClip(canvas);
        saveLayerClipRotate(canvas);
    }

    /**
     * 三.3.1 裁剪demo_save
     * @param canvas
     */
    private void saveClipClip(Canvas canvas){
        canvas.drawColor(Color.RED);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(100,0,200,100);
        canvas.restore();

        //这里黄色是全屏的，因为前面虽然是clipRect，但是restore恢复了
        canvas.drawColor(Color.YELLOW);
    }

    /**
     * 三.3.1 旋转demo_save
     * @param canvas
     */
    private void saveClipRotate(Canvas canvas){
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(100,0,200,100,mPaint);

        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.rotate(40);
        canvas.restore();

        //这里画黄色举行的是旋转的，因为restore没有恢复旋转信息
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(100,0,200,100,mPaint);
    }


    /**
     * 三.3.2 旋转demo_saveLayer
     * @param canvas
     */
    private void saveLayerClipRotate(Canvas canvas){
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(100,0,200,100,mPaint);
        //使用canvas.saveLayer(Canvas.CLIP_SAVE_FLAG)时，需要与Canvas.HAS_ALPHA_LAYER_SAVE_FLAG一起使用，不然新建画布所在区域原来的图像将被清空。
        canvas.saveLayer(0,0,getWidth(),getHeight(),mPaint,Canvas.CLIP_SAVE_FLAG|Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.rotate(40);
        canvas.restore();

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(100,0,200,100,mPaint);
    }
    /**
     * 三.3.2 裁剪demo_saveLayer
     * @param canvas
     */
    private void saveLayerClipClip(Canvas canvas){
        canvas.saveLayer(0,0,getWidth(),getHeight(),mPaint,Canvas.CLIP_SAVE_FLAG|Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.clipRect(100,0,200,100);
        canvas.restore();

        canvas.drawColor(Color.YELLOW);
    }

}

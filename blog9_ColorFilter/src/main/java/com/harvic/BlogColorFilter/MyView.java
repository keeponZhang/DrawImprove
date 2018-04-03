package com.harvic.BlogColorFilter;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/3.
 */
public class MyView extends View {
    private Paint mPaint;
    private Bitmap mBmp;
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        mBmp = BitmapFactory.decodeResource(getResources(),R.drawable.pic_music_c);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);

//        drawLightingFilter(canvas);


        drawPorterDuffFilter(canvas);


    }


    /**
     * 一\2LightingColorFilter示例
     * @param canvas
     */
    private void drawLightingFilter(Canvas canvas){
        int width  = 500;
        int height = width * mBmp.getHeight()/mBmp.getWidth();

        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);

        canvas.save();
        canvas.translate(550,0);
        mPaint.setColorFilter(new LightingColorFilter(0xffffff,0x0000f0));
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
        canvas.restore();
        
        canvas.translate(0,550);
        mPaint.setColorFilter(new LightingColorFilter(0x00ff00,0x000000));
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);

    }


    /**
     * 1 参数1必须是ARGB格式的颜色值,不然无效
     * 2 ADD DARKEN  OVERLAY  MULTIPLY  LIGHTEN有效果
     * 3 SRC 相关只显示SRC图像, DES相关仅显示DES图像
     * 4 可以使用SRC相关仅显示SRC图像的特点,可以实现主题色改变
     * http://yourbay.me/%E6%97%A5%E5%BF%97/2015/07/24/settint-colorfilter/
     * @param canvas
     */
    private void drawPorterDuffFilter(Canvas canvas){
        int width  = 100;
        int height = width * mBmp.getHeight()/mBmp.getWidth();

        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);

        canvas.translate(150,0);
        mPaint.setColorFilter(new PorterDuffColorFilter(0xffff00ff, PorterDuff.Mode.SRC));
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);

        ColorMatrix matrix = new ColorMatrix(new float[]{
                0,0,0,0,0,
                0,0,0,0,240,
                0,0,0,0,255,
                0,0,0,1,0
        });
        canvas.translate(150,0);
        mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
//        mPaint.setColorFilter(new PorterDuffColorFilter(0xff00f0ff, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);

        canvas.translate(150,0);
        mPaint.setColorFilter(new PorterDuffColorFilter(0xfff0f0ff, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);

        canvas.translate(150,0);
        mPaint.setColorFilter(new PorterDuffColorFilter(0xffffff00, PorterDuff.Mode.SRC_OVER));
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);


        canvas.translate(150,0);
        mPaint.setColorFilter(new PorterDuffColorFilter(0xff000000, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);



//        canvas.translate(550,0);
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DST));
//        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
//
//        canvas.translate(-550,550);
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DST_IN));
//        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
//
//        canvas.translate(550,0);
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DST_OUT));
//        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
//
//        canvas.translate(-550,550);
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DST_OVER));
//        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
//
//        canvas.translate(550,0);
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DST_ATOP));
//        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);



//



//        canvas.translate(550,0);
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN));//变暗
//        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
//
//        canvas.translate(-550,550);
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN));//变亮
//        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
//
//        canvas.translate(550,0);
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY));//正片叠底
//        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
//
//        canvas.translate(-550,550);
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.OVERLAY));//叠加
//        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
//
//        canvas.translate(550,0);
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SCREEN));//滤色
//        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
    }


}

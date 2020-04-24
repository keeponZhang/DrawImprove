package com.harvic.BlogPorterDuff;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

//google src_in 原理
public class GoogleXfermodes_SRC_IN extends View {
 
        // create a bitmap with a circle, used for the "dst" image
        static Bitmap makeDst(int w, int h) {
            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bm);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
 
            p.setColor(0xFFFFCC44);
            c.drawOval(new RectF(0, 0, w*3/4, h*3/4), p);
            return bm;
        }
 
        // create a bitmap with a rect, used for the "src" image
    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
 
        p.setColor(0xFF66AAFF);
        // 如果这里有背景，就不会只有四分之一角了
        // c.drawColor(Color.YELLOW);
        c.drawRect(w/3, h/3, w*19/20, h*19/20, p);
        return bm;
    }
    private int width = 400;
    private int height = 400;
    private Bitmap dstBmp;
    private Bitmap srcBmp;
    private Paint mPaint;
 
    public GoogleXfermodes_SRC_IN(Context context, AttributeSet attrs) {
        super(context,attrs);
 
        srcBmp = makeSrc(width, height);
        dstBmp = makeDst(width, height);
        mPaint = new Paint();
    }
 
    @Override 
    protected void onDraw(Canvas canvas) {
        // canvas.drawColor(Color.WHITE);
 
        int layerID = canvas.saveLayer(0,0,width*2,height*2,mPaint,Canvas.ALL_SAVE_FLAG);
        //这里google之所以会显示成一个叫，应为没有长方形的部分全是透明的
        canvas.drawBitmap(dstBmp, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBmp, 0, 0, mPaint);
        mPaint.setXfermode(null);
 
        canvas.restoreToCount(layerID);
    }
}

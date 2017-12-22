package com.harvic.blogCanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/20.
 */
public class BitmapCanvasView extends View {
    private Bitmap mBmp;
    private Paint mPaint;
    private Canvas mBmpCanvas;
    public BitmapCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mBmp = Bitmap.createBitmap(500 ,500 , Bitmap.Config.ARGB_8888);
        mBmpCanvas = new Canvas(mBmp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setTextSize(100);
        //只是将文字画在了mBmpCanvas上
        mBmpCanvas.drawText("启舰大SB",0,100,mPaint);

        //onDraw的canvas会直接绘制到View上
        canvas.drawBitmap(mBmp,0,0,mPaint);

    }
}

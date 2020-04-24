package com.harvic.BlogPorterDuff;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by qijian on 16/4/15.
 */
public class EraserView_DSTOUT extends View {
    // 被擦掉的是原图像
    private Paint mBitPaint;
    private Bitmap BmpDST,BmpSRC;
    private Path mPath;
    private float mPreX,mPreY;
    public EraserView_DSTOUT(Context context, AttributeSet attrs) {
        super(context, attrs);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBitPaint = new Paint();
        mBitPaint.setColor(Color.RED);
        mBitPaint.setStyle(Paint.Style.STROKE);
        mBitPaint.setStrokeWidth(45);

        BmpSRC = BitmapFactory.decodeResource(getResources(),R.drawable.dog,null);
        BmpDST = Bitmap.createBitmap(BmpSRC.getWidth(), BmpSRC.getHeight(), Bitmap.Config.ARGB_8888);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        //先把手指轨迹画到目标Bitmap上
        Canvas c = new Canvas(BmpDST);
        //这里千万不能设置背景，不然全部相交，显示透明，就是没有东西显示在屏幕上
        // c.drawColor(Color.YELLOW);
        c.drawPath(mPath,mBitPaint);

        //然后把目标图像画到画布上
        canvas.drawBitmap(BmpSRC,0,0,mBitPaint);

        //计算源图像区域
        mBitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        //这里为什么途径后来画呢，因为DST_OUT表示与dst相交部分不透明的变透明，所以轨迹部分都变透明了
        canvas.drawBitmap(BmpDST,0,0,mBitPaint);

        mBitPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(),event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX+event.getX())/2;
                float endY = (mPreY+event.getY())/2;
                mPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY =event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }
}

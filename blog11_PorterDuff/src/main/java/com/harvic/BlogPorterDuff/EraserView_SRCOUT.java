package com.harvic.BlogPorterDuff;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by qijian on 16/4/15.
 */
public class EraserView_SRCOUT extends View {
    // Mode.SRC_OUT从公式中可以看出，计算结果的透明度=Sa * (1 - Da)；说当目标图像图像完全透明时，计算结果将是透明的
    // Mode.SRC_OUT简单来说，当目标图像有图像时结果显示空白像素，当目标图像没有图像时，结果显示源图像。
    //逆向思维，被擦掉的是原图像，所以下面的图片是原图像，然后路径最为目标图标，目标图像与原图像相交，显示透明
    private Paint mBitPaint;
    private Bitmap BmpDST,BmpSRC;
    private Path mPath;
    private float mPreX,mPreY;
    public EraserView_SRCOUT(Context context, AttributeSet attrs) {
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
        canvas.drawBitmap(BmpDST,0,0,mBitPaint);

        //计算源图像区域
        mBitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(BmpSRC,0,0,mBitPaint);

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

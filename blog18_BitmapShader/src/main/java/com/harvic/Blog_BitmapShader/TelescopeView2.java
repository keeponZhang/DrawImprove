package com.harvic.Blog_BitmapShader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by qijian on 16/7/10.
 */
public class TelescopeView2 extends View {
    private Paint mPaint;
    private Bitmap mBitmap,mBitmapBG;
    private int mDx = -1, mDy = -1;
    public TelescopeView2(Context context) {
        super(context);
        init();
    }

    public TelescopeView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TelescopeView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_edge);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                postInvalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mDx = -1;
                mDy = -1;
                break;
        }


        postInvalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmapBG == null){
            mBitmapBG = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasbg = new Canvas(mBitmapBG);
            //这里需要注意的就是我们在将原图像画到mBitmapBG时，进行了拉压缩，把它拉伸到根当前控件一样大小。
            canvasbg.drawBitmap(mBitmap,null,new Rect(0,0,getWidth(),getHeight()),mPaint);
        }
//        canvas.drawBitmap(mBitmap,null,new Rect(0,0,getWidth(),getHeight()),mPaint);

        if (mDx != -1 && mDy != -1) {
            mPaint.setShader(new BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            canvas.drawCircle(mDx, mDy, 150, mPaint);
            canvas.drawCircle(mDx+150, mDy, 150, mPaint);
        }
    }

}

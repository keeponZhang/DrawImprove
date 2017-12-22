package com.harvic.tryBitmapShader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by qijian on 16/7/6.
 */
public class BitmapShaderView extends View {
    private Paint mPaint;
    private Bitmap mBitmap, mBitmapAlpha,mBitmapBG;
    private int mDx = -1, mDy = -1;

    public BitmapShaderView(Context context) {
        super(context);
        init();
    }

    public BitmapShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BitmapShaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scenery);
//        mBitmapAlpha = BitmapFactory.decodeResource(getResources(), R.drawable.scenery);



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
            canvasbg.drawBitmap(mBitmap,null,new Rect(0,0,getWidth(),getHeight()),mPaint);
        }
        BitmapShader bitmapShader = new BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f,0.5f);
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

//        canvas.drawColor(Color.BLACK);

//        Log.d("qijian","dx = "+mDx +"  mdy="+mDy);
//        if (mDx != -1 && mDy != -1) {
//            mPaint.setShader(new BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
//            canvas.drawCircle(mDx, mDy, 150, mPaint);
//        }
    }
}




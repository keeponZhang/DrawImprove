package com.harvic.Blog_BitmapShader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/7/10.
 */
public class AvatorViewDemo extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mStrokeWidth;

    public AvatorViewDemo(Context context, AttributeSet attrs) throws Exception{
        super(context, attrs);
        init();
    }

    public AvatorViewDemo(Context context, AttributeSet attrs, int defStyle) throws Exception{
        super(context, attrs, defStyle);
        init();
    }

    private void init() throws Exception{
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.avator);

        mPaint = new Paint();
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //将BitmapShader缩放到与控件宽高一致
        Matrix matrix = new Matrix();
        float scale = (float) getWidth() / mBitmap.getWidth();
        matrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(mBitmapShader);

        float half = getWidth() / 2;
        canvas.drawCircle(half, half, (getWidth() - 2 * mStrokeWidth) / 2, mPaint);

    }
}

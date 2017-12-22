package com.harvic.Blog_BitmapShader;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Shader.TileMode;

/**
 * Created by qijian on 16/7/8.
 */
public class BitmapShaderView extends View {
    private Paint mPaint;
    private Bitmap mBmp;
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

    private void init(){
        mPaint = new Paint();
        mBmp = BitmapFactory.decodeResource(getResources(),R.drawable.dog_edge);
        //填充Y轴，然后再填充X轴！
        mPaint.setShader(new BitmapShader(mBmp, TileMode.MIRROR, TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);
        //无论你利用绘图函数绘多大一块，在哪绘制，与Shader无关。因为Shader总是在控件的左上角开始，而你绘制的部分只是显示出来的部分而已
        // 。没有绘制的部分虽然已经生成，但只是不会显示出来罢了。
        canvas.drawRect(100,20,200,200,mPaint);
    }
}

package com.harvic.tryBitmapShader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/7/6.
 */
public class BitmapShaderView1 extends View {
	private Paint  mPaint;
	private Bitmap mBmp;

	public BitmapShaderView1(Context context) {
		super(context);
		init();
	}

	public BitmapShaderView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BitmapShaderView1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mBmp = BitmapFactory.decodeResource(getResources(), R.drawable.dog_edge);
//		mPaint.setShader(new BitmapShader(mBmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
		mPaint.setShader(new BitmapShader(mBmp, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));
		//填充Y轴，然后再填充X轴！
//		mPaint.setShader(new BitmapShader(mBmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//getWidth()用于获取控件宽度，getHeight()用于获取控件高度
//		canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

		//无论你利用绘图函数绘多大一块，在哪绘制，与Shader无关。因为Shader总是在控件的左上角开始，而你绘制的部分只是显示出来的部分而已
		// 。没有绘制的部分虽然已经生成，但只是不会显示出来罢了。
		canvas.drawRect(100,20,200,200,mPaint);
	}


}




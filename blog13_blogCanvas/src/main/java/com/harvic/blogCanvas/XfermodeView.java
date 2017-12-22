package com.harvic.blogCanvas;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/20.
 */
public class XfermodeView extends View {
	private int width  = 400;
	private int height = 400;
	private Bitmap dstBmp;
	private Bitmap srcBmp;
	private Paint  mPaint;

	public XfermodeView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		srcBmp = makeSrc(width, height);
		dstBmp = makeDst(width, height);
		mPaint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.GREEN);


		//saveLayer时，会生成了一个全新的bitmap，这个bitmap的大小就是我们指定的保存区域的大小，新生成的bitmap是全透明的，
 		int layerID = canvas.saveLayer(0, 0, width * 2, height * 2, mPaint, Canvas.ALL_SAVE_FLAG);

		canvas.drawBitmap(dstBmp, 0, 0, mPaint);

		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(srcBmp, width / 2, height / 2, mPaint);

		mPaint.setXfermode(null);

		//只有当调用restore()、resoreToCount()函数以后，才会返回到原始画布上绘制。
		//        canvas.restoreToCount(layerID);
	}

	// create a bitmap with a circle, used for the "dst" image
	static Bitmap makeDst(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

		p.setColor(0xFFFFCC44);
		c.drawOval(new RectF(0, 0, w, h), p);
		return bm;
	}

	// create a bitmap with a rect, used for the "src" image
	static Bitmap makeSrc(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

		p.setColor(0xFF66AAFF);
		c.drawRect(0, 0, w, h, p);
		return bm;
	}
}

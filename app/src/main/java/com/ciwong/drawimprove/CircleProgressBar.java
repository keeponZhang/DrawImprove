package com.ciwong.drawimprove;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by keepon on 2018/1/26.
 */

public class CircleProgressBar extends android.support.v7.widget.AppCompatImageView {
	public CircleProgressBar(Context context) {
		this(context,null);
	}

	public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	private int mCurrentStatu;

	private Paint paint;
	private final int NORMAL_COLOR = Color.parseColor("#97DE84");

	private final int ERROR_COLOR = Color.parseColor("#C8C8C8");

	private final int DOWNLOADING_COLOR = Color.parseColor("#19B300");

	private final int RED_COLOR = Color.parseColor("#FF0000");

	private final int GRAY_COLOR = Color.parseColor("#B0B1B0");

	private final int ARROW_COLOR = Color.parseColor("#d4d4d4");
	private int mCurrentColor;
	private RectF rectF;
	private int RADIU;
	private float progress, animationProgress;
	private int degree, degreeToGo;
	private Canvas canvas;
	private final float SPEED_FACTOR = 0.03f;
	private final float PERIMETER = 0.9f;
	private void init() {
		this.mCurrentStatu = DownloadStatus.STATUS_NO_STATU;
		this.mCurrentColor = NORMAL_COLOR;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(mCurrentColor);
	}

	private void setRectF()
	{
		if (rectF == null)
		{
			rectF = new RectF();
			rectF.left = getMeasuredWidth() / 2 - RADIU;
			rectF.top = getMeasuredHeight() / 2 - RADIU;
			rectF.right = getMeasuredWidth() / 2 + RADIU;
			rectF.bottom = getMeasuredHeight() / 2 + RADIU;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.e(TAG, "onDraw:" );
		animationProgress = 0f;
		this.canvas = canvas;
		paint.setColor(mCurrentColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setAlpha(255);
		int centerX = getMeasuredWidth() / 2;
		int centerY = getMeasuredHeight() / 2;
		if (RADIU == 0)
		{
			this.RADIU = (int) (getMeasuredHeight() * 0.25f);
		}
		super.onDraw(canvas);
		setRectF();
		canvas.drawCircle(centerX, centerY, RADIU, paint);// 圆形
//		drawPause(centerX, centerY, 255, true);
		animationProgress =1;
//		drawCompleted(centerX, centerY);
//		drawFailed(centerX, centerY);

		if(mCurrentStatu==DownloadStatus.STATUS_DOWNLOADING){
			Log.e(TAG, "onDraw: STATUS_DOWNLOADING" );
			drawDownloading(255, true);
		}

//		drawCompletedToNormal(centerX,centerY);
//		if (mCurrentStatu == DownloadStatus.STATUS_LOADING)
//		{
//			degree = degree + 5;
//			canvas.rotate(degree, centerX, centerY);
//			invalidate();
//			canvas.drawArc(rectF, -90, 360 * PERIMETER, false, paint);
//		}
	}



	private void drawPause(int centerX, int centerY, int alpha,
	                       boolean isDrawArc)
	{
		if (isDrawArc)
		{
			canvas.drawArc(rectF, -90, 360, false, paint);
		}
		paint.setStrokeWidth(3);
		paint.setAlpha(alpha);
		canvas.drawLine(centerX - centerX / 8, centerY - centerY / 5,
				centerX - centerX / 8, centerY + centerY / 5, paint);
		canvas.drawLine(centerX + centerX / 8, centerY - centerY / 5,
				centerX + centerX / 8, centerY + centerY / 5, paint);
	}
	private boolean isArrow = false;

	private void drawCompleted(int centerX, int centerY)
	{
		if (isArrow)
		{
			paint.setStrokeWidth(4);
			paint.setColor(ARROW_COLOR);
			canvas.drawLine(centerX - centerX / 5, centerY / 2 + centerY / 8,
					centerX + centerX / 5, centerY, paint);
			canvas.drawLine(centerX - centerX / 5,
					centerY * 3 / 2 - centerY / 8, centerX + centerX / 5,
					centerY, paint);
		}
		else
		{
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(centerX, centerY, RADIU, paint);
			canvas.drawArc(rectF, -90, 360 * (1 - animationProgress), false,
					paint);
			if (animationProgress >= 0 && animationProgress <= 0.3f)
			{
				canvas.drawLine(centerX - centerX / 4, centerY,
						centerX - centerX / 4
								+ centerX * animationProgress / 0.3f / 4,
						centerY + centerY * animationProgress / 0.3f / 4,
						paint);
			}
			else if (animationProgress > 0.3f && animationProgress <= 1)
			{
				int x = centerX / 8;
				canvas.drawLine(centerX - centerX / 4 - x, centerY,
						centerX - centerX / 4 + centerX / 4 - x,
						centerY + centerY / 4, paint);
				canvas.drawLine(centerX - x, centerY + centerY / 4,
						centerX + centerX * (animationProgress - 0.3f) / 2
								- x / 2,
						centerY + centerY / 4
								- centerY * (animationProgress - 0.3f) * 3 / 4,
						paint);
			}
		}
	}


	private void drawFailed(int centerX, int centerY)
	{
		paint.setColor(RED_COLOR);
		// canvas.drawLine(centerX, centerY,
		// centerX - centerX / 4 * (1 - animationProgress),
		// centerY - centerY / 4 * (1 - animationProgress), paint);
		// canvas.drawLine(centerX, centerY,
		// centerX + centerX / 4 * (1 - animationProgress),
		// centerY - centerY / 4 * (1 - animationProgress), paint);
		// canvas.drawLine(centerX, centerY,
		// centerX - centerX / 4 * (1 - animationProgress),
		// centerY + centerY / 4 * (1 - animationProgress), paint);
		// canvas.drawLine(centerX, centerY,
		// centerX + centerX / 4 * (1 - animationProgress),
		// centerY + centerY / 4 * (1 - animationProgress), paint);
		// 小圆点
		canvas.drawCircle(centerX, centerY * 5 / 4, 2, paint);
		Paint pointPaint = paint;
		pointPaint.setStrokeWidth(5);
		// 中间竖线
		canvas.drawLine(centerX, centerY / 3 * 2, centerX, centerY * 10 / 9,
				pointPaint);
		// canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
		// R.mipmap.icon_submit_fail_tip), 0, 0, paint);
		paint.setColor(NORMAL_COLOR);
	}
	private void drawDownloading(int alpha, boolean isDrawArc)
	{
		String text = String.valueOf(Math.round(progress * 100))+"%";
		Log.e(TAG, "drawDownloading: "+text);
		if (isDrawArc)
		{
			paint.setColor(DOWNLOADING_COLOR);
			canvas.drawArc(rectF, -90, 360 * progress, false, paint);
		}
		paint.setColor(GRAY_COLOR);
		paint.setStrokeWidth(1);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(getMeasuredWidth() * 0.2f);
		float tX = (getMeasuredWidth() - getFontlength(paint, text)) / 2;
		float tY = (getMeasuredHeight() - getFontHeight(paint)) / 2
				+ getFontLeading(paint);
		paint.setAlpha(alpha);
		canvas.drawText(text, tX, tY, paint);
		paint.setColor(NORMAL_COLOR);
	}

	/**
	 * @return 返回指定笔和指定字符串的长度
	 */
	public float getFontlength(Paint paint, String str)
	{
		return paint.measureText(str);
	}

	/**
	 * @return 返回指定笔的文字高度
	 */
	public float getFontHeight(Paint paint)
	{
		Paint.FontMetrics fm = paint.getFontMetrics();
		return fm.descent - fm.ascent;
	}
	/**
	 * @return 返回指定笔离文字顶部的基准距离
	 */
	public float getFontLeading(Paint paint)
	{
		Paint.FontMetrics fm = paint.getFontMetrics();
		return fm.leading - fm.ascent;
	}


	private void drawCompletedToNormal(int centerX, int centerY)
	{
		// 左边斜线
		canvas.drawLine(centerX - centerX / 4, centerY,
				centerX - centerX / 4 + centerX / 4, centerY + centerY / 4,
				paint);
		// 下边横线
		// canvas.drawLine(centerX * 3 / 4 + centerX * animationProgress / 4,
		// centerY * 5 / 4,
		// centerX * 5 / 4 - centerX * animationProgress / 4,
		// centerY * 5 / 4, paint);
		// 中间竖线
		canvas.drawLine(centerX, centerY * 5 / 4,
				centerX + centerX * (1 - animationProgress) / 4,
				centerY + centerY / 4 * animationProgress, paint);
		// 右边斜线
		canvas.drawLine(centerX, centerY + centerY / 4,
				centerX + centerX * animationProgress / 2, centerY / 2, paint);
	}


	private static final String TAG = "CircleProgressBar";
	public void updateDownloadProgress(float progress)
	{
		this.progress = progress;
		this.mCurrentStatu = DownloadStatus.STATUS_DOWNLOADING;
		Log.e(TAG, "updateDownloadProgress: "+Thread.currentThread().getName() );
        invalidate();
		Log.e(TAG, "updateDownloadProgress: "+progress );
	}

}

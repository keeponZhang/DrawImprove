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
	// 上面我们讲到了画布(Bitmap)、图层(Layer)和Canvas的概念，估计大家都会被绕晕了；下面我们下面来具体讲解下它们之间的关系。
	// 图层(Layer)：每一次调用canvas.drawXXX系列函数时，都会生成一个透明图层来专门来画这个图形，比如我们上面在画矩形时的透明图层就是这个概念。
	// 画布(bitmap)：每一个画布都是一个bitmap，所有的图像都是画在bitmap上的！我们知道每一次调用canvas.drawxxx函数时，都会生成一个专用的透明图层来画这个图形，
	//          画完以后，就盖在了画布上。所以如果我们连续调用五个draw函数，那么就会生成五个透明图层，画完之后依次盖在画布上显示。
	//         画布有两种，第一种是view的原始画布，是通过onDraw（Canvas
	//  		canvas）函数传进来的，其中参数中的canvas就对应的是view的原始画布，控件的背景就是画在这个画布上的！
	// 			另一种是人造画布，通过saveLayer()、new Canvas(bitmap)这些方法来人为新建一个画布。尤其是saveLayer()，一旦调用saveLayer()新建一个画布以后，
	// 		以后的所有draw函数所画的图像都是画在这个画布上的，只有当调用restore()、resoreToCount()函数以后，才会返回到原始画布上绘制。
	// Canvas:这个概念比较难理解，我们可以把Canvas理解成画板，Bitmap理解成透明画纸，而Layer则理解成图层；每一个draw函数都对应一个图层，在一个图形画完以后，
	// 			就放在画纸上显示。而一张张透明的画纸则一层层地叠加在画板上显示出来。我们知道画板和画纸都是用夹子夹在一起的，所以当我们旋转画板时，所有画纸都会跟着旋转！
	// 			当我们把整个画板裁小时，所以的画纸也都会变小了！
	// 		这一点非常重要，当我们利用saveLayer来生成多个画纸时，然后最上层的画纸调用canvas.rotate(30)是把画板给旋转了，所有的画纸也都被旋转30度！这一点非常注意
	// 		另外，如果最上层的画纸调用canvas.clipRect()将画板裁剪了，那么所有的画纸也都会被裁剪。唯一能够恢复的操作是调用canvas.revert()把上一次的动作给取消掉！
	// 		————————————————
	// 版权声明：本文为CSDN博主「启舰」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
	// 原文链接：https://blog.csdn.net/harvic880925/article/details/51317746

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.GREEN);


		//saveLayer时，会生成了一个全新的bitmap，这个bitmap的大小就是我们指定的保存区域的大小，新生成的bitmap是全透明的，
		//恢复之后又是全屏
 		int layerID = canvas.saveLayer(0, 0, width*2, width*2, mPaint, Canvas.ALL_SAVE_FLAG);

		canvas.drawBitmap(dstBmp, 0, 0, mPaint);

		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(srcBmp, width / 2, height / 2, mPaint);

		mPaint.setXfermode(null);

		//只有当调用restore()、resoreToCount()函数以后，才会返回到原始画布上绘制。(经测试好像会合成)
		//        canvas.restoreToCount(layerID);
		// canvas.drawColor(Color.BLUE);
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

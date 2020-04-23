package com.example.try_canvas_blog;

/**
 * created by harvic
 * 2014/9/4
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class MyRegionView extends View {

	public MyRegionView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);


		//		translate(canvas);
		//		canvasRelationship(canvas);
		//		rotate(canvas);
		//		scale(canvas);
				skew(canvas);
		//		clip(canvas);
//		saveAndRestore(canvas);
// 		saveAndRestorComplex(canvas);


	}

	private void saveAndRestorComplex(Canvas canvas) {
		//保存的画布大小为全屏幕大小
		canvas.save();
		canvas.drawColor(Color.RED);
		canvas.clipRect(new Rect(100, 100, 800, 800));
		canvas.drawColor(Color.GREEN);
		//保存画布大小为Rect(100, 100, 800, 800)
		canvas.save();

		canvas.clipRect(new Rect(200, 200, 700, 700));
		canvas.drawColor(Color.BLUE);
		//保存画布大小为Rect(200, 200, 700, 700)
		canvas.save();

		canvas.clipRect(new Rect(300, 300, 600, 600));
		canvas.drawColor(Color.BLACK);
		//保存画布大小为Rect(300, 300, 600, 600)
		canvas.save();

		canvas.clipRect(new Rect(400, 400, 500, 500));
		canvas.drawColor(Color.WHITE);
		//
		//	//连续出栈三次，将最后一次出栈的Canvas状态作为当前画布，并画成黄色背景
		//	canvas.restore();
		//	canvas.restore();
		//	canvas.restore();
		//	canvas.drawColor(Color.YELLOW);
		//
	}

	private void saveAndRestore(Canvas canvas) {
		/////////---------------初次演示Save和Restore的关系
		canvas.drawColor(Color.RED);

		//保存当前画布大小即整屏
		canvas.save();

		canvas.clipRect(new Rect(100, 100, 800, 800));
		canvas.drawColor(Color.GREEN);

		//恢复整屏画布
		canvas.restore();

		canvas.drawColor(Color.BLUE);
	}

	private void clip(Canvas canvas) {
		////////////////////------------canvas画布裁剪
		canvas.drawColor(Color.RED);
		canvas.clipRect(new Rect(100, 100, 200, 200));
		canvas.drawColor(Color.GREEN);
	}

	private void skew(Canvas canvas) {
		///////////////////////--------------------skew 扭曲
		Paint paint_green = generatePaint(Color.GREEN, Style.STROKE, 5);
		Paint paint_red = generatePaint(Color.RED, Style.STROKE, 5);
		Paint paint_blue = generatePaint(Color.BLUE, Style.STROKE, 5);

		Rect rect1 = new Rect(10, 10, 200, 100);

		canvas.drawRect(rect1, paint_green);
		int angel = 60;
		// Math.PI = 180
		double rad = 2 * Math.PI * (angel / 360d);
		float tan = (float) Math.tan(rad);
		Log.e("TAG", "MyRegionView skew tan:"+tan);

		// 比如，我画一个矩形，其中一个顶点是（200，300），
		// x=x0+k*y0
		// y=y0
		// skew（1,0），对于x斜切 那么k就为1，x轴最终坐标为200+300*1=500，y轴不变，最终是（500,300）
		//
		// skew（0,1）就是对于y轴斜切，上面的公式就变成
		// x=x0
		// y=y0+k*x0
		// Y轴最终坐标为200*1+300 = 500，X轴不变，也就是最终变成（200,500）

		// 错切原理
		// https://blog.csdn.net/ghy_111/article/details/78056441




		// canvas.skew(1.732f, 0);//X轴倾斜60度，Y轴不变
		canvas.save();
		canvas.skew(0, tan);
		canvas.drawRect(rect1, paint_red);
		canvas.drawLine(10,10, 200, 100,paint_red);
		// canvas.drawLine(10,10, 10+400, 10,paint_blue);
		canvas.restore();
		//针对200,100这个点,适合这个公式 x=x0,y=y0+k*x0
		int changeX = 200;
		int changeY = 300;
		//针对200,100这个点,适合这个公式x=x0+k*y0,y=y0
		int changeX2 = 300;
		int changeY2 = 100;
		canvas.drawLine(changeX,changeY,changeX+30,changeY,paint_blue);
		canvas.drawLine(changeX2,changeY2,changeX2+30,changeY2,paint_blue);
	}

	private void scale(Canvas canvas) {
		///////////////------------------------scale 缩放坐标系密度
		Paint paint_green = generatePaint(Color.GREEN, Style.STROKE, 5);
		Paint paint_red = generatePaint(Color.RED, Style.STROKE, 5);
		Paint paint_gray = generatePaint(Color.GRAY, Style.STROKE, 5);

		Rect rect1 = new Rect(10, 10, 200, 100);
		canvas.drawRect(rect1, paint_green);

		canvas.scale(0.5f, 1);
		canvas.drawRect(rect1, paint_red);
	}

	private void rotate(Canvas canvas) {
		////----------------------Rotate Canvas正方向旋转30度
		Paint paint_green = generatePaint(Color.GREEN, Style.FILL, 5);
		Paint paint_red = generatePaint(Color.RED, Style.STROKE, 5);

		Rect rect1 = new Rect(300, 10, 500, 100);
		canvas.drawRect(rect1, paint_red); //画出原轮廓


		//它的旋转中心点是原点（0，0）
		canvas.rotate(30);//顺时针旋转画布
		canvas.drawRect(rect1, paint_green);//画出旋转后的矩形
	}

	//////////-----------屏幕显示与Canvas的关系
	private void canvasRelationship(Canvas canvas) {

		////构造两个画笔，一个红色，一个绿色
		Paint paint_green = generatePaint(Color.GREEN, Style.STROKE, 5);
		Paint paint_red = generatePaint(Color.RED, Style.STROKE, 5);

		//构造一个矩形
		Rect rect1 = new Rect(0, 0, 400, 220);

		//在平移画布前用绿色画下边框
		canvas.drawRect(rect1, paint_green);

		////平移画布后,再用红色边框重新画下这个矩形
		canvas.translate(100, 100);
		canvas.drawRect(rect1, paint_red);


		//下面对上面的知识做一下总结：
		//		1、每次调用canvas.drawXXXX系列函数来绘图进，都会产生一个全新的Canvas画布。
		//		2、如果在DrawXXX前，调用平移、旋转等函数来对Canvas进行了操作，那么这个操作是不可逆的！每次产生的画布的最新位置都是这些操作后的位置。（关于Save()、Restore()的画布可逆问题的后面再讲）
		//		3、在Canvas与屏幕合成时，超出屏幕范围的图像是不会显示出来的。
	}

	private void translate(Canvas canvas) {
		//////------------translate  平移,即改变坐标系原点位置
		canvas.drawColor(Color.RED);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStyle(Style.FILL);

		canvas.translate(100, 100);
		Rect rect1 = new Rect(0, 0, 400, 220);
		canvas.drawRect(rect1, paint);
	}

	private Paint generatePaint(int color, Paint.Style style, int width) {
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setStyle(style);
		paint.setStrokeWidth(width);
		return paint;
	}
}





















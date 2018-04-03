package com.example.try_region_blog;

/**
 * created by harvic
 * 2014/9/4
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
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
//		set(canvas);
//		setPath(canvas);
		//////////////////--------------区域的合并、交叉等操作
//		//构造两个矩形
		Rect rect1 = new Rect(100,120,400,220);
		Rect rect2 = new Rect(200,20,300,320);

		//构造一个画笔，画出矩形轮廓
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2);

		canvas.drawRect(rect1, paint);
		canvas.drawRect(rect2, paint);
		
		
		
//		构造两个Region
		Region region = new Region(rect1);
		Region region2= new Region(rect2);

//		//取两个区域的交集		
//		region.op(region2, Region.Op.INTERSECT);
		
//		//DIFFERENCE,补集,最终区域为region1 与 region2不同的区域  
//		region.op(region2, Region.Op.DIFFERENCE);

//		//REPLACE,替换,得用传入的区域替换当前的区域
//		region.op(region2, Region.Op.REPLACE);

//		//REVERSE_DIFFERENCE,相当于region2.op(region, Op.INTERSECT);即最终区域为region2 与 region1不同的区域  
//		region.op(region2, Region.Op.REVERSE_DIFFERENCE);

//		UNION,并集
//		region.op(region2, Region.Op.UNION);

//		//XOR,异并集
		region.op(region2, Region.Op.XOR);
//
//		//再构造一个画笔,填充Region操作结果
		Paint paint_fill = new Paint();
		paint_fill.setColor(Color.GREEN);
		paint_fill.setStyle(Style.FILL);
		drawRegion(canvas, region, paint_fill);



	}

	private void setPath(Canvas canvas) {
		//////////-----------使用SetPath（）构造不规则区域
		//初始化Paint
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(2);
		//构造一个椭圆路径
		Path ovalPath = new Path();
		RectF rect =  new RectF(50, 50, 200, 500);
		ovalPath.addOval(rect, Path.Direction.CCW);
		//SetPath时,传入一个比椭圆区域小的矩形区域,让其取交集
		Region rgn = new Region();
		rgn.setPath(ovalPath,new  Region(50, 50, 200, 200));
		//画出路径
		drawRegion(canvas, rgn, paint);
	}

	private void set(Canvas canvas) {
		////--------------------品味Set函数的概念
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(2);

		Region rgn = new Region(10,10,100,100);

//		rgn.set(100, 100, 200, 200);
		drawRegion(canvas, rgn, paint);
	}

	private void drawRegion(Canvas canvas,Region rgn,Paint paint)
	{
		RegionIterator iter = new RegionIterator(rgn);
		Rect r = new Rect();
		
		while (iter.next(r)) {
		  canvas.drawRect(r, paint);
		} 
	}
}


















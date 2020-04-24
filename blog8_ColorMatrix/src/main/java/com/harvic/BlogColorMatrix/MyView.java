package com.harvic.BlogColorMatrix;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/3/31.
 */
public class MyView extends View {
    private Paint mPaint = new Paint();
    private Bitmap bitmap;// 位图
    private Bitmap bitmapFish;// 位图

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint.setAntiAlias(true);
        // 获取位图
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog);
        bitmapFish = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // drawRect(canvas);
        drawBitmap(canvas);
    }

    private void drawRect(Canvas canvas) {
        mPaint.setARGB(255, 200, 100, 100);
        // 绘制原始位图
        canvas.drawRect(0, 0, 500, 600, mPaint);

        canvas.translate(550, 0);
        // 生成色彩矩阵
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawRect(0, 0, 500, 600, mPaint);
    }

    private void drawBitmap(Canvas canvas) {
        canvas.drawBitmap(bitmap, null,
                new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);

        canvas.save();
        canvas.translate(510, 0);
        //只保留蓝色通道
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });

        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, null,
                new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);
        canvas.restore();


        canvas.save();
        //色彩的平移运算，实际上就是色彩的加法运算。其实就是在色彩变换矩阵的最后一行加上某个值；这样可以增加特定色彩的饱和度
        //在绿色值上添加增量50，即增大绿色的饱和度
        canvas.translate(0, 500);
        ColorMatrix colorMatrix2 = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 50,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });

        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
        canvas.drawBitmap(bitmap, null,
                new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);

        canvas.translate(510, 0);
        // 色彩反转就是求出每个色彩的补值来做为目标图像的对应颜色值
        ColorMatrix colorMatrix3 = new ColorMatrix(new float[]{
                -1, 0, 0, 0, 255,
                0, -1, 0, 0, 255,
                0, 0, -1, 0, 255,
                0, 0, 0, 1, 0

        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix3));
        canvas.drawBitmap(bitmap, null,
                new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);
        canvas.restore();


        canvas.save();
        canvas.translate(0, 500*2);
        //我们可以针对某一个颜色值进行放大缩小运算，但当对R、G、B、A同时进行放大缩小时，就是对亮度进行调节！
        ColorMatrix colorMatrix5 = new ColorMatrix(new float[]{
                1.2f, 0, 0, 0, 0,
                0, 1.2f, 0, 0, 50,
                0, 0, 1.2f, 0, 0,
                0, 0, 0, 1.2f, 0,

        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix5));
        canvas.drawBitmap(bitmap, null,
                new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);




        canvas.translate(510, 0);
        // 缩放变换的特殊应用（通道输出）,这里是红色通道输出
        ColorMatrix colorMatrix6 = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0,

        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix6));
        canvas.drawBitmap(bitmap, null,
                new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);
        canvas.restore();


        canvas.save();
        canvas.translate(0, 500*3);
        //色彩投射的一个最简单应用就是变为黑白图片：
        //要把RGB三通道的色彩信息设置成一样；即：R＝G＝B，那么图像就变成了灰色，并且，为了保证图像亮度不变，同一个通道中的R+G+B=1:如：0.213+0.715+0.072＝1；
        ColorMatrix colorMatrix7 = new ColorMatrix(new float[]{
                0.213f, 0.715f, 0.072f, 0, 0,
                0.213f, 0.715f, 0.072f, 0, 0,
                0.213f, 0.715f, 0.072f, 0, 0,
                0,       0,    0, 1, 0,

        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix7));
        canvas.drawBitmap(bitmap, null,
                new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);


        canvas.translate(510, 0);
        //投射运算的另一个应用是照片变旧，对应矩阵如下：
        ColorMatrix colorMatrix8 = new ColorMatrix(new float[]{
                1/2f,1/2f,1/2f,0,0,
                1/3f,1/3f,1/3f,0,0,
                1/4f,1/4f,1/4f,0,0,
                0,0,0,1,0


        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix8));
        canvas.drawBitmap(bitmap, null,
                new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);

        canvas.restore();







        canvas.save();
        canvas.translate(0, 500*4);
        mPaint.setColorFilter(null);
        canvas.drawBitmap(bitmapFish, null,
                new Rect(0, 0, 500, 500 * bitmapFish.getHeight() / bitmapFish.getWidth()), mPaint);

        canvas.translate(510, 0);
        // 投射运算的另一个应用是：色彩反色
        ColorMatrix colorMatrix9 = new ColorMatrix(new float[]{
                0,1,0,0,0,
                1,0,0,0,0,
                0,0,1,0,0,
                0,0,0,1,0

        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix9));
        canvas.drawBitmap(bitmapFish, null,
                new Rect(0, 0, 500,500 * bitmapFish.getHeight() / bitmapFish.getWidth()), mPaint);
    }
}

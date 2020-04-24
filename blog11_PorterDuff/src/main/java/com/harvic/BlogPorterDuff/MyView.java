package com.harvic.BlogPorterDuff;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/14.
 */
public class MyView extends View {
    private int width = 400;
    private int height = 400;
    private Bitmap dstBmp;
    private Bitmap srcBmp;
    private Paint mPaint;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        srcBmp = makeSrc(width, height);
        dstBmp = makeDst(width, height);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 两个图像重合的区域才会有颜色值变化,这里的重合区域跟google示例的重合区域是不一样的（这里重合主要是看src，src是从原点开始画的）


        //已经在画布的表示dest
        //有这个，背景定义蓝色，没有则是透明色，差别很大，这个是重点,但是如果有saveLayer,
        // 纯色背景也没用，saveLayer会有一个透明背景，但是可以在saveLayer之后为canvas设置背景
        // canvas.drawColor(Color.BLUE);


        int layerID = canvas.saveLayer(0, 0, width * 2, height * 2, mPaint, Canvas.ALL_SAVE_FLAG);
        // canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(dstBmp, 0, 0, mPaint);
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


        /*-------------------------------------颜色叠加相关模式--------------------------------------------------*/

        //Mode.ADD（饱和度相加）
        //它的公式是Saturate(S + D)；ADD模式简单来说就是对SRC与DST两张图片相交区域的饱和度进行相加
        // 从效果图中可以看出，只有源图与目标图像相交的部分的图像的饱和度产生了变化，没相交的部分是没有变的，因为对方的饱和度是0，当然不相交的位置饱和度是不会变的。
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));


        //Mode.LIGHTEN（变亮）
        // 它的算法是： [Sa + Da - Sa*Da,Sc*(1 - Da) + Dc*(1 - Sa) + max(Sc, Dc)]
        // 这个效果比较容易理解，两个图像重合的区域才会有颜色值变化，所以只有重合区域才有变亮的效果，
        // 源图像非重合的区域，由于对应区域的目标图像是空白像素，所以直接显示源图像,如果不是透明。(应用到书架)
        //如果Da = 0,DC = 0,最后[sa,sc]
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));


        //Mode.DARKEN（变暗）
        //对应公式是： [Sa + Da - Sa*Da,Sc*(1 - Da) + Dc*(1 - Sa) + max(Sc, Dc)]
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));


        //Mode.MULTIPLY(正片叠底)
        // 公式是：[Sa * Da, Sc * Dc]
        // 相交区域正片叠底后的颜色确实是绿色的，但源图像的非相交区域怎么没了？
        // 我们来看下他的计算公式：[Sa * Da, Sc * Dc]，在计算alpha值时的公式是Sa * Da，
        // 是用源图像的alpha值乘以目标图像的alpha值；由于源图像的非相交区域所对应的目标图像像素的alpha是0，
        // 所以结果像素的alpha值仍是0，所以源图像的非相交区域在计算后是透明的。
        // 在两个图像的相交区域的混合方式是与photoshop中的正片叠底效果是一致的。
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));

        // Mode.OVERLAY（叠加）
        // 虽然没有给出公式，但从效果图中可以看到，源图像交合部分有效果，非交合部分依然是存在的，这就可以肯定一点，当目标图像透明时，在这个模式下源图像的色值不会受到影响；
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));


        //Mode.SCREEN（滤色）
        // 对应公式是：[Sa + Da - Sa * Da, Sc + Dc - Sc * Dc]
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));

        // 到这里，这六种混合模式就讲完了，我们下面总结一下：
        // 1、这几种模式都是PhotoShop中存在的模式，是通过计算改变交合区域的颜色值的
        // 2、除了Mode.MULTIPLY(正片叠底)会在目标图像透明时将结果对应区域置为透明，其它图像都不受目标图像透明像素影响，
        // 即源图像非交合部分保持原样。



        /*-------------------------------------SRC相关模式--------------------------------------------------*/

        //Mode.SRC
        //[Sa, Sc]
        // 从公式中也可以看出，在处理源图像所在区域的相交问题时，全部以源图像显示
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));


        //Mode.SRC_IN
        // 公式为：[Sa * Da, Sc * Da]
        // 在这个公式中结果值的透明度和颜色值都是由Sa,Sc分别乘以目标图像的Da来计算的。所以当目标图像为空白像素时，计算结果也将会为空白像素
        //可以做遮罩和倒影效果（需要生成倒影图，然后上面到下面的透明度49%到0，下面就会看不到；）
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 大家注意SRC_IN模式与SRC模式的区别，一般而言，是在相交区域时无论SRC_IN还是SRC模式都是显示源图像，
        // 而唯一不同的是，当目标图像是空白像素时，在SRC_IN所对应的区域也将会变成空白像素；
        // 其实更严格的来讲，SRC_IN模式是在相交时利用目标图像的透明度来改变源图像的透明度和饱和度。
        // 当目标图像透明度为0时，源图像就完全不显示。


        //计算公式为：[Sa * (1 - Da), Sc * (1 - Da)]
        // 从公式中可以看出，计算结果的透明度=Sa * (1 - Da)；也就是说当目标图像图像完全透明时，计算结果将是透明的；
        // 从示例图中也可以看出，源图像与目标图像的相交部分由于目标图像的透明度为100%，所以相交部分的计算结果为空白像素。
        // 在目标图像为空白像素时，完全以源图像显示。
        //所以这个模式的特性可以概括为：以目标图像的透明度的补值来调节源图像的透明度和色彩饱和度。即当目标图像为空白像素时，
        // 就完全显示源图像，当目标图像的透明度为100%时，交合区域为空像素。
        //Mode.SRC_OUT简单来说，当目标图像有图像时结果显示空白像素，当目标图像没有图像时，结果显示源图像。(可以实现橡皮擦效果和刮刮卡效果)
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));


        //Mode.SRC_OVER
        //计算公式为：[Sa + (1 - Sa)*Da, Rc = Sc + (1 - Sa)*Dc]
        // 我们可以看到，在计算结果中，源图像没有变。它的意思就是在目标图像的顶部绘制源图像。
        // 从公式中也可以看出目标图像的透明度为Sa + (1 - Sa)*Da；即在源图像的透明度基础上增加一部分目标图像的透明度。
        // 增加的透明度是源图像透明度的补量；目标图像的色彩值的计算方式同理，所以当源图像透明度为100%时，就原样显示源图像；
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));


        //Mode.SRC_ATOP
        // 计算公式为：[Da, Sc * Da + (1 - Sa) * Dc]
        // 很奇怪，它的效果图竟然与SRC_IN模式是相同的，我们来对比一下它们的公式：
        // SRC_IN: [Sa * Da, Sc * Da]
        // SRC_ATOP:[Da, Sc * Da + (1 - Sa) * Dc]
        // 先看透明度：在SRC_IN中是Sa * Da,在SRC_ATOP是Da
        // SRC_IN是源图像透明度乘以目标图像的透明度做为结果透明度，而SRC_ATOP是直接使用目标图像的透明度做为结果透明度
        // 再看颜色值：
        // SRC_IN的颜色值为 Sc * Da，SRC_ATOP的颜色值为Sc * Da + (1 - Sa) * Dc；SRC_ATOP在SRC_IN的基础上还增加了(1 - Sa) * Dc；
        // 所以总结来了：
        // 1、当透明度只有100%和0%时，SRC_ATOP是SRC_IN是通用的
        // 2、当透明度不只有100%和0%时，SRC_ATOP相比SRC_IN源图像的饱和度会增加，即会显得更亮！
        //
        // 版权声明：本文为CSDN博主「启舰」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        // 原文链接：https://blog.csdn.net/harvic880925/article/details/51284710

        //Da=0 SRC_IN=[0,0] SRC_ATOP[0,x]都是透明效果
        //Da=1,SA此时也是1 SRC_IN=[Sa,Sc] SRC_ATOP[Da,SC]=[Sa,Sc]都是透明效果(此种情况比较特殊)
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));


        canvas.drawBitmap(srcBmp, width / 2, height / 2, mPaint);
        // canvas.drawRect(width/2,height/2,width/2+width,height+height/2,mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerID);

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

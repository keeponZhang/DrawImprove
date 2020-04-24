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
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));





        /*-------------------------------------DST相关模式--------------------------------------------------*/



        // Mode.DST
        // 计算公式为：[Da, Dc]
        // 从公式中也可以看出，在处理源图像所在区域的相交问题时，正好与Mode.SRC相反，全部以目标图像显示
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));

        // Mode.DST_IN
        // 计算公式为：[Da * Sa,Dc * Sa]
        // 我们与Mode.SRC_IN的公式对比一下：SRC_IN:[Sa * Da, Sc * Da]
        // 正好与SRC_IN相反，Mode.DST_IN是在相交时利用源图像的透明度来改变目标图像的透明度和饱和度。当源图像透明度为0时，目标图像就完全不显示。
        //src_in还有四份之一角，DST_IN这里没有了，因为原图像不透明，所以相交的部分显示了目标图像，另外四分之三，因为目标头像的透明度是0，所以也不显示
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));


        // Mode.DST_OUT
        // 计算公式为：[Da * (1 - Sa), Dc * (1 - Sa)]
        // 同样，我们拿这个公式与Mode.SRC_OUT对比一下，Mode.SRC_OUT：[Sa * (1 - Da), Sc * (1 - Da)]
        // 可以看出Mode.SRC_OUT是利用目标图像的透明度的补值来改变源图像的透明度和饱和度。而Mode.DST_OUT反过来，
        // 是通过源图像的透明度补值来改变目标图像的透明度和饱和度。
        // 简单来说，在Mode.DST_OUT模式下，就是相交区域显示的是目标图像，目标图像的透明度和饱和度与源图像的透明度相反，
        // 当源图像透明底是100%时，则相交区域为空值。当源图像透明度为0时，则完全显示目标图像。非相交区域完全显示目标图像。

//         图中编号1的相交区域：在DST_OUT模式下，由于源图像的透明度是100%，所以计算后的结果图像在这个区域是空像素。
//         图中编号2的非相交区域：在DST_OUT模式下，这个区域的源图像透明度仍为100%，所以计算后的结果图像在这个区域仍是空像素。
//         所以我们做下简单的总结，当源图像区域透明度为100%时，所在区域计算结果为透明像素，当源图像的区域透明时，计算结果就是目标图像；
//         这与SRC_OUT模式的结果正好相反，在SRC_OUT模式下，当目标图像区域透明度为100%时，所在区域计算结果为透明像素，当目标图像的区域透明时，计算结果就是源图像；
//         所以，在上篇中，使用SRC_OUT模式实现的橡皮擦效果和刮刮卡效果都是可以使用DST_OUT模式实现的，只需要将SRC和DST所对应的图像翻转一下就可以了；
// ————————————————
//         版权声明：本文为CSDN博主「启舰」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//         原文链接：https://blog.csdn.net/harvic880925/article/details/51288006
//         mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));


        //Mode.DST_OVER
        //计算公式为：[Sa + (1 - Sa)*Da, Rc = Dc + (1 - Da)*Sc]
        // 同样先写Mode.SRC_OVER对比一下，SRC_OVER：[Sa + (1 - Sa)*Da, Rc = Sc + (1 - Sa)*Dc]
        // 所以它们的效果就是在SRC模式中以显示SRC图像为主变成了以显示DST图像为主。
        // 从SRC模式中的使用目标图像控制结果图像的透明度和饱和度变成了由源图像控件结果图像的透明度和饱和度
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));



        //Mode.DST_ATOP
        // 计算公式为：[Sa, Sa * Dc + Sc * (1 - Da)]
        // 由于在SRC中，我们知道了Mode.SRC_ATOP与MODE.SRC_IN的区别：
        // 一般而言SRC_ATOP是可以和SRC_IN通用的，但SRC_ATOP所产生的效果图在目标图的透明度不是0或100%的时候，会比SRC_IN模式产生的图像更亮些；
        // 我们再来对比下DST中的两个模式与SRC中的这两个模式中公式中区别：
        // SRC_IN: [Sa * Da, Sc * Da]
        // SRC_ATOP:[Da, Sc * Da + (1 - Sa) * Dc]
        // DST_IN:[Da * Sa , Dc * Sa ]
        // DST_ATOP:[Sa, Sa * Dc + Sc * (1 - Da)]
        // 从公式中可以看到，在SRC模式中，以显示源图像为主，透明度和饱和度利用Da来调节
        // 而在DST模式中，以显示目标图像为主，透明度和饱和度利用Sa来调节
        // 所以Mode.DST_ATOP与Mode.DST_IN的关系也是：
        // 一般而言DST_ATOP是可以和DST_IN通用的，但DST_ATOP所产生的效果图在源图像的透明度不是0或100%的时候，会比DST_IN模式产生的图像更亮些；
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));


        // 到这里有关DST相关模式都讲完了，我们总结一下：
        // 1、DST相关模式是完全可以使用SRC对应的模式来实现的，只不过需要将目标图像和源图像对调一下即可。
        // 2、在SRC模式中，是以显示源图像为主，通过目标图像的透明度来调节计算结果的透明度和饱和度，而在DST模式中，是以显示目标图像为主，通过源图像的透明度来调节计算结果的透明度和饱和度。

        /*----------------------------------其它模式--------------------------------------------*/

        //Mode.CLEAR
        // 前面我们做清空图像的时候用过这个方法，从公式中可以看到，计算结果直接就是[0,0]即空像素。也就是说，源图像所在区域都会变成空像素！
        // 这样就起到了清空源图像所在区域图像的功能了
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));



        //Mode.XOR
        //计算公式为：[Sa + Da - Sa*Da,Sc*(1 - Da) + Dc*(1 - Sa) + min(Sc, Dc)]
        // 单从示例图像中，好像是异或的功能，即将源图像中除了相交区域以外的部分做为结果。但仔细看看公式，其实并没有这么简单。
        // 首先看公式中透明度部分：Sa + Da - Sa*Da，就是将目标图像和源图像的透明度相加，然后减去它们的乘积，所以计算结果的透明度会增大（即比目标图像和源图像都大，当其中一个图像的透明度为1时，那结果图像的透明度肯定是1）
        // 然后再看颜色值部分：Sc*(1 - Da) + Dc*(1 - Sa) + min(Sc, Dc)；表示源图像和目标图像分别以自己的透明度的补值乘以对方的颜色值，然后相加得到结果。最后再加上Sc, Dc中的最小值。
        // 这个模式太过复杂，在实际应用中应用也比较少，目前没想到有哪些示例，大家有用到的，可以跟我说哦。
        // mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));



        canvas.drawBitmap(srcBmp, width / 2, height / 2, mPaint);
        // canvas.drawRect(width/2,height/2,width/2+width,height+height/2,mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerID);




        // 1、首先，目标图像和源图像混合，需不需要生成颜色的叠加特效，如果需要叠加特效则从颜色叠加相关模式中选择，有Mode.ADD（饱和度相加）、Mode.DARKEN（变暗），Mode.LIGHTEN（变亮）、Mode.MULTIPLY（正片叠底）、Mode.OVERLAY（叠加），Mode.SCREEN（滤色）
        // 2、当不需要特效，而需要根据某一张图像的透明像素来裁剪时，就需要使用SRC相关模式或DST相关模式了。由于SRC相关模式与DST相关模式是相通的，唯一不同的是决定当前哪个是目标图像和源图像；
        // 3、当需要清空图像时，使用Mode.CLEAR

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

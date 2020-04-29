package com.harvic.blogCanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/22.
 */
public class ALPHA_COLOR_FALG_VIEW extends View {
    private Paint mPaint;

    public ALPHA_COLOR_FALG_VIEW(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // alphaColorFlag(canvas);


       defaultColorFlag(canvas);
    }

    private void alphaColorFlag(Canvas canvas) {
        canvas.drawColor(Color.RED);
        // FULL_COLOR_LAYER_SAVE_FLAG则表示新建的bimap画布在与上一个画布合成时，先将上一层画布对应区域清空，然后再盖在上面。
        //事实是没有出现效果，底层是绿色的FULL_COLOR_LAYER_SAVE_FLAG跟HAS_ALPHA_LAYER_SAVE_FLAG效果一样
       // canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
       // canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
//         canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(100, 100, 300, 300, mPaint);
        canvas.restore();
    }

    private void defaultColorFlag(Canvas canvas){
        canvas.drawColor(Color.RED);

        canvas.saveLayer(0,0,500,500,mPaint,Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate(40);
        canvas.drawRect(100, 100, 300, 300, mPaint);
        canvas.restore();
    }
    // 所以有关这两个标识的结论来了：
    //         1、HAS_ALPHA_LAYER_SAVE_FLAG表示新建的bitmap画布在与上一个画布合成时，不会将上一层画布内容清空，直接盖在上一个画布内容上面。
    //         2、FULL_COLOR_LAYER_SAVE_FLAG则表示新建的bimap画布在与上一个画布合成时，先将上一层画布对应区域清空，然后再盖在上面。
    //         3、当HAS_ALPHA_LAYER_SAVE_FLAG与FULL_COLOR_LAYER_SAVE_FLAG两个标识同时指定时，以HAS_ALPHA_LAYER_SAVE_FLAG为主
    //         4、当即没有指定HAS_ALPHA_LAYER_SAVE_FLAG也没有指定FULL_COLOR_LAYER_SAVE_FLAG时，系统默认使用FULL_COLOR_LAYER_SAVE_FLAG；
    //FULL_COLOR_LAYER_SAVE_FLAG没效果，所以第四点不成立
    // 原文链接：https://blog.csdn.net/harvic880925/article/details/51332494
}

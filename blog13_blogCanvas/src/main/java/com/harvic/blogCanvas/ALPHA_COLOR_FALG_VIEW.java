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

        alphaColorFlag(canvas);

//        defaultColorFlag(canvas);
    }

    private void alphaColorFlag(Canvas canvas) {
        canvas.drawColor(Color.RED);
//        canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
//        canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
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
}

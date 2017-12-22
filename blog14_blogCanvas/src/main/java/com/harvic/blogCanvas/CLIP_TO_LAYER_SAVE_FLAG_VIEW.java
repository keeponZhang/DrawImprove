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
public class CLIP_TO_LAYER_SAVE_FLAG_VIEW extends View {
    private Paint mPaint;
    public CLIP_TO_LAYER_SAVE_FLAG_VIEW(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.CLIP_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.restore();

        canvas.drawColor(Color.YELLOW);
    }
}

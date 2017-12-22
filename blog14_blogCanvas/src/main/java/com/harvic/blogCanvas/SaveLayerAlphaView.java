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
public class SaveLayerAlphaView extends View {
    private Paint mPaint;
    public SaveLayerAlphaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(100,100,300,300,mPaint);

        int layerID = canvas.saveLayerAlpha(0,0,600,600,0x88,Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(200,200,400,400,mPaint);
        canvas.restoreToCount(layerID);

    }
}

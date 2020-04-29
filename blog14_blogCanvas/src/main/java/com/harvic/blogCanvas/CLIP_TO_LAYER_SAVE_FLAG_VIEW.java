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
        // 这个标识比较犯贱，它的意义是，在新建bitmap前，先把canvas给裁剪，前面我们讲过canvas代表的是画板的意思，一旦画板被裁剪，那么其中的各个画布都会被受到影响。
        // 而且由于它是在新建bitmap前做的裁剪，所以是无法恢复的！
        //经实践，好像也没效果
        canvas.drawColor(Color.RED);
        canvas.saveLayer(0, 0, 500, 500, mPaint,  Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        // canvas.saveLayer(0, 0, 500, 500, mPaint, Canvas.CLIP_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.restore();

        canvas.drawColor(Color.YELLOW);
    }
}

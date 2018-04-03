package com.harvic.BlogPorterDuff_2;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/15.
 */
public class RoundImageView_DSTIN extends View {
    private Paint mBitPaint;
    private Bitmap BmpDST,BmpSRC;

    public RoundImageView_DSTIN(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBitPaint = new Paint();
        BmpDST = BitmapFactory.decodeResource(getResources(),R.drawable.dog,null);
        BmpSRC = BitmapFactory.decodeResource(getResources(),R.drawable.dog_shade,null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(BmpDST,0,0,mBitPaint);
        mBitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(BmpSRC,0,0,mBitPaint);

        mBitPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}

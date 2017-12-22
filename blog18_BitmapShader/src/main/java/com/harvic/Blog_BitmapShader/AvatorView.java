package com.harvic.Blog_BitmapShader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/7/10.
 */
public class AvatorView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mEnumFormat =0,mRadius = 5;

    public AvatorView(Context context, AttributeSet attrs) throws Exception{
        super(context, attrs);
        init(context,attrs);
    }

    public AvatorView(Context context, AttributeSet attrs, int defStyle) throws Exception{
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) throws Exception{
        /**
         * 提取属性定义
         */
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.AvatorView);
        int BitmapID = typedArray.getResourceId(R.styleable.AvatorView_src,-1);
        if (BitmapID == -1){
            throw new Exception("AvatorView 需要定义Src属性,而且必须是图像");
        }
        mBitmap = BitmapFactory.decodeResource(getResources(),BitmapID);
        mEnumFormat = typedArray.getInt(R.styleable.AvatorView_format,0);
        if (mEnumFormat == 1){
            mRadius = typedArray.getInt(R.styleable.AvatorView_radius,5);
        }

        typedArray.recycle();

        mPaint = new Paint();
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //当用户将控件的layout_with或者layout_heigt设置成wrap_content时，将宽高设置成图片的宽高
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth: width, (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight: height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将BitmapShader缩放到与控件宽高一致
        Matrix matrix = new Matrix();
        float scale = (float) getWidth()/mBitmap.getWidth();
        matrix.setScale(scale,scale);
        mBitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(mBitmapShader);

        float half = getWidth()/2;

        if (mEnumFormat == 0){
            canvas.drawCircle(half,half,getWidth()/2,mPaint);
        }else  if(mEnumFormat == 1){
            canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()),mRadius,mRadius,mPaint);
        }
    }
}

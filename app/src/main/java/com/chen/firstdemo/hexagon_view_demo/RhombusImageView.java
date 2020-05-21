package com.chen.firstdemo.hexagon_view_demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.chen.firstdemo.R;


public class RhombusImageView extends android.support.v7.widget.AppCompatImageView {

    private Path mBorderPath = new Path();

    private Paint mBgPaint;

    private Paint mBorderPaint;

    private Bitmap mBitmap;

    private Canvas mCanvas;

    public RhombusImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RhombusImageView);
        int color = obtainStyledAttributes.getColor(R.styleable.RhombusImageView_rhombus_color, -1);
        int borderConer = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RhombusImageView_rhombus_corner, 16);
        int borderWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RhombusImageView_rhombus_width, -1);
        obtainStyledAttributes.recycle();
        this.mBorderPaint = new Paint(1);
        this.mBorderPaint.setPathEffect(new CornerPathEffect((float) borderConer));
        if (borderWidth > 0) {
            this.mBgPaint = new Paint(1);
            this.mBgPaint.setStyle(Paint.Style.STROKE);
            this.mBgPaint.setStrokeWidth((float) borderWidth);
            this.mBgPaint.setPathEffect(new CornerPathEffect((float) borderConer));
            this.mBgPaint.setColor(color);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(this.mCanvas);
        Bitmap bitmap = this.mBitmap;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        this.mBorderPaint.setShader(new BitmapShader(bitmap, tileMode, tileMode));
        canvas.drawPath(this.mBorderPath, this.mBorderPaint);
        Paint paint = this.mBgPaint;
        if (paint != null) {
            canvas.drawPath(this.mBorderPath, paint);
        }
    }

    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        this.mBorderPath.reset();
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        double sin = Math.sin(Math.toRadians(60.0d));
        double cos = Math.cos(Math.toRadians(60.0d));
        float f = (float) halfWidth;
        this.mBorderPath.moveTo(f, 0.0f);
        Path path = this.mBorderPath;
        double d = (double) halfHeight;
        Double.isNaN(d);
        int i7 = (int) (sin * d);
        float f2 = (float) (halfWidth + i7);
        Double.isNaN(d);
        int i8 = (int) (cos * d);
        float f3 = (float) (halfHeight - i8);
        path.lineTo(f2, f3);
        float f4 = (float) (halfHeight + i8);
        this.mBorderPath.lineTo(f2, f4);
        this.mBorderPath.lineTo(f, (float) height);
        float f5 = (float) (halfWidth - i7);
        this.mBorderPath.lineTo(f5, f4);
        this.mBorderPath.lineTo(f5, f3);
        this.mBorderPath.close();
        this.mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.mCanvas = new Canvas(this.mBitmap);
    }
}

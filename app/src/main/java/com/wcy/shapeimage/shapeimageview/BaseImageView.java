package com.wcy.shapeimage.shapeimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by wcy.
 */
public abstract class BaseImageView extends ImageView {
    private static final String TAG = BaseImageView.class.getSimpleName();

    protected Context mContext;
    protected int borderColor;
    protected int borderWidth;
    protected int mShape;
    protected int roundRadius;
    protected int leftTopRadius;
    protected int rightTopRadius;
    protected int rightBottomRadius;
    protected int leftBottomRadius;
    protected boolean onlyDrawBorder;
    private Paint shaderPaint;
    private Paint mPaint;
    private Shader shader;
    private WeakReference<Bitmap> mWeakBitmap;

    public BaseImageView(Context context) {
        super(context);
        sharedConstructor(context);
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructor(context);
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        sharedConstructor(context);
    }

    public void setOnlyDrawBorder(boolean onlyDrawBorder) {
        this.onlyDrawBorder = onlyDrawBorder;
    }

    private void sharedConstructor(Context context) {
        mContext = context;
        shaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (onlyDrawBorder) {
            super.onDraw(canvas);
        } else {
            Bitmap bmp = drawableToBitmap(getDrawable());
            if (bmp != null) {
                float bmpW = bmp.getWidth();
                float bmpH = bmp.getHeight();
                float h = getHeight();
                float w = getWidth();
                drawShader(canvas, bmp, bmpW, bmpH, w, h);
                drawShape(canvas, w, h);
            }
        }
        drawBorder(canvas);
    }

    private void drawShader(Canvas canvas, Bitmap bmp, float bmpW, float bmpH, float w, float h) {
        shader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setScale(w / bmpW, h / bmpH);
        shader.setLocalMatrix(matrix);
        shaderPaint.setColor(Color.TRANSPARENT);
        shaderPaint.setShader(shader);
        canvas.drawPaint(shaderPaint);
    }


    private void drawShape(Canvas canvas, float w, float h) {
        RectF rectF = new RectF();
        shaderPaint.setColor(Color.WHITE);
        shaderPaint.setAntiAlias(true);
        shaderPaint.setStyle(Paint.Style.FILL);
        switch (mShape) {
            case Shape.CIRCLE:
                float min = Math.min(w, h);
                rectF.left = (w - min) / 2 + borderWidth / 2;
                rectF.top = (h - min) / 2 + borderWidth / 2;
                rectF.right = w - (w - min) / 2 - borderWidth / 2;
                rectF.bottom = h - (h - min) / 2 - borderWidth / 2;
                canvas.drawArc(rectF, 0, 360, true, shaderPaint);
                break;
            case Shape.RECTANGLE:
                Path path = new Path();
                rectF.left = borderWidth / 2;
                rectF.top = borderWidth / 2;
                rectF.right = w - borderWidth / 2;
                rectF.bottom = h - borderWidth / 2;
                float[] rad = {leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
                path.addRoundRect(rectF, rad, Path.Direction.CW);
                canvas.drawPath(path, shaderPaint);
                break;
            case Shape.ARC:
                rectF.left = borderWidth / 2;
                rectF.top = borderWidth / 2;
                rectF.right = w - borderWidth / 2;
                rectF.bottom = h - borderWidth / 2;
                canvas.drawArc(rectF, 0, 360, true, shaderPaint);
                break;
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;

    }


    private void drawBorder(Canvas canvas) {
        if (borderWidth == 0 || borderColor == Color.TRANSPARENT) {
            return;
        }
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderWidth);
        mPaint.setColor(borderColor);
        switch (mShape) {
            case Shape.CIRCLE:
                float radius = (Math.min(getWidth(), getHeight()) - borderWidth) / 2;
                float cx = getWidth() / 2f;
                float cy = getHeight() / 2f;
                canvas.drawCircle(cx, cy, radius, mPaint);
                break;
            case Shape.RECTANGLE:
                Path path = new Path();
                RectF rectF = new RectF();
                rectF.left = borderWidth / 2;
                rectF.top = borderWidth / 2;
                rectF.right = getWidth() - borderWidth / 2;
                rectF.bottom = getHeight() - borderWidth / 2;

                float[] rad = {leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
                path.addRoundRect(rectF, rad, Path.Direction.CW);
                canvas.drawPath(path, mPaint);
                return;
            case Shape.ARC:
                RectF rectF2 = new RectF();
                rectF2.left = borderWidth / 2;
                rectF2.top = borderWidth / 2;
                rectF2.right = getWidth() - borderWidth / 2;
                rectF2.bottom = getHeight() - borderWidth / 2;
                canvas.drawArc(rectF2, 0, 360, false, mPaint);
                break;
        }
    }

    public void setBorderColor(int color) {
        borderColor = color;
    }

    public void setBorderWidth(int width) {
        this.borderWidth = width;
    }

    public static class Shape {
        public static final int CIRCLE = 1;
        public static final int RECTANGLE = 2;
        public static final int ARC = 3;
    }


}

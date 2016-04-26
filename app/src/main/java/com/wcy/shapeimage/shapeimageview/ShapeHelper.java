package com.wcy.shapeimage.shapeimageview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * Created by changyou on 2015/8/13.
 */
public class ShapeHelper {

    public static Bitmap getCircle(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        float radius = Math.min(width / 2f, height / 2f) - 2;
        float cx = width / 2f;
        float cy = height / 2f;
        canvas.drawCircle(cx, cy, radius, paint);
        return bitmap;
    }

    private static Bitmap getRoundRectangle(int width, int height, int radius) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, paint);
        return bitmap;
    }

    public static Bitmap getRoundRectangle(int width, int height, int leftTopRadius, int rightTopRadius, int rightBottomRadius, int leftBottomRadius) {
        if (isSameRaidus(leftTopRadius,rightTopRadius,rightBottomRadius,leftBottomRadius)) {
            return getRoundRectangle(width, height, leftTopRadius);
        } else {
            float[] outerR = new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
            RoundRectShape shape = new RoundRectShape(outerR, null, null);
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bitmap);
            ShapeDrawable drawable = new ShapeDrawable(shape);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            return bitmap;
        }
    }

    public static boolean isSameRaidus(int leftTopRadius, int rightTopRadius, int rightBottomRadius, int leftBottomRadius){
        return (leftTopRadius == rightTopRadius) && (leftTopRadius == rightBottomRadius) && (leftTopRadius == leftBottomRadius);
    }

}

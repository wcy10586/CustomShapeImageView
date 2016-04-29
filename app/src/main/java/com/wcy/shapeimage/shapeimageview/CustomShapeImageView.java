package com.wcy.shapeimage.shapeimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.wcy.shapeimage.R;


/**
 * Created by wcy .
 */
public class CustomShapeImageView extends BaseImageView {


    public CustomShapeImageView(Context context) {
        super(context);
    }

    public CustomShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructor(context, attrs);
    }

    public CustomShapeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        sharedConstructor(context, attrs);
    }

    private void sharedConstructor(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomShapeImageView);
        mShape = a.getInt(R.styleable.CustomShapeImageView_shape, Shape.CIRCLE);
        borderColor = a.getColor(R.styleable.CustomShapeImageView_borderColor, Color.TRANSPARENT);
        borderWidth = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_imageBorderWidth, 0);
        roundRadius = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_roundRadius, 0);
        leftTopRadius = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_leftTopRadius, -1);
        if (leftTopRadius == -1){
            leftTopRadius = roundRadius;
        }
        rightTopRadius = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_rightTopRadius, -1);
        if (rightTopRadius == -1){
            rightTopRadius = roundRadius;
        }
        rightBottomRadius = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_rightBottomRadius, -1);
        if (rightBottomRadius == -1){
            rightBottomRadius = roundRadius;
        }
        leftBottomRadius = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_leftBottomRadius, -1);
        if (leftBottomRadius == -1){
            leftBottomRadius = roundRadius;
        }
        onlyDrawBorder = a.getBoolean(R.styleable.CustomShapeImageView_onlyDrawBorder, true);
        a.recycle();
    }


}

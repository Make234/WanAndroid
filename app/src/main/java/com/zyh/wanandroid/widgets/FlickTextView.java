package com.zyh.wanandroid.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @author 88421876
 * @date 2019/2/13
 */
public class FlickTextView extends AppCompatTextView {

    private int mViewWidth;

    /**
     * 颜色渐变
     */
    private LinearGradient mLinearGradient;

    private Matrix mGradientMatrix;

    private float mTranslate;

    public FlickTextView(Context context) {
        super(context);
    }

    public FlickTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlickTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ColorStateList textColors = getTextColors();
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                Paint paint = getPaint();
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                        new int[]{textColors.getDefaultColor(), 0XFFFFFFFF, textColors.getDefaultColor()},
                        null, Shader.TileMode.CLAMP);

                paint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;
            //当该控件渲染器的颜色变化正好移除屏幕时，从左侧进入
            int size = 2;
            if (mTranslate > size * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);

            //80ms 后继续刷新试图，即调用onDraw()方法。
            postInvalidateDelayed(80);
        }
    }
}

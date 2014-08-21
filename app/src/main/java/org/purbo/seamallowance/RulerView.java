package org.purbo.seamallowance;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by purbo on 8/20/14.
 */
public class RulerView extends View {

    float firstUnitMin;
    float firstUnitMax;
    float secondUnitMin;
    float secondUnitMax;

    private int backgroundColor;
    private int foregroundColor;

    public RulerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RulerView, 0, 0);
        try {
            firstUnitMin = a.getFloat(R.styleable.RulerView_firstUnitMin, 0);
            firstUnitMax = a.getFloat(R.styleable.RulerView_firstUnitMax, 1);
            secondUnitMin = a.getFloat(R.styleable.RulerView_secondUnitMin, 0);
            secondUnitMax = a.getFloat(R.styleable.RulerView_secondUnitMax, 1);
            backgroundColor = a.getInteger(R.styleable.RulerView_backgroundColor, 0);
            foregroundColor = a.getInteger(R.styleable.RulerView_foregroundColor, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(foregroundColor);

        //canvas.drawLine();
        //this.getMeasuredWidth();
    }

    public float getFirstUnitMin() {
        return firstUnitMin;
    }

    public void setFirstUnitMin(float firstUnitMin) {
        this.firstUnitMin = firstUnitMin;
        invalidate();
        requestLayout();
    }

    public float getFirstUnitMax() {
        return firstUnitMax;
    }

    public void setFirstUnitMax(float firstUnitMax) {
        this.firstUnitMax = firstUnitMax;
        invalidate();
        requestLayout();
    }

    public float getSecondUnitMin() {
        return secondUnitMin;
    }

    public void setSecondUnitMin(float secondUnitMin) {
        this.secondUnitMin = secondUnitMin;
        invalidate();
        requestLayout();
    }

    public float getSecondUnitMax() {
        return secondUnitMax;
    }

    public void setSecondUnitMax(float secondUnitMax) {
        this.secondUnitMax = secondUnitMax;
        invalidate();
        requestLayout();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
        requestLayout();
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
        invalidate();
        requestLayout();
    }
}

package org.purbo.seamallowance;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by purbo on 8/20/14.
 */
public class RulerView extends View {

    private static final int INVALID_POINTER_ID = -1;

    float firstUnitMin;
    float firstUnitMax;
    float secondUnitMin;
    float secondUnitMax;

    private int backgroundColor;
    private int foregroundColor;

    //http://stackoverflow.com/questions/5790503/can-we-use-scale-gesture-detector-for-pinch-zoom-in-android
    //http://www.zdnet.com/blog/burnette/how-to-use-multi-touch-in-android-2-part-6-implementing-the-pinch-zoom-gesture/1847

    private ScaleGestureDetector scaleDetector;
    private float scaleFactor = 1.f;
    private float posX;
    private float lastTouchX;
    private int activePointerId = INVALID_POINTER_ID;

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

        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(posX, 0);
        /*
        canvas.translate(posX, posY);
        canvas.scale(scaleFactor, scaleFactor);
        */

        Paint paint = new Paint();
        paint.setColor(foregroundColor);

        // main horizontal
        canvas.drawLine(0, getMeasuredHeight()/2, getMeasuredWidth() * scaleFactor, getMeasuredHeight()/2, paint);
        // min vertical
        canvas.drawLine(0, getMeasuredHeight()*0.25f, 0, getMeasuredHeight()*0.75f, paint);
        // max vertical
        canvas.drawLine(getMeasuredWidth() * scaleFactor, getMeasuredHeight()*0.25f, getMeasuredWidth() * scaleFactor, getMeasuredHeight()*0.75f, paint);

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        scaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();

                lastTouchX = x;
                activePointerId = ev.getPointerId(0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = ev.findPointerIndex(activePointerId);
                final float x = ev.getX(pointerIndex);

                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!scaleDetector.isInProgress()) {
                    final float dx = x - lastTouchX;

                    posX += dx;
                    if (posX > 0) posX = 0;

                    invalidate();
                }

                lastTouchX = x;
                break;
            }

            case MotionEvent.ACTION_UP: {
                activePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                activePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == activePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    lastTouchX = ev.getX(newPointerIndex);
                    activePointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }
        }

        return true;
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

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f));

            invalidate();
            return true;
        }
    }
}

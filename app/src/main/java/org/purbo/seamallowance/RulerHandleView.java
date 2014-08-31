package org.purbo.seamallowance;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by purbo on 8/31/14.
 */
public class RulerHandleView extends View {

    float alpha;
    float radius;

    public RulerHandleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RulerView, 0, 0);
        try {
            alpha = a.getFloat(R.styleable.RulerHandleView_alpha, .9f);
            radius = a.getFloat(R.styleable.RulerHandleView_radius, 1);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}

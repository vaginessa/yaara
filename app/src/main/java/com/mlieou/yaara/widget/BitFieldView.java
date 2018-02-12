package com.mlieou.yaara.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mlieou on 2/11/18.
 */

public class BitFieldView extends View {

    public BitFieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
    }

    public void setBitField(String bitField) {
        // Draw UI

        invalidate();
    }
}

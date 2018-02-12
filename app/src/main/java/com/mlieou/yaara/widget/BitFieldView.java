package com.mlieou.yaara.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mlieou.yaara.R;

/**
 * Created by mlieou on 2/11/18.
 */

public class BitFieldView extends View {

    private String bitField;
    private Drawable square = getResources().getDrawable(R.drawable.download_block);

    public BitFieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = getWidth() / 20;
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 2; y++) {
                square.setBounds(x * size, y * size, (x + 1) * size, (y + 1) * size);
                square.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
                square.draw(canvas);
            }
        }

    }

    public void setBitField(String bitField) {
        // Draw UI
        this.bitField = bitField;
        invalidate();
    }
}

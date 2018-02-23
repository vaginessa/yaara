package com.mlieou.yaara.widget;

import android.content.Context;
import android.graphics.Canvas;
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
    private int pieces;
    private int BLOCKS_PER_LINE = 20;
    private int lastBlockGradient;
    private Drawable square;
    private int[] colorGradient;

    public BitFieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        square = getResources().getDrawable(R.drawable.download_block);
        colorGradient = new int[]{
                getResources().getColor(R.color.md_grey_300),
                getResources().getColor(R.color.green_100),
                getResources().getColor(R.color.green_200),
                getResources().getColor(R.color.green_300),
                getResources().getColor(R.color.green_400)
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int rows = calculateNumOfBlocks(pieces) / BLOCKS_PER_LINE + 1;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width / BLOCKS_PER_LINE * rows);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int blockSize = width / BLOCKS_PER_LINE;
        int offset = (width - blockSize * BLOCKS_PER_LINE) / 2;
        int blocks = calculateNumOfBlocks(pieces);
        int i = 0;
        while (i < blocks) {
            square.setBounds(i % BLOCKS_PER_LINE * blockSize + offset,
                    i / BLOCKS_PER_LINE * blockSize,
                    (i % BLOCKS_PER_LINE + 1) * blockSize + offset,
                    (i / BLOCKS_PER_LINE + 1) * blockSize);
            int colorIndex = Integer.bitCount(Character.digit(bitField.charAt(i), 16));
            if (i + 1 == blocks && colorIndex == lastBlockGradient)  // special case for last block
                colorIndex = 4;
            square.setColorFilter(colorGradient[colorIndex], PorterDuff.Mode.SRC_ATOP);
            square.draw(canvas);
            i++;
        }
    }

    public void setBitField(String bitField) {
        if (bitField.equals(this.bitField))
            return;
        this.bitField = bitField;
        invalidate();
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
        invalidate();
        requestLayout();
    }

    private int calculateNumOfBlocks(int pieces) {
        int blocks = pieces / 8 * 2;
        if (pieces % 8 > 0) {
            if ((pieces - blocks * 4) > 4) {
                lastBlockGradient = (pieces - blocks * 4) - 4;
                blocks += 2;
            } else {
                lastBlockGradient = pieces - blocks * 4;
                blocks++;
            }
        }
        else {
            lastBlockGradient = 4;
        }
        return blocks;
    }

}

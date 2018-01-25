package com.mlieou.yaara.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.mlieou.yaara.R;

/**
 * Created by mlieou on 1/24/18.
 */

public class LetterIcon extends AppCompatTextView {

    public LetterIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.background_circle);
    }

    public void setLetter(@NonNull String string) {
        if (string.length() == 0)
            setLetter(' ');
        else
            setLetter(string.charAt(0));
    }

    public void setLetter(char letter) {
        String[] colorArray = getResources().getStringArray(R.array.icon_colors);
        int colorIndex = letter % 10;
        getBackground().setColorFilter(
                Color.parseColor(colorArray[colorIndex]), PorterDuff.Mode.SRC_ATOP);
        setText(Character.toString(letter));
    }
}

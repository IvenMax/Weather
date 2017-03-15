package com.iven.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * @author Iven
 * @date 2017/3/15 14:52
 * @Description
 */

public class ReadOnlyEditText extends EditText {
    public ReadOnlyEditText(Context context) {
        super(context);
    }

    public ReadOnlyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReadOnlyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

}

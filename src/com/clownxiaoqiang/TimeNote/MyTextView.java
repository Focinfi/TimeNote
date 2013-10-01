package com.clownxiaoqiang.TimeNote;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-10-1
 * Time: 上午10:43
 * To change this template use File | Settings | File Templates.
 */
public class MyTextView extends TextView {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;    //To change body of overridden methods use File | Settings | File Templates.
    }
}

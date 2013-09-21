package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-20
 * Time: afternoon 7:49
 * To change this template use File | Settings | File Templates.
 */
public class HomePage extends Activity {

    private int Screen_x,Screen_y;
    private DrawCircle drawCircle;
    private LinearLayout linearLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        DisplayMetrics displayMetrics =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Screen_x = displayMetrics.widthPixels;
        Screen_y = displayMetrics.heightPixels;
        drawCircle =new DrawCircle(this,Screen_x,Screen_y);
        linearLayout = (LinearLayout)findViewById(R.id.mylayout);
        linearLayout.addView(drawCircle);
    }
}
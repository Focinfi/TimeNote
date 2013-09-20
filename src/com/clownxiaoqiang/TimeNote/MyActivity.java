package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-19
 * Time: 下午12:12
 * To change this template use File | Settings | File Templates.
 */
public class MyActivity extends Activity {
    private float Screen_x,Screen_y;
    private DrawCircle drawCircle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        DisplayMetrics DisMetrics =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(DisMetrics);
        Screen_x = DisMetrics.widthPixels;
        Screen_y =DisMetrics.heightPixels;
        drawCircle = new DrawCircle(this , Screen_x , Screen_y );
        setContentView(drawCircle);
    }

}

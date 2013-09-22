package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-21
 * Time: 下午8:19
 * To change this template use File | Settings | File Templates.
 */
public class DiaryWrite extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.writediary);
    }

}
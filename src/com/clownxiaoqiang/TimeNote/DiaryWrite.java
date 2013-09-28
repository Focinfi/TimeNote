package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-21
 * Time: 下午8:19
 * To change this template use File | Settings | File Templates.
 */
public class DiaryWrite extends Activity {
    private String date;
    private TextView titleTextView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.writediary);
        titleTextView = (TextView)this.findViewById(R.id.writeDiaryTitleTextView);
        Intent intent = getIntent();
        date = intent.getStringExtra("date_month")+"的笔记";
        titleTextView.setText(date);
    }

}
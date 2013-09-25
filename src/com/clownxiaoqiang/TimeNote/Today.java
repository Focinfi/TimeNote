package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import come.clownxiaoqiang.TimeNote.Sql.SQlManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-20
 * Time: ����7:49
 * To change this template use File | Settings | File Templates.
 */
public class Today extends Activity {
    private String date;
    private Button addNote;
    private TextView dateTextView;
    private int Screen_x;
    private int Screen_y;
    private int first_in;
    private TodayCircle todayCircle;
    private LinearLayout Layout;
    private int logo ;
    private SQlManager sQlManager ;
    private ArrayList<Map<String,Object>> arrayList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Screen_x = displayMetrics.widthPixels;
        Screen_y = displayMetrics.heightPixels;
        todayCircle = new TodayCircle(this, Screen_x, Screen_y);
        Layout = (LinearLayout) this.findViewById(R.id.todayCircleView);
        Layout.addView(todayCircle);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date_S = new Date(System.currentTimeMillis());
        String date_x = simpleDateFormat.format(date_S);
        sQlManager = new SQlManager(Today.this);
        arrayList = new ArrayList<Map<String, Object>>();
        arrayList = sQlManager.GetTimeToDrawPicture(date_x);
        Log.d("arraylist",arrayList.toString());

        dateTextView = (TextView) this.findViewById(R.id.dateTextView);
        addNote = (Button) this.findViewById(R.id.addNote);
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        dateTextView.setText(date);


        addNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Today.this, DiaryWrite.class);
                startActivity(intent);
            }
        });
        first_in = 0;

    }

   /* public void onStart() {
        super.onStart();
    }*/

    public void onPause() {
        super.onPause();
        Layout.removeView(todayCircle);
    }

    public void onResume() {
        super.onResume();
        if(logo > 0){
            todayCircle = new TodayCircle(this, Screen_x, Screen_y);
            Layout.addView(todayCircle);
        }
        logo++;

        Log.d("resume_today", "run_resume");
    }

    public void onRestart() {
        super.onRestart();
        Log.d("restart_today", "run_today");

    }
}
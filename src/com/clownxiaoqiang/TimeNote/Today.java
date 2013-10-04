package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.clownxiaoqiang.TimeNote.Util.TimeNoteUtil;
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
    private int logo;
    private int workTime, studyTime, playTime, sleepTime;
    private TodayCircle todayCircle;
    private LinearLayout Layout;
    private SQlManager sQlManager;
    private ArrayList<Map<String, Object>> arrayList;
    private String date_x, date_month;
    private TimeNoteUtil timeNoteUtil;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today);

        init();

        GetData();

        DrawTodayCircle();

        Layout = (LinearLayout) this.findViewById(R.id.todayCircleView);
        Layout.addView(todayCircle);

        date = timeNoteUtil.CurrentTime();
        dateTextView.setText(date);

        addNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (arrayList.isEmpty()) {
                    Toast.makeText(Today.this, "今天还没有记录时间呢", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Today.this, DiaryWrite.class);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M月d日");
                    Date date_S = new Date(System.currentTimeMillis());
                    date_month = simpleDateFormat.format(date_S);
                    Log.d("t_date",date_x);
                    intent.putExtra("date", date_x);
                    intent.putExtra("date_month", date_month);
                    startActivity(intent);
                }
            }
        });
    }

    private void init(){
        timeNoteUtil = new TimeNoteUtil(Today.this);
        dateTextView = (TextView) this.findViewById(R.id.dateTextView);
        addNote = (Button) this.findViewById(R.id.addNote);
    }

    private void GetData(){
        date_x = timeNoteUtil.CurrentTime();
        sQlManager = new SQlManager(Today.this);
        arrayList = new ArrayList<Map<String, Object>>();
        arrayList = sQlManager.GetTimeToDrawPicture(date_x);
        if (arrayList.isEmpty()) {
            workTime = studyTime = playTime = sleepTime = 0;
        } else {
            workTime = Integer.parseInt((String) arrayList.get(0).get((Object) "work_time"))/4;
            studyTime = Integer.parseInt((String) arrayList.get(0).get((Object) "study_time"))/4;
            playTime = Integer.parseInt((String) arrayList.get(0).get((Object) "play_time"))/4;
            sleepTime = Integer.parseInt((String) arrayList.get(0).get((Object) "sleep_time"))/4;
        }
    }

    private void DrawTodayCircle(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Screen_x = displayMetrics.widthPixels;
        Screen_y = Screen_x;
        todayCircle = new TodayCircle(this, Screen_x, Screen_y, workTime, studyTime, playTime, sleepTime);
        todayCircle.setZOrderOnTop(true);
    }

    public void onPause() {
        super.onPause();
        Layout.removeView(todayCircle);
    }

    public void onResume() {
        super.onResume();
        if (logo > 0) {
            String date_x = timeNoteUtil.CurrentTime();
            arrayList = new ArrayList<Map<String, Object>>();
            arrayList = sQlManager.GetTimeToDrawPicture(date_x);
            if (arrayList.isEmpty()) {
                workTime = studyTime = playTime = sleepTime = 0;
            } else {
                workTime = Integer.parseInt((String) arrayList.get(0).get((Object) "work_time"))/4;
                studyTime = Integer.parseInt((String) arrayList.get(0).get((Object) "study_time"))/4;
                playTime = Integer.parseInt((String) arrayList.get(0).get((Object) "play_time"))/4;
                sleepTime = Integer.parseInt((String) arrayList.get(0).get((Object) "sleep_time"))/4;

            }
            todayCircle = new TodayCircle(this, Screen_x, Screen_y, workTime, studyTime, playTime, sleepTime);
            Layout.addView(todayCircle);
            todayCircle.setZOrderOnTop(true);
        }
        logo++;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            timeNoteUtil.DialogBuild();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }
}
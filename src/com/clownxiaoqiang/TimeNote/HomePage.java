package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.*;
import come.clownxiaoqiang.TimeNote.Sql.SQlManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-20
 * Time: afternoon 7:49
 * To change this template use File | Settings | File Templates.
 */
public class HomePage extends Activity {

    private int Screen_x, Screen_y, first_in;
    private DrawCircle drawCircle;
    private LinearLayout linearLayout;
    private Spinner myspinner;
    private Button savebutton;
    private static final String[] Spinner_Text = {"学习", "工作", "睡觉", "娱乐"};
    private ArrayAdapter<String> adapter;
    private SQlManager sQlManager, dataQuery;
    private String minute_time;
    private int logo;
    //要储存的数据
    private String tag, date, time, event_id;
    //要查询的数据
    private ArrayList<Map<String, Object>> arrayList;
    private int workTime, studyTime, playTime, sleepTime, totalTime;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这个是布局添加
        setContentView(R.layout.homepage);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Screen_x = displayMetrics.widthPixels;
        Screen_y = displayMetrics.heightPixels;
        first_in = 0;
        drawCircle = new DrawCircle(this, Screen_x, Screen_y);
        linearLayout = (LinearLayout) findViewById(R.id.mylinearlayout);
        linearLayout.addView(drawCircle);

        myspinner = (Spinner) findViewById(R.id.myspinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Spinner_Text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);
        myspinner.setSelection(0);
        myspinner.setOnItemSelectedListener(new ItemSelectedListener());


        savebutton = (Button) findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                time = drawCircle.getTime();
                minute_time = drawCircle.getMinuteTime() + "";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                Date date_S = new Date(System.currentTimeMillis());
                String date_x = simpleDateFormat.format(date_S);
                dataQuery = new SQlManager(HomePage.this);
                arrayList = new ArrayList<Map<String, Object>>();
                arrayList = dataQuery.GetTimeToDrawPicture(date_x);
                Log.d("arraylist", arrayList.toString());
                if (arrayList.isEmpty()) {
                    workTime = studyTime = playTime = sleepTime = 0;
                } else {
                    workTime = Integer.parseInt((String) arrayList.get(0).get((Object) "work_time"));
                    studyTime = Integer.parseInt((String) arrayList.get(0).get((Object) "study_time"));
                    playTime = Integer.parseInt((String) arrayList.get(0).get((Object) "play_time"));
                    sleepTime = Integer.parseInt((String) arrayList.get(0).get((Object) "sleep_time"));
                    totalTime = workTime + studyTime + playTime + sleepTime + Integer.parseInt(minute_time);
                    Log.d("arraylist", workTime + " " + studyTime + " " + playTime + " " + sleepTime + " "+totalTime);

                }
                if (totalTime > 1440) {
                    Toast.makeText(HomePage.this, "已经超过24小时了", Toast.LENGTH_SHORT).show();
                } else {
                    sQlManager = new SQlManager(HomePage.this);
                    sQlManager.Addnote(date, tag, time, event_id, minute_time);
                    Toast.makeText(HomePage.this, "保存成功", Toast.LENGTH_SHORT).show();
                    Log.d("message", date + tag + time + event_id);
                }
            }
        });
    }

    public void onStart() {
        super.onStart();
    }

    public void onPause() {
        super.onPause();
        Log.d("zhuangtai", "pause");
        linearLayout.removeView(drawCircle);
    }

    public void onResume() {
        super.onResume();
        Log.d("resume_home", logo + "");
        if (logo > 0) {
            linearLayout.addView(drawCircle);
        }
        logo++;
        Log.d("resume_home", logo + "");

    }

    public void onRestart() {
        super.onRestart();
        Log.d("restart_home", "home_run");
    }

    class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date_S = new Date(System.currentTimeMillis());

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("click", "id--->" + l);
            event_id = l + "";
            date = simpleDateFormat.format(date_S);
            tag = "打游戏";
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}

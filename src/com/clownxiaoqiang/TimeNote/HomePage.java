package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.clownxiaoqiang.TimeNote.Util.TimeNoteUtil;
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

    private int Screen_x, Screen_y;
    private DrawCircle drawCircle;
    private LinearLayout linearLayout;
    private Spinner myspinner;
    private Button savebutton,counttimebutton,canclebutton;
    private EditText tagEditText;
    private static final String[] Spinner_Text = {"学习", "工作", "休息", "娱乐"};
    private ArrayAdapter<String> adapter;
    private SQlManager sQlManager, dataQuery;
    private String minute_time;
    private int logo;
    private static boolean Iscounting = false;
    private TimeNoteUtil timeNoteUtil;
    final static int Count =2;
    //要储存的数据
    private String tag, date, time, event_id, date_month, date_week;
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
        drawCircle = new DrawCircle(this, Screen_x, Screen_y);
        drawCircle.setZOrderOnTop(true);
        linearLayout = (LinearLayout) findViewById(R.id.mylinearlayout);
        linearLayout.addView(drawCircle);

        tagEditText = (EditText) this.findViewById(R.id.tagEditText);

        myspinner = (Spinner) findViewById(R.id.myspinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Spinner_Text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);
        myspinner.setSelection(0);
        myspinner.setOnItemSelectedListener(new ItemSelectedListener());

        counttimebutton = (Button)findViewById(R.id.CountTimeButton);
        counttimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Iscounting == false ){
                    if ((drawCircle.getSecond()!=0)||(drawCircle.getMinuteTime()!=0)){
                        drawCircle.TimeCount();
                        Iscounting = true ;
                    }
                }
            }
        });

        canclebutton = (Button)findViewById(R.id.CancleButton);
        canclebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Iscounting){
                    drawCircle.TimeCountCancle();
                    Iscounting = false;
                }
            }
        });
        savebutton = (Button) findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                tag = tagEditText.getText().toString();
                time = drawCircle.getTime();
                minute_time = drawCircle.getMinuteTime() + "";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                Date date_S = new Date(System.currentTimeMillis());
                String date_x = simpleDateFormat.format(date_S);

                SimpleDateFormat monthFormat = new SimpleDateFormat("M月d日");
                date_month = monthFormat.format(date_S);

                SimpleDateFormat weekFormat = new SimpleDateFormat("E");
                date_week = weekFormat.format(date_S);


                dataQuery = new SQlManager(HomePage.this);
                arrayList = new ArrayList<Map<String, Object>>();
                arrayList = dataQuery.GetTimeToDrawPicture(date_x);
                if (arrayList.isEmpty()) {
                    workTime = 0;
                    studyTime = 0;
                    playTime = 0;
                    sleepTime = 0;
                } else {
                    workTime = Integer.parseInt((String) arrayList.get(0).get((Object) "work_time"));
                    studyTime = Integer.parseInt((String) arrayList.get(0).get((Object) "study_time"));
                    playTime = Integer.parseInt((String) arrayList.get(0).get((Object) "play_time"));
                    sleepTime = Integer.parseInt((String) arrayList.get(0).get((Object) "sleep_time"));
                    totalTime = workTime + studyTime + playTime + sleepTime + Integer.parseInt(minute_time);

                }
                if (totalTime > 1440) {
                    Toast.makeText(HomePage.this, "已经超过24小时了", Toast.LENGTH_SHORT).show();
                } else if (tag.isEmpty()) {
                    Toast.makeText(HomePage.this, "还没添加标签", Toast.LENGTH_SHORT).show();
                } else {
                    sQlManager = new SQlManager(HomePage.this);
                    sQlManager.Addnote(date, tag, time, event_id, minute_time, date_month, date_week);
                    Toast.makeText(HomePage.this, "保存成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void onPause() {
            linearLayout.removeView(drawCircle);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void onResume() {
        if (logo > 0) {
            linearLayout.addView(drawCircle);
            drawCircle.setZOrderOnTop(true);
        }
        logo++;
        super.onResume();

    }
     public static Handler cHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Count:
                    Iscounting = msg.getData().getBoolean("changeIscounting");
                    break;
                default:
                    break;
            }
        }
    };


    class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date_S = new Date(System.currentTimeMillis());

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            event_id = l + "";
            date = simpleDateFormat.format(date_S);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        {
            if (keyCode == event.KEYCODE_BACK) {
                    timeNoteUtil = new TimeNoteUtil(HomePage.this);
                    timeNoteUtil.DialogBuild();
                    return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
    }
}

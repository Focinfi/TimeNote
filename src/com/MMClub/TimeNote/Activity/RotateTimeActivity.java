package com.MMClub.TimeNote.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.*;
import com.MMClub.TimeNote.R;
import com.MMClub.TimeNote.View.RotateTimeCircle;
import com.MMClub.TimeNote.Util.TimeNoteUtil;
import com.MMClub.TimeNote.SQL.SQlManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: Dai Zhi Qiang
 * Date: 13-9-20
 * Time: afternoon 7:49
 * 旋转时间类
 */
public class RotateTimeActivity extends Activity {

    public static int Count = 2;
    private int Screen_x, Screen_y;
    private RotateTimeCircle rotateTimeCircle;
    private LinearLayout linearLayout;
    private Spinner myspinner;
    private Button savebutton, counttimebutton, canclebutton;
    private EditText tagEditText;
    private static final String[] Spinner_Text = {"学习", "工作", "休息", "娱乐"};
    private ArrayAdapter<String> adapter;
    private SQlManager sQlManager, dataQuery;
    private String minute_time;
    private int logo;
    private static boolean Iscounting = false;
    private TimeNoteUtil timeNoteUtil;
    private boolean IsDestroyButton = false;
    //要储存的数据
    private String tag, date, time, event_id, date_month, date_week;
    //要查询的数据
    private ArrayList<Map<String, Object>> arrayList;
    private int workTime, studyTime, playTime, sleepTime, totalTime;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这个是布局添加
        setContentView(R.layout.homepage);
        //初始化
        init();
        //Spinner的初始化操作
        initSpinner();
        //counttimebutton的初始化和onClick操作
        initCountTimeButton();
        //cancletimebutton的初始化和onClick操作
        initCancleButton();
        //savebutton的初始化和onClick操作
        initSaveButton();
    }

    private void init() {
        timeNoteUtil = new TimeNoteUtil(RotateTimeActivity.this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Screen_x = displayMetrics.widthPixels;
        Screen_y = displayMetrics.heightPixels;
        rotateTimeCircle = new RotateTimeCircle(this, Screen_x, Screen_y);
        rotateTimeCircle.setZOrderOnTop(true);
        linearLayout = (LinearLayout) findViewById(R.id.mylinearlayout);
        linearLayout.addView(rotateTimeCircle);
        tagEditText = (EditText) this.findViewById(R.id.tagEditText);
    }

    private void initSpinner() {
        myspinner = (Spinner) findViewById(R.id.myspinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Spinner_Text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);
        myspinner.setSelection(0);
        myspinner.setOnItemSelectedListener(new ItemSelectedListener());
    }

    private void initCountTimeButton() {
        counttimebutton = (Button) findViewById(R.id.CountTimeButton);
        counttimebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Iscounting == false) {
                    if ((rotateTimeCircle.getSecond() != 0) || (rotateTimeCircle.getMinuteTime() != 0)) {
                        rotateTimeCircle.TimeCount();
                        Iscounting = true;
                    }
                }
            }
        });
    }

    private void initSaveButton() {
        savebutton = (Button) findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                tag = tagEditText.getText().toString();
                time = rotateTimeCircle.getTime();
                minute_time = rotateTimeCircle.getMinuteTime() + "";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                Date date_S = new Date(System.currentTimeMillis());
                String date_x = simpleDateFormat.format(date_S);

                SimpleDateFormat monthFormat = new SimpleDateFormat("M月d日");
                date_month = monthFormat.format(date_S);

                SimpleDateFormat weekFormat = new SimpleDateFormat("E");
                date_week = weekFormat.format(date_S);

                dataQuery = new SQlManager(RotateTimeActivity.this);
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
                    Toast.makeText(RotateTimeActivity.this, "已经超过24小时了", Toast.LENGTH_SHORT).show();
                } else if (tag.isEmpty()) {
                    Toast.makeText(RotateTimeActivity.this, "还没添加标签", Toast.LENGTH_SHORT).show();
                } else if (minute_time.contentEquals("0")) {
                    Toast.makeText(RotateTimeActivity.this, "还没有旋转时间", Toast.LENGTH_SHORT).show();
                } else {
                    sQlManager = new SQlManager(RotateTimeActivity.this);
                    sQlManager.AddNote(date, tag, time, event_id, minute_time, date_month, date_week);
                    Toast.makeText(RotateTimeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initCancleButton() {
        canclebutton = (Button) findViewById(R.id.CancleButton);
        canclebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Iscounting) {
                    rotateTimeCircle.TimeCountCancel();
                    Iscounting = false;
                }
            }
        });
    }


    public void onPause() {
        linearLayout.removeView(rotateTimeCircle);
        super.onPause();
    }

    public void onResume() {
        if (logo > 0) {
            linearLayout.addView(rotateTimeCircle);
            rotateTimeCircle.setZOrderOnTop(true);
        }
        logo++;
        super.onResume();

    }

    public static Handler cHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    Iscounting = msg.getData().getBoolean("changeIscounting");
                    break;
                default:
                    break;
            }
        }
    };


    class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            event_id = l + "";
            date = timeNoteUtil.CurrentTime();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        {
            if (keyCode == event.KEYCODE_BACK) {
                timeNoteUtil.DialogBuild();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
    }
}

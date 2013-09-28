package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    private int Screen_x, Screen_y;
    private DrawCircle drawCircle;
    private LinearLayout linearLayout;
    private Spinner myspinner;
    private Button savebutton,counttimebutton;
    private EditText tagEditText;
    private static final String[] Spinner_Text = {"学习", "工作", "睡觉", "娱乐"};
    private ArrayAdapter<String> adapter;
    private SQlManager sQlManager, dataQuery;
    private String minute_time;
    private int logo;
    private static boolean Iscounting = false;
    private boolean IsDestroyButton = false;
    final static int Count =2;
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
        drawCircle = new DrawCircle(this, Screen_x, Screen_y);
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
                if(Iscounting == false){
                    drawCircle.TimeCount();
                    Iscounting = true ;
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
                dataQuery = new SQlManager(HomePage.this);
                arrayList = new ArrayList<Map<String, Object>>();
                arrayList = dataQuery.GetTimeToDrawPicture(date_x);
                Log.d("arraylist", arrayList.toString());
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
                    Log.d("arraylist", workTime + " " + studyTime + " " + playTime + " " + sleepTime + " " + totalTime);

                }
                if (totalTime > 1440) {
                    Toast.makeText(HomePage.this, "已经超过24小时了", Toast.LENGTH_SHORT).show();
                } else if (tag.isEmpty()) {
                    Toast.makeText(HomePage.this, "还没添加标签", Toast.LENGTH_SHORT).show();
                } else {
                    sQlManager = new SQlManager(HomePage.this);
                    sQlManager.Addnote(date, tag, time, event_id, minute_time);
                    Toast.makeText(HomePage.this, "保存成功", Toast.LENGTH_SHORT).show();
                    Log.d("message", date + tag + time + event_id);
                }
            }
        });
    }


    public void onPause() {
        super.onPause();
        Log.d("zhuangtai", "pause");
        if(IsDestroyButton == false){
            drawCircle.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        Log.d("zhuangtai","destroy");
    }

    public void onResume() {
        super.onResume();
        if (logo > 0) {
            drawCircle.setVisibility(View.VISIBLE);
        }
        logo++;

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
            Log.d("click", "id--->" + l);
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
                new AlertDialog.Builder(this)
                        .setTitle("提示：")
                        .setMessage("是否退出？")
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        IsDestroyButton = true ;
                                        finish();
                                    }
                                }).show();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
    }
}

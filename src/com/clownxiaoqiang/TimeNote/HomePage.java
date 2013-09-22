package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.*;
import come.clownxiaoqiang.TimeNote.Sql.SQlManager;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Spinner myspinner ;
    private Button savebutton;
    private static  final String[] Spinner_Text={"学习","工作","睡觉","娱乐"};
    private ArrayAdapter<String> adapter ;
    private SQlManager sQlManager;
    private String minute_time;
    //要储存的数据
    private String tag,date,time,event_id;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这个是布局添加
        setContentView(R.layout.homepage);

        DisplayMetrics displayMetrics =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Screen_x = displayMetrics.widthPixels;
        Screen_y = displayMetrics.heightPixels;
        drawCircle =new DrawCircle(this,Screen_x,Screen_y);
        linearLayout = (LinearLayout)findViewById(R.id.mylinearlayout);
        linearLayout.addView(drawCircle);

        myspinner = (Spinner)findViewById(R.id.myspinner);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Spinner_Text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);
        myspinner.setSelection(0);
        myspinner.setOnItemSelectedListener(new ItemSelectedListener());

        savebutton = (Button)findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                time = drawCircle.getTime();
                minute_time = drawCircle.getMinuteTime()+"";
                sQlManager = new SQlManager(HomePage.this);
                sQlManager.Addnote(date, tag, time, event_id,minute_time);
                Log.d("message",date+tag+time+event_id);
            }
        });
    }

    class  ItemSelectedListener implements AdapterView.OnItemSelectedListener{

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date_S = new Date(System.currentTimeMillis());
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("click","id--->"+l);
            event_id = l + "" ;
            date = simpleDateFormat.format(date_S);
            tag = "打游戏";
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}

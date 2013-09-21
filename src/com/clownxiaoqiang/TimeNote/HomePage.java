package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

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
    private static  final String[] Spinner_Text={"标签更换","写日记","保存"};
    private ArrayAdapter<String> adapter ;
    private static final long TAG_CHANGE = 0;
    private static final long WRITE_DIARY = 1;
    private static final long SAVE = 2;

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

        myspinner.setOnItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //To change body of implemented methods use File | Settings | File Templates.
            Log.d("click","标签---->  "+Spinner_Text[i]+"id为--->  "+l);
            if (l == TAG_CHANGE) {

            }else if(l == WRITE_DIARY){
                Intent intent =new Intent();
                intent.setClass(HomePage.this,DiaryWrite.class);
                startActivity(intent);
            }else if(l == SAVE){

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}

package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import come.clownxiaoqiang.TimeNote.Sql.SQlManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-21
 * Time: 下午8:19
 * To change this template use File | Settings | File Templates.
 */
public class DiaryWrite extends Activity {
    private String title, date, diary;
    private TextView titleTextView;
    private EditText noteEditText;
    private Button addNote;
    private SQlManager noteManager,updateManager;
    private Button backButton;
    private ArrayList<Map<String,Object>> noteArrayList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.writediary);

        titleTextView = (TextView) this.findViewById(R.id.writeDiaryTitleTextView);
        addNote = (Button) this.findViewById(R.id.saveNote);
        backButton = (Button) this.findViewById(R.id.writeDiaryBackButton);

        noteEditText = (EditText) this.findViewById(R.id.diaryEditText);
        Intent intent = getIntent();
        title = intent.getStringExtra("date_month") + "的笔记";
        date = intent.getStringExtra("date");

        titleTextView.setText(title);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaryWrite.this.finish();
            }
        });
        noteManager = new SQlManager(DiaryWrite.this);
        noteArrayList = new ArrayList<Map<String, Object>>();
        noteArrayList = noteManager.query(date);


        diary = noteArrayList.get(0).get((Object) "note").toString();

        noteEditText.setText(diary);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diary = noteEditText.getText().toString();
                Intent intent = getIntent();
                date = intent.getStringExtra("date");
                if (diary.isEmpty()) {
                    Toast.makeText(DiaryWrite.this, "还没有写笔记啊", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("d_d", diary + "-->" + date);
                    updateManager = new SQlManager(DiaryWrite.this);
                    updateManager.updatenote(diary, date);
                    Toast.makeText(DiaryWrite.this,"保存成功",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
package com.MMClub.TimeNote.Activity;

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
import com.MMClub.TimeNote.R;
import com.MMClub.TimeNote.SQL.SQlManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: Wang Tao
 * Date: 13-9-21
 * Time: 下午8:19
 * 写日记类
 */
public class DiaryWriteActivity extends Activity {
    private String title, date, diary;
    private TextView titleTextView;
    private EditText noteEditText;
    private Button addNote;
    private SQlManager noteManager, updateManager;
    private Button backButton;
    private ArrayList<Map<String, Object>> noteArrayList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.write_diary);

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
                DiaryWriteActivity.this.finish();
            }
        }); //返回按钮的实现

        noteManager = new SQlManager(DiaryWriteActivity.this);
        noteArrayList = new ArrayList<Map<String, Object>>();
        noteArrayList = noteManager.Query(date);


        diary = (String) noteArrayList.get(0).get((Object) "note"); //拿到当天的笔记

        noteEditText.setText(diary);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diary = String.valueOf(noteEditText.getText());
                Intent intent = getIntent();
                date = intent.getStringExtra("date");
                if (diary.isEmpty()) {
                    Toast.makeText(DiaryWriteActivity.this, "还没有写笔记啊", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("d_d", diary + "-->" + date);
                    updateManager = new SQlManager(DiaryWriteActivity.this);
                    updateManager.UpdateNote(diary, date);               //更新数据库笔记数据
                    Toast.makeText(DiaryWriteActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
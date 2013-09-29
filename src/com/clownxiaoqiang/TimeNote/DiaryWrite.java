package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import come.clownxiaoqiang.TimeNote.Sql.SQlManager;

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
    private SQlManager updateManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.writediary);

        titleTextView = (TextView) this.findViewById(R.id.writeDiaryTitleTextView);
        addNote = (Button) this.findViewById(R.id.saveNote);

        noteEditText = (EditText) this.findViewById(R.id.diaryEditText);
        diary = noteEditText.getText().toString();

        Intent intent = getIntent();
        title = intent.getStringExtra("date_month") + "的笔记";
        titleTextView.setText(title);
        date = intent.getStringExtra("date");
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateManager = new SQlManager(DiaryWrite.this);
                updateManager.updatenote(diary, date);
            }
        });
    }

}
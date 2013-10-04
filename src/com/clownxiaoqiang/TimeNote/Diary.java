package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.clownxiaoqiang.TimeNote.Util.TimeNoteUtil;
import come.clownxiaoqiang.TimeNote.Sql.SQlManager;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-20
 * Time: ����7:50
 * To change this template use File | Settings | File Templates.
 */
public class Diary extends Activity {
    private ListView diaryListView;
    private SQlManager sQlManager;
    private ArrayList<Map<String, Object>> arrayList;
    private DiaryAdapter timeLineAdapter;
    private TimeNoteUtil timeNoteUtil;
    private String date_x;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);
        diaryListView = (ListView) this.findViewById(R.id.diaryListView);
        sQlManager = new SQlManager(Diary.this);
        arrayList = new ArrayList<Map<String, Object>>();
        arrayList = sQlManager.query("");
        if (arrayList != null) {
            timeLineAdapter = new DiaryAdapter(Diary.this);
            diaryListView.setAdapter(timeLineAdapter);
            diaryListView.setOnItemClickListener(new diaryItemClickListener());
        }
    }

    public void onResume() {
        super.onResume();
        sQlManager = new SQlManager(Diary.this);
        arrayList = new ArrayList<Map<String, Object>>();

        arrayList = sQlManager.query("");

        if (arrayList != null) {
            timeLineAdapter.notifyDataSetChanged();
        }
    }

    public final class DiaryViewHolder {
        public TextView workTimeTextView;
        public TextView studyTimeTextView;
        public TextView playTimeTextView;
        public TextView sleepTimeTextView;
        public TextView diaryNoteTextView;
        public TextView dateMonthTextView;
        public TextView dateWeekTextView;
    }

    public class DiaryAdapter extends BaseAdapter {
        private LayoutInflater diaryInflater;

        public DiaryAdapter(Context context) {
            this.diaryInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public long getItemId(int position) {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            position = arrayList.size() - 1 - position; //倒置数组顺序
            DiaryViewHolder holder = null;
            if (convertView == null) {

                holder = new DiaryViewHolder();

                convertView = diaryInflater.inflate(R.layout.item_time_line, null);
                holder.workTimeTextView = (TextView) convertView.findViewById(R.id.workTimeView);
                holder.studyTimeTextView = (TextView) convertView.findViewById(R.id.studyTimeView);
                holder.playTimeTextView = (TextView) convertView.findViewById(R.id.playTimeView);
                holder.sleepTimeTextView = (TextView) convertView.findViewById(R.id.sleepTimeView);
                holder.diaryNoteTextView = (TextView) convertView.findViewById(R.id.diaryNoteTextView);
                holder.dateMonthTextView = (TextView) convertView.findViewById(R.id.dateMonthTextView);
                holder.dateWeekTextView = (TextView) convertView.findViewById(R.id.dateWeekTextView);
                convertView.setTag(holder);

            } else {

                holder = (DiaryViewHolder) convertView.getTag();
            }
            String work_time = "工作   " + ChangeTime(Integer.parseInt(arrayList.get(position).get((Object) "work_time").toString()));
            String study_time = "学习   " + ChangeTime(Integer.parseInt(arrayList.get(position).get((Object) "study_time").toString()));
            String play_time = "娱乐   " + ChangeTime(Integer.parseInt(arrayList.get(position).get((Object) "play_time").toString()));
            String sleep_time = "休息   " + ChangeTime(Integer.parseInt(arrayList.get(position).get((Object) "sleep_time").toString()));
            String note = "笔记：" + arrayList.get(position).get((Object) "note").toString();
            String date_month = arrayList.get(position).get((Object) "date_month").toString();
            String date_week = arrayList.get(position).get((Object) "date_week").toString();

            holder.workTimeTextView.setText(work_time);
            holder.studyTimeTextView.setText(study_time);
            holder.playTimeTextView.setText(play_time);
            holder.sleepTimeTextView.setText(sleep_time);
            holder.diaryNoteTextView.setText(note);
            holder.dateMonthTextView.setText(date_month);
            holder.dateWeekTextView.setText(date_week);

            return convertView;  //To change body of implemented methods use File | Settings | File Templates.
        }

        private String ChangeTime(int minute) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH : mm");
            Date date = new Date(0, 0, 0, 0, minute);
            String s = simpleDateFormat.format(date);
            return s;

        }


    }

    class diaryItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int truePosition = arrayList.size() - 1 - position;
            HashMap<String, Object> hashMap = (HashMap<String, Object>) parent.getItemAtPosition(truePosition);
            String datetext = (String) hashMap.get("date");
            Log.d("datetext", datetext);
            Intent intent = new Intent();
            intent.putExtra("datetext", datetext);
            intent.putExtra("date", arrayList.get(truePosition).get((Object) "date").toString());
            intent.setClass(Diary.this, TagDetail.class);
            Diary.this.startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            timeNoteUtil = new TimeNoteUtil(Diary.this);
            timeNoteUtil.DialogBuild();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
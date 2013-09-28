package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import come.clownxiaoqiang.TimeNote.Sql.SQlManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-20
 * Time: ����7:50
 * To change this template use File | Settings | File Templates.
 */
public class Diary extends Activity {
    private ListView diaryListView;
    private String workTime, studyTime, playTime, sleepTime, date, week;
    private SQlManager sQlManager;
    private ArrayList<Map<String, Object>> arrayList;
    private DiaryAdapter timeLineAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);
        diaryListView = (ListView) this.findViewById(R.id.diaryListView);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date_S = new Date(System.currentTimeMillis());
        String date_x = simpleDateFormat.format(date_S);
        sQlManager = new SQlManager(Diary.this);
        arrayList = new ArrayList<Map<String, Object>>();

        arrayList = sQlManager.query("");
        Log.d("d_all", arrayList.toString());

//        timeLineAdapter = new SimpleAdapter(Diary.this, arrayList, R.layout.item_time_line,
//                new String[]{"work_time", "study_time", "play_time", "sleep_time", "note", "date_month", "date_week"},
//                new int[]{R.id.workTimeView, R.id.studyTimeView, R.id.playTimeView, R.id.sleepTimeView, R.id.diaryNoteTextView, R.id.dateMonthTextView, R.id.dateWeekTextView});
        timeLineAdapter = new DiaryAdapter(Diary.this);
        diaryListView.setAdapter(timeLineAdapter);
    }

    public void onResume(){
        super.onResume();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date_S = new Date(System.currentTimeMillis());
        String date_x = simpleDateFormat.format(date_S);
        sQlManager = new SQlManager(Diary.this);
        arrayList = new ArrayList<Map<String, Object>>();

        arrayList = sQlManager.query("");
        Log.d("d_all", arrayList.toString());

//        timeLineAdapter = new SimpleAdapter(Diary.this, arrayList, R.layout.item_time_line,
//                new String[]{"work_time", "study_time", "play_time", "sleep_time", "note", "date_month", "date_week"},
//                new int[]{R.id.workTimeView, R.id.studyTimeView, R.id.playTimeView, R.id.sleepTimeView, R.id.diaryNoteTextView, R.id.dateMonthTextView, R.id.dateWeekTextView});
        timeLineAdapter.notifyDataSetChanged();
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
}
package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
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
 * Author: Wang Tao
 * Date: 13-9-20
 * Time: 7:50
 * 时间轴类
 */
public class TimeLineActivity extends Activity {
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
        sQlManager = new SQlManager(TimeLineActivity.this);
        arrayList = new ArrayList<Map<String, Object>>();
        arrayList = sQlManager.Query("");
        if (arrayList != null) {
            timeLineAdapter = new DiaryAdapter(TimeLineActivity.this);
            diaryListView.setAdapter(timeLineAdapter);
            diaryListView.setOnItemClickListener(new diaryItemClickListener());
        }
    }

    public void onResume() {
        super.onResume();
        sQlManager = new SQlManager(TimeLineActivity.this);
        arrayList = new ArrayList<Map<String, Object>>();
        arrayList = sQlManager.Query("");

        if (arrayList != null) {
            timeLineAdapter.notifyDataSetChanged(); //数据库数据发生改变，重新适配
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
        private LayoutInflater layoutInflater;

        public DiaryAdapter(Context context) {
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            position = arrayList.size() - 1 - position; //倒置数组顺序
            DiaryViewHolder holder = null;
            if (convertView == null) {

                holder = new DiaryViewHolder();

                convertView = layoutInflater.inflate(R.layout.item_time_line, null);
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
            String note = arrayList.get(position).get((Object) "note").toString();
            String date_month = arrayList.get(position).get((Object) "date_month").toString();
            String date_week = arrayList.get(position).get((Object) "date_week").toString();

            holder.workTimeTextView.setText(work_time);
            holder.studyTimeTextView.setText(study_time);
            holder.playTimeTextView.setText(play_time);
            holder.sleepTimeTextView.setText(sleep_time);
            holder.diaryNoteTextView.setText(note);
            holder.dateMonthTextView.setText(date_month);
            holder.dateWeekTextView.setText(date_week);

            return convertView;
        }

        private String ChangeTime(int minute) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH : mm");
            Date date = new Date(0, 0, 0, 0, minute);                           //函数已被废弃，正在寻找新的函数
            String s = simpleDateFormat.format(date);
            return s;

        }


    }

    class diaryItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int truePosition = arrayList.size() - 1 - position;     //倒置输出数据库数据
            HashMap<String, Object> hashMap = (HashMap<String, Object>) parent.getItemAtPosition(truePosition);
            String dateText = (String) hashMap.get("date");
            Log.d("dateText", dateText);
            Intent intent = new Intent();
            intent.putExtra("dateText", dateText);
            intent.putExtra("date", arrayList.get(truePosition).get((Object) "date").toString());
            intent.setClass(TimeLineActivity.this, TagDetailActivity.class);
            TimeLineActivity.this.startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            timeNoteUtil = new TimeNoteUtil(TimeLineActivity.this);
            timeNoteUtil.DialogBuild();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
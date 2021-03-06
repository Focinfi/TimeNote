package com.MMClub.TimeNote.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: Dai Zhi Qiang
 * Date: 13-9-20
 * Time: 下午11:51
 * 数据库操作类
 */
public class SQlManager {
    private SQLiteNote sqLite;
    private SQLiteToday sQliteTwo;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;


    public SQlManager(Context context) {
        super();
        this.context = context;
        sQliteTwo = new SQLiteToday(context);
        sqLite = new SQLiteNote(context);
        sqLiteDatabase = sQliteTwo.getWritableDatabase();
        sqLiteDatabase = sqLite.getWritableDatabase();
    }

    public void AddToday(String date, String work_time, String study_time, String sleep_time, String play_time, String note, String date_month, String date_week) {
        try {
            sqLiteDatabase.execSQL("insert into today values(null,?,?,?,?,?,?,?,?)", new String[]{date, work_time, study_time, sleep_time, play_time, note, date_month, date_week});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void AddNote(String date, String tag, String time, String event_id, String minute_time, String date_month, String date_week) {
        try {
            sqLiteDatabase.execSQL("insert into note values(null,?,?,?,?,?,?,?)", new String[]{date, tag, time, event_id, minute_time, date_month, date_week});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Update(date, event_id, minute_time, date_month, date_week);
            //Toast.makeText(context,date+tag+time+event_id+"minute_time-->"+minute_time,Toast.LENGTH_LONG).show();
        }
    }

    //这个是today表的获取方法
    public ArrayList<Map<String, Object>> GetTimeToDrawPicture(String date) {
        sqLiteDatabase = sQliteTwo.getWritableDatabase();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from today where date like ?", new String[]{"%" + date + "%"});
        while (cursor.moveToNext()) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("work_time", cursor.getString(cursor.getColumnIndex("work_time")));
            item.put("study_time", cursor.getString(cursor.getColumnIndex("study_time")));
            item.put("sleep_time", cursor.getString(cursor.getColumnIndex("sleep_time")));
            item.put("play_time", cursor.getString(cursor.getColumnIndex("play_time")));
            arrayList.add(item);
            Log.d("item", item.toString());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Map<String, Object>> Query(String date) {
        sqLiteDatabase = sQliteTwo.getWritableDatabase();
        ArrayList<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from today where date like ?", new String[]{"%" + date + "%"});
        while (cursor.moveToNext()) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("work_time", cursor.getInt(cursor.getColumnIndex("work_time")));
            item.put("study_time", cursor.getString(cursor.getColumnIndex("study_time")));
            item.put("sleep_time", cursor.getString(cursor.getColumnIndex("sleep_time")));
            item.put("play_time", cursor.getString(cursor.getColumnIndex("play_time")));
            item.put("note", cursor.getString(cursor.getColumnIndex("note")));
            item.put("date_month", cursor.getString(cursor.getColumnIndex("date_month")));
            item.put("date_week", cursor.getString(cursor.getColumnIndex("date_week")));
            item.put("date", cursor.getString(cursor.getColumnIndex("date")));
            records.add(item);
            Log.d("item", item.toString());
        }

        cursor.close();
        return records;
    }

    public ArrayList<Map<String, Object>> QueryTag(String date) {
        sqLiteDatabase = sqLite.getWritableDatabase();
        ArrayList<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from note where date like ?", new String[]{"%" + date + "%"});
        while (cursor.moveToNext()) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("tag", cursor.getString(cursor.getColumnIndex("tag")));
            item.put("time", cursor.getString(cursor.getColumnIndex("time")));
            item.put("event_id", cursor.getString(cursor.getColumnIndex("event_id")));
            records.add(item);
        }
        cursor.close();
        return records;
    }

    public boolean QueryDate(String date) {
        sqLiteDatabase = sqLite.getWritableDatabase();
        sqLiteDatabase = sQliteTwo.getWritableDatabase();
        ArrayList<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from today where date like ?", new String[]{"%" + date + "%"});
        while (cursor.moveToNext()) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("date", cursor.getString(cursor.getColumnIndex("date")));
            records.add(item);
        }
        Log.d("records", records.toString());
        cursor.close();
        if ((records == null) || (records.toString().trim().equals("[]"))) {
            Log.d("records", "false");
            return false;
        }
        Log.d("records", "true");
        return true;
    }

    public String QueryTime(String event_id, String date, String newTime) {
        sqLiteDatabase = sQliteTwo.getWritableDatabase();
        String TotalTime = null;
        String event_name = null;
        event_name = Judge(event_id);

        Cursor cursor = sqLiteDatabase.rawQuery("select * from today where date like ?", new String[]{date});
        while (cursor.moveToNext()) {
            TotalTime = cursor.getString(cursor.getColumnIndex(event_name));
        }

        TotalTime = (Integer.parseInt(TotalTime) + Integer.parseInt(newTime)) + "";
        cursor.close();
        return TotalTime;
    }

    private String Judge(String event_id) {
        String event_name = "";
        if (event_id.equals("0")) {
            event_name = "study_time";
        } else if (event_id.equals("1")) {
            event_name = "work_time";
        } else if (event_id.equals("2")) {
            event_name = "sleep_time";
        } else if (event_id.equals("3")) {
            event_name = "play_time";
        }
        return event_name;
    }

    private void JudgeEvent(String event_id, String date, String time, String date_month, String date_week) {
        // 如果不存在该表，就新建一个
        if (event_id.equals("0")) {
            AddToday(date, "0", time, "0", "0", "还没有写笔记", date_month, date_week);
        } else if (event_id.equals("1")) {
            AddToday(date, time, "0", "0", "0", "还没有写笔记", date_month, date_week);
        } else if (event_id.equals("2")) {
            AddToday(date, "0", "0", time, "0", "还没有写笔记", date_month, date_week);
        } else if (event_id.equals("3")) {
            AddToday(date, "0", "0", "0", time, "还没有写笔记", date_month, date_week);
        }
    }

    //更新
    private void Update(String date, String event_id, String newTime, String date_month, String date_week) {
        if (QueryDate(date)) {
            ContentValues values = new ContentValues();
            String event_name = Judge(event_id);
            String update_key = QueryTime(event_id, date, newTime);
            values.put(event_name, update_key);
            String whereClause = "date=?";
            String[] whereArgs = {String.valueOf(date)};
            sqLiteDatabase.update("today", values, whereClause, whereArgs);
        } else {
            JudgeEvent(event_id, date, newTime, date_month, date_week);
        }

    }

    public void UpdateNote(String note, String date) {
        sqLiteDatabase = sQliteTwo.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("note", note);
        String whereClause = "date=?";
        String[] whereArgs = {String.valueOf(date)};
        sqLiteDatabase.update("today", values, whereClause, whereArgs);
    }

}

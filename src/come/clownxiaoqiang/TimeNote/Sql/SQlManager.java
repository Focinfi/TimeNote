package come.clownxiaoqiang.TimeNote.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-20
 * Time: 下午11:51
 * To change this template use File | Settings | File Templates.
 */
public class SQlManager {
    private SQLiteNote sqLite ;
    private SQliteToday sQliteTwo;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;


    public  SQlManager(Context context){
        super();
        this.context = context;
        sQliteTwo = new SQliteToday(context);
        sqLite =new SQLiteNote(context);
        sqLiteDatabase = sQliteTwo.getWritableDatabase();
        sqLiteDatabase = sqLite.getWritableDatabase();
    }

    public void Addtoday(String date,String work_time,String study_time,String sleep_time,String play_time,String note){
        try{
            sqLiteDatabase.execSQL("insert into today values(null,?,?,?,?,?,?)",new String[]{date,work_time,study_time,sleep_time,play_time,note});
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        }
    }

    public void Addnote (String date,String tag,String time,String event_id,String minute_time){
        try{
            sqLiteDatabase.execSQL("insert into note values(null,?,?,?,?,?)",new String[]{date,tag,time,event_id,minute_time});
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            update(date,event_id,minute_time);
            //Toast.makeText(context,date+tag+time+event_id+"minute_time-->"+minute_time,Toast.LENGTH_LONG).show();
        }
    }
    //这个是today表的获取方法
    public ArrayList<Map<String,Object>> GetTimeToDrawPicture(String date){
        sqLiteDatabase = sQliteTwo.getWritableDatabase();
        ArrayList<Map<String,Object>> arrayList = new ArrayList<Map<String, Object>>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from today where date like ?",new String[]{"%"+date+"%"});
        while (cursor.moveToNext()){
            Map<String,Object> item = new HashMap<String, Object>();
            item.put("work_time",cursor.getString(cursor.getColumnIndex("work_time")));
            item.put("study_time",cursor.getString(cursor.getColumnIndex("study_time")));
            item.put("sleep_time",cursor.getString(cursor.getColumnIndex("sleep_time")));
            item.put("play_time",cursor.getString(cursor.getColumnIndex("play_time")));
            arrayList.add(item);
            Log.d("item",item.toString());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Map<String,Object>> query(String date){
        ArrayList<Map<String,Object>> records =new ArrayList<Map<String,Object>>();
        Cursor cursor =sqLiteDatabase.rawQuery("select * from today where date = ?",new String[]{date});
        while(cursor.moveToNext()){
            Map<String,Object> item =new HashMap<String, Object>();
            item.put("work_time", cursor.getInt(cursor.getColumnIndex("work_time")));
            item.put("study_time", cursor.getString(cursor.getColumnIndex("study_time")));
            item.put("sleep_time", cursor.getString(cursor.getColumnIndex("sleep_time")));
            item.put("play_time", cursor.getString(cursor.getColumnIndex("play_time")));
            item.put("note",cursor.getString(cursor.getColumnIndex("note")));
            records.add(item);
            Log.d("item", item.toString());
        }

        cursor.close();
        return records;
    }

    public ArrayList<Map<String,Object>> queryTag(String date,String event_id){
        sqLiteDatabase = sqLite.getWritableDatabase();
        ArrayList<Map<String,Object>> records = new ArrayList<Map<String, Object>>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from today where date = ? and event_id = ?", new String[]{date, event_id});
        while (cursor.moveToNext()){
            Map<String,Object> item = new HashMap<String, Object>();
            item.put("tag",cursor.getString(cursor.getColumnIndex("tag")));
            item.put("time",cursor.getString(cursor.getColumnIndex("time")));
            records.add(item);
        }
        cursor.close();
        return records;
    }

    public boolean querydate(String date){
        sqLiteDatabase = sqLite.getWritableDatabase();
        sqLiteDatabase = sQliteTwo.getWritableDatabase();
        ArrayList<Map<String,Object>> records =new ArrayList<Map<String,Object>>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from today where date like ?",new String[]{"%"+date+"%"});
        while (cursor.moveToNext()){
            Map<String,Object> item = new HashMap<String, Object>();
            item.put("date", cursor.getString(cursor.getColumnIndex("date")));
            records.add(item);
        }
        Log.d("records", records.toString());
        cursor.close();
        if((records == null)||(records.toString().trim().equals("[]"))){
            Log.d("records","false");
            return false;
        }
        Log.d("records","true");
        return true;
    }

    public String queryTime(String event_id,String date,String newTime){
        sqLiteDatabase = sQliteTwo.getWritableDatabase();
        String TotalTime = null ;
        String event_name = null ;
        event_name = Judge(event_id);
        Log.d("mg",event_id+event_name);

        Cursor cursor = sqLiteDatabase.rawQuery("select * from today where date like ?",new String[]{date});
        while (cursor.moveToNext()){
            TotalTime = cursor.getString(cursor.getColumnIndex(event_name));
        }
        Log.d("TotalTime",newTime);
        Log.d("TotalTime",TotalTime);

        TotalTime = (Integer.parseInt(TotalTime)+Integer.parseInt(newTime))+"";
        cursor.close();
        return TotalTime;
    }

    private String Judge(String event_id){
        String event_name ="";
        if (event_id.equals("0")){
            event_name = "study_time";
        } else if (event_id.equals("1")){
            event_name = "work_time";
        } else if (event_id.equals("2")){
            event_name = "sleep_time";
        } else if (event_id.equals("3")){
            event_name = "play_time";
        }
        return event_name;
    }
    private void JudgeMent(String event_id,String date,String time){
        Log.d("event_id",event_id);
        if (event_id.equals("0")){
            Addtoday(date,"0",time,"0","0","还没记录了");
        } else if (event_id.equals("1")){
            Addtoday(date,time,"0","0","0","还没记录了");
        } else if (event_id.equals("2")){
            Addtoday(date,"0","0",time,"0","还没记录了");
        } else if (event_id.equals("3")){
            Addtoday(date,"0","0","0",time,"还没记录了");
        }
    }

    private void update(String date,String event_id,String newTime){
        if(querydate(date)){
            ContentValues values =new ContentValues();
            String event_name = Judge(event_id);
            String update_key = queryTime(event_id,date,newTime);
            values.put(event_name,update_key);
            String whereClause = "date=?";
            String[] whereArgs = {String.valueOf(date)};
            sqLiteDatabase.update("today",values,whereClause,whereArgs);
        }else {
            JudgeMent(event_id,date,newTime);
        }

    }

    private void updatenote(String note,String event_id,String date){
        ContentValues values = new ContentValues();
        String event_name = Judge(event_id);
        values.put(event_name,note);
        String whereClause = "date=?";
        String [] whereArgs = {String.valueOf(date)};
        sqLiteDatabase.update("today",values,whereClause,whereArgs);
    }

}

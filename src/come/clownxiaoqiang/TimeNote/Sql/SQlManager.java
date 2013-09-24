package come.clownxiaoqiang.TimeNote.Sql;

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
    private SQLite sqLite ;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;


    public  SQlManager(Context context){
        this.context = context;
        sqLite =new SQLite(context);
        sqLiteDatabase = sqLite.getWritableDatabase();
    }

    public void Addnote (String date,String tag,String time,String event_id,String minute_time){
        sqLiteDatabase.beginTransaction();
        try{
            sqLiteDatabase.execSQL("insert into note values(null,?,?,?,?,?)",new String[]{date,tag,time,event_id,minute_time});
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqLiteDatabase.endTransaction();
            Toast.makeText(context,date+tag+time+event_id+"minute_time-->"+minute_time,Toast.LENGTH_LONG).show();
        }
    }
    public ArrayList<Map<String,Object>> query(String date){
        ArrayList<Map<String,Object>> records =new ArrayList<Map<String,Object>>();
        Cursor cursor =sqLiteDatabase.rawQuery("select * from note where date like ?",new String[]{"%"+date+"%"});
        while(cursor.moveToNext()){
            Map<String,Object> item =new HashMap<String, Object>();
            item.put("id", cursor.getInt(cursor.getColumnIndex("_id")));
            item.put("date", cursor.getString(cursor.getColumnIndex("date")));
            item.put("tag", cursor.getString(cursor.getColumnIndex("tag")));
            item.put("time", cursor.getString(cursor.getColumnIndex("time")));
            item.put("event_id",cursor.getString(cursor.getColumnIndex("event_id")));
            item.put("minute_time",cursor.getString(cursor.getColumnIndex("minute_time")));
            records.add(item);
            Log.d("item", item.toString());
        }
        cursor.close();
        return records;
    }
}

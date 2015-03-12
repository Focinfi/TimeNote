package come.clownxiaoqiang.TimeNote.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * Author: Dai Zhi Qiang
 * Date: 13-9-20
 * Time: 下午11:23
 * Note表类
 */
public class SQLiteNote extends SQLiteOpenHelper {
    //表名Diary
    public static final String SQL_NAME = "note";
    //_id这个自动生成，为了是生成的唯一性，subject为主题，time为记录下日记的时间，content为记录下的内容
    public static final String CREATE_TABLE = "create table if not exists note(_id integer primary key autoincrement,date String,tag String,time String,event_id String,minute_time String,date_month String,date_week String)";

    private static final int VERSION = 1;

    public SQLiteNote(Context context) {
        super(context, SQL_NAME, null, VERSION);
    }

    public SQLiteNote(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, SQL_NAME, factory, VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        System.out.println("from" + i + "to" + i2);
    }
}

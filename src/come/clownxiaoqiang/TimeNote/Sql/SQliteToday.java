package come.clownxiaoqiang.TimeNote.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-24
 * Time: 下午4:34
 * To change this template use File | Settings | File Templates.
 */
public class SQliteToday extends SQLiteOpenHelper {
    //表名为today
    private static final String SQLNAME_Today = "today";
    //储存的数据有_id,event_id定位work,study,sleep,play,note初始化的值为"今天还没感想"
    private static final String CREAT_TABLE_Today = "create table if not exists today(_id integer primary key autoincrement,date String,work_time String," +
            "study_time String,sleep_time String,play_time String,note String,date_month String,date_week String)";
    private static final int VERSION = 1;

    public SQliteToday(Context context) {
        super(context, SQLNAME_Today, null, VERSION);
    }

    public SQliteToday(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, SQLNAME_Today, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAT_TABLE_Today);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("from" + i + "to" + i2);
    }

}

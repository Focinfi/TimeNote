package come.clownxiaoqiang.TimeNote.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-20
 * Time: 下午11:23
 * To change this template use File | Settings | File Templates.
 */
public class SQLite extends SQLiteOpenHelper {
    //表名Diary
    private static final String SQLNAME = "Diary" ;
    private static final String SQLNAME_2 = "Tag" ;
    //_id这个自动生成，为了是生成的唯一性，subject为主题，time为记录下日记的时间，content为记录下的内容
    private static final String CREAT_TABLE = "creat table if not exist Diary(_id integer primary key autoincrement,subject String,time String,content String)";

    private static final String CREAT_TABLE_2 ="creat table if not exist Tag(_id integer primary key autoincrement,tag String)";

    private static final int VERSION = 1;


    public SQLite(Context context, String name, SQLiteDatabase factory, int version,String table) {
        super(context, name, null, VERSION);
        onCreate(factory, table);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase,String Name) {
        //To change body of implemented methods use File | Settings | File Templates.
        sqLiteDatabase.execSQL(Name);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("from"+i+"to"+i2);
    }
}

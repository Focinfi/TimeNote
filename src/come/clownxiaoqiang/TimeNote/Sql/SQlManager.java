package come.clownxiaoqiang.TimeNote.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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


    public  SQlManager(Context context,String name,String table){
        sqLite =new SQLite(context,name,null,1,table);
        sqLiteDatabase = sqLite.getWritableDatabase();
        this.context = context;
    }
}

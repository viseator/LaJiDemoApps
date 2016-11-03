package xyz.viseator.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by viseator on 2016/11/2.
 */

public class DataBase extends SQLiteOpenHelper{
    private static final String DB_NAME = "data.db";
    public static final String TABLE_NAME = "data";
    public static int DATABASE_VERSION = 1;

    public DataBase(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT,context TEXT," +
                "creTime TEXT," +
                "endTime TEXT," +
                "primer INTEGER," +
                "done INTEGER)");
        Log.d("wudi","created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("wudi","DROP");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}

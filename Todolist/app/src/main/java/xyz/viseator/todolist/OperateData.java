package xyz.viseator.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by viseator on 2016/11/2.
 */

public class OperateData {
    Context context;
    DataBase dataBase;
    SQLiteDatabase db = null;

    public OperateData(Context context){
        this.context = context;
        dataBase = new DataBase(context);
        db = dataBase.getWritableDatabase();

    }

    public void initTable(){
        db.beginTransaction();
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context) VALUES ('title1','context1')");
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context) VALUES ('title2','context2')");
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context) VALUES ('title3','context3')");
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context) VALUES ('title4','context4')");
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context) VALUES ('title5','context5')");
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context) VALUES ('title6','context6')");
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public String getTitle(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        return c.getString(c.getColumnIndex("title"));
    }

    public String getContext(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        return c.getString(c.getColumnIndex("context"));
    }

    public void closedb(){
        db.close();
    }
}

package xyz.viseator.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        Log.d("wudi", "Init");
        db.beginTransaction();
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context,creTime,endTime) VALUES ('title1','context1','20161024','20161024')");
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context,creTime,endTime) VALUES ('title2','context2','20161024','20161024')");
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context,creTime,endTime) VALUES ('title3','context3','20161024','20161024')");
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context,creTime,endTime) VALUES ('title4','context4','20161024','20161024')");
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context,creTime,endTime) VALUES ('title5','context5','20161024','20161024')");
        db.execSQL("INSERT INTO " + DataBase.TABLE_NAME + " (title,context,creTime,endTime) VALUES ('title6','context6','20161024','20161024')");
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public String getTitle(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        c.moveToFirst();
        return c.getString(c.getColumnIndex("title"));
    }

    public String getContext(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        c.moveToFirst();
        return c.getString(c.getColumnIndex("context"));
    }

    public String getCreTime(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        c.moveToFirst();
        return c.getString(c.getColumnIndex("creTime"));
    }

    public String getEndTime(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        c.moveToFirst();
        return c.getString(c.getColumnIndex("endTime"));
    }


    public void setData(String title,String content,String creTime,String endTime,String done) {
        db.beginTransaction();
        db.execSQL("INSERT INTO " +
                DataBase.TABLE_NAME +
                " (title,context,creTime,endTime,done) VALUES ("+
                title+","+
                content+","+
                creTime+","+
                endTime+","+
                done+")");
        db.endTransaction();
    }
    public int count(){
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME, null);
        return c.getCount();
    }

    public void closedb(){
        db.close();
    }
}

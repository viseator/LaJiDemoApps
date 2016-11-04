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

    public boolean getCheck(int position){
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        c.moveToFirst();
        if (c.getInt(c.getColumnIndex("done"))==1) return true;
        else return false;
    }

    public void setCheck(int position,int check) {
        db.beginTransaction();
        db.execSQL("UPDATE "+
                    DataBase.TABLE_NAME + " SET done = "+String.valueOf(check)+" WHERE id = "+String.valueOf(position));
        db.setTransactionSuccessful();
        db.endTransaction();
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
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    public int count(){
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME, null);
        return c.getCount();
    }

    public void closedb(){
        db.close();
    }

    public void remove(int position) {
        db.beginTransaction();
        db.execSQL("DELETE FROM " + DataBase.TABLE_NAME + " WHERE id = "+ String.valueOf(position));
    }
}

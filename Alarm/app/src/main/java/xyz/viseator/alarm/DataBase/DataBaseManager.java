package xyz.viseator.alarm.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by viseator on 2016/11/8.
 */

public class DataBaseManager {
    private SQLiteDatabase db = null;

    public DataBaseManager(Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        db = dataBaseHelper.getWritableDatabase();
    }

    public void createData(int hour, int minute) {
        db.beginTransaction();
        db.execSQL("insert into " + DataBaseHelper.TABLE_NAME +
                " (id,hour,minute,ison) values (" +
                String.valueOf(setID(hour, minute)) + "," +
                String.valueOf(hour) + "," +
                String.valueOf(minute) + ",1)");
        db.setTransactionSuccessful();
        db.endTransaction();

    }

    public void removeData(int position) {
        db.beginTransaction();
        db.execSQL("DELETE FROM " + DataBaseHelper.TABLE_NAME + " WHERE id = " + String.valueOf(position));
        for (int i = position + 1; i <= count(); i++) {
            db.execSQL("UPDATE " +
                    DataBaseHelper.TABLE_NAME + " SET id = " + String.valueOf(i - 1) + " WHERE id = " + String.valueOf(i));
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public void updateData(int position, int hour, int minute) {
        db.beginTransaction();
        db.execSQL("UPDATE " + DataBaseHelper.TABLE_NAME +
                        " SET hour = " + String.valueOf(hour) + "," +
                        "minute = " + String.valueOf(minute) + "," +
                        "ison = 1" +
//                "id = " + String.valueOf(changeID(hour, minute)) +
                        " WHERE id = " + String.valueOf(position)
        );
        db.setTransactionSuccessful();
        db.endTransaction();
        resortId();
    }


    public boolean getIsOn(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        c.moveToFirst();
        int ison = c.getInt(c.getColumnIndex("ison"));
        c.close();
        return (ison == 1);
    }

    public int getHour(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        c.moveToFirst();
        int hour = c.getInt(c.getColumnIndex("hour"));
        c.close();
        return hour;
    }

    public int getMin(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        c.moveToFirst();
        int min = c.getInt(c.getColumnIndex("minute"));
        c.close();
        return min;
    }

    public int getTotalMinutes(int position) {
        return getHour(position) * 60 + getMin(position);
    }

    public int count() {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_NAME, null);
        int count = c.getCount();
        return count;
    }

    public void setIsOn(int position, boolean ison) {
        db.beginTransaction();
        String status;
        if (ison) status = "1";
        else status = "0";
        db.execSQL("update " + DataBaseHelper.TABLE_NAME + " set ison = " + status +
                " where id = " + String.valueOf(position));
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    //为新元素设置ID并更改后面元素的ID
    private int setID(int hour, int minute) {
        int total = hour * 60 + minute;
        for (int i = 0; i < count(); i++) {
            if (total <= getHour(i) * 60 + getMin(i)) {
                adjustID(i);
                return i;
            }
        }
        return count();
    }



    //从position后的所有行的id+1
    private void adjustID(int position) {
        db.beginTransaction();
        for (int i = count(); i > position; i--) {
            db.execSQL("update " + DataBaseHelper.TABLE_NAME + " set id = " + String.valueOf(i) +
                    " where id = " + String.valueOf(i - 1));
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void swapId(int pos1, int pos2) {
        db.beginTransaction();
        db.execSQL("UPDATE " +
                DataBaseHelper.TABLE_NAME + " SET id = -1 WHERE id = " + String.valueOf(pos1));
        db.execSQL("UPDATE " +
                DataBaseHelper.TABLE_NAME + " SET id = " + String.valueOf(pos1) + " WHERE id = " + String.valueOf(pos2));
        db.execSQL("UPDATE " +
                DataBaseHelper.TABLE_NAME + " SET id = " + String.valueOf(pos2) + " WHERE id = -1");
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void resortId() {
        for (int i = 0; i < count(); i++) {
            for (int j = i + 1; j < count(); j++) {
                if (getTotalMinutes(j) < getTotalMinutes(i)) {
                    swapId(i, j);
                }
            }
        }
    }

    public void close() {
        db.close();
    }

}

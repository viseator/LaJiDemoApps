package xyz.viseator.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    public String getPrimer(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        c.moveToFirst();
        switch (c.getInt(c.getColumnIndex("primer"))) {
            case 1:
                return "Shit";
            case 2:
                return "Normal";
            case 3:
                return "Important";
            case 4:
                return "Holy";
            default:
                return "Error";
        }
    }

    public int getPrimerNum(int position) {
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME + " WHERE id = ?", new String[]{String.valueOf(position)});
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("primer"));
    }

    public String getRemain(int position) throws ParseException {
        long remainMillis = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").parse(getEndTime(position)).getTime() - System.currentTimeMillis();
        if (remainMillis<=0) return "已过期";
        long remainMinutes = remainMillis / 1000 / 60;
        long remainHours = (remainMinutes / 60);
        return "剩余:"+String.valueOf(remainHours / 24) + "天" + String.valueOf(remainHours % 24) + "时" + String.valueOf(remainMinutes % 60) + "分";
    }

    public void setCheck(int position,int check) {
        db.beginTransaction();
        db.execSQL("UPDATE "+
                    DataBase.TABLE_NAME + " SET done = "+String.valueOf(check)+" WHERE id = "+String.valueOf(position));
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void setData(String title,String content,String creTime,String endTime,int primer,String done) {
        db.beginTransaction();
        db.execSQL("INSERT INTO " +
                DataBase.TABLE_NAME +
                " (id,title,context,creTime,endTime,primer,done) VALUES (" +
                String.valueOf(count()) + "," +
                title + "," +
                content + "," +
                creTime + "," +
                endTime + "," +
                String.valueOf(primer) + "," +
                done + ")");
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void updateData(int pos,String title,String content,String endTime,int primer) {
        db.beginTransaction();
        db.execSQL("UPDATE " + DataBase.TABLE_NAME +
                " SET title = " + title + "," +
                "context = " + content + "," +
                "endTime = " + endTime + ","+
                "primer = "+String.valueOf(primer)+" WHERE id = " + String.valueOf(pos)
        );
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public int count(){
        Cursor c = db.rawQuery("SELECT * FROM " + DataBase.TABLE_NAME, null);
        return c.getCount();
    }

    public void swapId(int pos1,int pos2){
        db.beginTransaction();
        db.execSQL("UPDATE "+
                DataBase.TABLE_NAME + " SET id = -1 WHERE id = "+String.valueOf(pos1));
        db.execSQL("UPDATE "+
                DataBase.TABLE_NAME + " SET id = "+String.valueOf(pos1)+" WHERE id = "+String.valueOf(pos2));
        db.execSQL("UPDATE "+
                DataBase.TABLE_NAME + " SET id = "+String.valueOf(pos2)+" WHERE id = -1");
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void setId(int pos, int id) {
        db.beginTransaction();
        db.execSQL("UPDATE "+
                DataBase.TABLE_NAME + " SET id = "+String.valueOf(id)+" WHERE id = "+String.valueOf(pos));
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void closedb(){
        db.close();
    }

    public void remove(int position) {
        db.beginTransaction();
        db.execSQL("DELETE FROM " + DataBase.TABLE_NAME + " WHERE id = "+ String.valueOf(position));
        db.setTransactionSuccessful();
        db.endTransaction();
        for (int i = position + 1; i <= count(); i++)
            setId(i, i - 1);
    }

    public void sortDataByEndTime(MyAdapter adapter) throws ParseException {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        for(int i = 0;i < count();i++) {
            for(int j = i + 1;j < count();j++ ) {
                long iTime = simpleDateFormat.parse(getEndTime(i)).getTime();
                long jTime = simpleDateFormat.parse(getEndTime(j)).getTime();
                if (jTime <= iTime) {
                    swapId(i,j);
                    adapter.notifyItemMoved(j,i);
                    adapter.notifyItemMoved(i+1,j);
                    adapter.notifyItemChanged(i);
                    adapter.notifyItemChanged(j);

                }
            }
        }
    }

    public void sortDataByPrimer(MyAdapter adapter) throws ParseException {
        for(int i = 0;i < count();i++) {
            for(int j = i + 1;j < count();j++ ) {
                if (getPrimerNum(i) <= getPrimerNum(j)) {
                    swapId(i,j);
                    adapter.notifyItemMoved(j,i);
                    adapter.notifyItemMoved(i+1,j);
                    adapter.notifyItemChanged(i);
                    adapter.notifyItemChanged(j);
                }
            }
        }
    }
}

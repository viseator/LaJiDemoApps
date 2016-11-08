package xyz.viseator.alarm.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by viseator on 2016/11/8.
 */

public class DataBaseManager {
    private DataBaseHelper dataBaseHelper;
    SQLiteDatabase db = null;

    public DataBaseManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        db = dataBaseHelper.getWritableDatabase();
    }


}

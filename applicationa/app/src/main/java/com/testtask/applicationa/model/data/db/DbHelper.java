package com.testtask.applicationa.model.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.testtask.applicationa.utils.Consts.DATE;
import static com.testtask.applicationa.utils.Consts.ID;
import static com.testtask.applicationa.utils.Consts.IMAGE_URL;
import static com.testtask.applicationa.utils.Consts.STATUS;
import static com.testtask.applicationa.utils.Consts.TABLE_IMAGES;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "image_data";

    private static final String CREATE_TABLE_IMAGES = "CREATE TABLE " + TABLE_IMAGES + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            IMAGE_URL  + " TEXT, " +
            STATUS + " INTEGER, " +
            DATE + " TEXT);";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // SAVE USER DATA FIRST!!!
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(sqLiteDatabase);
    }

}

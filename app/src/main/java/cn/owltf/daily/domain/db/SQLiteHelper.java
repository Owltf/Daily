package cn.owltf.daily.domain.db;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.owltf.daily.domain.application.App;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;

    public static String DATABASE_NAME = "owldaily.db";

    public static String CREATE_DAILYGSON_TABLE = "CREATE TABLE daily("
            + "id SMALLINT PRIMARY KEY NOT NULL,"
            + "title TEXT NOT NULL,"
            + "type SMALLINT NOT NULL,"
            + "image_source TEXT NULL,"
            + "image TEXT NULL,"
            + "share_url TEXT NULL,"
            + "ga_prefix SMALLINT NULL,"
            + "body TEXT NULL,"
            + "UNIQUE (id))";

    public static String CREATE_DAILYRESULT_TABLE = "CREATE TABLE dailyresult("
            + "date SMALLINT,"
            + "id SMALLINT PRIMARY KEY,"
            + "title TEXT,"
            + "image TEXT NULL,"
            + "type SMALLINT,"
            + "ga_prefix SMALLINT,"
            + "UNIQUE (id))";

    public SQLiteHelper() {
        super(App.getContext(), DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DAILYGSON_TABLE);
        db.execSQL(CREATE_DAILYRESULT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

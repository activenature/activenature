package com.active.chdating.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.active.chdating.net.ZhihuDailyPresenter;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("create table if not exists Zhihu("
                + "id integer primary key autoincrement,"
                +"zhihu_id integer not null,"
                +"zhihu_story text,"
                +"zhihu_time real,"
                +"zhihu_content text)");
        db.execSQL("alter table Zhihu add column bookmark integer default 0");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

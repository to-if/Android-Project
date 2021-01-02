package com.example.testfinddatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_name="test.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    //创建数据库
    public DBHelper(@Nullable Context context) {
        super(context, DB_name,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //数据库插入
    public void insert(ContentValues value) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert("history",null,value);
        db.close();
    }

    //删除
    public void delete(long id) {
        if (db==null) {
            db=getWritableDatabase();
            db.delete("question","_id=?",new String[] {String.valueOf(id)});
        }
    }

    //查询
    public Cursor query(String name) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.query(name,null,null,null,null,null,null);
        return cur;
    }



    public void update(ContentValues value,long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.update("question",value,"_id=?", new String[] {String.valueOf(id)});
    }


    public void close() {
        if (db!=null) {
            db.close();
        }
    }

}

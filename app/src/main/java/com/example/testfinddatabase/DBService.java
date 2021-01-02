package com.example.testfinddatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBService {
    private SQLiteDatabase db ;
    public DBService() {
        db = SQLiteDatabase.openDatabase("/data/data/com.example.testfinddatabase/databases/test.db" +"",null,SQLiteDatabase.OPEN_READWRITE);

    }
    //获取数据库中的问题
    public List<Question> getQuestion() {
        List<Question> list = new ArrayList<Question>();
        Cursor cursor = db.rawQuery("select * from question ORDER BY Random() limit 10",null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            //将 cursor 中的每一条记录生成一个 question 对象，并将该 question 对象添加到list中
            for (int i=0; i<cursor.getCount();i++) {
                cursor.moveToPosition(i);
                Question question = new Question();
                question._id = cursor.getInt(cursor.getColumnIndex("_id"));
                question.question = cursor.getString(cursor.getColumnIndex("question"));
                question.answerA = cursor.getString(cursor.getColumnIndex("answerA"));
                question.answerB = cursor.getString(cursor.getColumnIndex("answerB"));
                question.answerC = cursor.getString(cursor.getColumnIndex("answerC"));
                question.answerD = cursor.getString(cursor.getColumnIndex("answerD"));
                question.answer = cursor.getInt(cursor.getColumnIndex("answer"));

                question.selectedAnswer=-1;
                list.add(question);
            }
        }
        return list;
    }

    public void DBserviceClose() {
        db.close();

    }
}

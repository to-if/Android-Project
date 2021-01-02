package com.example.testfinddatabase;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class History extends AppCompatActivity {

    DBHelper helper;
    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        this.setTitle("历史成绩:");
        listView = findViewById(R.id.listView_history);

        helper = new DBHelper(History.this);
        Cursor cursor = helper.query("history");

        Log.wtf("哈哈哈",cursor.getCount()+"");

        //from 对应表结构
        String[] from = {"_id","chengji","time"};
        int[] to = {R.id.textView5,R.id.textView6,R.id.textView7};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.item_history,cursor,from,to,1);
        listView.setAdapter(adapter);
        helper.close();

        //返回按键
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_setting:
                Toast.makeText(this,"设置",Toast.LENGTH_LONG).show();
                break;
            //退出程序
            case R.id.item_exit:
                ActivityManager.getInstance().exit();
                break;
            case R.id.item_lognOut:
                Toast.makeText(this,"退出登陆",Toast.LENGTH_LONG).show();
                break;
            //关于作者
            case R.id.item_author:
                Intent intent = new Intent(History.this, com.example.testfinddatabase.AuthorActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                Toast.makeText(this,"错误",Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    //Menu end
}

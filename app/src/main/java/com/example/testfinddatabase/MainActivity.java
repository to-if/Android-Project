package com.example.testfinddatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_test,button;
    private ListView listView;
    DBHelper helper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //将 activity 添加到 list中，以便结束活动
        ActivityManager.getInstance().addActivity(this);

        DatabaseUtil.packDataBase(this);
        listView=findViewById(R.id.listView);
        btn_test=findViewById(R.id.btn_test);
        button = findViewById(R.id.button);

        btn_test.setOnClickListener(this);
        button.setOnClickListener(this);


        //在 listView
        helper = new DBHelper(this);
        Cursor cursor = helper.query("question");
        //from 对应表结构
        String[] from = {"_id","question"};
        int[] to = {R.id.textView1,R.id.textView2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.item_listview,cursor,from,to,1);
        listView.setAdapter(adapter);
        helper.close();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                Intent intent = new Intent(this,exam.class);
                startActivity(intent);
                break;
            case R.id.button:
                Intent intent1 = new Intent(this,History.class);
                startActivity(intent1);
                break;
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
                Intent intent = new Intent(MainActivity.this,AuthorActivity.class);
                startActivity(intent);
                break;
            default:

                break;

        }
        return super.onOptionsItemSelected(item);
    }
    //Menu end
}
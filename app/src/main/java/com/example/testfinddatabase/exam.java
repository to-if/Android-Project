package com.example.testfinddatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class exam extends AppCompatActivity {
    private int count; //list 大小
    private Integer current; //list 下标
    int right = 0; //回答正确的个数

    private Counter counter = new Counter (10 * 1000, 1 * 1000);
    private List<Question> list;

    RadioGroup radioGroup;
    TextView textView, txv; // textView 显示
    RadioButton[] radioButtons = new RadioButton[4];

    int number; //第几题

//=========================上方全局变量===================================

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam);


        DBService dbService = new DBService();
        this.list = dbService.getQuestion();

//        Set<Integer> set = random();
//        Iterator<Integer> iterator = set.iterator();
//        Integer next = iterator.next();

//=======================================================================
        //count list大小
        count = list.size();
        //count 下标
        current = 0;

        txv = findViewById(R.id.txv);
        textView = findViewById(R.id.question);
        radioButtons[0] = findViewById(R.id.answerA);
        radioButtons[1] = findViewById(R.id.answerB);
        radioButtons[2] = findViewById(R.id.answerC);
        radioButtons[3] = findViewById(R.id.answerD);
        radioGroup = findViewById(R.id.radioGroup);

        //初次显示第一题
        Question q = list.get(current);
        refreshQuestion(q);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                for (int i = 0; i < 4; i++) {
                    if (radioButtons[i].isChecked() == true) {
                        if (list.get(current).answer == i) {
                            right++; //正确答案 +1
                        }

                        current++;
                        if (current < count) {
                            Question q = list.get(current);
                            refreshQuestion(q);
                        } else {
                            counter.cancel();
                            right *= 10;

                            ContentValues contentValues = new ContentValues();
                            contentValues.put("chengji",right);
                            contentValues.put("time",new SimpleDateFormat("HH:mm:ss").format(new Date()));
                            DBHelper dbHelper = new DBHelper(exam.this);
                            dbHelper.insert(contentValues);
                            dbHelper.close();
                            radioGroup.setVisibility(View.INVISIBLE);
                            txv.setVisibility(View.INVISIBLE);
                            textView.setText("你的成绩为："+right);
                        }
                        break;

                    }
                }


            } //onCheckedChanged end
        }); // radioGroup end


    }//onCreat end


    class Counter extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            txv.setText(millisUntilFinished/1000+"s");

        }

        @Override
        public void onFinish() {

            current++;
            if (current<count) {
                Question q = list.get(current);
                refreshQuestion(q);
            }else {
                right *= 10;
                txv.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.INVISIBLE);
                ContentValues contentValues = new ContentValues();
                contentValues.put("chengji",right);
                contentValues.put("time",new SimpleDateFormat("HH:mm:ss").format(new Date()));
                DBHelper dbHelper = new DBHelper(exam.this);
                dbHelper.insert(contentValues);
                dbHelper.close();



                textView.setText("你的成绩为："+right);
            }
        }


    }//Counter end

    //更新题目
    private void refreshQuestion(Question q) {
        counter.cancel();
        counter.start();

        number = current + 1;

        textView.setText("第" + number + "题" + q.question);
        radioButtons[0].setText(q.answerA);
        radioButtons[1].setText(q.answerB);
        radioButtons[2].setText(q.answerC);
        radioButtons[3].setText(q.answerD);

        radioGroup.clearCheck();


    }


    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exam,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainAcitivity:
                Intent intent = new Intent(exam.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //Menu end

    public Set<Integer> random(){
        Set<Integer> integers = new HashSet<>();

        while(integers.size() < 10){
            integers.add((int)(Math.random())*25);
        }
        return integers;
    }

}// exam end

package com.chenyang.calendarview;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MyCalendarView.MyCalendarViewListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyCalendarView viewById = (MyCalendarView) findViewById(R.id.mycalendarview);
        viewById.myCalendarViewListener=this;
    }

    @Override
    public void onItemLongPress(Date day) {
        DateFormat instance = SimpleDateFormat.getDateInstance();
        Toast.makeText(this, instance.format(day), Toast.LENGTH_SHORT).show();
    }
}

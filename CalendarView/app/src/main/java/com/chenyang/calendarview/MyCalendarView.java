package com.chenyang.calendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * @创建者     Administrator
 * @创建时间   2017/7/28
 * @描述	      ${TODO}$
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}$
 */
public class MyCalendarView extends LinearLayout {

    private ImageView iv_next;
    private ImageView iv_previous;
    private TextView tv_current_data;
    private GridView gridView;
    //获取日历对象
    private Calendar calendar = Calendar.getInstance();
    private String displayFormat;
    public MyCalendarViewListener myCalendarViewListener;

    public MyCalendarView(Context context) {
        super(context);
    }

    public MyCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public MyCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    public void initControl(Context context, AttributeSet attrs) {

        bindControl(context);
        bindControlEvent();


        //读取自定义属性
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyCalendarView);
        try {
            String format = typedArray.getString(R.styleable.MyCalendarView_dateFormat);
            displayFormat = format;
            if (displayFormat == null) {
                displayFormat = "MMM yyyy";
            }
        } finally {
            //回收
            typedArray.recycle();
        }

        renderCalendar();
    }

    /**
     * 渲染日历
     */
    private void renderCalendar() {
        //使用SimpleDateFormat转换成指定的类型
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(displayFormat);
        tv_current_data.setText(simpleDateFormat.format(calendar.getTime()));
        //克隆一个日历
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) this.calendar.clone();
        //获取当月中上月还有多少天
        //将给定的日历字段设置为给定值。
        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
        //返回上个月还有几天
        int prevDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -prevDays);

        int maxCellCount = 6 * 7;
        while (cells.size() < maxCellCount) {
            cells.add(calendar.getTime());
//            根据日历的规则，为给定的日历字段添加或减去指定的时间量。
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        gridView.setAdapter(new CalendarAdapter(getContext(), cells));
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(myCalendarViewListener==null){
                    return false;
                }else{
                    myCalendarViewListener.onItemLongPress((Date) adapterView.getItemAtPosition(i));
                    return true;
                }

            }
        });
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {
        LayoutInflater inflater;

        public CalendarAdapter(Context context, List<Date> days) {
            super(context, R.layout.calendar_textview, days);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //获取与数据集中指定位置相关联的数据项
            Date date = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.calendar_textview, parent, false);
            }
            //此日期所表示的月份中的某一天。
            int day = date.getDate();
            ((TextView) convertView).setText(String.valueOf(day));


            Date now = new Date();
            /*Calendar calendar1 = (Calendar) calendar.clone();
            calendar1.set(Calendar.DAY_OF_MONTH, 1);
            //给定此 Calendar 的时间值，返回指定日历字段可能拥有的最大值。
            //指示一个月中的某天
            //获取当月有多少天
            int actualMaximum = calendar1.getActualMaximum(Calendar.DATE);*/
            boolean isSameMonth = false;//是否是相同的月份
            if (date.getMonth() == now.getMonth()) {
                isSameMonth = true;
            }
            if (isSameMonth) {
                //当月有效日期
                ((TextView) convertView).setTextColor(Color.parseColor("#000000"));
            } else {
                //当月无效日期
                ((TextView) convertView).setTextColor(Color.parseColor("#666666"));
            }


            //月中的天相同,月份相同,年份相同
            if (now.getDate() == date.getDate() &&
                    now.getMonth() == date.getMonth() &&
                    now.getYear() == date.getYear()) {
                //当天
                ((Calendar_textview) convertView).isToday = true;
                ((Calendar_textview) convertView).setTextColor(getResources().getColor(R.color.colorAccent));
            }

            return convertView;
        }

    }


    /**
     * 给控件绑定事件
     */
    private void bindControlEvent() {
        iv_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, 1);
                renderCalendar();
            }
        });

        iv_previous.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH, -1);
                renderCalendar();
            }
        });

    }

    /**
     * 绑定控件
     *
     * @param context
     */
    private void bindControl(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.calendar_view, this);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_previous = (ImageView) findViewById(R.id.iv_previous);
        tv_current_data = (TextView) findViewById(R.id.tv_current_data);
        gridView = (GridView) findViewById(R.id.gridview);
    }

    public interface MyCalendarViewListener {
        void onItemLongPress(Date day);
    }
}

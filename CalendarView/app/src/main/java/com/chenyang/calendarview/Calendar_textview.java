package com.chenyang.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;


/*
 * @创建者     Administrator
 * @创建时间   2017/7/29
 * @描述	      ${TODO}$
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}$
 */
public class Calendar_textview extends TextView {
    //是否是当天
    public boolean isToday = false;
    private Paint paint=new Paint();

    public Calendar_textview(Context context) {
        super(context);
        initControl();
    }

    public Calendar_textview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl();
    }

    public Calendar_textview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }

    private void initControl() {
        paint.setAntiAlias(true);//设置抗锯齿
        /**
         * Android在用画笔的时候有三种Style，分别是
         Paint.Style.STROKE 只绘制图形轮廓（描边）
         Paint.Style.FILL 只绘制图形内容
         Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容
         */
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ff0000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isToday){
            //平移，将画布的坐标原点向左右方向移动x，向上下方向移动y.canvas的默认位置是在（0,0）.
            //设置圆心
            canvas.translate(getWidth()/2,getHeight()/2);
            canvas.drawCircle(0,0,getWidth()/2,paint);
        }

    }
}

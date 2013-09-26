package com.clownxiaoqiang.TimeNote;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-19
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 */
public class DrawCircle extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private float Center_x, Center_y;
    private float Touch_x, Touch_y;
    private float Move_x, Move_y;
    private float angle = 0;
    private float baseRadius;//低园半径
    private float middleRadius;//中园半径
    private float innerBlankRadius;//中间空白园半径
    private Paint w_paint, b_paint, r_paint, t_paint, p_paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder = null;
    boolean mIsRunning = false;
    private Context context;
    private float change_x, change_y;
    private static final double Average_Angle = 180 / Math.PI;
    private static final double Average_Time = 1;
    private String Time;
    private int MinuteTime;


    public DrawCircle(Context context) {
        super(context);
    }

    public DrawCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawCircle(Context context, float center_x, float center_y) {
        super(context);
        this.context = context;
        //获取焦点
        this.setFocusable(true);
        //可触摸事件
        this.setFocusableInTouchMode(true);
        this.requestFocus();

        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);

        canvas = new Canvas();

        w_paint = new Paint();
        w_paint.setAntiAlias(true);

        b_paint = new Paint();
        b_paint.setAntiAlias(true);

        r_paint = new Paint();
        r_paint.setAntiAlias(true);

        t_paint = new Paint();
        t_paint.setAntiAlias(true);

        p_paint = new Paint();
        p_paint.setAntiAlias(true);

        Center_x = center_x / 2;

        baseRadius = Center_x * 4 / 5;
        middleRadius = Center_x * 27 / 40;
        innerBlankRadius = Center_x * 11 / 20;

        Center_y = baseRadius;


        //Log.d("TAG","初始化完成");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //   Log.d("TAG","进入onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //   Log.d("TAG","action down");
                Touch_x = event.getX();
                Touch_y = event.getY();
                //    Log.d("TAG", "Touch_x--->" + Touch_x + "Touch_y--->" + Touch_y);
                change_x = Touch_x;
                change_y = Touch_y;
                Log.d("TAG", angle + "");
                Log.d("TAG_3", "change_x--->" + change_x + "change_y" + change_y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("TAG", "action move");
                Move_x = event.getX();
                Move_y = event.getY();
                try {
                    angle = (float) (angle + GetAngle(Center_x, Center_y, change_x, change_y, Move_x, Move_y));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (angle >= 359) {
                    angle = 360;
                    break;
                }
                if (angle <= 0.5) {
                    angle = 0;
                    break;
                }
                Log.d("TAG_angle", "" + angle);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("TAG", "action up");
                break;
        }
        return true;    //To change body of overridden methods use File | Settings | File Templates.
    }

    protected void Draw() {
        b_paint.setARGB(255, 7, 220, 208);
        RectF rectf = new RectF(Center_x - baseRadius, (float) 0, Center_x + baseRadius, baseRadius * 2);
        // Log.d("Test","upangle-->"+upangle+"angle--->"+angle);
        canvas.drawArc(rectf, 0, angle, true, b_paint);
    }

    protected void DrawinnerCircle() {
        r_paint.setARGB(255, 210, 245, 47);
        RectF rectF = new RectF(Center_x - middleRadius, baseRadius - middleRadius,
                Center_x + middleRadius, baseRadius + middleRadius);
        canvas.drawArc(rectF, 0, angle, true, r_paint);
    }

    protected void DrawWhiteCircle() {
        w_paint.setARGB(255, 237, 237, 237);
        canvas.drawCircle(Center_x, baseRadius, innerBlankRadius, w_paint);
    }

    protected void DrawPreyCircle() {
        p_paint.setARGB(255, 201, 201, 201);
        canvas.drawCircle(Center_x, baseRadius, baseRadius, p_paint);
    }

    protected void DrawText() {
        t_paint.setColor(Color.BLACK);
        t_paint.setTextSize(Center_x / 4);
        t_paint.setTextAlign(Paint.Align.CENTER);
        Time = ChangeTime(CountTime(angle));
        canvas.drawText(Time, Center_x, baseRadius, t_paint);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        /**开始游戏主循环线程**/
        mIsRunning = true;
        Log.d("TAG", "surfaceCreated");
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsRunning = false;
        Log.d("TAG", "destroy");
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private int CountTime(float angle) {
        int time = (int) (angle * Average_Time);
        MinuteTime = time;
        return time;
    }

    private String ChangeTime(int minute) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(0, 0, 0, 0, minute);
        String s = simpleDateFormat.format(date);
        return s;
    }

    private int GetDirection(float x1, float y1, float x2, float y2, float x3, float y3) {
        Log.d("x2", "x2--->" + x2);
        Log.d("x3", "x3--->" + x3);
        Log.d("y2", "y2--->" + y2);

        if ((y3 > baseRadius / 2) && (y3 < baseRadius / 2 * 3)) {
            if (y3 > y2) {
                if (x3 < x1)
                    return 0;
                if (x3 > x1)
                    return 1;
            }
            if (y3 < y2) {
                if (x3 < x1)
                    return 1;
                if (x3 > x1)
                    return 0;
            }
        } else {
            if (x3 > x2) {
                if (y3 < y1)
                    return 1;
                if (y3 > y1)
                    return 0;
            }
            if (x3 < x2) {
                if (y3 < y1)
                    return 0;
                if (y3 > y1)
                    return 1;
            }
        }


        return 2;
    }

    private Double GetAngle(float x1, float y1, float x2, float y2, float x3, float y3) {
        //这个求的是A角，也就是x1点要放，中心点
        float A_angle = 0;

        //对于A角的计算
        if (Math.abs(x2 - x3) > 1 && (Math.abs((y1 - y2) / (x1 - x2) - (y1 - y3) / (x1 - x3)) > 0.1)) {
            A_angle = (float) Math.acos(((x3 - x1) * (x2 - x1) + (y3 - y1) * (y2 - y1)) /
                    ((Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))) *
                            (Math.sqrt((Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2))))));
            Log.d("Angle", "Angle--->" + A_angle * Average_Angle);
            Log.d("TAG_1", "change_x--->" + change_x + "change_y--->" + change_y + "x3--->" + x3 + "y3--->" + y3);

            switch (GetDirection(x1, y1, change_x, change_y, x3, y3)) {
                case 0:
                    A_angle = -A_angle;
                    break;
                case 2:
                    A_angle = 0;
                    break;

            }
            change_x = x3;
            change_y = y3;
        }
        return A_angle * Average_Angle / 2;
    }

    @Override
    public void run() {
        Log.d("TAG", "in Thread");
        while (mIsRunning) {

            /** 取得更新游戏之前的时间 **/
            long startTime = System.currentTimeMillis();

            /** 在这里加上线程安全锁 **/
            synchronized (surfaceHolder) {
                /** 拿到当前画布 然后锁定 **/
                canvas = surfaceHolder.lockCanvas();
                canvas.drawARGB(255, 237, 237, 237);
                DrawPreyCircle();
                Draw();
                DrawinnerCircle();
                DrawWhiteCircle();
                DrawText();
                /** 绘制结束后解锁显示在屏幕上 **/
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            /** 取得更新游戏结束的时间 **/
            long endTime = System.currentTimeMillis();

            /** 计算出游戏一次更新的毫秒数 **/
            int diffTime = (int) (endTime - startTime);

            /** 确保每次更新时间为50帧 **/
            while (diffTime <= 50) {
                diffTime = (int) (System.currentTimeMillis() - startTime);
                /** 线程等待 **/
                Thread.yield();
            }
        }
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTime() {

        return Time;
    }

    public int getMinuteTime() {
        return MinuteTime;
    }

    public void setMinuteTime(int minuteTime) {
        MinuteTime = minuteTime;
    }
}

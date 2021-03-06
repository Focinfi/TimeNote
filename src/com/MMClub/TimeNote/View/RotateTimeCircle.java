package com.MMClub.TimeNote.View;

import android.content.Context;
import android.graphics.*;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.MMClub.TimeNote.Activity.RotateTimeActivity;
import com.MMClub.TimeNote.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: Dai Zhi Qiang
 * Date: 13-9-19
 * Time: 上午10:57
 * 绘制旋转时间类
 */
public class RotateTimeCircle extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private float Center_x, Center_y;
    private float Touch_x, Touch_y;
    private float Move_x, Move_y;
    private float angle = 0;
    private float baseRadius;//低园半径
    private float middleRadius;//中园半径

    private float innerBlankRadius;//中间空白园半径
    private CountTime countTime;
    private Paint w_paint, b_paint, r_paint, t_paint, p_paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder = null;
    boolean mIsRunning = false;
    private Context context;
    private float change_x, change_y;
    private static final double Average_Angle = 180 / Math.PI;
    private static final double Average_Time = 1;
    private static final double Average_CountTime = 360.0 / (6 * 60 * 60);
    private String Time, SecondString;
    private long Second;
    private int MinuteTime;
    final static int Msg = 1;


    public RotateTimeCircle(Context context) {
        super(context);
    }

    public RotateTimeCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateTimeCircle(Context context, float center_x, float center_y) {
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

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                Touch_x = event.getX();
                Touch_y = event.getY();

                change_x = Touch_x;
                change_y = Touch_y;
                break;
            case MotionEvent.ACTION_MOVE:
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
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    protected void DrawOutCircle() {
        b_paint.setARGB(255, 7, 220, 208);
        RectF rectf = new RectF(Center_x - baseRadius, (float) 0, Center_x + baseRadius, baseRadius * 2);
        canvas.drawArc(rectf, 0, angle, true, b_paint);
    }

    protected void DrawInnerCircle() {
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
        p_paint.setARGB(255, 216, 216, 216);
        canvas.drawCircle(Center_x, baseRadius, baseRadius, p_paint);
    }

    protected void DrawText() {
        t_paint.setColor(Color.BLACK);
        t_paint.setTextSize(Center_x / 4);
        t_paint.setTextAlign(Paint.Align.CENTER);
        Time = ChangeTime(CountTime(angle));
        canvas.drawText(Time, Center_x, baseRadius, t_paint);
    }

    protected void DrawTextSecond() {
        t_paint.setColor(Color.BLACK);
        t_paint.setTextSize(Center_x / 8);
        t_paint.setTextAlign(Paint.Align.CENTER);
        SecondString = ChangeSecond((int) Second % 60);
        canvas.drawText(SecondString, Center_x, baseRadius + Center_x / 4, t_paint);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        /**开始绘图主循环线程**/
        mIsRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsRunning = false;
    }

    private int CountTime(float angle) {
        int time = (int) (angle * Average_Time);
        MinuteTime = time;
        return time;
    }

    private String ChangeSecond(int Second) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss");
        Date date = new Date(0, 0, 0, 0, 0, Second);
        String s = simpleDateFormat.format(date);
        return s;
    }

    private String ChangeTime(int minute) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(0, 0, 0, 0, minute);
        String s = simpleDateFormat.format(date);
        return s;
    }

    //判断手指滑动的方向，顺时针返回 1，逆时针返回 0，无法判断返回 2
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

            //取得更新之前的时间
            long startTime = System.currentTimeMillis();

            try {
                //在这里加上线程安全锁
                synchronized (surfaceHolder) {
                    //拿到当前画布 然后锁定
                    canvas = surfaceHolder.lockCanvas();
                    canvas.drawARGB(255, 237, 237, 237);
                    DrawPreyCircle();
                    DrawOutCircle();
                    DrawInnerCircle();
                    DrawWhiteCircle();
                    DrawText();
                    DrawTextSecond();
                    //绘制结束后解锁显示在屏幕上
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

                //取得更新结束的时间
                long endTime = System.currentTimeMillis();

                //计算出更新一次的毫秒数
                int diffTime = (int) (endTime - startTime);

                //确保每次更新时间为50帧
                while (diffTime <= 50) {
                    diffTime = (int) (System.currentTimeMillis() - startTime);
                    //线程等待
                    Thread.yield();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void TimeCount() {
        countTime = new CountTime((MinuteTime * 60 + Integer.parseInt(SecondString)) * 1000, 1000);
        countTime.start();
    }

    public void TimeCountCancel() {
        countTime.cancel();
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Msg:
                    Second = msg.getData().getLong("Changetime") / 1000;
                    angle = (float) ((msg.getData().getLong("Changetime") / 1000) * Average_CountTime);
                    break;
                default:
                    break;
            }
        }

    };

    public String getTime() {

        return Time;
    }

    public int getMinuteTime() {
        return MinuteTime;
    }

    public long getSecond() {
        return Second;
    }


    class CountTime extends CountDownTimer {

        public CountTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            Log.d("counttime", "ChangeTime----->" + l);
            SendMessage(l);

        }

        @Override
        public void onFinish() {
            SendMessage(0);
            MediaPlayer mediaPlayer = new MediaPlayer().create(context, R.raw.alarm);
            mediaPlayer.start();
            SendMessage();

        }

        private void SendMessage(long l) {
            Message message = new Message();
            message.what = RotateTimeCircle.Msg;
            Bundle bundle = new Bundle();
            bundle.putLong("Changetime", l);
            message.setData(bundle);
            mHandler.sendMessage(message);
        }

        private void SendMessage() {
            Message message = new Message();
            message.what = RotateTimeActivity.Count;
            Bundle bundle = new Bundle();
            bundle.putBoolean("changeIscounting", false);
            message.setData(bundle);
            RotateTimeActivity.cHandler.sendMessage(message);
        }
    }
}


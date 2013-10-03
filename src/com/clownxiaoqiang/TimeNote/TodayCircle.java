package com.clownxiaoqiang.TimeNote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created with IntelliJ IDEA.
 * User: focinfi
 * Date: 13-9-22
 * Time: 下午7:48
 * To change this template use File | Settings | File Templates.
 */
public class TodayCircle extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private int workTime;
    private int studyTime;
    private int sleepTime;
    private int playTime;
    private int totalTime;
    private int startAngle;
    private int endAngle;
    private int angle;
    private float Center_x, Center_y;
    private float radius;
    private Canvas canvas;
    private Paint workPaint;
    private Paint studyPaint;
    private Paint sleepPaint;
    private Paint playPaint;
    private Paint greyPaint;
    private Context context;
    private SurfaceHolder surfaceHolder = null;
    RectF rectf;
    private boolean mIsRunning = false;

    public TodayCircle(Context context) {
        super(context);
    }

    public TodayCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TodayCircle(Context context, float center_x, float center_y, int work_time, int study_time, int play_time, int sleep_time) {

        super(context);
        this.context = context;
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);

        canvas = new Canvas();

        workPaint = new Paint();
        workPaint.setAntiAlias(true);

        studyPaint = new Paint();
        studyPaint.setAntiAlias(true);

        playPaint = new Paint();
        playPaint.setAntiAlias(true);


        sleepPaint = new Paint();
        sleepPaint.setAntiAlias(true);

        greyPaint = new Paint();
        greyPaint.setAntiAlias(true);

        Center_x = center_x / 2;
        Center_y = (float) (center_y * 0.4);

        radius = Center_x * 4 / 5;

        this.workTime = work_time;
        this.studyTime = study_time;
        this.playTime = play_time;
        this.sleepTime = sleep_time;

        rectf = new RectF(Center_x - radius, (float) 0, Center_x + radius, radius * 2);
        startAngle = 0;
        endAngle = 0;
        angle = 1;

    }

    public void DrawWorkCircle(int startAngle, int endAngle) {
        workPaint.setARGB(255, 153, 204, 0);
        canvas.drawArc(rectf, startAngle, endAngle, true, workPaint);
        super.onDraw(canvas);
    }

    public void DrawStudyCircle(int startAngle, int endAngle) {
        studyPaint.setARGB(255, 255, 102, 102);
        canvas.drawArc(rectf, startAngle, endAngle, true, studyPaint);
        super.onDraw(canvas);
    }

    public void DrawPlayCircle(int startAngle, int endAngle) {
        playPaint.setARGB(255, 254, 209, 0);
        canvas.drawArc(rectf, startAngle, endAngle, true, playPaint);
        super.onDraw(canvas);
    }

    public void DrawSleepCircle(int startAngle, int endAngle) {
        sleepPaint.setARGB(255, 0, 102, 204);
        canvas.drawArc(rectf, startAngle, endAngle, true, sleepPaint);
        super.onDraw(canvas);
    }

    public void DrawGreyCircle() {
        greyPaint.setARGB(255, 216, 216, 216);
        canvas.drawArc(rectf, 0, 360, true, greyPaint);
        super.onDraw(canvas);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsRunning = false;
    }

    @Override
    public void run() {
        while (mIsRunning) {
            /** 取得更新游戏之前的时间 **/
            long startTime = System.currentTimeMillis();

            /** 在这里加上线程安全锁 **/
            try {
                synchronized (surfaceHolder) {
                    /** 拿到当前画布 然后锁定 **/
                    canvas = surfaceHolder.lockCanvas();
                    canvas.drawARGB(255, 237, 237, 237);

                    DrawGreyCircle();

                    if (angle <= workTime + studyTime + playTime + sleepTime) {
                        DrawSleepCircle(0, angle);
                    } else {
                        DrawSleepCircle(0, workTime + studyTime + playTime + sleepTime);
                    }

                    if (angle <= workTime + studyTime + playTime) {
                        DrawPlayCircle(0, angle);
                    } else {
                        DrawPlayCircle(0, workTime + studyTime + playTime);
                    }
                    if (angle <= workTime + studyTime) {
                        DrawStudyCircle(0, angle);
                    } else {
                        DrawStudyCircle(0, workTime + studyTime);
                    }
                    if (angle <= workTime) {
                        DrawWorkCircle(0, angle);
                    } else {
                        DrawWorkCircle(0, workTime);
                    }
                    angle = angle + 4;


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
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}

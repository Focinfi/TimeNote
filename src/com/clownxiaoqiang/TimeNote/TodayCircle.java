package com.clownxiaoqiang.TimeNote;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
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
    private float Center_x, Center_y;
    private float radius;
    private Canvas canvas;
    private Paint workPaint;
    private Paint studyPaint;
    private Paint sleepPaint;
    private Paint playPaint;
    private Context context;
    private SurfaceHolder surfaceHolder = null;
    RectF rectf;
    private boolean mIsRunning;

    public TodayCircle(Context context) {
        super(context);
    }

    public TodayCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TodayCircle(Context context, float center_x, float center_y) {
        super(context);
        this.context = context;
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);

        canvas = new Canvas();

        workPaint = new Paint();
        workPaint.setAntiAlias(true);

        studyPaint = new Paint();
        studyPaint.setAntiAlias(true);

        sleepPaint = new Paint();
        sleepPaint.setAntiAlias(true);

        playPaint = new Paint();
        playPaint.setAntiAlias(true);

        Center_x = center_x / 2;
        Center_y = (float) (center_y * 0.4);

        workTime = 6;
        studyTime = 6;
        playTime = 6;
        sleepTime = 6;

        rectf = new RectF((float) 0.2 * Center_x, (float) (Center_y - (0.5 * Center_x) - 0.3 * Center_x), (float) (Center_x * 1.8), (float) ((0.5 * Center_x) + Center_y + 0.3 * Center_x));


    }

    public void DrawWorkCircle(int startAngle, int endAngle) {
        workPaint.setARGB(255, 68, 138, 202);
        canvas.drawArc(rectf, startAngle, endAngle, true, workPaint);
        super.onDraw(canvas);
    }

    public void DrawStudyCircle(int startAngle, int endAngle) {
        studyPaint.setARGB(255, 19, 181, 177);
        canvas.drawArc(rectf, startAngle, endAngle, true, studyPaint);
        super.onDraw(canvas);
    }

    public void DrawPlayCircle(int startAngle, int endAngle) {
        playPaint.setARGB(255, 236, 105, 65);
        canvas.drawArc(rectf, startAngle, endAngle, true, playPaint);
        super.onDraw(canvas);
    }

    public void DrawSleepCircle(int startAngle, int endAngle) {
        sleepPaint.setARGB(255, 250, 205, 137);
        canvas.drawArc(rectf, startAngle, endAngle, true, sleepPaint);
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

            /** 在这里加上线程安全锁 **/
            synchronized (surfaceHolder) {
                /** 拿到当前画布 然后锁定 **/
                canvas = surfaceHolder.lockCanvas();
                startAngle = 0;
                endAngle = 0;
                totalTime = workTime + studyTime + sleepTime + playTime;
                for (int i = 0; i < totalTime; i++) {

                    if (workTime > 0) {
                        endAngle = endAngle + workTime;
                        for (int angle = 0; angle <= endAngle; angle++)

                            for (int timeCounter = 0; timeCounter < 20; timeCounter++) {
                                if (timeCounter == 19) {
                                    DrawWorkCircle(startAngle, angle);
                                }
                            }

                        startAngle = endAngle;

                    }

                    if (studyTime > 0) {
                        endAngle = endAngle + studyTime;
                        for (int angle = 0; angle <= endAngle; angle++)

                            for (int timeCounter = 0; timeCounter < 20; timeCounter++) {
                                if (timeCounter == 19) {
                                    DrawStudyCircle(startAngle, angle);
                                }
                            }

                        startAngle = endAngle;

                    }

                    if (playTime > 0) {
                        endAngle = endAngle + playTime;
                        for (int angle = 0; angle <= endAngle; angle++)

                            for (int timeCounter = 0; timeCounter < 20; timeCounter++) {
                                if (timeCounter == 19) {
                                    DrawPlayCircle(startAngle, angle);
                                }
                            }

                        startAngle = endAngle;

                    }

                    if (studyTime > 0) {
                        endAngle = endAngle + sleepTime;
                        for (int angle = 0; angle <= endAngle; angle++)

                            for (int timeCounter = 0; timeCounter < 20; timeCounter++) {
                                if (timeCounter == 19) {
                                    DrawSleepCircle(startAngle, angle);
                                }
                            }

                        startAngle = endAngle;

                    }

                    for (int timeCounter = 0; timeCounter < 20; timeCounter++) {
                        if (timeCounter == 19) {

                        }
                    }
                }

                /** 绘制结束后解锁显示在屏幕上 **/
                surfaceHolder.unlockCanvasAndPost(canvas);
            }


        }
    }
}

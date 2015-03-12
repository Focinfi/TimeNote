package com.clownxiaoqiang.TimeNote.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: Dai Zhi Qiang
 * Date: 13-10-1
 * Time: 下午1:22
 * 工具类
 */
public class TimeNoteUtil {

    private Context context;
    private Activity activity;

    public TimeNoteUtil(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    //这个为工具类下的Dialog函数
    public void DialogBuild() {
        new AlertDialog.Builder(context)
                .setTitle("提示：")
                .setMessage("是否退出？")
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                activity.finish();
                            }
                        }).show();
    }

    //这个是点击效果函数
    public void HandlerDelayed(final RelativeLayout relativeLayout) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }, 300);
    }

    //时间转换
    public String CurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date_S = new Date(System.currentTimeMillis());
        String date_x = simpleDateFormat.format(date_S);
        return date_x;
    }


}

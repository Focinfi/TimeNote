package com.clownxiaoqiang.TimeNote.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.clownxiaoqiang.TimeNote.HomePage;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-10-1
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
public class TimeNoteUtil {

    private Context context;
    private Activity activity;
    public TimeNoteUtil(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    public void DialogBuild(){
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
}

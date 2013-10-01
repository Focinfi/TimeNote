package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import com.clownxiaoqiang.TimeNote.Util.TimeNoteUtil;
import come.clownxiaoqiang.TimeNote.Sql.SQlManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-20
 * Time: ����7:50
 * To change this template use File | Settings | File Templates.
 */
public class blank extends Activity {

    private TimeNoteUtil timeNoteUtil;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

    if (keyCode == event.KEYCODE_BACK) {
        timeNoteUtil = new TimeNoteUtil(blank.this);
        timeNoteUtil.DialogBuild();
        return true;
    } else {
        return super.onKeyDown(keyCode, event);
    }
    }
}
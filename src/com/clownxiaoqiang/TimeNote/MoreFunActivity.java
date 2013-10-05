package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.clownxiaoqiang.TimeNote.Util.TimeNoteUtil;

/**
 * Created with IntelliJ IDEA.
 * Author: Wang Tao
 * Date: 13-9-20
 * Time: 7:50
 * 更多 类
 */
public class MoreFunActivity extends Activity {
    private Dialog aboutDialog;
    private TimeNoteUtil timeNoteUtil;
    private RelativeLayout aboutRelativeLayout, shareRelativeLayout, collectionRelativeLayout, uploadRelativeLayout,
            settingRelativeLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_page);

        timeNoteUtil = new TimeNoteUtil(MoreFunActivity.this);
        aboutRelativeLayout = (RelativeLayout) this.findViewById(R.id.aboutRelativeLayout);
        shareRelativeLayout = (RelativeLayout) this.findViewById(R.id.shareRelativeLayout);
        collectionRelativeLayout = (RelativeLayout) this.findViewById(R.id.collectionRelativeLayout);
        uploadRelativeLayout = (RelativeLayout) this.findViewById(R.id.uploadRelativeLayout);
        settingRelativeLayout = (RelativeLayout) this.findViewById(R.id.settingRelativeLayout);


        aboutRelativeLayout = (RelativeLayout) this.findViewById(R.id.aboutRelativeLayout);
        aboutRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aboutRelativeLayout.setBackgroundColor(Color.parseColor("#CCFF00"));

                aboutDialog = new Dialog(MoreFunActivity.this);
                aboutDialog.setTitle("关于");
                aboutDialog.setContentView(R.layout.about_page);
                aboutDialog.show();
                timeNoteUtil.HandlerDelayed(aboutRelativeLayout);
            }
        });
        shareRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareRelativeLayout.setBackgroundColor(Color.parseColor("#CCFF00"));
                Toast.makeText(MoreFunActivity.this, "正在开发。。。", Toast.LENGTH_SHORT).show();
                timeNoteUtil.HandlerDelayed(shareRelativeLayout);
            }
        });
        collectionRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionRelativeLayout.setBackgroundColor(Color.parseColor("#CCFF00"));
                Toast.makeText(MoreFunActivity.this, "正在开发。。。", Toast.LENGTH_SHORT).show();
                timeNoteUtil.HandlerDelayed(collectionRelativeLayout);
            }
        });
        uploadRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRelativeLayout.setBackgroundColor(Color.parseColor("#CCFF00"));
                Toast.makeText(MoreFunActivity.this, "正在开发。。。", Toast.LENGTH_SHORT).show();
                timeNoteUtil.HandlerDelayed(uploadRelativeLayout);
            }
        });
        settingRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingRelativeLayout.setBackgroundColor(Color.parseColor("#CCFF00"));
                Toast.makeText(MoreFunActivity.this, "正在开发。。。", Toast.LENGTH_SHORT).show();
                timeNoteUtil.HandlerDelayed(settingRelativeLayout);
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            timeNoteUtil.DialogBuild();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
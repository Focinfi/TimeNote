package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-20
 * Time: ����7:50
 * To change this template use File | Settings | File Templates.
 */
public class FindPage extends Activity {
    private Button aboutButton,shareButton,collectionButton,uploadButton,settingButton;
    private Dialog aboutDialog;
    private RelativeLayout aboutRelativeLayout,shareRelativeLayout,collectionRelativeLayout,uploadRelativeLayout,
                            settingRelativeLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_page);

        aboutRelativeLayout = (RelativeLayout) this.findViewById(R.id.aboutRelativeLayout);
        shareRelativeLayout=(RelativeLayout)this.findViewById(R.id.shareRelativeLayout);
        collectionRelativeLayout=(RelativeLayout)this.findViewById(R.id.collectionRelativeLayout);
        uploadRelativeLayout=(RelativeLayout)this.findViewById(R.id.uploadRelativeLayout);
        settingRelativeLayout=(RelativeLayout)this.findViewById(R.id.settingRelativeLayout);


        aboutRelativeLayout = (RelativeLayout) this.findViewById(R.id.aboutRelativeLayout);
        aboutRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aboutRelativeLayout.setBackgroundColor(Color.parseColor("#CCFF00"));

                aboutDialog = new Dialog(FindPage.this);
                aboutDialog.setTitle("关于");
                aboutDialog.setContentView(R.layout.about_page);
                aboutDialog.show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        aboutRelativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }, 300);
            }
        });
        shareRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareRelativeLayout.setBackgroundColor(Color.parseColor("#CCFF00"));
                Toast.makeText(FindPage.this, "正在开发。。。",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        shareRelativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }, 300);
            }
        });
        collectionRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionRelativeLayout.setBackgroundColor(Color.parseColor("#CCFF00"));
                Toast.makeText(FindPage.this, "正在开发。。。",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        collectionRelativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }, 300);
            }
        });
        uploadRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRelativeLayout.setBackgroundColor(Color.parseColor("#CCFF00"));
                Toast.makeText(FindPage.this, "正在开发。。。",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        uploadRelativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }, 300);
            }
        });
        settingRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingRelativeLayout.setBackgroundColor(Color.parseColor("#CCFF00"));
                Toast.makeText(FindPage.this, "正在开发。。。",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        settingRelativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }, 300);
            }
        });

    }
}
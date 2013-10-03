package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
    private RelativeLayout aboutRelativeLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_page);
        aboutRelativeLayout = (RelativeLayout) this.findViewById(R.id.aboutRelativeLayout);
        shareButton = (Button)this.findViewById(R.id.shareButton);
        collectionButton = (Button)this.findViewById(R.id.collectionButton);
        uploadButton = (Button)this.findViewById(R.id.uploadButton);
        settingButton = (Button)this.findViewById(R.id.settingButton);


        aboutButton = (Button) this.findViewById(R.id.findPageAboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
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
                }, 500);
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindPage.this, "正在开发。。。",Toast.LENGTH_SHORT).show();
            }
        });
        collectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindPage.this, "正在开发。。。",Toast.LENGTH_SHORT).show();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindPage.this, "正在开发。。。",Toast.LENGTH_SHORT).show();
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindPage.this, "正在开发。。。",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TabHost;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-19
 * Time: 下午12:12
 * To change this template use File | Settings | File Templates.
 */
public class MyActivity extends TabActivity implements CompoundButton.OnCheckedChangeListener {
    private TabHost tabHost ;
    private Intent homepage_intent;
    private Intent today_intent;
    private Intent diary_intent;
    private Intent blank_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        this.homepage_intent = new Intent(MyActivity.this,HomePage.class);
        this.today_intent =new Intent(MyActivity.this,Today.class);
        this.diary_intent =new Intent(MyActivity.this,Diary.class);
        this.blank_intent =new Intent(MyActivity.this,blank.class);

        ((RadioButton)findViewById(R.id.radio_button0)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_button1)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_button2)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.radio_button3)).setOnCheckedChangeListener(this);

        this.tabHost = getTabHost();

        TabHost localtabhost = this.tabHost;

        localtabhost.addTab(buildTabSpec("homepage", R.string.homepage,
                R.drawable.namcard_picker_bkg_hover, this.homepage_intent));

        localtabhost.addTab(buildTabSpec("see", R.string.see,
                R.drawable.namcard_picker_bkg_hover, this.today_intent));

        localtabhost.addTab(buildTabSpec("write",
                R.string.see_diary, R.drawable.namcard_picker_bkg_hover,
                this.diary_intent));

        localtabhost.addTab(buildTabSpec("about",
                R.string.about, R.drawable.namcard_picker_bkg_hover, blank_intent));
    }

    private TabHost.TabSpec buildTabSpec(String tag, int name, int icon,
                                         Intent intent) {
        return this.tabHost.newTabSpec(tag).setIndicator(getString(name),
                getResources().getDrawable(icon)).setContent(intent);
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        //To change body of implemented methods use File | Settings | File Templates.
        if(b){
            if(compoundButton.getId()==R.id.radio_button0){
                this.tabHost.setCurrentTabByTag("homepage");
            }else if(compoundButton.getId()==R.id.radio_button1){
                this.tabHost.setCurrentTabByTag("see");
            }else if(compoundButton.getId()==R.id.radio_button2){
                this.tabHost.setCurrentTabByTag("write");
            }else if(compoundButton.getId()==R.id.radio_button3){
                this.tabHost.setCurrentTabByTag("about");
            }
        }
    }
}

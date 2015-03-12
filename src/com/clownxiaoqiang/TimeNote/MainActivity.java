package com.clownxiaoqiang.TimeNote;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;

/**
 * Created with IntelliJ IDEA.
 * Author: Dai Zhi Qiang
 * Date: 13-9-19
 * Time: 下午12:12
 * TableHost类
 */
public class MainActivity extends TabActivity implements CompoundButton.OnCheckedChangeListener {
    private TabHost tabHost;
    private Intent homepage_intent;
    private Intent today_intent;
    private Intent diary_intent;
    private Intent blank_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        this.homepage_intent = new Intent(MainActivity.this, RotateTimeActivity.class);
        this.today_intent = new Intent(MainActivity.this, TodayTimeActivity.class);
        this.diary_intent = new Intent(MainActivity.this, TimeLineActivity.class);
        this.blank_intent = new Intent(MainActivity.this, MoreFunActivity.class);

        ((RadioButton) findViewById(R.id.radio_button0)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button1)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button2)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button3)).setOnCheckedChangeListener(this);

        this.tabHost = getTabHost();

        TabHost localTabHost = this.tabHost;

        localTabHost.addTab(buildTabSpec("homepage", R.string.homepage,
                R.drawable.icon_home_page, this.homepage_intent));

        localTabHost.addTab(buildTabSpec("see", R.string.see,
                R.drawable.icon_today_page, this.today_intent));

        localTabHost.addTab(buildTabSpec("write",
                R.string.see_diary, R.drawable.icon_diary_page,
                this.diary_intent));

        localTabHost.addTab(buildTabSpec("more_page",
                R.string.about, R.drawable.icon_diary_page, blank_intent));
    }

    private TabHost.TabSpec buildTabSpec(String tag, int name, int icon,
                                         Intent intent) {
        return this.tabHost.newTabSpec(tag).setIndicator(getString(name),
                getResources().getDrawable(icon)).setContent(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            if (compoundButton.getId() == R.id.radio_button0) {
                this.tabHost.setCurrentTabByTag("homepage");
            } else if (compoundButton.getId() == R.id.radio_button1) {
                this.tabHost.setCurrentTabByTag("see");
            } else if (compoundButton.getId() == R.id.radio_button2) {
                this.tabHost.setCurrentTabByTag("write");
            } else if (compoundButton.getId() == R.id.radio_button3) {
                this.tabHost.setCurrentTabByTag("more_page");
            }
        }
    }
}

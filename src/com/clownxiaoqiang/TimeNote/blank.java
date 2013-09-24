package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import come.clownxiaoqiang.TimeNote.Sql.SQLite;
import come.clownxiaoqiang.TimeNote.Sql.SQlManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-20
 * Time: ����7:50
 * To change this template use File | Settings | File Templates.
 */
public class blank extends Activity {

    private Button querybutton;
    private ArrayList<Map<String,Object>> arrayList;
    private SQlManager sQlManager;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        querybutton = (Button)findViewById(R.id.querybutton);
        querybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sQlManager = new SQlManager(blank.this);
                arrayList = sQlManager.query("");

            }
        });
    }
}
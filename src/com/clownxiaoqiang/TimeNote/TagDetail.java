package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import come.clownxiaoqiang.TimeNote.Sql.SQlManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-9-28
 * Time: 下午7:08
 * To change this template use File | Settings | File Templates.
 */
public class TagDetail extends Activity {

    private Button backbutton,editbutton;
    private ArrayList<Map<String,Object>> arrayList,arrayList_note;
    private SQlManager sQlManager;
    private TextView dairytextview;
    private ListView tagdetailListView;
    private TagAdapter tagAdapter;
    private String date_to_search,notetext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tagdetail);

        backbutton = (Button)findViewById(R.id.back);
        editbutton = (Button)findViewById(R.id.edit);
        tagdetailListView = (ListView)findViewById(R.id.tagdetailListView);
        dairytextview = (TextView)findViewById(R.id.diarytextview);

        Intent intent = getIntent();
        date_to_search = intent.getStringExtra("datetext");

        arrayList = new ArrayList<Map<String, Object>>();
        arrayList_note = new ArrayList<Map<String, Object>>();

        sQlManager = new SQlManager(TagDetail.this);
        arrayList = sQlManager.queryTag(date_to_search);
        Log.d("arraylist",arrayList.toString());
        arrayList_note = sQlManager.query(date_to_search);
        notetext = "笔记："+arrayList_note.get(0).get((Object)"note").toString();
        dairytextview.setText(notetext);

        tagAdapter = new TagAdapter(TagDetail.this);
        tagdetailListView.setAdapter(tagAdapter);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TagDetail.this.finish();
            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

        private final class TagDetailHolder {
            public TextView tagItemLeftView;
                public TextView totaltagid;
                public TextView tagid;
                public TextView time;
            }

        private class TagAdapter extends BaseAdapter {
                private LayoutInflater TagInflater;

                public TagAdapter(Context context) {
                    this.TagInflater = LayoutInflater.from(context);
                }

                @Override
                public int getCount() {
                    return arrayList.size();  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public Object getItem(int position) {
                    return arrayList.get(position);  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public long getItemId(int position) {
                    return 0;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TagDetailHolder holder = null;
                    if (convertView == null) {

                        holder = new TagDetailHolder();

                        convertView = TagInflater.inflate(R.layout.tagitem, null);
                        holder.tagid = (TextView) convertView.findViewById(R.id.tagid);
                        holder.time = (TextView) convertView.findViewById(R.id.time);
                        holder.tagItemLeftView = (TextView)convertView.findViewById(R.id.tagItemLeftView);
                        convertView.setTag(holder);

                    } else {

                        holder = (TagDetailHolder) convertView.getTag();
                    }
                    String time = arrayList.get(position).get((Object) "time").toString();
                    String tag = arrayList.get(position).get((Object) "tag").toString();
                    String totaltagname = JudgeMent(arrayList.get(position).get("event_id").toString());
                    tag = totaltagname +"   "+ tag;
                    holder.time.setText(time);
                    holder.tagid.setText(tag);

                    holder.tagItemLeftView.setBackgroundColor(Color.parseColor(JudgeColor(arrayList.get(position).get("event_id").toString())));

                    return convertView;  //To change body of implemented methods use File | Settings | File Templates.
                }
            }
    private String JudgeMent(String event_id) {
        Log.d("event_id", event_id);
        if (event_id.equals("0")) {
            return "学习";
        } else if (event_id.equals("1")) {
            return "工作";
        } else if (event_id.equals("2")) {
            return "休息";
        } else if (event_id.equals("3")) {
            return "娱乐";
        }
        return "";
    }
    private String JudgeColor(String event_id) {
        Log.d("event_id", event_id);
        if (event_id.equals("0")) {
            return "#ff6666";
        } else if (event_id.equals("1")) {
            return "#99cc00";
        } else if (event_id.equals("2")) {
            return "#0066cc";
        } else if (event_id.equals("3")) {
            return "#ff6666";
        }
        return "";
    }
}
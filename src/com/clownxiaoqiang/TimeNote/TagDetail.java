package com.clownxiaoqiang.TimeNote;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.clownxiaoqiang.TimeNote.fragment.EditTextFragment;
import com.clownxiaoqiang.TimeNote.fragment.TextViewFragment;
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

    private Button backbutton, editbutton;
    private ArrayList<Map<String, Object>> arrayList, arrayList_note;
    private SQlManager sQlManager, updateManager;
    private ListView tagdetailListView;
    private TagAdapter tagAdapter;
    private String date_to_search, notetext;
    private FragmentTransaction fragmentTransaction, fragmentTransaction_e;
    private FragmentManager fragmentManager, fragmentManager_e;
    private TextViewFragment textViewFragment;
    private EditTextFragment editTextFragment;
    private boolean editStatus;
    private String date, diary;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tagdetail);

        init();

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TagDetail.this.finish();
            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editStatus) {
                    editbutton.setText("保存");
                    initFragment(fragmentManager_e, fragmentTransaction_e, editTextFragment);
                    editStatus = true;
                } else {
                    initFragment(fragmentManager, fragmentTransaction, textViewFragment);
                    editbutton.setText("编辑");
                    Intent intent1 = getIntent();
                    date = intent1.getStringExtra("date");
                    diary = editTextFragment.getText();
                    if (diary.isEmpty()) {
                        diary = "还没有写笔记";
                    }
                    updateManager = new SQlManager(TagDetail.this);
                    updateManager.updatenote(diary, date);
                    sQlManager = new SQlManager(TagDetail.this);
                    arrayList = sQlManager.queryTag(date_to_search);
                    Log.d("arraylist", arrayList.toString());
                    arrayList_note = sQlManager.query(date_to_search);

                    notetext = arrayList_note.get(0).get((Object) "note").toString();

                    editTextFragment = new EditTextFragment(notetext, TagDetail.this);
                    textViewFragment = new TextViewFragment(notetext, TagDetail.this);

                    initFragment(fragmentManager, fragmentTransaction, textViewFragment);


                    editStatus = false;
                }


            }
        });

    }

    private void init() {
        backbutton = (Button) findViewById(R.id.back);
        editbutton = (Button) findViewById(R.id.edit);
        tagdetailListView = (ListView) findViewById(R.id.tagdetailListView);

        Intent intent = getIntent();
        date_to_search = intent.getStringExtra("datetext");

        arrayList = new ArrayList<Map<String, Object>>();
        arrayList_note = new ArrayList<Map<String, Object>>();

        sQlManager = new SQlManager(TagDetail.this);
        arrayList = sQlManager.queryTag(date_to_search);
        arrayList_note = sQlManager.query(date_to_search);

        notetext = arrayList_note.get(0).get((Object) "note").toString();

        editTextFragment = new EditTextFragment(notetext, TagDetail.this);
        textViewFragment = new TextViewFragment(notetext, TagDetail.this);

        initFragment(fragmentManager, fragmentTransaction, textViewFragment);

        tagAdapter = new TagAdapter(TagDetail.this);
        tagdetailListView.setAdapter(tagAdapter);

        editStatus = false;
    }

    //初始化
    private void initFragment(FragmentManager fm, FragmentTransaction ft, Fragment fragment) {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.tagdetailfragment, fragment);
        ft.commit();
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
                holder.tagItemLeftView = (TextView) convertView.findViewById(R.id.tagItemLeftView);
                convertView.setTag(holder);

            } else {

                holder = (TagDetailHolder) convertView.getTag();
            }
            String time = arrayList.get(position).get((Object) "time").toString();
            String tag = arrayList.get(position).get((Object) "tag").toString();
            String totaltagname = JudgeMent(arrayList.get(position).get("event_id").toString());
            tag = totaltagname + "   " + tag;
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
            return "#fed100";
        }
        return "";
    }
}
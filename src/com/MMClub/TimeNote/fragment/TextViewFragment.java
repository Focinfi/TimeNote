package com.MMClub.TimeNote.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.MMClub.TimeNote.R;

/**
 * Created with IntelliJ IDEA.
 * Author: Dai Zhi Qiang
 * Date: 13-10-1
 * Time: 上午11:16
 * 显示笔记Fragment
 */
public class TextViewFragment extends Fragment {

    private TextView textView;
    private String note;
    private Activity activity;


    public TextViewFragment(String note, Context context) {
        this.note = note;
        activity = (Activity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = (TextView) activity.findViewById(R.id.diarytextview);
        textView.setText(note);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_view, container, false);
    }
}

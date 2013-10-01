package com.clownxiaoqiang.TimeNote.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.clownxiaoqiang.TimeNote.R;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-10-1
 * Time: 上午11:16
 * To change this template use File | Settings | File Templates.
 */
public class TextViewFragment extends Fragment {

    private TextView textView;
    private String note;
    private Activity activity;


    public TextViewFragment(String note,Context context) {
        this.note = note;
        activity = (Activity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        textView = (TextView)activity.findViewById(R.id.diarytextview);
        textView.setText(note);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.textviewfragment, container, false);    //To change body of overridden methods use File | Settings | File Templates.
    }
}

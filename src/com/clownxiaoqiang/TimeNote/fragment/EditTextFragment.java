package com.clownxiaoqiang.TimeNote.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.clownxiaoqiang.TimeNote.R;

/**
 * Created with IntelliJ IDEA.
 * User: z
 * Date: 13-10-1
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
 */
public class EditTextFragment extends Fragment {

    private String note,diary;
    private EditText editText;
    private Activity activity;
    private int diaryLength;

    public EditTextFragment(String note, Context context) {
        this.note = note;
        activity = (Activity) context;
        editText = (EditText) activity.findViewById(R.id.edittext);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        initEditText();
        editText.setText(note);
    }

    public String getText() {
        initEditText();
        diary = editText.getText().toString();
        diaryLength = diary.length();
        diary = diary.substring(3, diaryLength);
        return diary;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edittextfragment, container, false);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void initEditText(){
        editText = (EditText) activity.findViewById(R.id.edittext);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }
}

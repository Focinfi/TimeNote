package com.clownxiaoqiang.TimeNote.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.clownxiaoqiang.TimeNote.R;

/**
 * Created with IntelliJ IDEA.
 * Author: Dai Zhi Qiang
 * Date: 13-10-1
 * Time: 上午11:30
 * 编辑Fragment
 */
public class EditTextFragment extends Fragment {

    private String note, diary;
    private EditText editText;
    private Activity activity;
    private int diaryLength, EditEnd, EditStart;
    ;

    public EditTextFragment(String note, Context context) {
        this.note = note;
        activity = (Activity) context;
        editText = (EditText) activity.findViewById(R.id.edittext);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEditText();
        editText.setText(note);
    }

    public String getText() {
        initEditText();
        diary = editText.getText().toString();
        return diary;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edittextfragment, container, false);
    }

    private void initEditText() {
        editText = (EditText) activity.findViewById(R.id.edittext);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }
}

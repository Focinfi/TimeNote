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
 * User: z
 * Date: 13-10-1
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
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
        super.onActivityCreated(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        initEditText();
        editText.setText(note);
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                EditEnd = editText.getSelectionEnd();
//                EditStart = editText.getSelectionStart();
//                //   editText.removeTextChangedListener(this);
//                if (calculateLength(editable.toString()) <= 2) {
//                    Log.d("edittext", editText.getText().toString());
//                    editText.setEnabled(false);
//                    editText.setText(editText.getText().toString() + "：");
//                    editText.setEnabled(true);
//                    editText.setSelection(editText.length());
//                    Toast.makeText(activity, "别删除我。。。。", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    public String getText() {
        initEditText();
        diary = editText.getText().toString();
        return diary;
    }

    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edittextfragment, container, false);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void initEditText() {
        editText = (EditText) activity.findViewById(R.id.edittext);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }
}

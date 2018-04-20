package com.todo.ui.feature.addedittask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.todo.R;
import com.todo.ui.base.BaseDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPriorityDialog extends BaseDialog {

    public static final String TAG = "SelectPriorityDialog";

    private SelectPriorityDialogCallback callback;

    /********* Static Methods  ********/

    public static SelectPriorityDialog newInstance(SelectPriorityDialogCallback callback) {
        Bundle args = new Bundle();
        SelectPriorityDialog fragment = new SelectPriorityDialog();
        fragment.setArguments(args);
        fragment.setSelectPriorityDialogCallback(callback);
        return fragment;
    }


    /********* Lifecycle Methods ********/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_priority_dialog, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /********* Butterknife Binded Methods  ********/

    @OnClick({R.id.select_priority_linear_layout_priority_low,
            R.id.select_priority_linear_layout_priority_normal,
            R.id.select_priority_linear_layout_priority_high,
            R.id.select_priority_linear_layout_priority_crcucial})
    public void onPrioritySelected(LinearLayout linearLayout) {
        int priority = Integer.valueOf(linearLayout.getTag().toString());
        callback.onPrioritySelected(priority);
        dismiss();
    }

    @OnClick(R.id.select_priority_button_cancel)
    public void onButtonCancelClick() {
        dismiss();
    }

    /********* Member Methods  ********/

    public void setSelectPriorityDialogCallback(SelectPriorityDialogCallback callback) {
        this.callback = callback;
    }

    public interface SelectPriorityDialogCallback {

        void onPrioritySelected(int priority);
    }

}

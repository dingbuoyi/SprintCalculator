package com.dingbuoyi.sprintcalculator;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.dingbuoyi.sprintcalculator.model.SprintSchedule;
import com.dingbuoyi.sprintcalculator.utils.Constants;

import java.util.ArrayList;

public class SprintScheduleActivity extends AppCompatActivity {

    private ArrayList<SprintSchedule> sprintScheduleList;
    private Toolbar toolbar;
    private TextView sprintContentTextView;
    private FloatingActionButton fab;
    private int sprintNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_schedule);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.sprint_schedule);
        sprintContentTextView = findViewById(R.id.sprintContentTextView);
        sprintScheduleList = (ArrayList<SprintSchedule>) getIntent().getSerializableExtra(ActivityExtras.SPRINT_SCHEDULE_LIST);
        sprintNumber = getIntent().getIntExtra(ActivityExtras.SPRINT_NUMBER, 1);
        if (sprintScheduleList != null && !sprintScheduleList.isEmpty()) {
            sprintContentTextView.setText(getSprintScheduleContent(sprintScheduleList));
        }
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 得到剪贴板管理器
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(sprintContentTextView.getText().toString().trim());
                Toast.makeText(SprintScheduleActivity.this, getString(R.string.copy_sprint_content_success), Toast.LENGTH_LONG).show();
            }
        });
    }

    private CharSequence getSprintScheduleContent(ArrayList<SprintSchedule> sprintScheduleList) {
        StringBuilder sprintScheduleContent = new StringBuilder();
        for (int i = 0; i < sprintScheduleList.size(); i++) {
            SprintSchedule sprintSchedule = sprintScheduleList.get(i);
            sprintScheduleContent.append(getString(R.string.sprint) + "  " + (sprintNumber + i)).append(Constants.SEPARATOR).append(Constants.SEPARATOR);
            sprintScheduleContent.append(sprintSchedule.getFormatString(SprintScheduleActivity.this));
        }
        return sprintScheduleContent;
    }
}

package com.dingbuoyi.sprintcalculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.dingbuoyi.sprintcalculator.model.SprintSchedule;

import java.util.ArrayList;

public class SprintScheduleActivity extends AppCompatActivity {

    private ArrayList<SprintSchedule> sprintScheduleList;
    private Toolbar toolbar;
    private TextView sprintContentTextView;
    private String separator;
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
            separator = System.getProperty("line.separator");
            sprintContentTextView.setText(getSprintScheduleContent(sprintScheduleList));
        }
    }

    private CharSequence getSprintScheduleContent(ArrayList<SprintSchedule> sprintScheduleList) {
        StringBuilder sprintScheduleContent = new StringBuilder();
        for (int i = 0; i < sprintScheduleList.size(); i++) {
            SprintSchedule sprintSchedule = sprintScheduleList.get(i);
            sprintScheduleContent.append("Sprint " + (sprintNumber + i)).append(separator);
            sprintScheduleContent.append(getString(R.string.start_date)).append(" ").append(sprintSchedule.getStartDate()).append(separator);
            sprintScheduleContent.append(getString(R.string.end_date)).append(" ").append(sprintSchedule.getEndDate()).append(separator);
            sprintScheduleContent.append(getString(R.string.total_days)).append(" ").append(sprintSchedule.getTotalDays()).append(separator).append(separator).append(separator);
        }
        return sprintScheduleContent;
    }
}

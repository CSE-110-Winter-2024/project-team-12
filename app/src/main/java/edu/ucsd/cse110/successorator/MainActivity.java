package edu.ucsd.cse110.successorator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.text.DateFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.list.dialog.CreateTaskDialogFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding view;
    private boolean isShowingCreateTask = true;
    int daysToAdd = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);

        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        Button timeskipButton = findViewById(R.id.timeskipButton);
        timeskipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daysToAdd = daysToAdd+1;
                showTime(daysToAdd);
            }
        });

        showTime(0);
      
        view.createTaskButton.setOnClickListener(v-> {
            var dialogFragment= CreateTaskDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(),"CreateCardDialogFragment");
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void showTime(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysToAdd);
        Date date = calendar.getTime();

        String dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(date);
        String timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);

        TextView dateTextView = findViewById(R.id.date);
        TextView timeTextView = findViewById(R.id.time);
        dateTextView.setText(dateFormat);
        timeTextView.setText(timeFormat);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
/*
    private void swapFragments() {
        getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, StudyFragment.newInstance())
                    .commit();
        }
        isShowingStudy = !isShowingStudy;
    }*/
}

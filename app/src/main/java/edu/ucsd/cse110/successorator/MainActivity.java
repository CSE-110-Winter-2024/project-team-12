package edu.ucsd.cse110.successorator;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.ui.list.dialog.CreateTaskDialogFragment;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.MainViewModel;


// Represents the running state of the app (its primary activity)
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding view;

    private boolean isShowingCreateTask = true;
    public boolean daysAdded = false;

    Calendar calendar = Calendar.getInstance();


    private MainViewModel activityModel;

    // Called when the app (MainActivity) is starting
    // if the activity is being re-init after previously shut down, savedInstanceState contains
    // previous data
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Creates from savedInstanceState and sets app title
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);

        var modelOwner=this;
        var modelFactory= ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider=new ViewModelProvider(modelOwner,modelFactory);
        this.activityModel=modelProvider.get(MainViewModel.class);
        
      // Sets view to the inflated binding of activity_main.xml
        this.view = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(view.getRoot());

        Button timeskipButton = findViewById(R.id.timeskipButton);
        timeskipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daysAdded = true;
                showTime(daysAdded);
            }
        });

        showTime(daysAdded);
        checkForDayChange();

        // createTaskButton in view listens for click
        // if clicked, initiates a CreateTaskDialogFragment instance
        view.createTaskButton.setOnClickListener(v-> {
            var dialogFragment= CreateTaskDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(),"CreateCardDialogFragment");
        });

    }

    // Creates the options menu for app from files placed in app/res/menu package
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    // Defines behavior of menu options item once selected (calls to AppCompatActivity
    // implementation)
    @Override
    protected void onResume() {
        super.onResume();
        showTime(daysAdded);
        checkForDayChange();
    }

    private void saveLastKnownDay(long epochDay) {
        SharedPreferences sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("LastKnownEpochDay", epochDay);
        editor.apply();
    }

    private long getLastKnownDay() {
        SharedPreferences sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return sharedPref.getLong("LastKnownEpochDay", -1);
    }

    private void checkForDayChange() {
        long currentEpochDay = calendar.getTimeInMillis() / (24 * 60 * 60 * 1000);

        if (currentEpochDay != getLastKnownDay()) {
            ArrayList<Integer> temp = Task.getDoneToday();
            for (int taskId : temp) {
                activityModel.remove(taskId);
            }
            Task.clearDoneToday();
            saveLastKnownDay(currentEpochDay);
        }
    }

    public void showTime(boolean daysAdded) {
        if(!daysAdded) {
            calendar.add(Calendar.DATE, 0);
        }else{
            calendar.add(Calendar.DATE, 1);
        }
        Date date = calendar.getTime();

        String dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(date);
        String timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        
      // Sets TextView for Time incrementer
        TextView dateTextView = findViewById(R.id.date);
        TextView timeTextView = findViewById(R.id.time);
        checkForDayChange();
        dateTextView.setText(dateFormat);
        timeTextView.setText(timeFormat);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}

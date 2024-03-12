package edu.ucsd.cse110.successorator;

import static android.app.PendingIntent.getActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.lib.domain.Tag;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.ui.list.FragmentDropdownSelectListener;
import edu.ucsd.cse110.successorator.ui.list.TaskListFragment;
import edu.ucsd.cse110.successorator.ui.list.dialog.CreateTaskDialogFragment;
import edu.ucsd.cse110.successorator.lib.domain.Task;


// Represents the running state of the app (its primary activity)
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding view;

    private boolean isShowingCreateTask = true;
    public int daysAdded = 0;
    private boolean inMain = true;
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
                daysAdded = daysAdded + 1;
                showTime(daysAdded);
                setStartingText();
                checkForDayChange();
            }
        });

        showTime(daysAdded);
        checkForDayChange();

        // createTaskButton in view listens for click
        // if clicked, initiates a CreateTaskDialogFragment instance
        view.createTaskButton.setOnClickListener(v-> {
            var dialogFragment= CreateTaskDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(),"CreateCardDialogFragment");
            setStartingText();
        });

        setStartingText();

        // ui change for the spinner view
        Spinner spinnerView = findViewById(R.id.spinner_view);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.view, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerView.setAdapter(adapter);
        TextView TaskType = findViewById(R.id.TasksType);
        TaskType.setText("Choose Tasks");
        spinnerView.setOnItemSelectedListener(new FragmentDropdownSelectListener(this, pos -> {
            switch (pos) {
                // this is for filtering for different task types
                case 0:
                    // this is for the main list with all the tasks
                    TaskType.setText("All Tasks");
                    showTime(0);
                    inMain = true;
                    return TaskListFragment.newInstance(null, true);
                case 1:
                    TaskType.setText("Today's Tasks");
                    showTime(0);
                    inMain = false;
                    return TaskListFragment.newInstance(LocalDate.now(), false);
                case 2:
                    TaskType.setText("Tomorrow's Tasks");
                    showTime(1);
                    inMain = false;
                    return TaskListFragment.newInstance(LocalDate.now().plusDays(1), false);
                case 3:
                    TaskType.setText("Recurring Tasks");
                    showTime(0);
                    inMain = false;
                    return TaskListFragment.newInstance(LocalDate.now(), false);
                case 4:
                    TaskType.setText("Pending Tasks");
                    showTime(0);
                    inMain = false;
                    return TaskListFragment.newInstance(null, false);
                default:
                    TaskType.setText("All Tasks");
                    showTime(0);
                    inMain = true;
                    return TaskListFragment.newInstance(null, true);
            }
        }));

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] itemsArray = new String[5];
        itemsArray[0] = "No Mode Selected";
        itemsArray[1] = "Home";
        itemsArray[2] = "Work";
        itemsArray[3] = "School";
        itemsArray[4] = "Errand";
        ArrayAdapter<String> adapter_Two = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemsArray);
        spinner.setAdapter(adapter_Two);
        spinner.setOnItemSelectedListener(new FragmentDropdownSelectListener(this, pos -> {
            LocalDate temp = TaskListFragment.filterDate;
            System.out.println(temp);
            switch (pos) {
                // shows the different choices for filtering for different task contexts
                case 0:
                    TaskListFragment.filterType = null;
                    return TaskListFragment.newInstance(temp, inMain);
                case 1:
                    TaskListFragment.filterType = Tag.fromChar('h');
                    return TaskListFragment.newInstance(temp, inMain);
                case 2:
                    TaskListFragment.filterType = Tag.fromChar('w');
                    return TaskListFragment.newInstance(temp, inMain);
                case 3:
                    TaskListFragment.filterType = Tag.fromChar('s');
                    return TaskListFragment.newInstance(temp, inMain);
                case 4:
                    TaskListFragment.filterType = Tag.fromChar('e');
                    return TaskListFragment.newInstance(temp, inMain);
                default:
                    TaskListFragment.filterType = null;
                    return TaskListFragment.newInstance(temp, inMain);
            }
        }));

    }

    // Creates the options menu for app from files placed in app/res/menu package
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public void setStartingText(){
        TextView noTasksText = findViewById(R.id.noTasks);
        TaskRepository temp = activityModel.getTaskRepository();
        temp.findAll().observe(tasks -> {
            if (tasks == null || tasks.size() == 0){
                noTasksText.setText("No goals for the Day.  Click the + at the upper right to enter your Most Important Thing.");
            }else{
                noTasksText.setText("");
            }
        });
    }


    // Defines behavior of menu options item once selected (calls to AppCompatActivity
    // implementation)
    @Override
    protected void onResume() {
        super.onResume();
        LocalDate today = LocalDate.now();
        TaskRepository temp = activityModel.getTaskRepository();
        /*temp.findAll().observe(tasks -> {
            for (Task task : tasks) {
                if (task.getDate() != today && task.isDone()) {
                    //activityModel.remove(task.getId());
                }
            }
        });*/
        checkForDayChange();
        daysAdded = 0;
        showTime(daysAdded);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkForDayChange();
        daysAdded = 0;
        showTime(daysAdded);
    }

    private void saveLastKnownDay(LocalDate day) {
        SharedPreferences sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("LastKnownEpochDay", day.toEpochDay());
        editor.apply();
    }

    private LocalDate getLastKnownDay() {
        SharedPreferences sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return LocalDate.ofEpochDay(sharedPref.getLong("LastKnownEpochDay", -1));
    }

    private void checkForDayChange() {
        LocalDate today = LocalDate.now();
        // for when the app starts again
        ArrayList<Task>doneList = new ArrayList<>();
        List<Task> taskRepo = activityModel.getTaskRepository().findAll().getValue();
        if (taskRepo != null) {
            for (Task task : taskRepo) {
                if (task.getDate() != today && !task.isDone()) {
                    int id = task.getId();
                    String text = task.getText();
                    boolean isDone = task.isDone();
                    int sortOrder = task.getSortOrder();
                    activityModel.remove(task.getId());
                    Tag tag = task.getTag();
                    doneList.add(new Task(id, text, isDone, sortOrder, today, tag));
                } else if (task.getDate() != today && task.isDone()) {
                    activityModel.remove(task.getId());
                }
            }
        }
        if(doneList != null) {
            for (Task task : doneList) {
                activityModel.prepend(task);
            }
        }

        // for the timeskip
        if (today != getLastKnownDay()) {
            ArrayList<Integer> temp = Task.getDoneToday();
            for (int taskId : temp) {
                activityModel.remove(taskId);
            }
            Task.clearDoneToday();
            setStartingText();
            saveLastKnownDay(today);
        }
        setStartingText();
    }

    public void showTime(int daysAdded) {
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAdded);

        Date date = calendar.getTime();

        String dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(date);
        String timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);

      // Sets TextView for Time incrementer
        TextView dateTextView = findViewById(R.id.date);
        TextView timeTextView = findViewById(R.id.time);
        checkForDayChange();
        dateTextView.setText(dateFormat);
        timeTextView.setText(timeFormat);
        setStartingText();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}

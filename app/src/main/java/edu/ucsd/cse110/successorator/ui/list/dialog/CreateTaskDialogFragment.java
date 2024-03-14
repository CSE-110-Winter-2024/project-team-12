package edu.ucsd.cse110.successorator.ui.list.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.IllegalFormatCodePointException;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentCreateMitBinding;
import edu.ucsd.cse110.successorator.lib.domain.Tag;
import edu.ucsd.cse110.successorator.lib.domain.Task;

public class CreateTaskDialogFragment extends DialogFragment{// implements View.OnClickListener{
    private MainViewModel activityModel;
    private FragmentCreateMitBinding view;
    CreateTaskDialogFragment() {

    }
    public static CreateTaskDialogFragment newInstance() {
        var fragment=new CreateTaskDialogFragment();
        Bundle args=new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner=requireActivity();
        var modelFactory= ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider=new ViewModelProvider(modelOwner,modelFactory);
        this.activityModel=modelProvider.get(MainViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view=FragmentCreateMitBinding.inflate(getLayoutInflater());

        var task = new Task(null, null,false,-1, LocalDate.now(),Tag.HOME, 0); // Not sure if setting the default tag to home is okay

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("New Task")
                .setMessage("Please provide the task that has to be completed. ")
                .setView(view.getRoot())
                .create();

        // Set OnClickListener to the pre-existing save button
        Button saveButton = view.saveButton;
        saveButton.setOnClickListener(v -> onPositiveButtonClick(alertDialog, task));

        ImageView hButton = view.hButton;
        hButton.setOnClickListener(v -> onTagHClick(alertDialog, task));

        ImageView wButton = view.wButton;
        wButton.setOnClickListener(v -> onTagWClick(alertDialog, task));

        ImageView sButton = view.sButton;
        sButton.setOnClickListener(v -> onTagSClick(alertDialog, task));

        ImageView eButton = view.eButton;
        eButton.setOnClickListener(v -> onTagEClick(alertDialog, task));

        return alertDialog;
    }

    private void onPositiveButtonClick(DialogInterface dialog, Task task){
        var taskText = view.editTaskText.getText().toString();
        task.setText(taskText);

        //Make more prepends, prepends to different views
        activityModel.prepend(task);

        // Options for a task
        if(view.dailyButton.isChecked()){
            task.setRecurring(1);
        } else if (view.weeklyButton.isChecked()){
            task.setRecurring(2);
        } else if (view.monthlyButton.isChecked()) {
            task.setRecurring(3);
            //should open calendar
        } else if (view.yearlyButton.isChecked()) {
            task.setRecurring(4);
            //should open calendar
        } else if (view.oneTimeButton.isChecked()) {
            task.setRecurring(0);
            //not sure what to do
        } else {
            throw new IllegalStateException("No radio button is checked. ");
        }

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }

    private void onTagHClick(DialogInterface dialog, Task task) {
        //Tags for task
        task.setTag(Tag.HOME);
    }

    private void onTagSClick(DialogInterface dialog, Task task) {
        //Tags for task
        task.setTag(Tag.SCHOOl);
    }

    private void onTagWClick(DialogInterface dialog, Task task) {
        //Tags for task
        task.setTag(Tag.WORK);
    }

    private void onTagEClick(DialogInterface dialog, Task task) {
        //Tags for task
        task.setTag(Tag.ERRANDS);
    }
}

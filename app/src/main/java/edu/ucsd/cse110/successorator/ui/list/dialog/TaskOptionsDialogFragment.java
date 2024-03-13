package edu.ucsd.cse110.successorator.ui.list.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentTaskOptionsBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;

public class TaskOptionsDialogFragment extends DialogFragment {
    private static final String ARG_TASK_ID="task_id";
    private int taskId;
    private MainViewModel activityModel;
    private FragmentTaskOptionsBinding view;

    TaskOptionsDialogFragment() {

    }

    public static TaskOptionsDialogFragment newInstance(int taskId) {
        var fragment = new TaskOptionsDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID,taskId);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.taskId=requireArguments().getInt(ARG_TASK_ID);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentTaskOptionsBinding.inflate(getLayoutInflater());

        return new AlertDialog.Builder(getActivity())
                .setTitle("Task Options")
                .setMessage("Select what you want to do with the task.")
                .setView(view.getRoot())
                .setPositiveButton("Apply", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) { // Need to add task param

        if(view.moveToTodayButton.isChecked()) {
            // should be pending view and not activity model
            activityModel.remove(taskId);
            // Added to the Today View

        } else if (view.moveToTomorrowButton.isChecked()) {
            // should be pending view and not activity model
            activityModel.remove(taskId);
            // Added to the Tomorrow View
        } else if (view.completedButton.isChecked()){
            // Updates the current task to isDone
        } else if (view.deleteButton.isChecked()){
            activityModel.remove(taskId);
        } else {
            throw new IllegalStateException("No radio button is checked.");
        }
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}

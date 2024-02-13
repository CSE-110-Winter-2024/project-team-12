package edu.ucsd.cse110.successorator.ui.list.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainViewModel;
// import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateTaskBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;

public class ConfirmDeleteTaskDialogFragment extends DialogFragment {
    private static final String ARG_TASK_ID="task_id";
    private int taskId;
    private MainViewModel activityModel;

    ConfirmDeleteTaskDialogFragment() {

    }
    public static ConfirmDeleteTaskDialogFragment newInstance(int taskId) {
        var fragment=new ConfirmDeleteTaskDialogFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_TASK_ID,taskId);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.taskId=requireArguments().getInt(ARG_TASK_ID);

        var modelOwner=requireActivity();
        var modelFactory= ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider=new ViewModelProvider(modelOwner,modelFactory);
        this.activityModel=modelProvider.get(MainViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this Task? ")
                .setPositiveButton("Delete", this::onPositiveButtonClick)
                .setNegativeButton("Cancel",this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        activityModel.remove(taskId);
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}


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
    public static final String ARG_TASK_ID="task_id";
    public int taskId;
    public MainViewModel activityModel;

    ConfirmDeleteTaskDialogFragment() {

    }
    // creates a new bundle and sets the arguments of the task onto the fragment with the taskId
    public static ConfirmDeleteTaskDialogFragment newInstance(int taskId) {
        var fragment=new ConfirmDeleteTaskDialogFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_TASK_ID,taskId);
        fragment.setArguments(args);
        return fragment;
    }
}


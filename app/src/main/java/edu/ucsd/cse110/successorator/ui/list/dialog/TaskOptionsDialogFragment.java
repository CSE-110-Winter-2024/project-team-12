package edu.ucsd.cse110.successorator.ui.list.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentTaskOptionsBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;

public class TaskOptionsDialogFragment extends DialogFragment {
    private MainViewModel activityModel;
    private FragmentTaskOptionsBinding view;

    TaskOptionsDialogFragment() {

    }

    public static TaskOptionsDialogFragment newInstance() {
        var fragment = new TaskOptionsDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentTaskOptionsBinding.inflate(getLayoutInflater());

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Task Options")
                .setMessage("Select what you want to do with the task.")
                .setView(view.getRoot())
                .setPositiveButton("Apply", this::onPositiveButtonClick, task)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which, Task task) {

        activityModel.prepend(task);

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}

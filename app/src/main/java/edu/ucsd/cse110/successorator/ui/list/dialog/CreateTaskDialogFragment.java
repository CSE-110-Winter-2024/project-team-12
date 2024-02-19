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
import edu.ucsd.cse110.successorator.databinding.FragmentCreateMitBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;

public class CreateTaskDialogFragment extends DialogFragment{
    public MainViewModel activityModel;
    public FragmentCreateMitBinding view;

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

    public void onPositiveButtonClick(DialogInterface dialog, int which) {
        var taskText=view.editTextText.getText().toString();
        var task = new Task(null, taskText,false,-1);
        activityModel.prepend(task);
        dialog.dismiss();
    }
}

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
        return new AlertDialog.Builder(getActivity())
                .setTitle("New Task")
                .setMessage("Please provide the task that has to be completed. ")
                .setView(view.getRoot())
                .setPositiveButton("Create", this::onPositiveButtonClick)
                .setNegativeButton("Cancel",this::onNegativeButtonClick)
                .create();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var taskText=view.editTextText.getText().toString();

        var task = new Task(null, taskText,-1);
        activityModel.append(task);

        /*if(view.appendRadioBtn.isChecked()){
            activityModel.append(task);
        }
        else {
            throw new IllegalStateException("No radio button is checked. ");
        }*/
        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}

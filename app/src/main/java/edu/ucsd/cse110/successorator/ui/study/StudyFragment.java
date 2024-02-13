package edu.ucsd.cse110.successorator.ui.study;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentCreateMitBinding;

public class StudyFragment extends Fragment{
    private MainViewModel activityModel;
    private FragmentCreateMitBinding view;

    public StudyFragment() {
        // Required empty public constructor
    }

    public static StudyFragment newInstance() {
        StudyFragment fragment = new StudyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = FragmentCreateMitBinding.inflate(inflater, container, false);
        setupMvp();
        return view.getRoot();
    }

    private void setupMvp(){
        activityModel.getDisplayedText().observe(text -> view.editTextText.setText(text));
    }
}

package edu.ucsd.cse110.successorator.ui.list;

import android.view.View;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.time.LocalDate;
import java.util.function.Function;

import edu.ucsd.cse110.successorator.R;

public class FragmentDropdownSelectListener implements AdapterView.OnItemSelectedListener {
    private final FragmentActivity activity;
    private final Function<Integer, Fragment> positionToFragment;

    public FragmentDropdownSelectListener(FragmentActivity activity, Function<Integer, Fragment> positionToFragment) {
        this.activity = activity;
        this.positionToFragment = positionToFragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        navigateToFragment(positionToFragment.apply(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

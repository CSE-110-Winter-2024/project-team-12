package edu.ucsd.cse110.successorator.ui.list.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        LocalDate now = LocalDate.now();
        if(now.getMonthValue()==month){
            if(now.getDayOfMonth()==day){
                // set the task to today
                return;
            } else if (now.getDayOfMonth()== day+1){
                return;
            }
            return;
        }
    }
}
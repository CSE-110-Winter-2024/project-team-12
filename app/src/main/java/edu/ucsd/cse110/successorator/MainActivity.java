package edu.ucsd.cse110.successorator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        //view.placeholderText.setText(R.string.hello_world)
        setContentView(view.getRoot());
        // getting date
        Calendar c = Calendar.getInstance();
        String dateString = c.getTime().toString();
        dateString = dateString.substring(0,11);
        // showing it on the textview
        setContentView(R.layout.activity_main);
        TextView dateView = (TextView)findViewById(R.id.date);
        dateView.setText(dateString);
    }
}

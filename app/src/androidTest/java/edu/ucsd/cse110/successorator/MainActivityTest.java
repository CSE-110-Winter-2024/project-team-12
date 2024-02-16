package edu.ucsd.cse110.successorator;

import static androidx.test.core.app.ActivityScenario.launch;

import static junit.framework.TestCase.assertEquals;

import android.content.res.Resources;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Test
    /* Function tests if the date is displayed as we made the date display through MainActivity.*/
    public void TestDisplaysDate() {
        try (var scenario = ActivityScenario.launch(MainActivity.class)) {
            // Observe the scenario's lifecycle to wait until the activity is created.
            scenario.onActivity(activity -> {
                TextView dateView = activity.findViewById(R.id.date);

                // make the expected date string that's the same as what we made before for the date
                Calendar c = Calendar.getInstance();
                String expectedDateString = c.getTime().toString().substring(0, 11);

                // get the actual text from the date TextView
                String actual = dateView.getText().toString();

                // compate
                assertEquals(expectedDateString, actual);
            });
            scenario.moveToState(Lifecycle.State.STARTED);
        }
    }
}
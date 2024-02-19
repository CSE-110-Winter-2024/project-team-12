package edu.ucsd.cse110.successorator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.containsString;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Test
    public void dateAndTimeDisplayTest() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Assumes date and time are displayed in TextViews with id date and time respectively
            // Hard to test dynamic content like the current date/time without stubbing or mocking the system clock
            onView(withId(R.id.date)).check(matches(withText(containsString("Expected Date Format"))));
            onView(withId(R.id.time)).check(matches(withText(containsString("Expected Time Format"))));
        }
    }
    @Test
    public void createTaskButtonTriggersDialogTest() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            onView(withId(R.id.createTaskButton)).perform(click());
            onView(withId(R.id.task_text)).check(matches(withText(containsString("Expected Task Text"))));
        }
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onCreateOptionsMenu() {
    }

    @Test
    public void onOptionsItemSelected() {
    }
}


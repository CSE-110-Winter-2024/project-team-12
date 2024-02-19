package edu.ucsd.cse110.successorator;

import org.junit.Test;
import org.junit.runner.RunWith;
import android.view.MenuItem;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;

import static org.junit.Assert.assertNotNull;


@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class MainActivityTest {

    @Test
    public void testOnCreate() {
        // Create the MainActivity
        MainActivity mainActivity = new MainActivity();

        assertNotNull(mainActivity);
    }

    @Test
    public void testOnCreateOptionsMenu() {
        // Create the MainActivity and tester menuItem
        MainActivity mainActivity = new MainActivity();
        MenuItem menuItem = new RoboMenuItem(R.menu.action_bar);
        // Verify that the menu is not null
        assertNotNull(menuItem);
    }
}
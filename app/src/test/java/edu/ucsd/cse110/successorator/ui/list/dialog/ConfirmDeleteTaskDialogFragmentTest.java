package edu.ucsd.cse110.successorator.ui.list.dialog;

import static androidx.fragment.app.testing.FragmentScenario.launchInContainer;

import junit.framework.TestCase;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import static edu.ucsd.cse110.successorator.ui.list.dialog.ConfirmDeleteTaskDialogFragment.ARG_TASK_ID;

import android.app.AlertDialog;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.test.core.app.ActivityScenario;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;


import org.junit.Test;
import org.robolectric.shadows.ShadowAlertDialog;

import edu.ucsd.cse110.successorator.MainViewModel;

// class tests the confirm delete task dialog fragment
@RunWith(RobolectricTestRunner.class)
public class ConfirmDeleteTaskDialogFragmentTest extends TestCase {

    // creating mock objects for the tests
    @Mock
    ViewModelProvider.Factory mockFactory;
    @Mock
    MainViewModel mockMainViewModel;

    // before the other functions are run, initializing mocks for junit
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // testing the creation of a new Instance for the delete task dialog fragment
    @Test
    public void testNewInstance() {
        int taskId = 123;
        ConfirmDeleteTaskDialogFragment fragment = ConfirmDeleteTaskDialogFragment.newInstance(taskId);
        // making sure it's not null so it was created
        assertNotNull(fragment);
        Bundle args = fragment.getArguments();
        assertNotNull(args);
        // making sure it has the task_id and it's right
        assertTrue(args.containsKey(ARG_TASK_ID));
        assertEquals(taskId, args.getInt(ARG_TASK_ID));
    }
}
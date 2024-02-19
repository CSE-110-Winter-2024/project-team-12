package edu.ucsd.cse110.successorator.ui.list.dialog;

import junit.framework.TestCase;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static edu.ucsd.cse110.successorator.ui.list.dialog.ConfirmDeleteTaskDialogFragment.ARG_TASK_ID;

import android.os.Bundle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import org.junit.Test;

// class tests the confirm delete task dialog fragment
@RunWith(RobolectricTestRunner.class)
public class ConfirmDeleteTaskDialogFragmentTest extends TestCase {
    @Test
    public void testNewInstance() {
        int taskId = 123;
        ConfirmDeleteTaskDialogFragment fragment = ConfirmDeleteTaskDialogFragment.newInstance(taskId);

        assertNotNull(fragment);

        Bundle args = fragment.getArguments();
        assertNotNull(args);

        assertTrue(args.containsKey(ARG_TASK_ID));
        assertEquals(taskId, args.getInt(ARG_TASK_ID));
    }
    @Test
    public void testOnCreate() {
    }
    @Test
    public void testOnCreateDialog() {
    }
}
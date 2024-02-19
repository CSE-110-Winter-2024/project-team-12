package edu.ucsd.cse110.successorator.ui.list;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.lib.domain.Task;

public class TaskListAdapterTest extends TestCase {

    Context mockContext;
    ViewGroup mockParent;
    Consumer<Task> mockOnTaskClicked;

    private TaskListAdapter adapter;
    private List<Task> tasks;
    private Context context;

    public void setUp() {
        tasks = Arrays.asList(
                new Task(1, "Task 1", false, 0),
                new Task(2, "Task 2", true, 1)
        );
        adapter = new TaskListAdapter(mockContext, tasks, mockOnTaskClicked);
    }


    public void testGetView_BindingData() {

    }


    public void testOnClick_CallsConsumerWithCorrectTask() {

    }

}
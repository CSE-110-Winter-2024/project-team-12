package edu.ucsd.cse110.successorator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.lifecycle.MutableLiveData;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.util.Observer;


public class MainViewModelTest extends TestCase {

    private TaskRepository taskRepository;
    private Observer<List<Task>> observer;
    private MainViewModel viewModel;

    /**
     * Sets up testing environment
     * Initializes mocks and the MainViewModel instance
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new MainViewModel(taskRepository);
    }

    /**
     * Tests MainViewModel.save()
     */
    @Test
    public void testSave() {
        Task task = new Task(1, "Task 1", false, 0);
        viewModel.save(task);
        verify(taskRepository).save(task);
    }

    /**
     * Tests MainViewModel.deleteDone()
     */
    @Test
    public void testDeleteDone() {
        viewModel.deleteDone();
        verify(taskRepository).deleteDone();
    }

    /**
     * Tests MainViewModel.append() with dummy task
     */
    @Test
    public void testAppend() {
        Task task = new Task(2, "Task 2", false, 1);
        viewModel.append(task);
        verify(taskRepository).append(task);
    }

    /**
     * Tests MainViewModel.prepend() with dummy task
     */
    @Test
    public void testPrepend() {
        Task task = new Task(3, "Task 3", false, 2);
        viewModel.prepend(task);
        verify(taskRepository).prepend(task);
    }

    /**
     * Tests MainViewModel.remove() with dummy task
     */
    @Test
    public void testRemove() {
        int taskId = 1;
        viewModel.remove(taskId);
        verify(taskRepository).remove(taskId);
    }

    /**
     * Tests that the list of ordered tasks is correctly updated and observed
     */
    @Test
    public void testOrderedTasksUpdates() {
        List<Task> tasks = Arrays.asList(
                new Task(1, "Task 1", false, 1),
                new Task(2, "Task 2", true, 2)
        );

        when(taskRepository.findAll()).thenReturn(new MutableLiveData<>(tasks));
        viewModel.getOrderedTasks().observe(observer);

        verify(observer).onChanged(any());
    }

}
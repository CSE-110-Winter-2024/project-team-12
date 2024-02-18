package edu.ucsd.cse110.successorator;

import static org.mockito.Mockito.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModelTest {

    @Mock
    private TaskRepository taskRepository;

    private MainViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        viewModel = new MainViewModel(taskRepository);
    }

    @Test
    public void getOrderedTasks() {
        List<Task> tasks = Arrays.asList(new Task(1, "Task 1", false, 1), new Task(2, "Task 2", false, 2));
        when(taskRepository.findAll()).thenReturn((Subject<List<Task>>) tasks);

        Observer<List<Task>> observer = mock(Observer.class);
        viewModel.getOrderedTasks().observe((edu.ucsd.cse110.successorator.lib.util.Observer<List<Task>>) observer);

        // Verify the observer's onChanged method is called with the expected tasks
        verify(observer).onChanged(tasks);
    }

    @Test
    public void save() {
        Task task = new Task(3, "Task 3", false, 3);
        viewModel.save(task);
        verify(taskRepository).save(task);
    }

    @Test
    public void deleteDone() {
        viewModel.deleteDone();
        verify(taskRepository).deleteDone();
    }

    @Test
    public void append() {
        Task task = new Task(4, "Task 4", false, 4);
        viewModel.append(task);
        verify(taskRepository).append(task);
    }

    @Test
    public void prepend() {
        Task task = new Task(5, "Task 5", false, 5);
        viewModel.prepend(task);
        verify(taskRepository).prepend(task);
    }

    @Test
    public void remove() {
        viewModel.remove(1);
        verify(taskRepository).remove(1);
    }
}

package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.SimpleTaskRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
public class SimpleTaskRepositoryTest {

    private SimpleTaskRepository repository;
    private InMemoryDataSource dataSource;

    @Before
    public void setUp() {
        dataSource = new InMemoryDataSource();
        repository = new SimpleTaskRepository(dataSource);
    }

    @Test
    public void testSaveAndFindTask() {
        Task task = new Task(1, "Test Task", false, 0);
        repository.save(task);
        assertNotNull("Task should be retrievable", repository.find(task.getId()).getValue());
    }

    @Test
    public void testFindAllTasks() {
        repository.save(new Task(1, "Task 1", false, 0));
        repository.save(new Task(2, "Task 2", false, 1));
        List<Task> tasks = repository.findAll().getValue();
        assertEquals("There should be two tasks", 2, tasks.size());
    }

    @Test
    public void testRemoveTask() {
        Task task = new Task(1, "Task to remove", false, 0);
        repository.save(task);
        repository.remove(task.getId());
        assertNull("Task should be removed", repository.find(task.getId()).getValue());
    }

    @Test
    public void testAppendAndPrependTask() {
        Task appendTask = new Task(3, "Appended Task", false, 2);
        Task prependTask = new Task(4, "Prepended Task", false, -1);
        repository.append(appendTask);
        repository.prepend(prependTask);
        assertEquals("Prepended task should have the lowest sort order", Integer.valueOf(-1), repository.find(prependTask.getId()).getValue().getSortOrder());
        assertTrue("Appended task should have the highest sort order", repository.find(appendTask.getId()).getValue().getSortOrder() > 0);
    }
}
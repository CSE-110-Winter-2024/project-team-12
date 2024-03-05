package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TaskTest {

    private Task task;

    @Before
    public void setUp() throws Exception {
        task = new Task(1, "Initial task", false, 1);
    }

    @Test
    public void getId() {
        assertEquals(Integer.valueOf(1), task.getId());
    }

    @Test
    public void withId() {
        Task newTask = task.withId(2);
        assertEquals(Integer.valueOf(2), newTask.getId());
    }

    @Test
    public void getText() {
        assertEquals("Initial task", task.getText());
    }

    @Test
    public void withText() {
        Task newTask = task.withText("Updated task");
        assertEquals("Updated task", newTask.getText());
    }

    @Test
    public void isDone() {
        assertFalse(task.isDone());
    }

    @Test
    public void withDone() {
        Task newTask = task.withDone(true);
        assertTrue(newTask.isDone());
    }

    @Test
    public void getSortOrder() {
        assertEquals(Integer.valueOf(1), task.getSortOrder());
    }

    @Test
    public void withSortOrder() {
        Task newTask = task.withSortOrder(2);
        assertEquals(Integer.valueOf(2), newTask.getSortOrder());
    }

    @Test
    public void testEquals() {
        Task task2 = new Task(1, "Initial task", false, 1);
        assertEquals(task, task2);
    }

    @Test
    public void testHashCode() {
        Task task2 = new Task(1, "Initial task", false, 1);
        assertEquals(task.hashCode(), task2.hashCode());
    }
}
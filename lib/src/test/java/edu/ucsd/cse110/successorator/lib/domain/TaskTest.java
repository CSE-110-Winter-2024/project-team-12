package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class TaskTest {

    private Task task;
    private Task todayTask;
    //private Task tomorrowTask;
    private Task homeTask;
    private Task recurringTask;
    private Task workTask;
    private Task pendingTask;
    //private Task errandTask;

    @Before
    public void setUp() throws Exception {
        task = new Task(1, "Initial task", false, 1, null, null,0);
        todayTask = new Task(2, "Initial task", false, 2, LocalDate.now(), null,0);
        homeTask = new Task(3, "Initial task", false, 4, null, Tag.HOME,0);
        //tomorrowTask = new Task(4, "Initial task", false, 3, LocalDate.from(LocalDate.now()).plusDays(1), null);
        pendingTask = new Task(5, "Initial task", false, 3, null, null,0);
        recurringTask = new Task(4, "Initial task", false, 3, null, null,1);
        //workTask = new Task(7, "Initial task", false, 5, null, Tag.WORK);
        //schoolTask = new Task(8, "Initial task", false, 6, null, Tag.SCHOOl);
        //errandTask = new Task(9, "Initial task", false, 7, null, Tag.ERRANDS);
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
    public void setText() {
        task.setText("New description");
        assertEquals(task.getText(), "New description");
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
    public void getDate() {
        assertEquals(todayTask.getDate(), LocalDate.now());
    }

    @Test
    public void withDate() {
        Task newTask = task.withDate(LocalDate.now());
        assertEquals(newTask.getDate(), LocalDate.now());
    }

    @Test
    public void getDoneToday() {
        ArrayList<Integer> arrList = new ArrayList<>();
        arrList.add(task.getId());
        task.withDone(true);
        assertEquals(Task.getDoneToday(), arrList);
    }

    @Test
    public void setDoneToday() {
        ArrayList<Integer> arrList = new ArrayList<>();
        arrList.add(task.getId());
        Task.setDoneToday(arrList);
        assertEquals(Task.getDoneToday(), arrList);
    }

    @Test
    public void clearDoneToday() {
        Task.clearDoneToday();
        assertEquals(Task.getDoneToday(), new ArrayList<Integer>());
    }

    @Test
    public void getSortOrder() {
        assertEquals(Integer.valueOf(1), task.getSortOrder());
    }


    @Test
    public void getTag() {
        assertEquals(homeTask.getTag(), Tag.HOME);
    }
    @Test
    public void setTag() {
        task.setTag(Tag.ERRANDS);
        assertEquals(task.getTag(), Tag.ERRANDS);
    }

    @Test
    public void withSortOrder() {
        Task newTask = task.withSortOrder(2);
        assertEquals(Integer.valueOf(2), newTask.getSortOrder());
    }

    @Test
    public void testEquals() {
        Task task2 = new Task(1, "Initial task", false, 1,null,null,0);
        assertEquals(task, task2);
    }

    @Test
    public void testHashCode() {
        Task task2 = new Task(1, "Initial task", false, 1, null, null,0);
        assertEquals(task.hashCode(), task2.hashCode());
    }

    @Test
    public void testIsRecurring() {
        assertEquals(recurringTask.isRecurring(),1);
        assertEquals(todayTask.isRecurring(),0);
    }
    @Test
    public void testSetRecurring() {
        assertEquals(task.isRecurring(), 0);
        task.setRecurring(1);
        assertEquals(task.isRecurring(), 1);
    }

    @Test
    public void testIsPending() {
        assertEquals(pendingTask.isRecurring(),0);
        assertNull(pendingTask.getDate());
    }
    @Test
    public void testSetPending() {
        assertEquals(task.isRecurring(), 0);
        task=task.withDate(null);
        assertNull(task.getDate());
    }
}
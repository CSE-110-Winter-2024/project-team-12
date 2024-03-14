package edu.ucsd.cse110.successorator.lib.domain;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTaskRepository;
import edu.ucsd.cse110.successorator.lib.domain.Tag;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class InMemoryDataSourceTest extends TestCase {

    InMemoryDataSource imd = new InMemoryDataSource();
    SimpleTaskRepository str = new SimpleTaskRepository(imd);
    Task homeTomorrowTask = new Task(1,"Clean room",false,1, LocalDate.from(LocalDate.now()).plusDays(1), Tag.HOME);
    Task schoolTodayTask = new Task(2,"Submit reflection",false,2, LocalDate.now(), Tag.SCHOOl);
    Task errandsTodayTask = new Task(3,"Buy groceries",false,3, LocalDate.from(LocalDate.now()), Tag.ERRANDS);
    Task workTomorrowTask = new Task(4,"Submit PTO request to boss",false,4, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))), Tag.WORK);

    private final List<Task> tasks
            = new ArrayList<>();

    /* inserting tasks into InMemoryDataSource out of order ... */
    @Before
    public void setUp() {

        tasks.add(homeTomorrowTask);
        tasks.add(schoolTodayTask);
        tasks.add(errandsTodayTask);
    }

    @Test
    public void testPutTask() {
        assertEquals(imd.getTasks().size(),0);
        imd.putTask(workTomorrowTask);
        assertEquals(imd.getTasks().size(),1);
        assertEquals(imd.getTask(workTomorrowTask.getId()),workTomorrowTask);
        Task testTask1 = new Task(4, "test", false, 5, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))),Tag.HOME);
        imd.putTask(testTask1);
        assertEquals(imd.getTask(4).getSortOrder(), Integer.valueOf(5));
        assertEquals(imd.getTasks().size(),1);
        assertEquals(imd.getTask(4),testTask1);
    }

    @Test
    public void testPutTasks() {
        assertEquals(imd.getTasks().size(), 0);
        imd.putTasks(tasks);
        assertEquals(imd.getTasks(), tasks);
        assertEquals(imd.getTasks().size(), tasks.size());
    }

    @Test
    public void testRemoveTask() {
        imd.putTasks((List<Task>) tasks);
        assertEquals(3, imd.getTasks().size());
        imd.removeTask(homeTomorrowTask.getId());
        assertEquals(2, imd.getTasks().size());
        assertEquals(imd.getTask(2).getSortOrder(),Integer.valueOf(1));
        assertEquals(imd.getTask(3).getSortOrder(), Integer.valueOf(2));
    }

    @Test
    public void testShiftSortOrders() {
        tasks.add(workTomorrowTask);
        imd.putTasks((List<Task>) tasks);
        imd.shiftSortOrders(1, 3, 4);
        assertEquals(imd.getTask(1).getSortOrder(), Integer.valueOf(5));
        assertEquals(imd.getTask(2).getSortOrder(), Integer.valueOf(6));
        assertEquals(imd.getTask(3).getSortOrder(), Integer.valueOf(7));
        assertEquals(imd.getTask(4).getSortOrder(), Integer.valueOf(4));
    }
}


package edu.ucsd.cse110.successorator.lib.domain;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTaskRepository;
import edu.ucsd.cse110.successorator.lib.domain.Tag;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class SimpleTaskRepositoryTest extends TestCase{

    InMemoryDataSource imd = new InMemoryDataSource();
    SimpleTaskRepository str = new SimpleTaskRepository(imd);

    Task homeTomorrowTask = new Task(1,"Clean room",false,1, LocalDate.from(LocalDate.now()).plusDays(1), Tag.HOME,0);
    Task schoolTodayTask = new Task(2,"Submit reflection",false,2, LocalDate.now(), Tag.SCHOOl,0);
    Task errandsTodayTask = new Task(3,"Buy groceries",false,3, LocalDate.from(LocalDate.now()), Tag.ERRANDS,0);
    Task workTomorrowTask = new Task(4,"Submit PTO request to boss",false,4, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))), Tag.WORK,0);

    /* inserting tasks into InMemoryDataSource out of order ... */
    @Before
    public void setUp(){
        imd.putTask(homeTomorrowTask);
        imd.putTask(errandsTodayTask);
        imd.putTask(schoolTodayTask);
    }
    @Test
    public void testFind() {
        int id = 1; //id of homeTomorrowTask
        assertEquals(str.find(id).getClass(), SimpleSubject.class);
        assertEquals(str.find(id).getValue(),homeTomorrowTask);
    }

    @Test
    public void testFindAll() {
        List<Task> tasks = imd.getTasks();
        assertEquals(str.findAll().getClass(), SimpleSubject.class);
        assertEquals(str.findAll().getValue(), tasks);
    }

    @Test
    public void testSave() {
        str.save(workTomorrowTask);
        assertEquals(imd.getTask(imd.getMaxSortOrder()),workTomorrowTask);
    }

    @Test
    public void testAppend() {
        assertEquals(3, imd.getTasks().size());
        Task testTask1 = new Task(4, "test", false, 5, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))),Tag.HOME,0);
        str.append(testTask1);
        assertEquals(4, imd.getTasks().size());
        assertEquals(imd.getTask(4).getTag(),Tag.HOME);
        assertEquals(imd.getTask(4).getDate(),LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))));
        assertEquals(imd.getTask(4).getText(),"test");
        assertEquals(imd.getTask(4).getSortOrder(), Integer.valueOf(4));
        assertFalse(imd.getTask(4).isDone());
    }

    @Test
    public void testPrepend() {
        assertEquals(3, imd.getTasks().size());
        Task testTask1 = new Task(5, "test", false, 5, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))),Tag.HOME,0);
        str.prepend(testTask1);
        assertEquals(4, imd.getTasks().size());
        assertEquals(imd.getTask(5).getTag(),Tag.HOME);
        assertEquals(imd.getTask(5).getDate(),LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))));
        assertEquals(imd.getTask(5).getText(),"test");
        assertEquals(imd.getTask(5).getSortOrder(), Integer.valueOf(1));
        assertFalse(imd.getTask(5).isDone());
    }

    @Test
    public void testRemove(){
        assertEquals(3, imd.getTasks().size());
        Task testTask1 = imd.getTask(3);
        str.remove(3);
        assertEquals(2, imd.getTasks().size());
        assertTrue(imd.getTask(3) == null);
    }

}

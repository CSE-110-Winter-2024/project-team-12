package edu.ucsd.cse110.successorator;

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

public class MainViewModelTest extends TestCase {

    //we will need to update this, along with TaskTest, once we
    //implement Pending and Recurring date types
    InMemoryDataSource imd = new InMemoryDataSource();
    SimpleTaskRepository str = new SimpleTaskRepository(imd);
    MainViewModel mvm = new MainViewModel(str);
    Task homeTomorrowTask = new Task(1,"Clean room",false,1, LocalDate.from(LocalDate.now()).plusDays(1), Tag.HOME);
    Task schoolTodayTask = new Task(2,"Submit reflection",false,2, LocalDate.now(), Tag.SCHOOl);
    Task errandsTodayTask = new Task(3,"Buy groceries",false,3, LocalDate.from(LocalDate.now()), Tag.ERRANDS);
     Task workTomorrowTask = new Task(4,"Submit PTO request to boss",false,4, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))), Tag.WORK);

    /* inserting tasks into InMemoryDataSource out of order ... */
    @Before
    public void setUp(){
        imd.putTask(homeTomorrowTask);
        imd.putTask(errandsTodayTask);
        imd.putTask(schoolTodayTask);
    }
    @Test
    public void testGetOrderedTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(homeTomorrowTask);
        tasks.add(schoolTodayTask);
        tasks.add(errandsTodayTask);

        SimpleSubject<List<Task>> orderedTasks = new SimpleSubject<List<Task>>();
        orderedTasks.setValue(tasks);

        SimpleSubject<List<Task>> test= (SimpleSubject<List<Task>>) mvm.getOrderedTasks();
        assertEquals(mvm.getOrderedTasks().getValue(), orderedTasks.getValue());
    }

    @Test
    public void testGetTaskRepository() {
        assertEquals(str, mvm.getTaskRepository());
    }

    @Test
    public void testSave() {
        mvm.save(workTomorrowTask);
        assertEquals(imd.getTask(imd.getMaxSortOrder()),workTomorrowTask);
    }

    @Test
    public void testAppend() {
        assertEquals(3, imd.getTasks().size());
        Task testTask1 = new Task(4, "test", false, 5, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))),Tag.HOME);
        mvm.append(testTask1);
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
        Task testTask1 = new Task(5, "test", false, 5, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))),Tag.HOME);
        mvm.prepend(testTask1);
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
        mvm.remove(3);
        assertEquals(2, imd.getTasks().size());
        assertTrue(imd.getTask(3) == null);
    }

    public void testMoveDeletedTask(){
        Task testTask1 = new Task(4,"Test",false,4, LocalDate.from(LocalDate.now()), Tag.WORK);
        mvm.append(testTask1);
        mvm.remove(4);
        testTask1.withDate(LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))));
        assertTrue(imd.getTask(4)==null);
    }

}
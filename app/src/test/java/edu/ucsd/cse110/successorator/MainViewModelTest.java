package edu.ucsd.cse110.successorator;

import androidx.annotation.Nullable;

import junit.framework.TestCase;

import org.junit.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTaskRepository;
import edu.ucsd.cse110.successorator.lib.domain.Tag;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Observer;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

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
        imd.putTask(workTomorrowTask);
        imd.putTask(schoolTodayTask);
    }
    @Test
    public void testGetOrderedTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(homeTomorrowTask);
        tasks.add(schoolTodayTask);
        tasks.add(errandsTodayTask);
        tasks.add(workTomorrowTask);

        SimpleSubject<List<Task>> orderedTasks = new SimpleSubject<List<Task>>();
        orderedTasks.setValue(tasks);

        assertEquals(mvm.getOrderedTasks(), orderedTasks);
    }

    public void testGetTaskRepository() {
        assertEquals(str, mvm.getTaskRepository());
    }

    /*
    public void testSave() {

    }

    @Test
    public void testAppend() {
        assertEquals(2, imd.getTasks().size());
        mvm.append(testTask1);
        assertEquals(3, imd.getTasks().size());
        //getOrderedTasks returns Subject<List<Task>>
    }

    public void testPrepend() {
    }


    public void scenario1(){
        // create task
        // then delete it
        // then move to tmrw
        //i.e. specifically
        // then create a task
        // assert some behavior
    }*/
}
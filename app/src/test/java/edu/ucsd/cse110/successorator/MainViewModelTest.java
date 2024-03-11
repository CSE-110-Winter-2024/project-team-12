package edu.ucsd.cse110.successorator;

import junit.framework.TestCase;

import org.junit.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.SimpleTaskRepository;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;

public class MainViewModelTest extends TestCase {
/*
    InMemoryDataSource imd = new InMemoryDataSource();


    SimpleTaskRepository str = new SimpleTaskRepository(imd);
    MainViewModel mvm = new MainViewModel(str);
    Task testTask1 = new Task(22300,"Clear nostrils",false,3, 1220227200);
    Task testTask2 = new Task(22301,"Eat my prescription",false,4, 1220227200);


    @Before
    public void setUp(){
        imd.putTask(new Task(12300,"Take out trash",false,1, 1220227200));
        imd.putTask(new Task(12301,"Walk the fish",false,2, 1221436800));
    }
    @Test
    public void testGetOrderedTasks() {
    }

    public void testGetTaskRepository() {
    }

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
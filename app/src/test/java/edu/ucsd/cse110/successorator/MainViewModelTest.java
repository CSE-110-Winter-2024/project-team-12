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
import java.time.temporal.ChronoUnit;

public class MainViewModelTest extends TestCase {

    //we will need to update this, along with TaskTest, once we
    //implement Pending and Recurring date types
    InMemoryDataSource imd = new InMemoryDataSource();
    SimpleTaskRepository str = new SimpleTaskRepository(imd);
    MainViewModel mvm = new MainViewModel(str);
    Task homeTomorrowTask = new Task(1,"Clean room",false,1, LocalDate.from(LocalDate.now()).plusDays(1), Tag.HOME,0);
    Task schoolTodayTask = new Task(2,"Submit reflection",false,2, LocalDate.now(), Tag.SCHOOl,0);
    Task errandsTodayTask = new Task(3,"Buy groceries",false,3, LocalDate.from(LocalDate.now()), Tag.ERRANDS,0);
     Task workTomorrowTask = new Task(4,"Submit PTO request to boss",false,4, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))), Tag.WORK,0);

    /* inserting tasks into InMemoryDataSource out of order ... */
    @Before
    public void setUp() {
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

        SimpleSubject<List<Task>> test = (SimpleSubject<List<Task>>) mvm.getOrderedTasks();
        assertEquals(mvm.getOrderedTasks().getValue(), orderedTasks.getValue());
    }

    @Test
    public void testGetTaskRepository() {
        assertEquals(str, mvm.getTaskRepository());
    }

    @Test
    public void testSave() {
        mvm.save(workTomorrowTask);
        assertEquals(imd.getTask(imd.getMaxSortOrder()), workTomorrowTask);
    }

    @Test
    public void testAppend() {
        assertEquals(3, imd.getTasks().size());
        Task testTask1 = new Task(4, "test", false, 5, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))),Tag.HOME,0);
        mvm.append(testTask1);
        assertEquals(4, imd.getTasks().size());
        assertEquals(imd.getTask(4).getTag(), Tag.HOME);
        assertEquals(imd.getTask(4).getDate(), LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))));
        assertEquals(imd.getTask(4).getText(), "test");
        assertEquals(imd.getTask(4).getSortOrder(), Integer.valueOf(4));
        assertFalse(imd.getTask(4).isDone());
    }

    @Test
    public void testPrepend() {
        assertEquals(3, imd.getTasks().size());
        Task testTask1 = new Task(5, "test", false, 5, LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))),Tag.HOME,0);

        mvm.prepend(testTask1);
        assertEquals(4, imd.getTasks().size());
        assertEquals(imd.getTask(5).getTag(), Tag.HOME);
        assertEquals(imd.getTask(5).getDate(), LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))));
        assertEquals(imd.getTask(5).getText(), "test");
        assertEquals(imd.getTask(5).getSortOrder(), Integer.valueOf(1));
        assertFalse(imd.getTask(5).isDone());
    }

    @Test
    public void testRemove() {
        assertEquals(3, imd.getTasks().size());
        Task testTask1 = imd.getTask(3);
        mvm.remove(3);
        assertEquals(2, imd.getTasks().size());
        assertTrue(imd.getTask(3) == null);
    }

    public void testMoveDeletedTask(){
        Task testTask1 = new Task(4,"Test",false,4, LocalDate.from(LocalDate.now()), Tag.WORK,0);
        mvm.append(testTask1);
        mvm.remove(4);
        testTask1.withDate(LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1))));
        assertTrue(imd.getTask(4) == null);
    }

    public void testSeeTasksOfOnlyOneContext() {
        ArrayList<Task> focusedErrandsContext = new ArrayList<>();
        for (Task t : mvm.getOrderedTasks().getValue()) {
            if (t.getTag() == Tag.ERRANDS) {
                focusedErrandsContext.add(t);
            }
        }
        assertEquals(focusedErrandsContext.size(), 1);
        assertEquals(focusedErrandsContext.get(0), errandsTodayTask);
        assertFalse(focusedErrandsContext.stream().findAny().equals(homeTomorrowTask));
        assertFalse(focusedErrandsContext.stream().findAny().equals(schoolTodayTask));
    }

    public void testAddContextForTask() {
        Task testTask1 = new Task(4, "Test", false, 4, LocalDate.from(LocalDate.now()), Tag.WORK, 0);
        assertEquals(testTask1.getTag(), Tag.WORK);
        testTask1.setTag(Tag.HOME);
        assertEquals(testTask1.getTag(), Tag.HOME);
    }

    public void testSeeContextForTasks() {
        ArrayList<Tag> visibleTags = new ArrayList<>();
        Tag t1 = mvm.getOrderedTasks().getValue().get(0).getTag();
        Tag t2 = mvm.getOrderedTasks().getValue().get(1).getTag();
        Tag t3 = mvm.getOrderedTasks().getValue().get(2).getTag();
        visibleTags.add(t1);
        visibleTags.add(t2);
        visibleTags.add(t3);
        assertNotNull(visibleTags);
        assertNotSame(t1, t2);
        assertNotSame(t2, t3);
        assertNotSame(t1, t3);
    }

    public void testAddTodayTask() {
        assertEquals(mvm.getOrderedTasks().getValue().size(), 3);
        Task testTask1 = new Task(4, "Test", false, 4, LocalDate.from(LocalDate.now()), Tag.WORK, 0);
        mvm.append(testTask1);
        assertTrue(ChronoUnit.DAYS.between(mvm.getOrderedTasks().getValue().get(3).getDate(), LocalDate.now()) == 0);
        assertEquals(mvm.getOrderedTasks().getValue().size(), 4);
    }

    public void testAddTomorrowTask() {
        assertEquals(mvm.getOrderedTasks().getValue().size(), 3);
        Task testTask1 = new Task(4, "Test", false, 4, LocalDate.from(LocalDate.now().plusDays(1)), Tag.WORK, 0);
        mvm.append(testTask1);
        assertTrue(ChronoUnit.DAYS.between(mvm.getOrderedTasks().getValue().get(3).getDate(), LocalDate.from(LocalDate.now().plusDays(1))) == 0);
        assertEquals(mvm.getOrderedTasks().getValue().size(), 4);
    }

    @Test
    public void testToggleBetweenDateViews() {
        mvm.append(new Task(4, "Test", false, 4, LocalDate.from(LocalDate.now().plusDays(1)), Tag.WORK, 1));
        mvm.append(new Task(5, "Test", false, 5, null, Tag.WORK, 0));
        ArrayList<Task> todayTasks = new ArrayList<>();
        ArrayList<Task> tomorrowTasks = new ArrayList<>();
        ArrayList<Task> pendingTasks = new ArrayList<>();
        ArrayList<Task> recurringTasks = new ArrayList<>();

        for (Task t : mvm.getOrderedTasks().getValue()) {
            if (t.getDate() == LocalDate.now()) {
                todayTasks.add(t);
            } else if (t.getDate() == LocalDate.from(LocalDate.from(LocalDate.now().plusDays(1)))) {
                tomorrowTasks.add(t);
            }
            /*TODO: implement if cases for Pending and Recurring dates once US 7 and 8 done.
            Assert each arrayList equals the expected amount of tasks for each view
             */
            else if (t.getDate() == null) {
                pendingTasks.add(t);
            } else if (t.isRecurring() == 1) {
                recurringTasks.add(t);
            }
        }
        assertEquals(todayTasks.size(),0);
        assertEquals(tomorrowTasks.size(),0);
        assertEquals(pendingTasks.size(),1);
        assertEquals(recurringTasks.size(),1);
    }

    public void testSeeTasksOfOnlyOneContext() {
        ArrayList<Task> focusedErrandsContext = new ArrayList<>();
        for(Task t : mvm.getOrderedTasks().getValue()) {
            if(t.getTag()==Tag.ERRANDS){
                focusedErrandsContext.add(t);
            }
        }
        assertEquals(focusedErrandsContext.size(), 1);
        assertEquals(focusedErrandsContext.get(0), errandsTodayTask);
        assertFalse(focusedErrandsContext.stream().findAny().equals(homeTomorrowTask));
        assertFalse(focusedErrandsContext.stream().findAny().equals(schoolTodayTask));
    }
  
    public void testAddContextForTask() {
        Task testTask1 = new Task(4,"Test",false,4, LocalDate.from(LocalDate.now()), Tag.WORK);
        assertEquals(testTask1.getTag(),Tag.WORK);
        testTask1.setTag(Tag.HOME);
        assertEquals(testTask1.getTag(),Tag.HOME);
    }

    public void testSeeContextForTasks() {
        ArrayList<Tag> visibleTags = new ArrayList<>();
        Tag t1 = mvm.getOrderedTasks().getValue().get(0).getTag();
        Tag t2 = mvm.getOrderedTasks().getValue().get(1).getTag();
        Tag t3 = mvm.getOrderedTasks().getValue().get(2).getTag();
        visibleTags.add(t1); visibleTags.add(t2); visibleTags.add(t3);
        assertNotNull(visibleTags);
        assertNotSame(t1, t2); assertNotSame(t2,t3); assertNotSame(t1,t3);
    }

    public void testAddTodayTask() {
        assertEquals(mvm.getOrderedTasks().getValue().size(), 3);
        Task testTask1 = new Task(4,"Test",false,4, LocalDate.from(LocalDate.now()), Tag.WORK);
        mvm.append(testTask1);
        assertTrue(ChronoUnit.DAYS.between(mvm.getOrderedTasks().getValue().get(3).getDate(),LocalDate.now()) == 0);
        assertEquals(mvm.getOrderedTasks().getValue().size(),4);
    }

    public void testAddTomorrowTask() {
        assertEquals(mvm.getOrderedTasks().getValue().size(), 3);
        Task testTask1 = new Task(4,"Test",false,4, LocalDate.from(LocalDate.now().plusDays(1)), Tag.WORK);
        mvm.append(testTask1);
        assertTrue(ChronoUnit.DAYS.between(mvm.getOrderedTasks().getValue().get(3).getDate(),LocalDate.from(LocalDate.now().plusDays(1))) == 0);
        assertEquals(mvm.getOrderedTasks().getValue().size(), 4);
    }

}

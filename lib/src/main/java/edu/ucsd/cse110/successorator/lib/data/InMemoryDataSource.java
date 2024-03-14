package edu.ucsd.cse110.successorator.lib.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

/**
 * Class used as a sort of "database" of decks and tasks that exist. This
 * will be replaced with a real database in the future, but can also be used
 * for testing.
 */
public class InMemoryDataSource {
    private int nextId = 0;

    private int minSortOrder = Integer.MAX_VALUE;
    private int maxSortOrder = Integer.MIN_VALUE;

    private final Map<Integer, Task> tasks
            = new HashMap<>();
    private final Map<Integer, MutableSubject<Task>> taskSubjects
            = new HashMap<>();
    private final MutableSubject<List<Task>> allTasksSubject
            = new SimpleSubject<>();

    public InMemoryDataSource() {
    }

    public List<Task> getTasks() {
        return List.copyOf(tasks.values());
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subject<Task> getTaskSubject(int id) {
        if (!taskSubjects.containsKey(id)) {
            var subject = new SimpleSubject<Task>();
            subject.setValue(getTask(id));
            taskSubjects.put(id, subject);
        }
        return taskSubjects.get(id);
    }

    public Subject<List<Task>> getAllTasksSubject() {
        return allTasksSubject;
    }

    public int getMinSortOrder() {
        return minSortOrder;
    }

    public int getMaxSortOrder() {
        return maxSortOrder;
    }

    /**
     * Inserts a task the of beginning of the task list. Updates the 
     * value of 'allTasksSubject' with the current list of tasks.
     */
    public void putTask(Task task) {
        var fixedTask = preInsert(task);

        tasks.put(fixedTask.getId(), fixedTask);
        postInsert();
        assertSortOrderConstraints();

        if (taskSubjects.containsKey(fixedTask.getId())) {
            taskSubjects.get(fixedTask.getId()).setValue(fixedTask);
        }
        allTasksSubject.setValue(getTasks());
    }

    /**
     * Inserts a list task at the end of the task list. Updates the 
     * value of 'allTasksSubject' with the current list of tasks.
     */
    public void putTasks(List<Task> taskList) {
        var fixedTasks = taskList.stream()
                .map(this::preInsert)
                .collect(Collectors.toList());

        fixedTasks.forEach(task -> tasks.put(task.getId(), task));
        postInsert();
        assertSortOrderConstraints();

        fixedTasks.forEach(task -> {
            if (taskSubjects.containsKey(task.getId())) {
                taskSubjects.get(task.getId()).setValue(task);
            }
        });
        allTasksSubject.setValue(getTasks());
    }

    /*
     * Removes the task with the associated id. Updates the 
     * value of 'allTasksSubject' with the current list of tasks.
     */
    public void removeTask(int id) {
        var task = tasks.get(id);
        var sortOrder = task.getSortOrder();

        tasks.remove(id);
        shiftSortOrders(sortOrder, maxSortOrder, -1);

        if (taskSubjects.containsKey(id)) {
            taskSubjects.get(id).setValue(null);
        }
        allTasksSubject.setValue(getTasks());
    }

    /**
     * Shifts the provided task to a given amount of times.
     */
    public void shiftSortOrders(int from, int to, int by) {
        var taskList = tasks.values().stream()
                .filter(task -> task.getSortOrder() >= from && task.getSortOrder() <= to)
                .map(task -> task.withSortOrder(task.getSortOrder() + by))
                .collect(Collectors.toList());

        putTasks(taskList);
    }

    /**
     * Private utility method to maintain state of the fake DB: ensures that new
     * tasks inserted have an id, and updates the nextId if necessary.
     */
    private Task preInsert(Task task) {
        var id = task.getId();
        if (id == null) {
            // If the task has no id, give it one.
            task = task.withId(nextId++);
        }
        else if (id > nextId) {
            // If the task has an id, update nextId if necessary to avoid giving out the same
            // one. This is important for when we pre-load tasks like in fromDefault().
            nextId = id + 1;
        }

        return task;
    }

    /**
     * Private utility method to maintain state of the fake DB: ensures that the
     * min and max sort orders are up to date after an insert.
     */
    private void postInsert() {
        // Keep the min and max sort orders up to date.
        minSortOrder = tasks.values().stream()
                .map(Task::getSortOrder)
                .min(Integer::compareTo)
                .orElse(Integer.MAX_VALUE);

        maxSortOrder = tasks.values().stream()
                .map(Task::getSortOrder)
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE);
    }

    /**
     * Safety checks to ensure the sort order constraints are maintained.
     * <p></p>
     * Will throw an AssertionError if any of the constraints are violated,
     * which should never happen. Mostly here to make sure I (Dylan) don't
     * write incorrect code by accident!
     */
    private void assertSortOrderConstraints() {
        // Get all the sort orders...
        var sortOrders = tasks.values().stream()
                .map(Task::getSortOrder)
                .collect(Collectors.toList());

        // Non-negative...
        //assert sortOrders.stream().allMatch(i -> i >= 0);

        // Unique...
        assert sortOrders.size() == sortOrders.stream().distinct().count();

        // Between min and max...
        assert sortOrders.stream().allMatch(i -> i >= minSortOrder);
        assert sortOrders.stream().allMatch(i -> i <= maxSortOrder);
    }
}

package edu.ucsd.cse110.successorator.lib.domain;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import java.util.List;

/* Maintains a coherent collection of tasks that interact with and modify data.InMemoryDataSource
 * by implementing TaskRepository interface's methods */
public class SimpleTaskRepository implements TaskRepository {
    private final InMemoryDataSource dataSource;

    // SimpleTaskRepository object that assigns an InMemoryDataSource object to be the repo's
    // dataSource
    public SimpleTaskRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // returns a Subject<Task> associated with the input id (an observable Task) by calling
    // getTaskSubject query on dataSource
    @Override
    public Subject<Task> find(int id) {
        return dataSource.getTaskSubject(id);
    }

    // returns a Subject<List<Task>> (an observable list of all Tasks in the app) by calling
    // getAllTasksSubject query on dataSource
    @Override
    public Subject<List<Task>> findAll() {
        return dataSource.getAllTasksSubject();
    }

    // saves a Task object by putting it into dataSource
    @Override
    public void save(Task task) {
        dataSource.putTask(task);
    }

    // removes a Task object associated with the input id by calling removeTask query on dataSource
    @Override
    public void remove(int id) {
        dataSource.removeTask(id);
    }

    // appends a Task object in the repository by putting the Task at the end of dataSource
    // (assigns with maxSortOrder + 1)
    @Override
    public void append(Task task) {
        dataSource.putTask(
                task.withSortOrder(dataSource.getMaxSortOrder()+1)
        );
    }

    // prepends a Task object in the repository by putting the Task at the beginning of dataSource
    // (assigns with minSortOrder - 1)
    @Override
    public void prepend(Task task) {
        dataSource.shiftSortOrders(0, dataSource.getMaxSortOrder(), 1);
        dataSource.putTask(
                task.withSortOrder(dataSource.getMinSortOrder()-1)
        );
    }

}


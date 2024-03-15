package edu.ucsd.cse110.successorator.lib.domain;
import edu.ucsd.cse110.successorator.lib.util.Subject;


import java.time.LocalDate;
import java.util.List;

/* Outlines method names for mutating, finding, and saving Tasks in a repository format */
public interface TaskRepository {

    // returns a Subject<Task> associated with the input id (an observable Task)
    Subject<Task> find(int id);

    // returns a Subject<List<Task>> (an observable list of all Tasks in the app)
    Subject<List<Task>> findAll();

    // saves a Task object
    void save(Task task);

    // removes a Task object associated with the input id
    void remove(int id);

    // appends a Task object in the repository
    void append(Task task);


    // prepends a Task object in the repository
    void prepend(Task task);

    //reschedule a Task object in the repository
    void rescheduleTask(int taskId, LocalDate newDate);
}

package edu.ucsd.cse110.successorator.data.db;

// imports
import androidx.lifecycle.Transformations;
import java.util.List;
import java.util.stream.Collectors;
import edu.ucsd.cse110.successorator.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;
import edu.ucsd.cse110.successorator.lib.util.Subject;

/**
 * This class is an implementation of Task Repository. It is tailored to work with the Room
 * database in Android development. It serves as an in-between for the application's data
 * model and underlying room database.  */
public class RoomTaskRepository implements TaskRepository{

    private final TaskDao taskDao;

    /**
     * This function constructs a repository with a specific DAO for task operations.
     * @param taskDao the DAO giving access to database operations
     */
    public RoomTaskRepository(TaskDao taskDao) {
        this.taskDao=taskDao;
    }

    /**
     * This function finds a task by its ID.
     * @param id The ID of the task
     * @return a subject that returns the live data of the task
     */
    @Override
    public Subject<Task> find(int id) {
        var entityLiveData=taskDao.findAsLiveData(id);
        var taskLiveData= Transformations.map(entityLiveData,TaskEntity::toTask);
        return new LiveDataSubjectAdapter<>(taskLiveData);
    }

    /**
     * This function gets all the data from the database.
     * @return the subject returns all the live data from a list of all the tasks
     */
    public Subject<List<Task>> findAll() {
        var entitiesLiveData=taskDao.findAllAsLiveData();
        var tasksLiveData=Transformations.map(entitiesLiveData,entities->{
            return entities.stream()
                    .map(TaskEntity::toTask)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(tasksLiveData);
    }


    /**
     * This function saves a task into the database.
     * @param task the task that needs to be saved
     */
    public void save(Task task) {
        taskDao.insert(TaskEntity.fromTask(task));
    }

    /**
     * This function saves a list of tasks into the database.
     * @param tasks the list of tasks to be saved.
     */
    public void save(List<Task> tasks) {
        var entities=tasks.stream()
                .map(TaskEntity::fromTask)
                .collect(Collectors.toList());
        taskDao.insert(entities);
    }

    /**
     * This function adds a task to the end of the tasks list in the database.
     * @param task the task to append.
     */
    public void append(Task task) {
        taskDao.append(TaskEntity.fromTask(task));
    }

    /**
     * This function adds a task to the beginning of the tasks list in the database.
     * @param task the task to prepend.
     */
    public void prepend(Task task) {
        taskDao.prepend(TaskEntity.fromTask(task));
    }

    /**
     * This function removes a task by its ID from the database.
     * @param id the ID of the task to remove.
     */
    public void remove(int id) {
        taskDao.delete(id);
    }
}


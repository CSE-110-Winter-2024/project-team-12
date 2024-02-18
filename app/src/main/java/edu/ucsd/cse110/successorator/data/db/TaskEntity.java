package edu.ucsd.cse110.successorator.data.db;
// imports
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import edu.ucsd.cse110.successorator.lib.domain.Task;

/**
 * This class defines the data model for the task object in the Room Database.  */
@Entity(tableName = "tasks")
public class TaskEntity {
    // variables
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public Integer id=null;
    @ColumnInfo(name="text")
    public String text;
    @ColumnInfo(name="is_done")
    public boolean isDone;
    @ColumnInfo(name="sort_order")
    public int sortOrder;

    /**
     * This function initializes a TaskEntity object.
     * @param id the task id
     * @param text the task text
     * @param isDone the status of the task - whether or not its done
     * @param sortOrder the sortOrder of the task
     */
    public TaskEntity(Integer id, String text, boolean isDone, int sortOrder)  {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
        this.sortOrder = sortOrder;
    }
    /**
     * This function makes a TaskEntity object from a task
     * @param task this is the task object that the TaskEntity object is made from
     * @return returns the TaskEntity object
     */
    public static TaskEntity fromTask(Task task) {
        return new TaskEntity(task.getId(), task.getText(), task.isDone(), task.getSortOrder());
    }
    /**
     * This function makes a task from a TaskEntity object
     * @return it returns a Task object with the features from the TaskEntity Object
     */
    public Task toTask() {
        return new Task(id, text, isDone, sortOrder);
    }
}
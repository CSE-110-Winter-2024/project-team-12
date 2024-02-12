package edu.ucsd.cse110.successorator.lib.domain;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

//import edu.ucsd.cse110.successorator.lib.util.errors.NotImplementedException;


/*
 * This class is an object that represents a single task
 *
 * Private variables:
 * String taskText - contains the user input for what the task is
 * boolean isDone - whether or not the task has been marked as done
 *
 * Constructor:
 * 1. initializes the String taskText to constructor parameter
 * 2. intializes isDone to false by default;
 */
public class Task implements Serializable{

    private final @Nullable Integer id;

    private String taskText;
    private boolean isDone;

    private Integer sortOrder;

    /*public Task(String taskText){
        this.taskText = taskText;
        this.isDone = false;
    }*/

    public Task(@Nullable Integer id, @NonNull String taskText, int sortOrder) {
        this.id = id;
        this.taskText = taskText;
        this.sortOrder = sortOrder;
    }

    /* Below are getter methods for the taskText
     * both instance and static
     *
     * @return taskText
     */
    public String getTaskText() {
        return taskText;
    }
    public static String getTaskText(Task theTask) {
        return theTask.taskText;
    }
    /* Below are getter methods for whether this task is done or not
     * @return isDone
     */
    public boolean getDoneStatus() {
        return isDone;
    }
    public static boolean getDoneStatus(Task theTask) {
        return theTask.isDone;
    }

    /* Below are setter methods to "mark task as done"
     */
    public void markAsDone(){
        this.isDone = true;
    }
    public static void markAsDone(Task theTask){
        theTask.isDone = true;
    }

    /* Below are setter methods to "mark task as not done"
     */
    public void markAsToDo(){
        this.isDone = false;
    }
    public static void markAsToDo(Task theTask){
        theTask.isDone = false;
    }

    public Task withId(int id) {
        return new Task(id, taskText, sortOrder);
    }

    public Task withSortOrder(int sortOrder) {
        return new Task(id, taskText, sortOrder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskText, task.taskText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskText);
    }



}

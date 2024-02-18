package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;

// Represents a single Task object in the Room database
// Each TaskEntity instance represents a row of data in the
// TaskEntity table

@Entity(tableName = "tasks")
public class TaskEntity {

    // Column for auto-incrementing primary key id to identify specific TaskEntity objects
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public Integer id=null;

    // Column for the TaskEntity text field
    @ColumnInfo(name="text")
    public String text;

    // Column to show whether or not the TaskEntity is marked as done
    @ColumnInfo(name="is_done")
    public boolean isDone;

    // Column with the sort order of TaskEntity within list
    @ColumnInfo(name="sort_order")
    public int sortOrder;




    // TaskEntity constructor
    public TaskEntity(Integer id, String text, boolean isDone, int sortOrder) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
        this.sortOrder = sortOrder;

    }

    // Returns a TaskEntity object from a Task object
    public static TaskEntity fromTask(Task task) {
        return new TaskEntity(task.getId(), task.getText(), task.isDone(), task.getSortOrder());
    }

    // Returns a Task object from a TaskEntity object
    public Task toTask() {
        return new Task(id, text, isDone, sortOrder);
    }
}
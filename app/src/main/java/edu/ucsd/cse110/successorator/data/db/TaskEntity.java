package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.successorator.lib.domain.Task;
import edu.ucsd.cse110.successorator.lib.domain.TaskRepository;


@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public Integer id=null;

    @ColumnInfo(name="text")
    public String text;

    @ColumnInfo(name="is_done")
    public boolean isDone;

    @ColumnInfo(name="sort_order")
    public int sortOrder;

    public TaskEntity(Integer id, String text, boolean isDone, int sortOrder) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
        this.sortOrder = sortOrder;
    }

    public static TaskEntity fromTask(Task task) {
        return new TaskEntity(task.getId(), task.getText(), task.isDone(), task.getSortOrder());
    }

    public Task toTask() {
        return new Task(id, text, isDone, sortOrder);
    }
}
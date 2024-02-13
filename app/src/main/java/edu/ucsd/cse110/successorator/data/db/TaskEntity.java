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

    @ColumnInfo(name="task-text")
    public String taskText;

    @ColumnInfo(name="sort_order")
    public int sortOrder;

    TaskEntity(String taskText, int sortOrder) {
        this.taskText=taskText;
        this.sortOrder=sortOrder;
    }

    public static TaskEntity fromTask(Task task) {
        var taskEntity = new TaskEntity(task.getTaskText(), task.getSortOrder());
        taskEntity.id = task.getId();
        return taskEntity;
    }

    public Task toTask() {
        return new Task(id, taskText, sortOrder);
    }
}
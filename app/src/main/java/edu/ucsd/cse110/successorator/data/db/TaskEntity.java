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

    @ColumnInfo(name="front")
    public String front;

    @ColumnInfo(name="back")
    public String back;

    @ColumnInfo(name="sort_order")
    public int sortOrder;

    TaskEntity(String front,String back, int sortOrder) {
        this.front=front;
        this.back=back;
        this.sortOrder=sortOrder;
    }

    public static TaskEntity fromTask(Task task) {
        var card=new TaskEntity(task.front(), task.back(), task.sortOrder());
        card.id=task.id();
        return card;
    }

    public Task toTask() {
        return new Task(id, front,back,sortOrder);
    }
}

public class TaskEntity {
}

package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import java.util.List;

// This is a Task Data Access Object for SQL queries to be used for TaskEntity
@Dao
public interface TaskDao {

    //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TaskEntity task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<TaskEntity> tasks);

    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskEntity find(int id);

    @Query("SELECT * FROM tasks ORDER BY sort_order")
    List<TaskEntity> findAll();

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<TaskEntity> findAsLiveData(int id);

    @Query("SELECT * FROM tasks ORDER BY sort_order")
    LiveData<List<TaskEntity>> findAllAsLiveData();

    @Query("SELECT COUNT(*) FROM tasks")
    int count();

    @Query("SELECT MIN(sort_order) FROM tasks")
    int getMinSortOrder();

    @Query("SELECT * FROM tasks WHERE isRecurring = :isRecurring")
    int getRecurring(int isRecurring);

    @Query("SELECT MAX(sort_order) FROM tasks")
    int getMaxSortOrder();

    @Query("UPDATE tasks SET sort_order=sort_order + :by " +
            "WHERE sort_order >= :from AND sort_order <= :to")
    void shiftSortOrders(int from, int to, int by);

    @Transaction
    default int append(TaskEntity task) {
        var maxSortOrder = getMaxSortOrder();

        var newTask = task;
        if(task.date!= null) {
            newTask = new TaskEntity(
                    null, task.text, task.isDone, maxSortOrder + 1, task.date, task.tag, task.isRecurring
            );
        } else {
            newTask = new TaskEntity(
                    null, task.text, task.isDone, maxSortOrder + 1, null, task.tag, task.isRecurring
            );
        }
        return Math.toIntExact(insert(newTask));
    }

    @Query("DELETE FROM tasks WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM tasks WHERE is_done = 1")
    void deleteDone();

    @Update
    void update(TaskEntity taskEntity);

    @Transaction
    default int prepend(TaskEntity task) {
        shiftSortOrders(getMinSortOrder(), getMaxSortOrder(),1);
        var newFlashcard=new TaskEntity(
                null, task.text, task.isDone, getMinSortOrder()-1, task.date, task.tag, task.isRecurring
        );
        return Math.toIntExact(insert(newFlashcard));
    }

}
